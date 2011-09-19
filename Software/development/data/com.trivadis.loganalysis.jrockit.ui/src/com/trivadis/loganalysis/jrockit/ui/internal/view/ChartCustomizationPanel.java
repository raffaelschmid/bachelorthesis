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

import static org.eclipse.core.databinding.beans.BeansObservables.observeMaps;

import java.awt.Color;
import java.util.Arrays;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.trivadis.loganalysis.jrockit.ui.internal.domain.profile.ValueProvider;
import com.trivadis.loganalysis.ui.common.GridDataBuilder;
import com.trivadis.loganalysis.ui.domain.profile.Axis;
import com.trivadis.loganalysis.ui.domain.profile.AxisType;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IValueProvider;
import com.trivadis.loganalysis.ui.domain.profile.Serie;

public class ChartCustomizationPanel extends Composite {
	private final WritableList series;
	private final ComboViewer cboXAxis, cboYAxis;
	private final Text txtLabel;

	public ChartCustomizationPanel(final Composite section, final int style, final FormToolkit toolkit,
			final IChart chart) {
		super(section, style);
		this.setLayout(new GridLayout(2, false));
		final Composite left = toolkit.createComposite(this, SWT.BORDER);
		left.setLayout(new GridLayout(1, false));
		left.setLayoutData(new GridDataBuilder().fillVertical().build());
		series = new WritableList(chart.getSeries(), Serie.class);
		new Label(left, SWT.NONE).setText("Series:");
		final TableViewer tableViewer = tableSeries(left, toolkit);
		tableViewer.getTable().setLayoutData(new GridDataBuilder().fillVertical().build());
		final Composite right = toolkit.createComposite(this, SWT.BORDER);
		right.setLayoutData(new GridDataBuilder().fillVertical().build());
		right.setLayout(new GridLayout(1, false));
		final Label lblLabel = new Label(right, SWT.NONE);
		lblLabel.setText("Label:");
		txtLabel = new Text(right, SWT.BORDER);
		txtLabel.setLayoutData(new GridDataBuilder().fill().build());
		cboXAxis = comboAxisSelection(right, "X-Axis:");
		cboXAxis.getCombo().setLayoutData(new GridDataBuilder().fill().build());
		cboYAxis = comboAxisSelection(right, "Y-Axis:");
		cboYAxis.getCombo().setLayoutData(new GridDataBuilder().fill().build());
		new Label(right, SWT.NONE);
		final Composite buttonPanel = toolkit.createComposite(right, SWT.NONE);
		buttonPanel.setLayout(new FillLayout(SWT.HORIZONTAL));
		button(chart, left, "Remove Serie", new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final ISelection selection = tableViewer.getSelection();
				if (selection instanceof StructuredSelection) {
					final Serie element = (Serie) ((StructuredSelection) selection).getFirstElement();
					if (element != null) {
						series.remove(element);
						chart.removed(element);
					}
				}
			}

		});

		button(chart, buttonPanel, "Add Serie", new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final ValueProvider xValue = (ValueProvider) getValue(cboXAxis);
				final ValueProvider yValue = (ValueProvider) getValue(cboYAxis);
				if (xValue != null && yValue != null) {
					if(txtLabel.getText()!=null && !txtLabel.getText().equals(""));
					final Serie serie = new Serie(txtLabel.getText(), new Axis(AxisType.X, "", new Color(255, 0, 0),
							xValue), new Axis(AxisType.Y, "", new Color(255, 255, 0), yValue));
					series.add(serie);
					chart.added(serie);
				}
			}

			private IValueProvider getValue(final ComboViewer cboXAxis) {
				IValueProvider retVal = null;
				final ISelection selection = cboXAxis.getSelection();
				if (selection instanceof StructuredSelection) {
					final StructuredSelection ss = (StructuredSelection) selection;
					retVal = (IValueProvider) ss.getFirstElement();
				}
				return retVal;
			}

		});
	}

	protected void button(final IChart chart, final Composite buttonPanel, final String label,
			final SelectionListener listener) {
		final Button btnAddSeries = new Button(buttonPanel, SWT.NONE);
		btnAddSeries.setText(label);
		btnAddSeries.addSelectionListener(listener);
	}

	private ComboViewer comboAxisSelection(final Composite right, final String label) {
		final Label lblXAxis = new Label(right, SWT.NONE);
		lblXAxis.setText(label);

		final ComboViewer cboXAxis = new ComboViewer(right, SWT.DROP_DOWN | SWT.READ_ONLY);
		final ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		cboXAxis.setContentProvider(contentProvider);
		cboXAxis.setLabelProvider(new ObservableMapLabelProvider(observeMaps(contentProvider.getKnownElements(),
				ValueProvider.class, new String[] { "name" })));
		cboXAxis.setInput(new WritableList(Arrays.asList(ValueProvider.values()), ValueProvider.class));
		return cboXAxis;
	}

	private TableViewer tableSeries(final Composite section, final FormToolkit toolkit) {
		final Table table = toolkit.createTable(section, SWT.NONE);
		final TableViewer tableViewer = new TableViewer(table);
		tableViewer.setContentProvider(new ObservableListContentProvider());
		tableViewer.setInput(series);
		tableViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				final String retVal;
				if (element instanceof Serie) {
					return ((Serie) element).getLabel();
				} else {
					retVal = super.getText(element);
				}
				return retVal;
			}
		});
		return tableViewer;
	}

}
