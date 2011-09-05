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

public class Size {
	private static final int SHIFT = 1024;

	public enum SizeType {
		BYTE {
			@Override
			Double toByte(double value) {
				return value;
			}
		},
		KILOBYTE {
			@Override
			Double toByte(double value) {
				return value * SHIFT;
			}
		},
		MEGABYTE {
			@Override
			Double toByte(double value) {
				return value * SHIFT * SHIFT;
			}
		},
		GIGABYTE {
			@Override
			Double toByte(double value) {
				return value * SHIFT * SHIFT * SHIFT;
			}
		};
		abstract Double toByte(double value);
	}

	private final double value;

	public Size(double value, SizeType type) {
		this.value = type.toByte(value);
	}

	public Size(double value) {
		this(value, SizeType.KILOBYTE);
	}

	public Double getByte() {
		return value;
	}

	public Double getKiloByte() {
		return value / SHIFT;
	}

	public Double getMegaByte() {
		return getKiloByte() / SHIFT;
	}

	public Double getGigaByte() {
		return getMegaByte() / SHIFT;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Size other = (Size) obj;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}
	
}
