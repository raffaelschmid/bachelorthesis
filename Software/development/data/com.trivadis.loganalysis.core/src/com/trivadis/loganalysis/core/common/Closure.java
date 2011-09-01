package com.trivadis.loganalysis.core.common;

public interface Closure<I,O> {
	public O call(I in);

}
