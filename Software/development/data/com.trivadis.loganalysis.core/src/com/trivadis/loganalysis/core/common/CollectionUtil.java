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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionUtil {
	public static <I, O> List<O> collect(List<I> list, ClosureIO<I, O> closure) {
		List<O> retVal = new ArrayList<O>();
		for (I item : list) {
			retVal.add(closure.call(item));
		}
		return retVal;
	}

	public static BigDecimal avg(List<BigDecimal> list) {
		return list.size() > 0 ? sum(list).setScale(10, RoundingMode.HALF_UP).divide(new BigDecimal(list.size()),
				RoundingMode.HALF_UP) : BigDecimal.ZERO;
	}

	public static <I> void foreach(List<I> list, Closure<I> closure) {
		for (I item : list) {
			closure.call(item);
		}
	}

	public static <T> List<T> findAll(List<T> list, Predicate<T> predicate) {
		List<T> retVal = new ArrayList<T>();
		for (T item : list) {
			if (predicate.matches(item))
				retVal.add(item);
		}
		return retVal;
	}

	public static <T> List<T> prepend(T value, List<T> list) {
		List<T> values = new ArrayList<T>();
		values.add(value);
		values.addAll(list);
		return values;
	}

	public static <T> List<T> append(List<T> list, T value) {
		List<T> values = new ArrayList<T>();
		values.addAll(list);
		values.add(value);
		return values;
	}

	public static String[] toArray(List<String> values) {
		return values.toArray(new String[values.size()]);
	}

	public static <T> List<String> stringList(List<T> list) {
		return collect(list, new ClosureIO<T, String>() {
			public String call(T in) {
				return String.valueOf(in);
			}
		});
	}

	public static String[] stringArray(Object[] objects) {
		List<String> list = collect(Arrays.asList(objects), new ClosureIO<Object, String>() {
			public String call(Object in) {
				return String.valueOf(in);
			}
		});
		return list.toArray(new String[list.size()]);
	}

	public static BigDecimal sum(List<BigDecimal> list) {
		BigDecimal total = BigDecimal.ZERO;
		for (BigDecimal item : list) {
			total = total.add(item);
		}
		return total;
	}

	public static BigDecimal max(List<BigDecimal> list) {
		BigDecimal max = BigDecimal.ZERO;
		for (BigDecimal item : list) {
			if (item.compareTo(max)>0)
				max = item;
		}
		return max;
	}

	public static List<BigDecimal> intervals(List<BigDecimal> list) {
		//list must be sorted for interval calculation
		Collections.sort(list);
		List<BigDecimal> retVal = new ArrayList<BigDecimal>();
		BigDecimal before = list.get(0);
		for(int i=1;i<list.size();i++){
			BigDecimal current = list.get(i);
			retVal.add(current.subtract(before));
			before=current;
		}
		return retVal;
	}
}
