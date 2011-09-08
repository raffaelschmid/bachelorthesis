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
import java.util.List;
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
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;

import com.trivadis.loganalysis.ui.GridLayoutUtil;
import com.trivadis.loganalysis.ui.domain.profile.AxisType;

public class ChartPanel extends Composite {
	private final XYSeriesCollection dataset = new XYSeriesCollection();
	private final JFreeChart chart;
	private final AtomicInteger seriesNr = new AtomicInteger(0);
	private final List<Color> colors = asList(Color.blue, Color.red, Color.black, Color.yellow, Color.cyan);

	public ChartPanel(final Composite parent, final int style, final DataWrapper data, final String labelXAxis, final String lableYAxis) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		chart = createChart(dataset, this, data, "Heap Usage", labelXAxis, lableYAxis);
		data.addPropertyChangeListeners(createPropertyChangeListener(data), AxisType.X, AxisType.Y);
		updateChart(data);
	}

	private PropertyChangeListener createPropertyChangeListener(final DataWrapper data) {
		final PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent evt) {
				updateChart(data);
			}
		};
		return propertyChangeListener;
	}

	private void updateChart(final DataWrapper data) {
		dataset.removeAllSeries();
		dataset.addSeries(data.getDataset());
	}

	private JFreeChart createChart(final XYDataset dataset, final Composite parent, final DataWrapper data, final String chartName,
			final String xAxisLabel, final String yAxisLabel) {
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
		chartComposite.setLayoutData(GridLayoutUtil.fill());
		return chart;
	}

	public void addRangeAxis(final String yLabel, final XYDataset series) {
		final int index = seriesNr.incrementAndGet();

		final XYPlot plot = chart.getXYPlot();
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
