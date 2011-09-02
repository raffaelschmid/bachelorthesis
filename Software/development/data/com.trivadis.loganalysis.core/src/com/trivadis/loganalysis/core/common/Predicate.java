package com.trivadis.loganalysis.core.common;

public interface Predicate<T> {
	boolean matches(T item);
}
