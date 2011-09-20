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

import static com.trivadis.loganalysis.ui.common.CompositeBuilder.composite;
import static com.trivadis.loganalysis.ui.common.binding.BindingUtil.bindCheckbox;

import java.awt.Color;
import java.util.Arrays;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.trivadis.loganalysis.jrockit.ui.internal.domain.profile.StateValueProvider;
import com.trivadis.loganalysis.ui.common.FillLayoutBuilder;
import com.trivadis.loganalysis.ui.common.GridDataBuilder;
import com.trivadis.loganalysis.ui.domain.profile.Axis;
import com.trivadis.loganalysis.ui.domain.profile.AxisType;
import com.trivadis.loganalysis.ui.domain.profile.Chart;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IValueProvider;
import com.trivadis.loganalysis.ui.domain.profile.Serie;

public class ChartCustomizationPanel extends Composite {
	private static final int MAX_NUMBER_OF_SERIES = 4;
	private final WritableList series;
	private final Text txtLabel;

	public ChartCustomizationPanel(final Composite section, final int style, final FormToolkit toolkit,
			final IChart chart) {
		super(section, style);
		this.setLayout(new GridLayout(2, false));
		final Composite left = toolkit.createComposite(this, SWT.BORDER);
		left.setLayout(new GridLayout(1, false));
		left.setLayoutData(new GridDataBuilder().heightHint(220).build());
		new Label(left, SWT.BOLD).setText("Series:");
		series = new WritableList(chart.getSeries(), Serie.class);
		final TableViewer tableViewer = tableSeries(left, toolkit);

		final Button chkShowOldCollections = new Button(left, SWT.CHECK);
		chkShowOldCollections.setSelection(false);
		chkShowOldCollections.setText("Old Collections");

		final Button chkShowYoungCollections = new Button(left, SWT.CHECK);
		chkShowYoungCollections.setSelection(false);
		chkShowYoungCollections.setText("Young Collections");

		bindCheckbox(chkShowYoungCollections, Chart.class, chart,"showYoungCollections");
		bindCheckbox(chkShowOldCollections, Chart.class, chart,"showOldCollections");

		new Label(left, SWT.NONE).setLayoutData(new GridDataBuilder().fillVertical().build());
		tableViewer.getTable().setLayoutData(new GridDataBuilder().fillVertical().build());
		final Composite right = toolkit.createComposite(this, SWT.BORDER);
		right.setLayoutData(new GridDataBuilder().heightHint(220).build());
		right.setLayout(new GridLayout(2, false));

		final Label lblTitleRight = new Label(right, SWT.BOLD);
		lblTitleRight.setText("Add Serie:");
		lblTitleRight.setLayoutData(new GridDataBuilder().horizontalSpan(2).build());
		new Label(right, SWT.NONE).setText("Label:");
		txtLabel = new Text(right, SWT.BORDER);

		new Label(right, SWT.NONE).setText("X-Axis:");
		final ComboViewer cboXAxis = comboAxisSelection(right, "X-Axis:");

		new Label(right, SWT.NONE).setText("Y-Axis:");
		final ComboViewer cboYAxis = comboAxisSelection(right, "Y-Axis:");

		new Label(right, SWT.NONE).setText("");
		final Composite pnlCheckboxes = composite(right, SWT.NONE, new FillLayoutBuilder().vertical());
		final Button chkLine = new Button(pnlCheckboxes, SWT.CHECK);
		chkLine.setSelection(true);
		chkLine.setText("line");

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
		new Label(right, SWT.NONE).setLayoutData(new GridDataBuilder().horizontalSpan(2).fill().build());
		new Label(right, SWT.NONE);
		button(chart, right, "Add Serie", new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final StateValueProvider xValue = (StateValueProvider) getValue(cboXAxis);
				final StateValueProvider yValue = (StateValueProvider) getValue(cboYAxis);
				if (areAxisSelected(xValue, yValue) && isLabelNotEmpty() && hasSpace()) {
					final Serie serie = new Serie(txtLabel.getText(), new Axis(AxisType.X, "", new Color(255, 0, 0),
							xValue), new Axis(AxisType.Y, "", new Color(255, 255, 0), yValue));
					serie.setDotted(!chkLine.getSelection());
					series.add(serie);
					chart.added(serie);
				}
			}

			protected boolean hasSpace() {
				return series.size() < MAX_NUMBER_OF_SERIES;
			}

			protected boolean isLabelNotEmpty() {
				return txtLabel.getText() != null && !txtLabel.getText().equals("");
			}

			protected boolean areAxisSelected(final StateValueProvider xValue, final StateValueProvider yValue) {
				return xValue != null && yValue != null;
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
		final ComboViewer cboXAxis = new ComboViewer(right, SWT.DROP_DOWN | SWT.READ_ONLY);
		final ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		cboXAxis.setContentProvider(contentProvider);
		cboXAxis.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				final String retVal;
				if (element instanceof IValueProvider) {
					final IValueProvider vp = (IValueProvider) element;
					retVal = vp.getLabel() + " (" + vp.getUnit() + ")";
				} else {
					retVal = super.getText(element);
				}
				return retVal;
			}
		});
		cboXAxis.setInput(new WritableList(Arrays.asList(StateValueProvider.values()), StateValueProvider.class));
		return cboXAxis;
	}

	private TableViewer tableSeries(final Composite section, final FormToolkit toolkit) {
		final Table table = toolkit.createTable(section, SWT.NONE);
		table.setLayoutData(new GridDataBuilder().fill().build());
		final TableViewer tableViewer = new TableViewer(table);
		final TableViewerColumn viewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerNameColumn.getColumn().setWidth(300);

		viewerNameColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(final ViewerCell cell) {
				cell.setText(calculateLabel((Serie) cell.getElement()));
			}

			private String calculateLabel(final Serie serie) {
				return serie.getLabel() + " (" + serie.getXaxis().getValueProvider().getLabel() + " / "
						+ serie.getYaxis().getValueProvider().getLabel() + ")";
			}
		});
		tableViewer.setContentProvider(new ObservableListContentProvider());
		tableViewer.setInput(series);
		return tableViewer;
	}
}
