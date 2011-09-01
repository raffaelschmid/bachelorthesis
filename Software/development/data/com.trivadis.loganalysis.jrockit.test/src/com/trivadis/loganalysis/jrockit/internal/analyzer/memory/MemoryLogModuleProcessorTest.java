package com.trivadis.loganalysis.jrockit.internal.analyzer.memory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.trivadis.loganalysis.core.ModuleResult;

public class MemoryLogModuleProcessorTest {
	private MemoryLogModuleProcessor instance = new MemoryLogModuleProcessor();

	@Test
	public void testProceed() {
		ModuleResult chainResult = instance.proceed(null, null);
		assertEquals(ModuleResult.RETURN, chainResult);
	}

}
