package com.trivadis.loganalysis.jrockit.domain;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.jrockit.internal.analyzer.JRockitExtractor;

public class MemoryLogModule implements ILogModule {
	
	

	private final JRockitExtractor extractor = new JRockitExtractor();

	public ModuleResult proceed(JRockitLog logFile, String line) {
		if(extractor.checkDataLine(line))
			logFile.addDataFromLine(line, extractor);
		return ModuleResult.RETURN;
	}
}
