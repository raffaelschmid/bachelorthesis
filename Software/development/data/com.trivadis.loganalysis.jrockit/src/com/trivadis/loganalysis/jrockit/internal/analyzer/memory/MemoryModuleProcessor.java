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
package com.trivadis.loganalysis.jrockit.internal.analyzer.memory;

import java.util.Map;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.file.Token;
import com.trivadis.loganalysis.jrockit.internal.analyzer.IProcessor;
import com.trivadis.loganalysis.jrockit.internal.analyzer.ISyntacticAnalyzer;
import com.trivadis.loganalysis.jrockit.internal.analyzer.JRockitLexer;
import com.trivadis.loganalysis.jrockit.internal.analyzer.TokenType;

public class MemoryModuleProcessor implements IProcessor {

	private final JRockitLexer memoryLexer = new JRockitLexer(new MemoryUsageLine());
	private final JRockitLexer infoLexer = new JRockitLexer(new HeapInfoLine());

	private final ISyntacticAnalyzer<JRockitJvmRun> memoryAnalyzer = new MemorySyntacticAnalyzer();
	private final ISyntacticAnalyzer<JRockitJvmRun> infoAnalyzer = new InfoSyntacticAnalyzer();

	public ModuleResult process(final JRockitJvmRun jvmRun, final String line) {
		ModuleResult retVal = ModuleResult.PROCEED;

		if (memoryLexer.matches(line)) {
			final Map<TokenType, Token> extraction = memoryLexer.tokenize(line);
			memoryAnalyzer.process(jvmRun, extraction);
			retVal = ModuleResult.RETURN;
		} else if (infoLexer.matches(line)) {
			final Map<TokenType, Token> heapInfo = infoLexer.tokenize(line);
			infoAnalyzer.process(jvmRun, heapInfo);

			retVal = ModuleResult.RETURN;
		}
		return retVal;
	}
}
