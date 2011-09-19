package com.trivadis.loganalysis.jrockit.ui.internal.view;

import java.math.BigDecimal;

import org.jfree.data.xy.XYSeries;

import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.ui.IDatasetProvider;
import com.trivadis.loganalysis.ui.domain.profile.IAxis;
import com.trivadis.loganalysis.ui.domain.profile.Serie;

public class JRockitDatasetProvider implements IDatasetProvider {
	public XYSeries getDataset(final IJvmRun ijvm, final Serie serie) {
		final JRockitJvmRun jvm = (JRockitJvmRun) ijvm;
		XYSeries retVal = null;
		final IAxis xAxis = serie.getXaxis();
		final IAxis yAxis = serie.getYaxis();

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
		return retVal;
	}

}
