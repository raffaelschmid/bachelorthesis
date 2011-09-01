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
	
	public static List<String> prepend(String value, List<String> list) {
		List<String> values = new ArrayList<String>();
		values.add(value);
		values.addAll(list);
		return values;
	}
	
	public static String[] toArray(List<String> values) {
		return values.toArray(new String[values.size()]);
	}
}
