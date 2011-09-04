package com.trivadis.loganalysis.core.domain.unit;

public class Size {
	private static final int SHIFT = 1024;

	public enum SizeType {
		BYTE {
			@Override
			double toByte(double value) {
				return value;
			}
		},
		KILOBYTE {
			@Override
			double toByte(double value) {
				return value * SHIFT;
			}
		},
		MEGABYTE {
			@Override
			double toByte(double value) {
				return value * SHIFT * SHIFT;
			}
		},
		GIGABYTE {
			@Override
			double toByte(double value) {
				return value * SHIFT * SHIFT * SHIFT;
			}
		};
		abstract double toByte(double value);
	}

	private final double value;

	public Size(double value, SizeType type) {
		this.value = type.toByte(value);
	}

	public Size(double value) {
		this(value, SizeType.KILOBYTE);
	}

	public double getByte() {
		return value;
	}

	public double getKiloByte() {
		return value / SHIFT;
	}

	public double getMegaByte() {
		return getKiloByte() / SHIFT;
	}

	public double getGigaByte() {
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
