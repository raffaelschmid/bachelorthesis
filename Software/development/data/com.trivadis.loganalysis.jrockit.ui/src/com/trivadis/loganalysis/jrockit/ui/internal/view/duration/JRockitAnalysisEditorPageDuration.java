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
package com.trivadis.loganalysis.jrockit.ui.internal.view.duration;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
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
import com.trivadis.loganalysis.jrockit.ui.internal.view.heapusage.GridLayoutUtil;
import com.trivadis.loganalysis.jrockit.ui.internal.view.heapusage.JRockitAnalysisEditorPageHeapUsage;
import com.trivadis.loganalysis.ui.GridFormPage;

public class JRockitAnalysisEditorPageDuration extends GridFormPage {

	public static final String ID = JRockitAnalysisEditorPageHeapUsage.class.getName();
	private JRockitJvmRun jvm;

	public JRockitAnalysisEditorPageDuration(JRockitAnalysisEditor editor, JRockitJvmRun logFile) {
		super(editor, ID, Messages.JRockitAnalysisEditorPageDuration_0, 1, 1);
		this.jvm = logFile;
	}

	protected void createSections(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		createGeneralSection(managedForm, toolkit);

	}

	private void createGeneralSection(IManagedForm managedForm, FormToolkit toolkit) {
		Composite composite = createGridSection(managedForm, Messages.JRockitAnalysisEditorPageDuration_1,
				Messages.JRockitAnalysisEditorPageDuration_2, 1, SWT.FILL, 800);
		composite.setLayout(new GridLayout(1, false));

		DataWrapper logWrapper = new DataWrapper(jvm);
		logWrapper.addAxisSelection(Axis.X, ValueType.TIME);
		logWrapper.addAxisSelection(Axis.Y, ValueType.DURATION);
		ChartPanel chartPanel = new ChartPanel(composite, SWT.BORDER, logWrapper, "Time (seconds)",
				"Garbage Collection Time");
		chartPanel.setLayoutData(GridLayoutUtil.fill());
	}
}
