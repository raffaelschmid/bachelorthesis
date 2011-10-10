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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.core.domain.unit.Size;
import com.trivadis.loganalysis.jrockit.analyzer.DummyDescriptor;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;

public class MemoryLogModuleProcessorTest {
	private MemoryModuleProcessor instance = new MemoryModuleProcessor();
	private JRockitJvmRun jvm = new JRockitJvmRun(new DummyDescriptor());

	private final String heapInfoLine = "[INFO ][memory ] Heap size: 65536KB, maximal heap size: 1048576KB, nursery size: 20480KB.";
	private final String gcLine = "[INFO ][memory ] [YC#2] 1.531-1.532: YC 156652KB->156691KB (233624KB), 0.001 s, sum of pauses 0.564 ms, longest pause 0.564 ms.";

	@Test
	public void test_gcLine() {
		ModuleResult chainResult = instance.process(jvm, gcLine);
		State beginCollection = jvm.getHeap().getStates().get(0);
		State endCollection = jvm.getHeap().getStates().get(1);

		assertEquals(new Size(156652), beginCollection.getMemoryUsed());
		assertEquals(new Size(156691), endCollection.getMemoryUsed());
		assertEquals(new Size(233624), endCollection.getMemoryCapacity());
		assertEquals(ModuleResult.RETURN, chainResult);
	}

	@Test
	public void test_heapInfoLine() throws Exception {

		ModuleResult chainResult = instance.process(jvm, heapInfoLine);
		assertEquals(ModuleResult.RETURN, chainResult);

		assertEquals(new Size(1048576.0), jvm.getHeap().getMaximumCapacity());
		assertEquals(new Size(65536.0), jvm.getHeap().getInitialCapacity());
		assertEquals(new Size(20480.0), jvm.getHeap().getNursery().getInitialCapacity());
		assertEquals(new Size(65536d - 20480d), jvm.getHeap().getTenured().getInitialCapacity());
	}
}
