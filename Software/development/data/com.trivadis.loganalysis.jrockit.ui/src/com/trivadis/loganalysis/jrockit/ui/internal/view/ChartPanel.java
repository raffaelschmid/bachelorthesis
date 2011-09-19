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

import static java.util.Arrays.asList;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;

import com.trivadis.loganalysis.core.common.Assert;
import com.trivadis.loganalysis.core.domain.AbstractJvmRun;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.ui.internal.domain.profile.ValueProvider;
import com.trivadis.loganalysis.ui.common.GridDataBuilder;
import com.trivadis.loganalysis.ui.domain.profile.AxisType;
import com.trivadis.loganalysis.ui.domain.profile.IAxis;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.Serie;

public class ChartPanel extends Composite {
	private final XYSeriesCollection dataset = new XYSeriesCollection();
	private final JFreeChart jfreeChart;
	private final AtomicInteger seriesNr = new AtomicInteger(0);
	private final List<Color> colors = asList(Color.blue, Color.red, Color.black, Color.yellow, Color.cyan);
	private final JRockitJvmRun jvm;
	private final IChart chart;

	public ChartPanel(final Composite parent, final int style, final AbstractJvmRun jvm, final IChart chart) {
		super(parent, style);
		this.jvm = (JRockitJvmRun) jvm;
		setLayout(new GridLayout(1, false));
		jfreeChart = createChart(dataset, this, chart, chart.getLabel(), "x", "y");
		this.chart = chart;
		init();
		chart.addPropertyChangeListener("series", new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent evt) {
				if (evt.getOldValue() == null)
					serieAdded((Serie) evt.getNewValue());
				else
					serieRemoved((Serie) evt.getOldValue());
			}
		});
		chart.addPropertyChangeListener("label", new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent evt) {
				jfreeChart.setTitle((String) evt.getNewValue());
			}
		});
	}

	private void serieRemoved(final Serie serie) {
		final XYSeries toRemove = seriesmap.get(serie);
		Assert.assertNotNull(toRemove);
		dataset.removeSeries(toRemove);
		seriesmap.remove(serie);
	}

	private final Map<Serie, XYSeries> seriesmap = new HashMap<Serie, XYSeries>();

	private void serieAdded(final Serie serie) {
		final XYSeries series = getDataset(serie);
		dataset.addSeries(series);
		seriesmap.put(serie, series);
	}

	private void serieChanged(final Serie serie) {
		serieRemoved(serie);
		serieAdded(serie);
	}

	private void init() {
		for (final Serie s : chart.getSeries()) {
			s.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(final PropertyChangeEvent evt) {
					serieChanged(s);
				}
			});

			final XYSeries series = getDataset(s);
			dataset.addSeries(series);
			seriesmap.put(s, series);
		}
	}

	public XYSeries getDataset(final Serie serie) {
		XYSeries retVal = null;
		final List<IAxis> xAxes = serie.getAxes(AxisType.X);
		final List<IAxis> yAxes = serie.getAxes(AxisType.Y);

		if (xAxes.size() > 0 && yAxes.size() > 0) {
			final IAxis xAxis = xAxes.get(0);
			final IAxis yAxis = yAxes.get(0);

			final XYSeries series = new XYSeries(serie.getLabel());
			if (xAxis != null && yAxis != null) {
				for (final State state : jvm.getHeap().getStates()) {
					final BigDecimal x = ((ValueProvider) xAxis.getValueProvider()).data(state);
					final BigDecimal y = ((ValueProvider) yAxis.getValueProvider()).data(state);
					if (x != null && y != null)
						series.add(x, y);
				}
				retVal = series;
			}
		}
		return retVal;
	}

	private JFreeChart createChart(final XYDataset dataset, final Composite parent, final IChart data,
			final String chartName, final String xAxisLabel, final String yAxisLabel) {
		final JFreeChart chart = ChartFactory.createXYLineChart(chartName, xAxisLabel, yAxisLabel, dataset,
				PlotOrientation.VERTICAL, true, true, false);
		chart.setBackgroundPaint(Color.white);
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.lightGray);

		plot.setRenderer(seriesNr.get(), getRenderer(color()));

		final ChartComposite chartComposite = new ChartComposite(parent, SWT.NONE, chart, true);
		chartComposite.setLayoutData(new GridDataBuilder().fill().build());
		return chart;
	}

	public void addRangeAxis(final String yLabel, final XYDataset series) {
		final int index = seriesNr.incrementAndGet();

		final XYPlot plot = jfreeChart.getXYPlot();
		plot.setRenderer(index, getRenderer(color()));
		plot.setRangeAxis(index, new NumberAxis(yLabel));
		plot.setRangeAxisLocation(index, AxisLocation.BOTTOM_OR_RIGHT);
		plot.setDataset(index, series);
	}

	private Color color() {
		return colors.get(seriesNr.get());
	}

	private XYLineAndShapeRenderer getRenderer(final Color color) {
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, color);
		return renderer;
	}
}
