package com.trivadis.loganalysis.jrockit.internal.analyzer;

import java.util.List;

import com.trivadis.loganalysis.core.IAnalyzer;
import com.trivadis.loganalysis.core.IContentReader;
import com.trivadis.loganalysis.core.Loganalysis;
import com.trivadis.loganalysis.core.common.progress.IProgress;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;
import com.trivadis.loganalysis.jrockit.domain.JRockitLog;

public class JRockitAnalyzer implements IAnalyzer<JRockitLog> {

	public static final String ANALYZER_EDITOR_ID = "com.trivadis.loganalysis.jrockit.ui.AnalysisEditor";
	private final IContentReader contentReader;
	private final JRockitExtractor extractor;

	public JRockitAnalyzer() {
		this(Loganalysis.contentReader(), new JRockitExtractor());
	}

	public JRockitAnalyzer(IContentReader contentReader, JRockitExtractor jRockitExtractor) {
		this.contentReader = contentReader;
		this.extractor = jRockitExtractor;
	}

	public boolean isResponsible(ILogFileDescriptor descriptor) {
		List<String> logs = descriptor.getListContent(contentReader);
		return extractor.checkGcInfo(logs.get(0));
	}

	public JRockitLog process(ILogFileDescriptor descriptor, IProgress progress) {
		List<String> content = descriptor.getListContent(contentReader);
		JRockitLog logFile = new JRockitLog(descriptor);
		int numberOfLines = content.size();
		for (int i = 0; i < numberOfLines; i++) {
			if (i % 500 == 0 && i!=0)
				progress.worked(i*100/numberOfLines);
			String line = content.get(i);
			if (extractor.checkDataLine(line)) {
				logFile.addDataFromLine(line, extractor);
			}
		}
		progress.done();
		return logFile;
	}

	public String getEditorId() {
		return ANALYZER_EDITOR_ID;
	}

}
