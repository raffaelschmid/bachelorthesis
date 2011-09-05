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

public class Size {
	private static final BigDecimal SHIFT = new BigDecimal(1024);

	public enum SizeType {
		BYTE {
			@Override
			BigDecimal toByte(BigDecimal value) {
				return value;
			}
		},
		KILOBYTE {
			@Override
			BigDecimal toByte(BigDecimal value) {
				return value.multiply(SHIFT);
			}
		},
		MEGABYTE {
			@Override
			BigDecimal toByte(BigDecimal value) {
				return KILOBYTE.toByte(value).multiply(SHIFT);
			}
		},
		GIGABYTE {
			@Override
			BigDecimal toByte(BigDecimal value) {
				return MEGABYTE.toByte(value).multiply(SHIFT);
			}
		};
		abstract BigDecimal toByte(BigDecimal value);
	}

	private final BigDecimal value;

	public Size(double value, SizeType type) {
		this.value = type.toByte(new BigDecimal(value));
	}

	public Size(double value) {
		this(value, SizeType.KILOBYTE);
	}

	public BigDecimal getByte() {
		return value;
	}

	public BigDecimal getKiloByte() {
		return getByte().divide(SHIFT);
	}

	public BigDecimal getMegaByte() {
		return getKiloByte().divide(SHIFT);
	}

	public BigDecimal getGigaByte() {
		return getMegaByte().divide(SHIFT);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	
}
