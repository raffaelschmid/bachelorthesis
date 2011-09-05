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
