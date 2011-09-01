package com.trivadis.loganalysis.jrockit.internal.analyzer.memory;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.jrockit.domain.JRockitLog;
import com.trivadis.loganalysis.jrockit.internal.analyzer.IModuleProcessor;

public class MemoryLogModuleProcessor implements IModuleProcessor {

	private final JRockitExtractor extractor = new JRockitExtractor();

	public ModuleResult proceed(JRockitLog logFile, String line) {
		ModuleResult retVal = ModuleResult.PROCEED;
		if (extractor.checkDataLine(line)) {
			logFile.addDataFromLine(line, extractor);
			retVal = ModuleResult.RETURN;
		}
		return retVal;
	}
}
