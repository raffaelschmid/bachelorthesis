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

import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.common.ClosureI;
import com.trivadis.loganalysis.ui.IChartChangeListener;

public class Profile implements IProfile {

	public static final String ATTRIBUTE_LABEL = "label";
	public static final String MEMENTO_ELEMENT_NAME = "profile";
	private final String label;
	private final List<IChart> charts = new ArrayList<IChart>();
	private IConfiguration configuration;
	private final List<IChartChangeListener> listeners = new ArrayList<IChartChangeListener>();

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
		for (final IChartChangeListener listener : listeners) {
			listener.added(chart);
		}
	}

	public List<IChart> getCharts() {
		return charts;
	}

	public void saveMemento(final IMemento parent) {
		final IMemento memento = parent.createChild(MEMENTO_ELEMENT_NAME);
		memento.putString(ATTRIBUTE_LABEL, label);
		foreach(charts, new ClosureI<IChart>() {
			public void call(final IChart chart) {
				chart.saveMemento(memento);
			}
		});
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

	public void addChartListener(final IChartChangeListener iChartChangeListener) {
		this.listeners.add(iChartChangeListener);
	}

	public boolean removeChart(final IChart chart) {
		final int indexOf = charts.indexOf(chart);
		final boolean retVal = charts.remove(chart);
		if (retVal) {
			for (final IChartChangeListener listener : listeners) {
				listener.removed(indexOf);
			}
		}
		return retVal;
	}
}
