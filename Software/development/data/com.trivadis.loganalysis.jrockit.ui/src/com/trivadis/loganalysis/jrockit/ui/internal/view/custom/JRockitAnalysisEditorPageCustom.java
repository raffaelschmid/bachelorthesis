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
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.file.ValueType;
import com.trivadis.loganalysis.jrockit.ui.Messages;
import com.trivadis.loganalysis.jrockit.ui.internal.view.Axis;
import com.trivadis.loganalysis.jrockit.ui.internal.view.ChartPanel;
import com.trivadis.loganalysis.jrockit.ui.internal.view.DataWrapper;
import com.trivadis.loganalysis.jrockit.ui.internal.view.JRockitAnalysisEditor;
import com.trivadis.loganalysis.jrockit.ui.internal.view.heapusage.JRockitAnalysisEditorPageHeapUsage;
import com.trivadis.loganalysis.ui.GridFormPage;
import com.trivadis.loganalysis.ui.GridLayoutUtil;

public class JRockitAnalysisEditorPageCustom extends GridFormPage {

	public static final String ID = JRockitAnalysisEditorPageHeapUsage.class.getName();
	private JRockitJvmRun jvm;

	public JRockitAnalysisEditorPageCustom(JRockitAnalysisEditor editor, JRockitJvmRun logFile) {
		super(editor, ID, "Custom", 1, 1);
		this.jvm = logFile;
	}

	protected void createSections(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();

		DataWrapper logWrapper = new DataWrapper(jvm);
		logWrapper.addAxisSelection(Axis.X, ValueType.TIME);
		logWrapper.addAxisSelection(Axis.Y, ValueType.DURATION);

		createDiagramSection(managedForm, toolkit, logWrapper);
	}

	private void createDiagramSection(IManagedForm managedForm, FormToolkit toolkit, DataWrapper logWrapper) {
		Composite composite = createGridSection(managedForm, Messages.JRockitAnalysisEditorPageDuration_1,
				Messages.JRockitAnalysisEditorPageDuration_2, 1, SWT.FILL, 700, true);
		composite.setLayout(new GridLayout(1, false));

		ChartPanel chartPanel = new ChartPanel(composite, SWT.BORDER, logWrapper, "CustomX", "CustomY");
		chartPanel.setLayoutData(GridLayoutUtil.fill());
		chartPanel.addRangeAxis("foo", getSeries(0, "Test A"));
		chartPanel.addRangeAxis("bar", getSeries(1, "Test B"));
	}

	private XYSeriesCollection getSeries(int start, String seriesTitle) {
		XYSeries xySeries = new XYSeries(seriesTitle);
		int i = start;
		xySeries.add(i, i++);
		xySeries.add(i, i++);
		xySeries.add(i, i++);
		xySeries.add(i, i++);
		xySeries.add(i, i++);
		XYSeriesCollection test = new XYSeriesCollection();
		test.addSeries(xySeries);
		return test;
	}

}
