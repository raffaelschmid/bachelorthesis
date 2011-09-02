package com.trivadis.loganalysis.core.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectionUtil {
	public static <I, O> List<O> collect(List<I> list, ClosureIO<I, O> closure) {
		List<O> retVal = new ArrayList<O>();
		for (I item : list) {
			retVal.add(closure.call(item));
		}
		return retVal;
	}

	public static double avg(List<Double> list) {
		return sum(list) / list.size();
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

	public static <T extends Number> double sum(List<T> df) {
		double total = 0;
		for (Number item : df) {
			total += item.doubleValue();
		}
		return total;
	}
}
