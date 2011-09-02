package com.trivadis.loganalysis.core.common;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtil {
	public static <I, O> List<O> collect(List<I> list, Closure<I, O> closure) {
		List<O> retVal = new ArrayList<O>();
		for (I item : list) {
			retVal.add(closure.call(item));
		}
		return retVal;
	}
	
	public static <T> List<T> prepend(T value, List<T> list) {
		List<T> values = new ArrayList<T>();
		values.add(value);
		values.addAll(list);
		return values;
	}
	public static <T> List<T> append(List<T> list, T value){
		List<T> values = new ArrayList<T>();
		values.addAll(list);
		values.add(value);
		return values;
	}
	
	public static String[] toArray(List<String> values) {
		return values.toArray(new String[values.size()]);
	}
	
	public static List<String> stringList(List<Integer> list) {
		return collect(list, new Closure<Integer, String>() {
			public String call(Integer in) {
				return String.valueOf(in);
			}
		});
	}
	
	public static <T extends Number> double sum(List<T> df) {
		double total = 0;
		for(Number item : df){
			total+=item.doubleValue();
		}
		return total;
	}
}
