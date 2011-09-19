package com.trivadis.loganalysis.jrockit.ui.internal.view;

import java.math.BigDecimal;
import java.util.List;

import org.jfree.data.xy.XYSeries;

import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.ui.IDatasetProvider;
import com.trivadis.loganalysis.ui.domain.profile.AxisType;
import com.trivadis.loganalysis.ui.domain.profile.IAxis;
import com.trivadis.loganalysis.ui.domain.profile.Serie;

public class JRockitDatasetProvider implements IDatasetProvider {
	public XYSeries getDataset(final IJvmRun ijvm, final Object o) {
		final JRockitJvmRun jvm = (JRockitJvmRun) ijvm;
		final Serie serie = (Serie) o;
		XYSeries retVal = null;
		final List<IAxis> xAxes = serie.getAxes(AxisType.X);
		final List<IAxis> yAxes = serie.getAxes(AxisType.Y);

		if (xAxes.size() > 0 && yAxes.size() > 0) {
			final IAxis xAxis = xAxes.get(0);
			final IAxis yAxis = yAxes.get(0);

			final XYSeries series = new XYSeries(serie.getLabel());
			if (xAxis != null && yAxis != null) {
				for (final State state : jvm.getStates()) {
					final BigDecimal x = (xAxis.getValueProvider()).data(state);
					final BigDecimal y = (yAxis.getValueProvider()).data(state);
					if (x != null && y != null)
						series.add(x, y);
				}
				
				retVal = series;
			}
		}
		return retVal;
	}

}
