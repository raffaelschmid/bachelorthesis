/**
 * Copyright (c) 2011 Loganalysis team and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Raffael Schmid - initial API and implementation
 */
package com.trivadis.loganalysis.jrockit.internal.analyzer.memory;

import java.math.BigDecimal;
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

	public ModuleResult process(final JRockitJvmRun jvmRun, final String line) {
		ModuleResult retVal = ModuleResult.PROCEED;
		if (extractor.checkDataLine(line)) {
			final Map<DataGroups, Value> extraction = extractor.extractDataLine(line);
			final BigDecimal duration = extraction.get(DataGroups.TOTAL_COLLECTION_TIME).toBigDecimal();
			final BigDecimal longestPause = extraction.get(DataGroups.TOTAL_SUM_PAUSE).toBigDecimal();
			final BigDecimal sumOfPauses = extraction.get(DataGroups.LONGEST_PAUSE).toBigDecimal();
			final GarbageCollection transition = ("oc".equalsIgnoreCase(extraction.get(DataGroups.TYPE1).toString())) ? new OldCollection(
					duration, sumOfPauses, longestPause) : new YoungCollection(duration, sumOfPauses, longestPause);

			final State startState = new State(extraction.get(DataGroups.START_TIME).toDouble()).memoryUsed(
					new Size(extraction.get(DataGroups.MEMORY_BEFORE).toBigDecimal())).transitionStart(transition);
			final State endState = new State(extraction.get(DataGroups.END_TIME).toBigDecimal())
					.memoryUsed(new Size(extraction.get(DataGroups.MEMORY_AFTER).toDouble()))
					.memoryCapacity(new Size(extraction.get(DataGroups.HEAP_SIZE_AFTER).toDouble()))
					.transitionEnd(transition);

			jvmRun.getHeap().addStates(transition, startState, endState);

			retVal = ModuleResult.RETURN;
		} else if (extractor.checkHeapInfo(line)) {
			final Map<HeapInfoGroups, Value> heapInfo = extractor.extractHeapInfo(line);
			final long initHeapSize = heapInfo.get(HeapInfoGroups.HEAP_SIZE).toLong();
			final long maxHeapSize = heapInfo.get(HeapInfoGroups.MAXIMAL_HEAP_SIZE).toLong();
			final long initNurserySize = heapInfo.get(HeapInfoGroups.NURSERY_SIZE).toLong();
			final long iniTenuredSize = initHeapSize - initNurserySize;

			jvmRun.getHeap().setStartState(new State(0d).memoryCapacity(new Size(initHeapSize)));
			jvmRun.getHeap().setMaximumState(new State(0d).memoryCapacity(new Size(maxHeapSize)));
			jvmRun.getHeap().getNursery().setStartState(new State(0d).memoryCapacity(new Size(initNurserySize)));
			jvmRun.getHeap().getTenured().setStartState(new State(0d).memoryCapacity(new Size(iniTenuredSize)));

			retVal = ModuleResult.RETURN;
		}
		return retVal;
	}
}
