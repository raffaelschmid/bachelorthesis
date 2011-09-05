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
package com.trivadis.loganalysis.core.domain.unit;

import java.math.BigDecimal;

public class Time {
	private final BigDecimal seconds;

	public Time(double seconds){
		this(new BigDecimal(seconds));
	}
	public Time(BigDecimal seconds) {
		this.seconds = seconds;
	}

	public BigDecimal getSeconds() {
		return seconds;
	}

	@Override
	public String toString() {
		return String.valueOf(seconds) + " seconds";
	}
}
