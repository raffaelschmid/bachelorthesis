package com.trivadis.loganalysis.jrockit.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.jrockit.domain.LogModuleChain;
import com.trivadis.loganalysis.jrockit.domain.ILogModule;
import com.trivadis.loganalysis.jrockit.domain.JRockitLog;

public class LogModuleChainTest {
	AtomicInteger i = new AtomicInteger(0);
	private ILogModule proceedModule = new ILogModule() {
		public ModuleResult proceed(JRockitLog logFile, String line) {
			i.incrementAndGet();
			return ModuleResult.PROCEED;
		}
	};

	private ILogModule returnModule = new ILogModule() {
		public ModuleResult proceed(JRockitLog logFile, String line) {
			i.incrementAndGet();
			return ModuleResult.RETURN;
		}

	};

	@Test
	public void test_proceed() {
		ILogModule module = new LogModuleChain(proceedModule, proceedModule);
		assertSame(ModuleResult.PROCEED, module.proceed(null, null));
		assertEquals(2, i.get());
	}

	@Test
	public void test_return() throws Exception {
		ILogModule chain = new LogModuleChain(returnModule, returnModule);
		assertSame(ModuleResult.RETURN, chain.proceed(null, null));
		assertEquals(1, i.get());
	}

}
