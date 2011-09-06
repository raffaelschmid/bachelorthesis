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

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

public class Profile implements IProfile {

	private final String name;
	private final List<IChart> charts = new ArrayList<IChart>();

	public Profile(String name, IChart... c) {
		this.name = name;
		this.charts.addAll(asList(c));
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public void addChart(IChart chart) {
		this.charts.add(chart);
	}
}
