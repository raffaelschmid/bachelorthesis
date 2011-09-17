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
package com.trivadis.loganalysis.ui.domain.profile;

import java.awt.Color;

import org.eclipse.ui.IMemento;

public class Axis implements IAxis {
	public static final String ATTRIBUTE_VALUE_PROVIDER = "valueProvider";
	public static final String ATTRIBUTE_AXIS_TYPE = "axisType";
	public static final String ATTRIBUTE_COLOR = "color";
	public static final String MEMENTO_ELEMENT_NAME = "axis";
	public static final String ATTRIBUTE_LABEL = "label";
	private final String label;
	private final Color color;
	private final IValueProvider valueProvider;
	private final AxisType axisType;

	public Axis(final AxisType axisType, final String label, final Color color, final IValueProvider valueProvider) {
		this.axisType = axisType;
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

	public IValueProvider getValueProvider() {
		return valueProvider;
	}

	public void save(final IMemento parent) {
		final IMemento memento = parent.createChild(MEMENTO_ELEMENT_NAME);
		memento.putString(ATTRIBUTE_LABEL, label);
		memento.putInteger(ATTRIBUTE_COLOR, color.getRGB());
		memento.putString(ATTRIBUTE_VALUE_PROVIDER, getValueProvider().name());
		memento.putString(ATTRIBUTE_AXIS_TYPE, axisType.toString());
	}

	public AxisType getAxisType() {
		return axisType;
	}

	public static Axis create(final AxisType type, final IValueProvider vp) {
		return new Axis(type, "", Color.red, vp);
	}

	@Override
	public String toString() {
		return "Axis [label=" + label + ", color=" + color + ", valueProvider=" + valueProvider + ", axisType="
				+ axisType + "]";
	}

}
