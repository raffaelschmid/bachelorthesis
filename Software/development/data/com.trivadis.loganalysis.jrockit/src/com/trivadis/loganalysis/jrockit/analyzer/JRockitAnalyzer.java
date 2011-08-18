package com.trivadis.loganalysis.jrockit.analyzer;

import java.util.List;

import com.trivadis.loganalysis.core.IAnalyzer;
import com.trivadis.loganalysis.core.IContentReader;
import com.trivadis.loganalysis.core.Loganalysis;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;

public class JRockitAnalyzer implements IAnalyzer<JRockitLogFile> {

	public static final String ANALYZER_EDITOR_ID = "com.trivadis.loganalysis.jrockit.ui.AnalysisEditor";
	private final IContentReader contentReader;

	public JRockitAnalyzer() {
		this(Loganalysis.contentReader());
	}

	public JRockitAnalyzer(IContentReader contentReader) {
		this.contentReader = contentReader;
	}

	public boolean isResponsible(ILogFileDescriptor descriptor) {
		List<String> logs = descriptor.getListContent(contentReader);
		return JRockitExtractor.getDefault().checkGcInfo(logs.get(0));
	}

	public JRockitLogFile process(ILogFileDescriptor descriptor) {
		List<String> content = descriptor.getListContent(contentReader);
		return new JRockitLogFile(descriptor);
	}

	public String getEditorId() {
		return ANALYZER_EDITOR_ID;
	}

}
