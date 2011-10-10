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
package com.trivadis.loganalysis.jrockit.file;

import java.math.BigDecimal;

public class Token {
	private final String value;
	public Token(String value) {
		this.value = value;
	}
	
	public String toString(){
		return value;
	}
	public BigDecimal toBigDecimal(){
		return new BigDecimal(value);
	}
	public Double toDouble(){
		return Double.valueOf(value);
	}

	public long toLong() {
		return Long.valueOf(value);
	}

}
