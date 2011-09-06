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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.file.ValueType;
import com.trivadis.loganalysis.jrockit.ui.Messages;
import com.trivadis.loganalysis.jrockit.ui.internal.view.Axis;
import com.trivadis.loganalysis.jrockit.ui.internal.view.ChartPanel;
import com.trivadis.loganalysis.jrockit.ui.internal.view.DataWrapper;
import com.trivadis.loganalysis.jrockit.ui.internal.view.JRockitAnalysisEditor;
import com.trivadis.loganalysis.ui.GridFormPage;
import com.trivadis.loganalysis.ui.GridLayoutUtil;

public class JRockitAnalysisEditorPageHeapUsage extends GridFormPage {

	public static final String ID = JRockitAnalysisEditorPageHeapUsage.class.getName();
	private JRockitJvmRun jvm;

	public JRockitAnalysisEditorPageHeapUsage(JRockitAnalysisEditor editor, JRockitJvmRun logFile) {
		super(editor, ID, Messages.JRockitAnalysisEditorPageHeapUsage_0, 1, 1);
		this.jvm = logFile;
	}

	protected void createSections(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		createDiagramSection(managedForm, toolkit);
	}

	private void createDiagramSection(IManagedForm managedForm, FormToolkit toolkit) {
		Composite composite = createGridSection(managedForm, Messages.JRockitAnalysisEditorPageHeapUsage_2,
				Messages.JRockitAnalysisEditorPageHeapUsage_1, 1, SWT.FILL, 800, true);

		DataWrapper data = new DataWrapper(jvm);
		data.addAxisSelection(Axis.X, ValueType.TIME);
		data.addAxisSelection(Axis.Y, ValueType.MEMORY);

		ChartPanel chartPanel = new ChartPanel(composite, SWT.BORDER, data, "Time (seconds)", "Memory (KB)");
		chartPanel.setLayoutData(GridLayoutUtil.fill());
	}
}