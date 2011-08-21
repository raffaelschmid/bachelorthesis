package com.trivadis.loganalysis.jrockit.ui.internal;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;

public class HeapUsageChartPanel extends Composite {

	private final JRockitLogFileWrapper logFileWrapper;
	private final XYSeriesCollection dataset;

	public HeapUsageChartPanel(Composite parent, int style, final JRockitLogFileWrapper logFileWrapper) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		this.logFileWrapper = logFileWrapper;

		PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				dataset.removeAllSeries();
				dataset.addSeries(logFileWrapper.getDataset());
			}
		};
		this.dataset = createChart(this);
		

		logFileWrapper.addPropertyChangeListener(Axis.X, propertyChangeListener);
		logFileWrapper.addPropertyChangeListener(Axis.Y, propertyChangeListener);
	}

	private XYSeriesCollection createChart(Composite parent) {
		final XYSeriesCollection dataset = new XYSeriesCollection(logFileWrapper.getDataset());
		final JFreeChart chart = ChartFactory.createXYLineChart("Heap Usage", "Clock Time (seconds)",
				"Heap Usage (MBytes)", dataset, PlotOrientation.VERTICAL, true, true, false);
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
		chartComposite.setLayoutData(fill());
		return dataset;
	}

	private GridData fill() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		return gridData;
	}

}
