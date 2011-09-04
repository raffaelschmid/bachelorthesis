package com.trivadis.loganalysis.jrockit.internal.analyzer.memory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.jrockit.analyzer.DummyDescriptor;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;

public class MemoryLogModuleProcessorTest {
	private MemoryLogModuleProcessor instance = new MemoryLogModuleProcessor();
	private JRockitJvmRun jvm = new JRockitJvmRun(new DummyDescriptor());

	private final String heapInfoLine = "[INFO ][memory ] Heap size: 65536KB, maximal heap size: 1048576KB, nursery size: 20480KB.";
	private final String gcLine = "[INFO ][memory ] [YC#2] 1.531-1.532: YC 156652KB->156691KB (233624KB), 0.001 s, sum of pauses 0.564 ms, longest pause 0.564 ms.";

	@Test
	public void test_gcLine() {
		ModuleResult chainResult = instance.proceed(jvm, gcLine);
		State beginCollection = jvm.getHeap().getStates().get(0);
		State endCollection = jvm.getHeap().getStates().get(1);

		assertEquals(156652.0, beginCollection.getMemoryUsed(), 0.0);
		assertEquals(156691.0, endCollection.getMemoryUsed(), 0.0);
		assertEquals(233624.0, endCollection.getMemoryCapacity(), 0.0);
		assertEquals(ModuleResult.RETURN, chainResult);
	}

	@Test
	public void test_heapInfoLine() throws Exception {

		ModuleResult chainResult = instance.proceed(jvm, heapInfoLine);
		assertEquals(ModuleResult.RETURN, chainResult);

		assertEquals(1048576.0, jvm.getHeap().getMaximumCapacity(), 0.0);
		assertEquals(65536.0, jvm.getHeap().getInitialCapacity(), 0.0);
		assertEquals(20480.0, jvm.getHeap().getNursery().getInitialCapacity(), 0.0);
		assertEquals(65536d - 20480d, jvm.getHeap().getTenured().getInitialCapacity(), 0.0);
	}
}
