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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.ui.internal.Messages;
import com.trivadis.loganalysis.ui.GridFormPage;
import com.trivadis.loganalysis.ui.GridLayoutUtil;
import com.trivadis.loganalysis.ui.domain.profile.IAxis;
import com.trivadis.loganalysis.ui.domain.profile.IChart;

public class JRockitAnalysisEditorPage extends GridFormPage {

	public static final String ID = JRockitAnalysisEditorPage.class.getName();
	private final JRockitJvmRun jvm;
	private final IChart chart;

	public JRockitAnalysisEditorPage(final JRockitAnalysisEditor editor, final JRockitJvmRun logFile, final IChart chart) {
		super(editor, ID, chart.getLabel(), 1, 1);
		this.jvm = logFile;
		this.chart = chart;
	}

	@Override
	protected void createSections(final IManagedForm managedForm) {
		final FormToolkit toolkit = managedForm.getToolkit();
		createDiagramSection(managedForm, toolkit);
	}

	private void createDiagramSection(final IManagedForm managedForm, final FormToolkit toolkit) {
		final Composite composite = createGridSection(managedForm, Messages.JRockitAnalysisEditorPageHeapUsage_2,
				chart.getDescription(), 1, SWT.FILL, 800, true);

		final DataWrapper data = new DataWrapper(jvm);

		for(final IAxis axis : chart.getAxes()){
			data.addAxisSelection(axis.getAxisType(), axis.getValueProvider());
		}

		final ChartPanel chartPanel = new ChartPanel(composite, SWT.BORDER, data, chart.getXLabel(), chart.getYLabel());
		chartPanel.setLayoutData(GridLayoutUtil.fill());
	}
}