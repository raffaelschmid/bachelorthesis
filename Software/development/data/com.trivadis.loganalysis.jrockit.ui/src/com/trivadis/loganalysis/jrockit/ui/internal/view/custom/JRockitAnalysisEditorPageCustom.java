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
package com.trivadis.loganalysis.jrockit.ui.internal.view.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.ui.Messages;
import com.trivadis.loganalysis.jrockit.ui.domain.ValueProvider;
import com.trivadis.loganalysis.jrockit.ui.internal.view.ChartPanel;
import com.trivadis.loganalysis.jrockit.ui.internal.view.DataWrapper;
import com.trivadis.loganalysis.jrockit.ui.internal.view.JRockitAnalysisEditor;
import com.trivadis.loganalysis.jrockit.ui.internal.view.JRockitAnalysisEditorPage;
import com.trivadis.loganalysis.ui.GridFormPage;
import com.trivadis.loganalysis.ui.GridLayoutUtil;
import com.trivadis.loganalysis.ui.domain.profile.AxisType;

public class JRockitAnalysisEditorPageCustom extends GridFormPage {

	public static final String ID = JRockitAnalysisEditorPage.class.getName();
	private final JRockitJvmRun jvm;

	public JRockitAnalysisEditorPageCustom(final JRockitAnalysisEditor editor, final JRockitJvmRun logFile) {
		super(editor, ID, "Custom", 1, 1);
		this.jvm = logFile;
	}

	@Override
	protected void createSections(final IManagedForm managedForm) {
		final FormToolkit toolkit = managedForm.getToolkit();

		final DataWrapper logWrapper = new DataWrapper(jvm);
		logWrapper.addAxisSelection(AxisType.X, ValueProvider.TIME);
		logWrapper.addAxisSelection(AxisType.Y, ValueProvider.DURATION);

		createCustomizationSection(managedForm, toolkit, logWrapper);
		createDiagramSection(managedForm, toolkit, logWrapper);
	}

	private void createCustomizationSection(final IManagedForm managedForm, final FormToolkit toolkit,
			final DataWrapper logWrapper) {
		final Composite composite = createGridSection(managedForm, "Customize Labels:",
				"Within this section you are able to customize the chart below.", 1, true);

		// labels
		final Composite labels = new Composite(composite, SWT.NONE);
		labels.setLayout(new GridLayout(2, false));
		new Label(labels, SWT.NULL).setText("Tab Name");
		new Text(labels, SWT.BORDER).setText("Custom Diagram");

		// chart
		final Composite chart = new Composite(composite, SWT.NONE);
		chart.setLayout(new GridLayout(1, false));

	}

	private void createDiagramSection(final IManagedForm managedForm, final FormToolkit toolkit,
			final DataWrapper logWrapper) {
		final Composite composite = createGridSection(managedForm, Messages.JRockitAnalysisEditorPageDuration_1,
				Messages.JRockitAnalysisEditorPageDuration_2, 1, SWT.FILL, 700, true);
		composite.setLayout(new GridLayout(1, false));

		final ChartPanel chartPanel = new ChartPanel(composite, SWT.BORDER, logWrapper, "CustomX", "CustomY");
		chartPanel.setLayoutData(GridLayoutUtil.fill());
		chartPanel.addRangeAxis("foo", getSeries(0, "Test A"));
		chartPanel.addRangeAxis("bar", getSeries(1, "Test B"));
	}

	private XYSeriesCollection getSeries(final int start, final String seriesTitle) {
		final XYSeries xySeries = new XYSeries(seriesTitle);
		int i = start;
		xySeries.add(i, i++);
		xySeries.add(i, i++);
		xySeries.add(i, i++);
		xySeries.add(i, i++);
		xySeries.add(i, i++);
		final XYSeriesCollection test = new XYSeriesCollection();
		test.addSeries(xySeries);
		return test;
	}

}
