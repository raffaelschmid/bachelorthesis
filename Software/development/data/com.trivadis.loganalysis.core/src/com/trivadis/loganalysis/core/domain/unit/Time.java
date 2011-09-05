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

public class Time {
	private final Double seconds;

	public Time(Double seconds) {
		this.seconds = seconds;
	}

	public Double getSeconds() {
		return seconds;
	}

	@Override
	public String toString() {
		return String.valueOf(seconds) + " seconds";
	}
}
