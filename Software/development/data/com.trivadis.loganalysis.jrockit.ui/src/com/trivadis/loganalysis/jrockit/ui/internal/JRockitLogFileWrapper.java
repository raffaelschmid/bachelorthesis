package com.trivadis.loganalysis.jrockit.ui.internal;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.xy.XYSeries;

import com.trivadis.loganalysis.jrockit.domain.DataLine;
import com.trivadis.loganalysis.jrockit.domain.JRockitLogFile;
import com.trivadis.loganalysis.jrockit.domain.MetaInfo;
import com.trivadis.loganalysis.jrockit.domain.Value;

public class JRockitLogFileWrapper {
	private final JRockitLogFile logFile;
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private Map<Axis, MetaInfo> axisSelection = new HashMap<Axis, MetaInfo>();

	public JRockitLogFileWrapper(JRockitLogFile logFile) {
		this.logFile = logFile;
		axisSelection.put(Axis.X, MetaInfo.TIME);
		axisSelection.put(Axis.Y, MetaInfo.MEMORY);
	}

	public XYSeries getDataset() {
		MetaInfo xAxis = getAxisSelection(Axis.X);
		MetaInfo yAxis = getAxisSelection(Axis.Y);
		final XYSeries series1 = new XYSeries(xAxis + "/" + yAxis);
		if (logFile != null) {
			for (DataLine line : logFile.getData()) {
				Value y = line.get(yAxis);
				Value x = line.get(xAxis);
				if (y != null && x != null)
					series1.add(x.toDouble(), y.toDouble());
			}

		}
		return series1;
	}

	public MetaInfo getAxisSelection(Axis axis) {
		return axisSelection.get(axis);
	}

	public void addPropertyChangeListener(Axis propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName.toString(), listener);
	}

	public void setSelection(Axis axis, MetaInfo selection) {
		MetaInfo oldValue = axisSelection.get(axis);
		axisSelection.put(axis, selection);
		this.propertyChangeSupport.firePropertyChange(axis.toString(), oldValue, selection);
	}
}
