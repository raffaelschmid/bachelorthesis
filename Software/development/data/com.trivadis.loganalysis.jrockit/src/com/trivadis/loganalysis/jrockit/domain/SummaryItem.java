package com.trivadis.loganalysis.jrockit.domain;

public class SummaryItem {
	private final String name;
	private final double value;

	public SummaryItem(String name, double value) {
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
