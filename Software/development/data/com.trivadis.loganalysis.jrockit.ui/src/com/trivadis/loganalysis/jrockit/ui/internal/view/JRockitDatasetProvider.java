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

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.findAll;

import java.awt.BasicStroke;
import java.awt.Color;
import java.math.BigDecimal;
import java.util.List;

import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.xy.XYSeries;

import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.core.domain.GarbageCollectionType;
import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.domain.gc.GarbageCollection;
import com.trivadis.loganalysis.jrockit.domain.gc.Transition;
import com.trivadis.loganalysis.jrockit.domain.gc.YoungCollection;
import com.trivadis.loganalysis.ui.IDatasetProvider;
import com.trivadis.loganalysis.ui.domain.profile.IAxis;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.Serie;

public class JRockitDatasetProvider implements IDatasetProvider {

	private final ClosureIO<State, Marker> collectmarker = new ClosureIO<State, Marker>() {
		public Marker call(final State in) {
			final Color color = in.getTransitionEnd() instanceof YoungCollection ? Color.blue : Color.red;
			return new ValueMarker(in.getTimestamp().getSeconds().doubleValue(), color, new BasicStroke());
		}
	};

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

	public List<Marker> getMarkers(final IJvmRun ijvm, final IChart chart, final GarbageCollectionType type) {
		final JRockitJvmRun jvm = (JRockitJvmRun) ijvm;
		return collect(findAll(jvm.getStates(), new Predicate<State>() {
			public boolean matches(final State in) {
				final Transition transition = in.getTransitionEnd();
				return notNull(transition) && type.equals(((GarbageCollection) transition).getType());
			}
		}), collectmarker);
	}

	private boolean notNull(final Transition transition) {
		return transition != null;
	}
}
