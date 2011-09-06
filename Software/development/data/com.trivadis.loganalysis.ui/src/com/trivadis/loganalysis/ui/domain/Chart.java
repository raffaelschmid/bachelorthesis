package com.trivadis.loganalysis.ui.domain;

public class Chart implements IChart {

	private final String label;

	public Chart(String label, IAxis... axes) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
