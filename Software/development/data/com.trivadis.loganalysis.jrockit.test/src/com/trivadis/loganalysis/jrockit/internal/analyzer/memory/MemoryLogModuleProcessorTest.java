package com.trivadis.loganalysis.jrockit.internal.analyzer.memory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.jrockit.analyzer.DummyDescriptor;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;

public class MemoryLogModuleProcessorTest {
	private MemoryLogModuleProcessor instance = new MemoryLogModuleProcessor();
	private final String testline = "[INFO ][memory ] [YC#2] 1.531-1.532: YC 156652KB->156691KB (233624KB), 0.001 s, sum of pauses 0.564 ms, longest pause 0.564 ms.";

	@Test
	public void testProceed() {
		ModuleResult chainResult = instance.proceed(new JRockitJvmRun(new DummyDescriptor(testline)), testline);
		assertEquals(ModuleResult.RETURN, chainResult);
	}

}
