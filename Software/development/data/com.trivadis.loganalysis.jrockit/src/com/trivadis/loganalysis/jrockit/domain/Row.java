package com.trivadis.loganalysis.jrockit.domain;

public class Row {
	private String[] values;

	public Row(String... values) {
		this.values = values;
	}

	public String getValueAt(int index) {
		return this.values[index];
	}

	public int numberOfValues() {
		return values.length;
	}
}