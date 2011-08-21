package com.trivadis.loganalysis.jrockit.ui.internal;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.xy.XYSeries;

import com.trivadis.loganalysis.jrockit.domain.DataExtractor;
import com.trivadis.loganalysis.jrockit.domain.DataLine;
import com.trivadis.loganalysis.jrockit.domain.JRockitLogFile;

public class JRockitLogFileWrapper {
	private final JRockitLogFile logFile;
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private Map<Axis, DataExtractor> axisSelection = new HashMap<Axis, DataExtractor>();

	public JRockitLogFileWrapper(JRockitLogFile logFile) {
		this.logFile = logFile;
		axisSelection.put(Axis.X, DataExtractor.START_TIME);
		axisSelection.put(Axis.Y, DataExtractor.MEMORY_AFTER);
	}

	public XYSeries getDataset() {
		final XYSeries series1 = new XYSeries("First");
		if (logFile != null) {
			for (DataLine line : logFile.getData()) {
				Double startTime = line.getValue(getAxisSelection(Axis.X)).toDouble();
				Double memoryBefore = line.getValue(getAxisSelection(Axis.Y)).toDouble();
				series1.add(startTime, memoryBefore);
			}

		}
		return series1;
	}

	public DataExtractor getAxisSelection(Axis axis) {
		return axisSelection.get(axis);
	}

	public void addPropertyChangeListener(Axis propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName.toString(), listener);
	}

	public void setSelection(Axis axis, DataExtractor selection) {
		DataExtractor oldValue = axisSelection.get(axis);
		axisSelection.put(axis, selection);
		this.propertyChangeSupport.firePropertyChange(axis.toString(), oldValue, selection);
	}
}
