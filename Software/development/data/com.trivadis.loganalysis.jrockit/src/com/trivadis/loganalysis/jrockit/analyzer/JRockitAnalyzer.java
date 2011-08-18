package com.trivadis.loganalysis.jrockit.analyzer;

import com.trivadis.loganalysis.core.IAnalyzer;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;

public class JRockitAnalyzer implements IAnalyzer<JRockitLogFile> {

	private static final String ANALYZER_EDITOR_ID = "com.trivadis.loganalysis.jrockit.ui.AnalysisEditor"; //$NON-NLS-1$

	public boolean isResponsible(ILogFileDescriptor content) {
		return true;
	}

	public JRockitLogFile process(ILogFileDescriptor logFileDescriptor) {
		return new JRockitLogFile(logFileDescriptor);
	}

	public String getEditorId() {
		return ANALYZER_EDITOR_ID;
	}

}
