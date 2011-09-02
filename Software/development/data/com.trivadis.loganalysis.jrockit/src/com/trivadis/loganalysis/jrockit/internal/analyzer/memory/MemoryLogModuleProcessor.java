package com.trivadis.loganalysis.jrockit.internal.analyzer.memory;

import java.util.Map;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.internal.analyzer.IModuleProcessor;
import com.trivadis.loganalysis.jrockit.old.Value;

public class MemoryLogModuleProcessor implements IModuleProcessor {

	private final JRockitExtractor extractor = new JRockitExtractor();

	public ModuleResult proceed(JRockitJvmRun jvm, String line) {
		ModuleResult retVal = ModuleResult.PROCEED;
		if (extractor.checkDataLine(line)) {
			Map<DataGroups, Value> extraction = extractor.extractDataLine(line);
			jvm.getHeap()
					.addStates(
							new State(extraction.get(DataGroups.START_TIME).toDouble()).memoryUsed(extraction
									.get(DataGroups.MEMORY_BEFORE).toLong()),
							new State(extraction.get(DataGroups.END_TIME).toDouble())
									.memoryUsed(extraction.get(DataGroups.MEMORY_AFTER).toLong()));
			retVal = ModuleResult.RETURN;
		}
		return retVal;
	}
}
