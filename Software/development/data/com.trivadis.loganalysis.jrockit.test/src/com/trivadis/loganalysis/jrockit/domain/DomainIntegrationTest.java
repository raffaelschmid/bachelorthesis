package com.trivadis.loganalysis.jrockit.domain;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.trivadis.loganalysis.jrockit.analyzer.DummyDescriptor;
import com.trivadis.loganalysis.jrockit.domain.gc.AbstractGarbageCollection;
import com.trivadis.loganalysis.jrockit.domain.gc.YoungCollection;
import com.trivadis.loganalysis.jrockit.domain.space.Heap;

public class DomainIntegrationTest {

	@Test
	public void test_fromLogEntry() {
		JRockitJvmRun jvm = new JRockitJvmRun(new DummyDescriptor(""));
		Heap heap = jvm.getHeap();
		assertEquals(3, heap.getSpaces().size());

		AbstractGarbageCollection transition = new YoungCollection(45);
		heap.addStates(transition,new State(4.472),
				new State(4.575));
		List<State> states = heap.getStates();
		assertEquals(2, states.size());
	}

}
