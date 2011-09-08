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
package com.trivadis.loganalysis.jrockit.ui.domain;

import java.awt.Color;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.ui.domain.profile.AxisType;
import com.trivadis.loganalysis.ui.domain.profile.IAxis;

public class Axis implements IAxis {
	private static final String ATTRIBUTE_VALUE_PROVIDER = "valueProvider";
	protected static final String ATTRIBUTE_AXIS_TYPE = "axisType";
	private static final String ATTRIBUTE_COLOR = "color";
	public static final String MEMENTO_ELEMENT_NAME = "axis";
	private static final String ATTRIBUTE_LABEL = "label";
	private final String label;
	private final Color color;
	private final ValueProvider valueProvider;
	private final AxisType axisType;

	public Axis(final AxisType axisType, final String label, final Color color, final ValueProvider valueProvider) {
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

	public ValueProvider getValueProvider() {
		return valueProvider;
	}

	public void saveMemento(final IMemento parent) {
		final IMemento memento = parent.createChild(MEMENTO_ELEMENT_NAME);
		memento.putString(ATTRIBUTE_LABEL, label);
		memento.putInteger(ATTRIBUTE_COLOR, color.getRGB());
		memento.putString(ATTRIBUTE_VALUE_PROVIDER, getValueProvider().name());
		memento.putString(ATTRIBUTE_AXIS_TYPE, axisType.toString());
	}

	public AxisType getAxisType() {
		return axisType;
	}

	public static IAxis loadMemento(final IMemento in) {
		return new Axis(AxisType.valueOf(in.getString(ATTRIBUTE_AXIS_TYPE)), in.getString(ATTRIBUTE_LABEL), new Color(
				in.getInteger(ATTRIBUTE_COLOR)), ValueProvider.valueOf(in.getString(ATTRIBUTE_VALUE_PROVIDER)));
	}

}
