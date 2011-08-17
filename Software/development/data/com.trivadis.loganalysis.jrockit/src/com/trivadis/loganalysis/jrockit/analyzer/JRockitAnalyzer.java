package com.trivadis.loganalysis.jrockit.analyzer;

import com.trivadis.loganalysis.core.IFileProcessor;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;

public class JRockitAnalyzer implements IFileProcessor<JRockitLogFile> {

	private static final String ANALYZER_EDITOR_ID = "com.trivadis.loganalysis.jrockit.ui.AnalysisEditor";

	public boolean isResponsible(String content) {
		return true;
	}

	public JRockitLogFile process(ILogFileDescriptor logFileDescriptor) {
		System.out.println("Processing JRockit GC Log File");
		return new JRockitLogFile(logFileDescriptor);
	}

	public String getEditorId() {
		return ANALYZER_EDITOR_ID;
	}

}
