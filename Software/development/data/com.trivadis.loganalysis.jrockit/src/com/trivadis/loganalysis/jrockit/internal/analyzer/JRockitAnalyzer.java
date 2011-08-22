package com.trivadis.loganalysis.jrockit.internal.analyzer;

import java.util.List;

import com.trivadis.loganalysis.core.IAnalyzer;
import com.trivadis.loganalysis.core.IContentReader;
import com.trivadis.loganalysis.core.Loganalysis;
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

	public JRockitLog process(ILogFileDescriptor descriptor) {
		List<String> content = descriptor.getListContent(contentReader);
		JRockitLog logFile = new JRockitLog(descriptor);
		for(String line : content){
			if(extractor.checkDataLine(line)){
				logFile.addDataFromLine(line, extractor);
			}
		}
		return logFile;
	}

	public String getEditorId() {
		return ANALYZER_EDITOR_ID;
	}

}
