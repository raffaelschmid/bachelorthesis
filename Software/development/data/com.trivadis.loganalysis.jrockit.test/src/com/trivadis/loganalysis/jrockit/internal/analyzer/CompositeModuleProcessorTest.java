package com.trivadis.loganalysis.jrockit.internal.analyzer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.jrockit.domain.JRockitLog;
import com.trivadis.loganalysis.jrockit.internal.analyzer.IModuleProcessor;
import com.trivadis.loganalysis.jrockit.internal.analyzer.CompositeModuleProcessor;

public class CompositeModuleProcessorTest {
	AtomicInteger i = new AtomicInteger(0);
	private IModuleProcessor proceedModule = new IModuleProcessor() {
		public ModuleResult proceed(JRockitLog logFile, String line) {
			i.incrementAndGet();
			return ModuleResult.PROCEED;
		}
	};

	private IModuleProcessor returnModule = new IModuleProcessor() {
		public ModuleResult proceed(JRockitLog logFile, String line) {
			i.incrementAndGet();
			return ModuleResult.RETURN;
		}

	};

	@Test
	public void test_proceed() {
		IModuleProcessor module = new CompositeModuleProcessor(proceedModule, proceedModule);
		assertSame(ModuleResult.PROCEED, module.proceed(null, null));
		assertEquals(2, i.get());
	}

	@Test
	public void test_return() throws Exception {
		IModuleProcessor chain = new CompositeModuleProcessor(returnModule, returnModule);
		assertSame(ModuleResult.RETURN, chain.proceed(null, null));
		assertEquals(1, i.get());
	}

}
