package com.trivadis.loganalysis.jrockit.ui;

import java.awt.Color;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
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

import com.trivadis.loganalysis.jrockit.domain.DataExtractor;
import com.trivadis.loganalysis.jrockit.domain.DataLine;
import com.trivadis.loganalysis.jrockit.domain.JRockitLogFile;

public class PageHeapUsageAfterGC extends Composite {

	public PageHeapUsageAfterGC(Composite parent, int style, JRockitLogFile logFile) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		new ChartComposite(this, SWT.NONE, createChart(createDataset(logFile)), true);
	}

	private XYSeriesCollection createDataset(JRockitLogFile logFile) {
		Assert.isNotNull(logFile);
		final XYSeries series1 = new XYSeries("First");
		for (DataLine line : logFile.getData()) {
			Double startTime = line.getValue(DataExtractor.START_TIME).toDouble();
			Double memoryBefore = line.getValue(DataExtractor.MEMORY_BEFORE).toDouble();
			Double endTime = line.getValue(DataExtractor.END_TIME).toDouble();
			Double memoryAfter = line.getValue(DataExtractor.MEMORY_AFTER).toDouble();

			series1.add(startTime, memoryBefore);
			series1.add(endTime, memoryAfter);
		}

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);

		return dataset;
	}

	private JFreeChart createChart(XYSeriesCollection dataset) {

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
		return chart;

	}

}
