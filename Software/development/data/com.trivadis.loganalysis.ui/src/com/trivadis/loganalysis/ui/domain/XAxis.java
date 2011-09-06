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
