package com.trivadis.loganalysis.jrockit.internal.analyzer;

import java.util.List;

import com.trivadis.loganalysis.core.IAnalyzer;
import com.trivadis.loganalysis.core.IContentReader;
import com.trivadis.loganalysis.core.Loganalysis;
import com.trivadis.loganalysis.core.common.progress.IProgress;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;
import com.trivadis.loganalysis.jrockit.domain.LogModuleChain;
import com.trivadis.loganalysis.jrockit.domain.JRockitLog;
import com.trivadis.loganalysis.jrockit.domain.MemoryLogModule;

public class JRockitAnalyzer implements IAnalyzer<JRockitLog> {

	public static final String ANALYZER_EDITOR_ID = "com.trivadis.loganalysis.jrockit.ui.AnalysisEditor";
	private final IContentReader contentReader;
	private final JRockitExtractor extractor;
	private LogModuleChain chain;

	public JRockitAnalyzer() {
		this(Loganalysis.contentReader(), new JRockitExtractor(), new LogModuleChain(new MemoryLogModule()));
	}

	public JRockitAnalyzer(IContentReader contentReader, JRockitExtractor jRockitExtractor,
			LogModuleChain chain) {
		this.contentReader = contentReader;
		this.extractor = jRockitExtractor;
		this.chain = chain;
	}

	public boolean isResponsible(ILogFileDescriptor descriptor) {
		List<String> logs = descriptor.getListContent(contentReader);
		return extractor.checkGcInfo(logs.get(0));
	}

	public JRockitLog process(ILogFileDescriptor descriptor, IProgress progress) {
		List<String> content = descriptor.getListContent(contentReader);
		JRockitLog logFile = new JRockitLog(descriptor);
		for (int i = 0; i < content.size(); i++) {
			if (shouldReportProgress(i)) {
				progress.worked(calculateProgress(content.size(), i));
			}
			chain.proceed(logFile, content.get(i));
		}
		progress.done();
		return logFile;
	}

	private boolean shouldReportProgress(int i) {
		return i % 500 == 0 && i != 0;
	}

	private int calculateProgress(int numberOfLines, int i) {
		return i * 100 / numberOfLines;
	}

	public String getEditorId() {
		return ANALYZER_EDITOR_ID;
	}

}
