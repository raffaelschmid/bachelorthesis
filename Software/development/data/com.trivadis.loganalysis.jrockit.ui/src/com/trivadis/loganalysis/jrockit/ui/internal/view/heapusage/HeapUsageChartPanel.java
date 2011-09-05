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

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;

import com.trivadis.loganalysis.jrockit.ui.internal.view.Axis;

public class HeapUsageChartPanel extends Composite {

	private final XYSeriesCollection dataset;

	public HeapUsageChartPanel(Composite parent, int style,
			final HeapUsageDataWrapper logFileWrapper) {
		super(parent, style);
		setLayout(new GridLayout(1, false));

		PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				dataset.removeAllSeries();
				dataset.addSeries(logFileWrapper.getDataset());
			}
		};
		this.dataset = createChart(this, logFileWrapper.getDataset());
		logFileWrapper.addPropertyChangeListener(Axis.X, propertyChangeListener);
		logFileWrapper.addPropertyChangeListener(Axis.Y, propertyChangeListener);
		
		this.setLayoutData(GridLayoutUtil.fill());
	}

	private XYSeriesCollection createChart(Composite parent, XYSeries series) {
		final XYSeriesCollection dataset = new XYSeriesCollection(series);
		final JFreeChart chart = ChartFactory.createXYLineChart("Heap Usage",
				"Clock Time (seconds)", "Heap Usage (KBytes)", dataset, PlotOrientation.VERTICAL,
				true, true, false);
		chart.setBackgroundPaint(Color.white);
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRenderer(new XYLineAndShapeRenderer());
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		ChartComposite chartComposite = new ChartComposite(parent, SWT.NONE, chart, true);
		chartComposite.setLayoutData(GridLayoutUtil.fill());
		return dataset;
	}
}
