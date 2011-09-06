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
import com.trivadis.loganalysis.jrockit.file.ValueType;

public class DataWrapper {
	private final JRockitJvmRun jvm;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private final Map<Axis, ValueType> axisSelection = new HashMap<Axis, ValueType>();

	public DataWrapper(JRockitJvmRun jvm) {
		this.jvm = jvm;
	}

	public ValueType addAxisSelection(Axis axis, ValueType type) {
		return axisSelection.put(axis, type);
	}

	public XYSeries getDataset() {
		ValueType xAxis = getAxisSelection(Axis.X);
		ValueType yAxis = getAxisSelection(Axis.Y);
		final XYSeries series = new XYSeries(xAxis + "/" + yAxis);
		if (jvm != null) {
			for (State state : jvm.getHeap().getStates()) {
				BigDecimal x = xAxis.data(state);
				BigDecimal y = yAxis.data(state);
				if (x != null && y != null)
					series.add(x, y);
			}
		}
		return series;
	}

	public ValueType getAxisSelection(Axis axis) {
		return axisSelection.get(axis);
	}

	public void addPropertyChangeListener(Axis propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName.toString(), listener);
	}

	public void setSelection(Axis axis, ValueType selection) {
		ValueType oldValue = axisSelection.get(axis);
		axisSelection.put(axis, selection);
		this.propertyChangeSupport.firePropertyChange(axis.toString(), oldValue, selection);
	}

	public void addPropertyChangeListeners(PropertyChangeListener listener, Axis... axes) {
		for (Axis axis : axes) {
			addPropertyChangeListener(axis, listener);
		}
	}
}
