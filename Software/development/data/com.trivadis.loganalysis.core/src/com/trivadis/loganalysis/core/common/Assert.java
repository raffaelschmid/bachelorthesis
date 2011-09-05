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
