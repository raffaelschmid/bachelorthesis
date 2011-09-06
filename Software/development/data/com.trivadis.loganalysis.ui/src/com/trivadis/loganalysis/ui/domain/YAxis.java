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
