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
package com.trivadis.loganalysis.jrockit.ui.internal.view.heapusage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.xy.XYSeries;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.file.ValueType;
import com.trivadis.loganalysis.jrockit.ui.internal.view.Axis;

public class HeapUsageDataWrapper {
	private final JRockitJvmRun jvm;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private final Map<Axis, ValueType> axisSelection = new HashMap<Axis, ValueType>();

	public HeapUsageDataWrapper(JRockitJvmRun logFile) {
		this.jvm = logFile;
		axisSelection.put(Axis.X, ValueType.TIME);
		axisSelection.put(Axis.Y, ValueType.MEMORY);
	}

	public XYSeries getDataset() {
		ValueType xAxis = getAxisSelection(Axis.X);
		ValueType yAxis = getAxisSelection(Axis.Y);
		final XYSeries series = new XYSeries(xAxis + "/" + yAxis);
		if (jvm != null) {
			for (State state : jvm.getHeap().getStates()) {
				series.add(xAxis.data(state), yAxis.data(state));
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
}
