package com.trivadis.loganalysis.core.common;

public class Assert {
	public static void assertNotNull(Object o){
		if(o==null){
			throw new AssertionError("object is null");
		}
	}
	
	public static void assertTrue(boolean bool){
		assertTrue(bool, "value is not true");
	}
	public static void assertTrue(boolean bool, String message){
		if(!bool)
			throw new AssertionError(message);
	}

}
