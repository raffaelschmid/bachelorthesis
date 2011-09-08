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
package com.trivadis.loganalysis.jrockit.ui.internal.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.xy.XYSeries;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.ui.domain.ValueProvider;
import com.trivadis.loganalysis.ui.domain.profile.AxisType;
import com.trivadis.loganalysis.ui.domain.profile.IValueProvider;

public class DataWrapper {
	private final JRockitJvmRun jvm;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private final Map<AxisType, ValueProvider> axisSelection = new HashMap<AxisType, ValueProvider>();

	public DataWrapper(final JRockitJvmRun jvm) {
		this.jvm = jvm;
	}

	public void addAxisSelection(final AxisType axisType, final IValueProvider iValueProvider) {
		axisSelection.put(axisType, (ValueProvider) iValueProvider);
	}

	public XYSeries getDataset() {
		final ValueProvider xAxis = getAxisSelection(AxisType.X);
		final ValueProvider yAxis = getAxisSelection(AxisType.Y);
		final XYSeries series = new XYSeries(xAxis + "/" + yAxis);
		if (jvm != null) {
			for (final State state : jvm.getHeap().getStates()) {
				final BigDecimal x = xAxis.data(state);
				final BigDecimal y = yAxis.data(state);
				if (x != null && y != null)
					series.add(x, y);
			}
		}
		return series;
	}

	public ValueProvider getAxisSelection(final AxisType axis) {
		return axisSelection.get(axis);
	}

	public void addPropertyChangeListener(final AxisType propertyName, final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName.toString(), listener);
	}

	public void setSelection(final AxisType axis, final ValueProvider selection) {
		final ValueProvider oldValue = axisSelection.get(axis);
		axisSelection.put(axis, selection);
		this.propertyChangeSupport.firePropertyChange(axis.toString(), oldValue, selection);
	}

	public void addPropertyChangeListeners(final PropertyChangeListener listener, final AxisType... axes) {
		for (final AxisType axis : axes) {
			addPropertyChangeListener(axis, listener);
		}
	}
}
