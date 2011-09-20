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
package com.trivadis.loganalysis.ui;

import static java.util.Arrays.asList;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;

import com.trivadis.loganalysis.core.domain.GarbageCollectionType;
import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.ui.common.GridDataBuilder;
import com.trivadis.loganalysis.ui.domain.profile.Chart;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.Serie;

public final class ChartPanel extends Composite {
	private final JFreeChart jfreeChart;
	private final AtomicInteger seriesSequence = new AtomicInteger(0);
	private final List<Color> colors = asList(Color.blue, Color.red, Color.green, Color.darkGray);
	private final IJvmRun jvm;
	private final IChart chart;
	private final IDatasetProvider datasetProvider;
	private final PropertyChangeListener seriesListener = new PropertyChangeListener() {
		public void propertyChange(final PropertyChangeEvent evt) {
			if (evt.getOldValue() == null)
				add((Serie) evt.getNewValue());
			else
				remove((Serie) evt.getOldValue());
		}
	};
	private final PropertyChangeListener labelListener = new PropertyChangeListener() {
		public void propertyChange(final PropertyChangeEvent evt) {
			jfreeChart.setTitle((String) evt.getNewValue());
		}
	};

	private final PropertyChangeListener oldCollectionListener;
	private final PropertyChangeListener youngCollectionListener;
	private final Map<GarbageCollectionType, List<Marker>> markersByType;

	public ChartPanel(final Composite parent, final int style, final IJvmRun jvm, final IChart chart,
			final IDatasetProvider datasetProvider) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		this.jvm = jvm;
		jfreeChart = createChart(null, this, chart, chart.getLabel(), "x", "y");
		this.chart = chart;
		this.datasetProvider = datasetProvider;

		oldCollectionListener = new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent evt) {
				final boolean showOldCollection = (Boolean) evt.getNewValue();
				if (showOldCollection) {
					addMarkers(GarbageCollectionType.OLD);
				} else {
					removeMarkers(GarbageCollectionType.OLD);
				}
			}
		};
		youngCollectionListener = new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent evt) {
				final boolean showYoungCollection = (Boolean) evt.getNewValue();
				if (showYoungCollection) {
					addMarkers(GarbageCollectionType.YOUNG);
				} else {
					removeMarkers(GarbageCollectionType.YOUNG);
				}

			}
		};
		markersByType = new HashMap<GarbageCollectionType, List<Marker>>();
		markersByType.put(GarbageCollectionType.OLD, datasetProvider.getMarkers(jvm, chart, GarbageCollectionType.OLD));
		markersByType.put(GarbageCollectionType.YOUNG,
				datasetProvider.getMarkers(jvm, chart, GarbageCollectionType.YOUNG));

		initializeChart();
	}

	private void addMarkers(final GarbageCollectionType type) {
		final XYPlot plot = jfreeChart.getXYPlot();
		removeMarkers(type);
		for (final Marker marker : markersByType.get(type)) {
			plot.addDomainMarker(marker);
		}
	}

	private void removeMarkers(final GarbageCollectionType type) {
		final XYPlot plot = jfreeChart.getXYPlot();
		for (final Marker marker : markersByType.get(type)) {
			plot.removeDomainMarker(marker);
		}
	}

	private void initializeChart() {
		final XYPlot plot = (XYPlot) jfreeChart.getPlot();
		plot.setRangeAxis(0, null);
		plot.setDomainAxis(0, null);
		for (final Serie serie : chart.getSeries()) {
			add(serie);
		}
		
		if(chart.isShowOldCollections())
			addMarkers(GarbageCollectionType.OLD);
		if(chart.isShowYoungCollections())
			addMarkers(GarbageCollectionType.YOUNG);
		chart.addPropertyChangeListener(Chart.PROPERTY_SERIES, seriesListener);
		chart.addPropertyChangeListener(Chart.PROPERTY_LABEL, labelListener);
		chart.addPropertyChangeListener(Chart.PROPERTY_SHOW_OC, oldCollectionListener);
		chart.addPropertyChangeListener(Chart.PROPERTY_SHOW_YC, youngCollectionListener);

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

		final ChartComposite chartComposite = new ChartComposite(parent, SWT.NONE, chart, true);
		chartComposite.setLayoutData(new GridDataBuilder().fill().build());
		return chart;
	}

	private void remove(final Serie serie) {
		final int index = serie.getIndex();
		if (index >= 0 && !isDisposed()) {
			final XYPlot plot = (XYPlot) jfreeChart.getPlot();
			removeDataset(index, plot);
			removeRangeAxis(index, plot);
			removeDomainAxis(index, plot);
			seriesSequence.decrementAndGet();
		}
	}

	protected void removeDomainAxis(final int index, final XYPlot plot) {
		plot.setDomainAxis(index, null);
	}

	protected void removeRangeAxis(final int index, final XYPlot plot) {
		plot.setRangeAxis(index, null);
	}

	/**
	 * This method first removes the change listener, otherwise the change
	 * listener will act on a already disposed widget.
	 * 
	 * @param index
	 * @param plot
	 */
	private void removeDataset(final int index, final XYPlot plot) {
		final XYDataset existing = plot.getDataset(index);
		if (existing != null)
			existing.removeChangeListener(plot);
		plot.setDataset(index, null);
	}

	private void add(final Serie serie) {
		if (!isDisposed()) {
			serie.setIndex(seriesSequence.get());
			final XYSeriesCollection dataset = new XYSeriesCollection(datasetProvider.getDataset(jvm, serie));
			final int index = serie.getIndex();
			final XYPlot plot = (XYPlot) jfreeChart.getPlot();
			final Color color = getColor(index);
			removeDataset(index, plot);
			plot.setDataset(index, dataset);
			plot.setRangeAxis(index, axis(color, serie.getYaxis().getValueProvider().getUnit()));
			plot.setDomainAxis(index, axis(color, serie.getXaxis().getValueProvider().getUnit()));
			plot.mapDatasetToDomainAxis(index, index);
			plot.mapDatasetToRangeAxis(index, index);
			plot.setRenderer(index, getRenderer(color, serie.isDotted()));
			seriesSequence.incrementAndGet();
		}
	}

	private NumberAxis axis(final Color color, final String label) {
		final NumberAxis yNumberAxis = new NumberAxis(label);
		yNumberAxis.setTickLabelPaint(color);
		yNumberAxis.setLabelPaint(color);
		return yNumberAxis;
	}

	private Color getColor(final int index) {
		return colors.get(index % colors.size());
	}

	private XYItemRenderer getRenderer(final Color color, final boolean dotted) {
		final XYItemRenderer renderer = dotted ? dotRenderer() : lineRenderer();
		renderer.setSeriesPaint(0, color);
		return renderer;
	}

	protected XYLineAndShapeRenderer lineRenderer() {
		return new XYLineAndShapeRenderer();
	}

	protected XYDotRenderer dotRenderer() {
		final XYDotRenderer renderer = new XYDotRenderer();
		renderer.setDotHeight(4);
		renderer.setDotWidth(4);
		return renderer;
	}

	@Override
	public void dispose() {
		chart.removePropertyChangeListener(Chart.PROPERTY_LABEL, labelListener);
		chart.removePropertyChangeListener(Chart.PROPERTY_SERIES, seriesListener);
		chart.removePropertyChangeListener(Chart.PROPERTY_SHOW_OC, oldCollectionListener);
		chart.removePropertyChangeListener(Chart.PROPERTY_SHOW_YC, youngCollectionListener);

		super.dispose();
	}
}
