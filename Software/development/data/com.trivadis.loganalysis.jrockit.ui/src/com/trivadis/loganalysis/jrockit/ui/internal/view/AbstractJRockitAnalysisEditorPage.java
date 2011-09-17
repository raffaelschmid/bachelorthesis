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

import static org.eclipse.core.databinding.beans.BeanProperties.value;
import static org.eclipse.core.databinding.beans.BeansObservables.observeMaps;
import static org.eclipse.jface.databinding.swt.WidgetProperties.text;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.ui.internal.Messages;
import com.trivadis.loganalysis.jrockit.ui.internal.domain.profile.ValueProvider;
import com.trivadis.loganalysis.ui.AnalysisPage;
import com.trivadis.loganalysis.ui.GridLayoutUtil;
import com.trivadis.loganalysis.ui.domain.profile.Axis;
import com.trivadis.loganalysis.ui.domain.profile.AxisType;
import com.trivadis.loganalysis.ui.domain.profile.Chart;
import com.trivadis.loganalysis.ui.domain.profile.IAxis;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;
import com.trivadis.loganalysis.ui.domain.profile.IValueProvider;
import com.trivadis.loganalysis.ui.domain.profile.Serie;

public abstract class AbstractJRockitAnalysisEditorPage extends AnalysisPage {

	public static final String ID = AbstractJRockitAnalysisEditorPage.class.getName();
	private final JRockitJvmRun jvm;
	private final IChart chart;
	private ChartPanel chartPanel;

	public AbstractJRockitAnalysisEditorPage(final JRockitAnalysisEditor editor, final JRockitJvmRun logFile,
			final IProfile profile, final IChart chart) {
		super(editor, ID, chart.getTabName(), profile, chart);
		this.jvm = logFile;
		this.chart = chart;
	}

	protected void createCustomizationChart(final IManagedForm managedForm, final FormToolkit toolkit) {
		final Composite toolbar = toolkit.createComposite(managedForm.getForm().getBody());
		toolbar.setLayout(new GridLayout(1, false));
		final Composite section = createGridSection(managedForm, "Chart Customization", "", 6, true);

		final Table table = toolkit.createTable(section, SWT.NONE);
		final TableViewer tableViewer = new TableViewer(table);
		table.setLayoutData(GridLayoutUtil.fill());
		final Composite right = toolkit.createComposite(section, SWT.NONE);
		right.setLayout(new GridLayout(4, false));
		tableViewer.setContentProvider(new ObservableListContentProvider());
		final WritableList input = new WritableList(chart.getSeries(), Serie.class);
		tableViewer.setInput(input);
		tableViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				final String retVal;
				if (element instanceof Serie) {
					final Serie s = (Serie) element;
					final List<IAxis> xAxis = s.getAxes(AxisType.X);
					final List<IAxis> yAxis = s.getAxes(AxisType.Y);
					retVal = ((xAxis.size() > 0) ? xAxis.get(0).getValueProvider() : "") + "-" + ((yAxis.size() > 0) ? yAxis.get(0).getValueProvider() : "");
				} else {
					retVal = super.getText(element);
				}
				return retVal;
			}
		});
		tableViewer.getTable().setSize(300, 300);

		final Label lblXAxis = new Label(right, SWT.NONE);
		lblXAxis.setText("X-Axis:");
		final ComboViewer cboXAxis = new ComboViewer(right, SWT.DROP_DOWN | SWT.READ_ONLY);
		final ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		cboXAxis.setContentProvider(contentProvider);
		cboXAxis.setLabelProvider(new ObservableMapLabelProvider(observeMaps(contentProvider.getKnownElements(),
				ValueProvider.class, new String[] { "name" })));
		cboXAxis.setInput(new WritableList(Arrays.asList(ValueProvider.values()), ValueProvider.class));

		final Label lblYAxis = new Label(right, SWT.NONE);
		lblYAxis.setText("Y-Axis:");
		final ComboViewer cboYAxis = new ComboViewer(right, SWT.DROP_DOWN | SWT.READ_ONLY);
		final ObservableListContentProvider contentProviderY = new ObservableListContentProvider();
		cboYAxis.setContentProvider(contentProviderY);
		cboYAxis.setLabelProvider(new ObservableMapLabelProvider(observeMaps(contentProviderY.getKnownElements(),
				ValueProvider.class, new String[] { "name" })));
		cboYAxis.setInput(new WritableList(Arrays.asList(ValueProvider.values()), ValueProvider.class));

		new Label(right, SWT.NONE);
		final Composite button = toolkit.createComposite(right, SWT.NONE);
		button.setLayout(new FillLayout(SWT.HORIZONTAL));
		final GridData btnGd = new GridData();
		btnGd.horizontalSpan = 3;
		button.setLayoutData(btnGd);

		final Button btnRemoveSeries = new Button(button, SWT.NONE);
		btnRemoveSeries.setText("Remove Serie");
		btnRemoveSeries.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final ISelection selection = tableViewer.getSelection();
				if (selection instanceof StructuredSelection) {
					final Object element = ((StructuredSelection) selection).getFirstElement();
					input.remove(element);
					chartPanel.updateChart();
				}
			}

		});

		final Button btnAddSeries = new Button(button, SWT.NONE);
		btnAddSeries.setText("Add Serie");
		btnAddSeries.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {

				final ValueProvider xValue = (ValueProvider) getValue(cboXAxis);
				final ValueProvider yValue = (ValueProvider) getValue(cboYAxis);
				if (xValue != null && yValue != null) {
					input.add(new Serie(new Axis(AxisType.X, "", new Color(255, 0, 0), xValue), new Axis(
							AxisType.Y, "", new Color(255, 255, 0), yValue)));
					chartPanel.updateChart();
				}
			}

			protected IValueProvider getValue(final ComboViewer cboXAxis) {
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

	protected void createCustomizationGeneral(final IManagedForm managedForm, final FormToolkit toolkit) {
		final Composite toolbar = toolkit.createComposite(managedForm.getForm().getBody());
		toolbar.setLayout(new GridLayout(1, false));
		final Composite composite = createGridSection(managedForm, "General Customization", "", 2, true);
		final DataBindingContext ctx = new DataBindingContext();
		final Label label = new Label(composite, SWT.NONE);
		label.setText("Tab Name:");
		final Text name = new Text(composite, SWT.BORDER);
		ctx.bindValue(text(SWT.Modify).observe(name), value(Chart.class, "tabName").observe(chart));
	}

	protected void createDiagramSection(final IManagedForm managedForm, final FormToolkit toolkit) {
		final Composite toolbar = toolkit.createComposite(managedForm.getForm().getBody());
		toolbar.setLayout(new GridLayout(1, false));

		final Composite composite = createGridSection(managedForm, Messages.JRockitAnalysisEditorPageHeapUsage_2,
				chart.getDescription(), 1, SWT.FILL, 800, true);

		chartPanel = new ChartPanel(composite, SWT.BORDER, jvm, chart);
		chartPanel.setLayoutData(GridLayoutUtil.fill());
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {

	}
}