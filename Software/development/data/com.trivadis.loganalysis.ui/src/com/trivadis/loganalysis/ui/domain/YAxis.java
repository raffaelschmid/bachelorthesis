package com.trivadis.loganalysis.ui.domain;

import java.awt.Color;

public class YAxis implements IAxis {

	private final Color color;
	private final String label;

	public YAxis(String label, Color color) {
		this.label = label;
		this.color = color;
	}

	public String getLabel() {
		return label;
	}

	public Color getColor() {
		return color;
	}

}
