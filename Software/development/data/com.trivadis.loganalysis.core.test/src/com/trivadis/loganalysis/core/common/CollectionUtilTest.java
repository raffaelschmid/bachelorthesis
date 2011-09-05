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
import static com.trivadis.loganalysis.core.common.CollectionUtil.intervals;
import static com.trivadis.loganalysis.core.common.CollectionUtil.max;
import static com.trivadis.loganalysis.core.common.CollectionUtil.prepend;
import static com.trivadis.loganalysis.core.common.CollectionUtil.stringArray;
import static com.trivadis.loganalysis.core.common.CollectionUtil.stringList;
import static com.trivadis.loganalysis.core.common.CollectionUtil.sum;
import static com.trivadis.loganalysis.core.common.CollectionUtil.toArray;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class CollectionUtilTest {

	private List<BigDecimal> list = asList(new BigDecimal(1), new BigDecimal(2), new BigDecimal(3), new BigDecimal(4),
			new BigDecimal(5), new BigDecimal(6));

	@Test
	public void test_collect() {
		assertEquals(asList(new BigDecimal[] { new BigDecimal("1.1"), new BigDecimal("2.1"), new BigDecimal("3.1"),
				new BigDecimal("4.1"), new BigDecimal("5.1"), new BigDecimal("6.1") }),
				collect(list, new ClosureIO<BigDecimal, BigDecimal>() {
					public BigDecimal call(BigDecimal in) {
						return in.add(new BigDecimal(0.1)).setScale(1, RoundingMode.DOWN);
					}
				}));
	}

	@Test
	public void test_avg() {
		assertEquals(new BigDecimal(3.5), avg(list).setScale(1));
	}

	@Test
	public void test_foreach() {
		final AtomicInteger sum = new AtomicInteger();
		foreach(list, new Closure<BigDecimal>() {
			public void call(BigDecimal in) {
				sum.addAndGet(in.intValue());
			}
		});
		assertEquals(21, sum.get());
	}

	@Test
	public void test_findAll() {
		List<BigDecimal> ret = findAll(list, new Predicate<BigDecimal>() {
			public boolean matches(BigDecimal item) {
				return item.intValue() % 2 == 0;
			}
		});
		assertEquals(asList(valueOf(2), valueOf(4), valueOf(6)), ret);
	}

	@Test
	public void testPrepend() {
		assertEquals(asList(valueOf(0), valueOf(1), valueOf(2), valueOf(3), valueOf(4), valueOf(5), valueOf(6)),
				prepend(new BigDecimal(0), list));
	}

	@Test
	public void testAppend() {
		assertEquals(asList(valueOf(1), valueOf(2), valueOf(3), valueOf(4), valueOf(5), valueOf(6), valueOf(7)),
				append(list, new BigDecimal(7)));
	}

	@Test
	public void testToArray() {
		assertArrayEquals(new String[] { "1", "2" }, toArray(asList("1", "2")));
	}

	@Test
	public void testStringList() {
		assertEquals(asList("1", "2", "3", "4", "5", "6"), stringList(list));
	}

	@Test
	public void testStringArray() {
		assertArrayEquals(new String[] { "1", "2" }, stringArray(new Integer[] { 1, 2 }));
	}

	@Test
	public void testSum() {
		assertEquals(new BigDecimal(21), sum(list));
	}

	@Test
	public void testMax() {
		assertEquals(new BigDecimal(6), max(list));
	}

	@Test
	public void test_intervals() {
		assertEquals(asList(new BigDecimal("0.1"), new BigDecimal("0.1")),
				intervals(asList(new BigDecimal("1.1"), new BigDecimal("1.2"), new BigDecimal("1.3"))));
	}
}
