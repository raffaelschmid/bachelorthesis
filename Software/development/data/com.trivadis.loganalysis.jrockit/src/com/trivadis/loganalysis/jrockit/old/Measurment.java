package com.trivadis.loganalysis.jrockit.old;

public class Measurment {
	private final String name;
	private final double value;

	public Measurment(String name, double value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public double getValue() {
		return value;
	}

}
