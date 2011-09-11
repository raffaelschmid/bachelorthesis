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

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.common.Closure;
import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class Profile implements IProfile {

	private static final String ATTRIBUTE_LABEL = "label";
	public static final String MEMENTO_ELEMENT_NAME = "profile";
	private final String label;
	private final List<IChart> charts = new ArrayList<IChart>();
	private IConfiguration configuration;

	public Profile(final String label, final IChart... c) {
		this(label, asList(c));
	}

	public Profile(final String label, final List<IChart> charts) {
		this.label = label;
		this.charts.addAll(charts);
	}

	public String getLabel() {
		return label;
	}

	public void addChart(final IChart chart) {
		this.charts.add(chart);
	}

	public List<IChart> getCharts() {
		return charts;
	}

	public void saveMemento(final IMemento parent) {
		final IMemento memento = parent.createChild(MEMENTO_ELEMENT_NAME);
		memento.putString(ATTRIBUTE_LABEL, label);
		foreach(charts, new Closure<IChart>() {
			public void call(final IChart chart) {
				chart.saveMemento(memento);
			}
		});
	}

	public static IProfile loadMemento(final IMemento in) {
		return new Profile(in.getString(ATTRIBUTE_LABEL), collect(asList(in.getChildren(Chart.MEMENTO_ELEMENT_NAME)),
				new ClosureIO<IMemento, IChart>() {
					public IChart call(final IMemento in) {
						return Chart.loadMemento(in);
					}
				}));
	}

	@Override
	public String toString() {
		return "Profile [label=" + label + ", charts=" + charts + "]";
	}

	public IConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(final IConfiguration configuration) {
		this.configuration = configuration;
	}
}
