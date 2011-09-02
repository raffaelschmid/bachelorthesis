package com.trivadis.loganalysis.jrockit.internal.analyzer.memory;

import java.util.Map;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.domain.gc.GarbageCollection;
import com.trivadis.loganalysis.jrockit.domain.gc.OldCollection;
import com.trivadis.loganalysis.jrockit.domain.gc.YoungCollection;
import com.trivadis.loganalysis.jrockit.file.Value;
import com.trivadis.loganalysis.jrockit.internal.analyzer.IModuleProcessor;

public class MemoryLogModuleProcessor implements IModuleProcessor {

	private final JRockitExtractor extractor = new JRockitExtractor();

	public ModuleResult proceed(JRockitJvmRun jvm, String line) {
		ModuleResult retVal = ModuleResult.PROCEED;
		if (extractor.checkDataLine(line)) {
			Map<DataGroups, Value> extraction = extractor.extractDataLine(line);
			GarbageCollection transition = ("oc".equalsIgnoreCase(extraction.get(DataGroups.TYPE1)
					.toString())) ? new OldCollection(extraction.get(
					DataGroups.TOTAL_COLLECTION_TIME).toDouble()) : new YoungCollection(extraction.get(
							DataGroups.TOTAL_COLLECTION_TIME).toDouble());

			State startState = new State(extraction.get(DataGroups.START_TIME).toDouble())
					.memoryUsed(extraction.get(DataGroups.MEMORY_BEFORE).toLong()).transitionStart(
							transition);
			State endState = new State(extraction.get(DataGroups.END_TIME).toDouble()).memoryUsed(
					extraction.get(DataGroups.MEMORY_AFTER).toLong()).transitionEnd(transition);

			jvm.getHeap().addStates(transition, startState, endState);

			retVal = ModuleResult.RETURN;
		}
		return retVal;
	}
}
