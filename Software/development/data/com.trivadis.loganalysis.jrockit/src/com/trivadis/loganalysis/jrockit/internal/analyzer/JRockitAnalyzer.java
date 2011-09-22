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
package com.trivadis.loganalysis.jrockit.internal.analyzer;

import java.util.List;
import java.util.regex.Pattern;

import com.trivadis.loganalysis.core.IAnalyzer;
import com.trivadis.loganalysis.core.IContentReader;
import com.trivadis.loganalysis.core.Loganalysis;
import com.trivadis.loganalysis.core.common.CollectionUtil;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.core.common.progress.IProgress;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.internal.analyzer.memory.JRockitExtractor;
import com.trivadis.loganalysis.jrockit.internal.analyzer.memory.MemoryLogModuleProcessor;

public class JRockitAnalyzer implements IAnalyzer<JRockitJvmRun> {

	private final Pattern firstLinePattern = Pattern.compile(JRockitExtractor.prefix() + "<start>-<end>:\\s<type>\\s<before>KB-><after>KB\\s\\(<heap>KB\\), <time> ms, sum of pauses <pause> ms\\.");//"
			//+ "\\]\\s.*");
	public static final String ANALYZER_EDITOR_ID = "com.trivadis.loganalysis.jrockit.ui.AnalysisEditor";
	private final IContentReader contentReader;
	private final IModuleProcessor moduleProcessor;

	public JRockitAnalyzer() {
		this(Loganalysis.getDefault().contentReader(), new CompositeModuleProcessor(
				new MemoryLogModuleProcessor()));
	}

	public JRockitAnalyzer(final IContentReader contentReader, final IModuleProcessor moduleProcessor) {
		this.contentReader = contentReader;
		this.moduleProcessor = moduleProcessor;
	}

	public boolean canHandleLogFile(final IFileDescriptor descriptor) {
		final List<String> logs = descriptor.getContent(contentReader);
		
		final String definitionLine = CollectionUtil.findFirst(logs, new Predicate<String>(){
			public boolean matches(final String line) {
				return firstLinePattern.matcher(line).matches();
			}
		});
		return definitionLine!=null;
	}

	public JRockitJvmRun process(final IFileDescriptor descriptor, final IProgress progress) {
		final List<String> content = descriptor.getContent(contentReader);
		final JRockitJvmRun logFile = new JRockitJvmRun(descriptor);
		progress.beginTask(content.size());
		for (int i = 0; i < content.size(); i++) {
			if (shouldReportProgress(i)) {
				progress.worked(i);
			}
			moduleProcessor.process(logFile, content.get(i));
		}
		progress.done();
		return logFile;
	}

	private boolean shouldReportProgress(final int i) {
		return i % 500 == 0 && i != 0;
	}

	public String getEditorId() {
		return ANALYZER_EDITOR_ID;
	}

}
