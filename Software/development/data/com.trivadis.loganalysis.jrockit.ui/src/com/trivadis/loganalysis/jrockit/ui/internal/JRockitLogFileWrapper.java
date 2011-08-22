package com.trivadis.loganalysis.jrockit.ui.internal;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.xy.XYSeries;

import com.trivadis.loganalysis.jrockit.domain.JRockitLog;
import com.trivadis.loganalysis.jrockit.domain.JRockitLogData;
import com.trivadis.loganalysis.jrockit.domain.Value;
import com.trivadis.loganalysis.jrockit.domain.ValueType;

public class JRockitLogFileWrapper {
	private final JRockitLog logFile;
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private Map<Axis, ValueType> axisSelection = new HashMap<Axis, ValueType>();

	public JRockitLogFileWrapper(JRockitLog logFile) {
		this.logFile = logFile;
		axisSelection.put(Axis.X, ValueType.TIME);
		axisSelection.put(Axis.Y, ValueType.MEMORY);
	}

	public XYSeries getDataset() {
		ValueType xAxis = getAxisSelection(Axis.X);
		ValueType yAxis = getAxisSelection(Axis.Y);
		final XYSeries series = new XYSeries(xAxis + "/" + yAxis);
		if (logFile != null) {
			for (JRockitLogData line : logFile.getData()) {
				Value y = line.get(yAxis);
				Value x = line.get(xAxis);
				if (y != null && x != null)
					series.add(x.toDouble(), y.toDouble());
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
