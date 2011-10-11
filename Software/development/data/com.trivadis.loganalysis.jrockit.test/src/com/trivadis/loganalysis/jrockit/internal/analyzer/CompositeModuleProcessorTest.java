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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;

public class CompositeModuleProcessorTest {
	AtomicInteger i = new AtomicInteger(0);
	private ILogParser proceedModule = new ILogParser() {
		public ModuleResult process(JRockitJvmRun logFile, String line) {
			i.incrementAndGet();
			return ModuleResult.PROCEED;
		}
	};

	private ILogParser returnModule = new ILogParser() {
		public ModuleResult process(JRockitJvmRun jvmRun, String line) {
			i.incrementAndGet();
			return ModuleResult.RETURN;
		}

	};

	@Test
	public void test_proceed() {
		ILogParser module = new CompositeLogParser(proceedModule, proceedModule);
		assertSame(ModuleResult.PROCEED, module.process(null, null));
		assertEquals(2, i.get());
	}

	@Test
	public void test_return() throws Exception {
		ILogParser chain = new CompositeLogParser(returnModule, returnModule);
		assertSame(ModuleResult.RETURN, chain.process(null, null));
		assertEquals(1, i.get());
	}

}
