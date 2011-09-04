package com.trivadis.loganalysis.jrockit.internal.analyzer.memory;

import java.util.Map;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.core.domain.unit.Size;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.domain.gc.GarbageCollection;
import com.trivadis.loganalysis.jrockit.domain.gc.OldCollection;
import com.trivadis.loganalysis.jrockit.domain.gc.YoungCollection;
import com.trivadis.loganalysis.jrockit.file.Value;
import com.trivadis.loganalysis.jrockit.internal.analyzer.IModuleProcessor;
import com.trivadis.loganalysis.jrockit.internal.analyzer.memory.JRockitExtractor.HeapInfoGroups;

public class MemoryLogModuleProcessor implements IModuleProcessor {

	private final JRockitExtractor extractor = new JRockitExtractor();

	public ModuleResult proceed(JRockitJvmRun jvm, String line) {
		ModuleResult retVal = ModuleResult.PROCEED;
		if (extractor.checkDataLine(line)) {
			Map<DataGroups, Value> extraction = extractor.extractDataLine(line);
			GarbageCollection transition = ("oc".equalsIgnoreCase(extraction.get(DataGroups.TYPE1).toString())) ? new OldCollection(
					extraction.get(DataGroups.TOTAL_COLLECTION_TIME).toDouble()) : new YoungCollection(extraction.get(
					DataGroups.TOTAL_COLLECTION_TIME).toDouble());

			State startState = new State(extraction.get(DataGroups.START_TIME).toDouble()).memoryUsed(
					new Size(extraction.get(DataGroups.MEMORY_BEFORE).toDouble())).transitionStart(transition);
			State endState = new State(extraction.get(DataGroups.END_TIME).toDouble())
					.memoryUsed(new Size(extraction.get(DataGroups.MEMORY_AFTER).toDouble()))
					.memoryCapacity(new Size(extraction.get(DataGroups.HEAP_SIZE_AFTER).toDouble()))
					.transitionEnd(transition);

			jvm.getHeap().addStates(transition, startState, endState);

			retVal = ModuleResult.RETURN;
		} else if (extractor.checkHeapInfo(line)) {
			Map<HeapInfoGroups, Value> heapInfo = extractor.extractHeapInfo(line);
			long initHeapSize = heapInfo.get(HeapInfoGroups.HEAP_SIZE).toLong();
			long maxHeapSize = heapInfo.get(HeapInfoGroups.MAXIMAL_HEAP_SIZE).toLong();
			long initNurserySize = heapInfo.get(HeapInfoGroups.NURSERY_SIZE).toLong();
			long iniTenuredSize = initHeapSize - initNurserySize;

			jvm.getHeap().setStartState(new State(0d).memoryCapacity(new Size(initHeapSize)));
			jvm.getHeap().setMaximumState(new State(0d).memoryCapacity(new Size(maxHeapSize)));
			jvm.getHeap().getNursery().setStartState(new State(0d).memoryCapacity(new Size(initNurserySize)));
			jvm.getHeap().getTenured().setStartState(new State(0d).memoryCapacity(new Size(iniTenuredSize)));

			retVal = ModuleResult.RETURN;
		}
		return retVal;
	}
}
