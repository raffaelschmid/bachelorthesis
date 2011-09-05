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
package com.trivadis.loganalysis.core.common;

import static com.trivadis.loganalysis.core.common.CollectionUtil.append;
import static com.trivadis.loganalysis.core.common.CollectionUtil.avg;
import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.findAll;
import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;
import static com.trivadis.loganalysis.core.common.CollectionUtil.max;
import static com.trivadis.loganalysis.core.common.CollectionUtil.prepend;
import static com.trivadis.loganalysis.core.common.CollectionUtil.stringArray;
import static com.trivadis.loganalysis.core.common.CollectionUtil.stringList;
import static com.trivadis.loganalysis.core.common.CollectionUtil.sum;
import static com.trivadis.loganalysis.core.common.CollectionUtil.toArray;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class CollectionUtilTest {

	private List<Integer> list = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6 });

	@Test
	public void test_collect() {
		assertEquals(Arrays.asList(new Double[] { 1.1, 2.1, 3.1, 4.1, 5.1, 6.1 }),
				collect(list, new ClosureIO<Integer, Double>() {
					public Double call(Integer in) {
						return in + 0.1;
					}
				}));
	}

	@Test
	public void test_avg() {
		assertEquals(3.5, avg(list), 0.0);
	}

	@Test
	public void test_foreach() {
		final AtomicInteger sum = new AtomicInteger();
		foreach(list, new Closure<Integer>() {
			public void call(Integer in) {
				sum.addAndGet(in);
			}
		});
		assertEquals(21, sum.get());
	}

	@Test
	public void test_findAll() {
		List<Integer> ret = findAll(list, new Predicate<Integer>() {
			public boolean matches(Integer item) {
				return item % 2 == 0;
			}
		});
		assertEquals(Arrays.asList(new Integer[] { 2, 4, 6 }), ret);
	}

	@Test
	public void testPrepend() {
		assertEquals(Arrays.asList(new Integer[] { 0, 1, 2, 3, 4, 5, 6 }), prepend(0, list));
	}

	@Test
	public void testAppend() {
		assertEquals(Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 7 }), append(list, 7));
	}

	@Test
	public void testToArray() {
		assertArrayEquals(new String[]{"1","2"}, toArray(Arrays.asList("1", "2")));
	}

	@Test
	public void testStringList() {
		assertEquals(Arrays.asList(new String[]{"1","2","3","4","5","6"}), stringList(list));
	}

	@Test
	public void testStringArray() {
		System.out.println(stringArray(new Integer[]{1,2}));
	}

	@Test
	public void testSum() {
		assertEquals(21, sum(list),0.0);
	}

	@Test
	public void testMax() {
		assertEquals(6, max(list),0.0);
	}

}
