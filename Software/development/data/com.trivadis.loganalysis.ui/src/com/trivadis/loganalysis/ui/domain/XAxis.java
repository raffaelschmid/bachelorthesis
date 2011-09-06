package com.trivadis.loganalysis.ui.domain;

import java.awt.Color;

public class XAxis implements IAxis {

	private final String label;
	private final Color color;
	private final ValueProvider valueProvider;

	public XAxis(String label, Color color, ValueProvider valueProvider) {
		this.label = label;
		this.color = color;
		this.valueProvider = valueProvider;
	}

	public String getLabel() {
		return label;
	}

	public Color getColor() {
		return color;
	}

	public ValueProvider getValueProvider() {
		return valueProvider;
	}

}
