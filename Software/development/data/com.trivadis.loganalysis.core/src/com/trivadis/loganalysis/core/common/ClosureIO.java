package com.trivadis.loganalysis.core.common;

public interface ClosureIO<I,O> {
	public O call(I in);

}
