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
package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.ui.internal.JRockitAnalysisEditor;
import com.trivadis.loganalysis.jrockit.ui.internal.Messages;
import com.trivadis.loganalysis.ui.AnalysisPage;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class JRockitAnalysisEditorPageSummary extends AnalysisPage {

	private final JRockitJvmRun logFile;

	public static final String ID = JRockitAnalysisEditorPageSummary.class.getName();

	public JRockitAnalysisEditorPageSummary(final JRockitAnalysisEditor editor, final JRockitJvmRun logFile,
			final IProfile profile) {
		super(editor, ID, Messages.JRockitAnalysisEditorPageSummary_0, profile, null);
		this.logFile = logFile;
	}

	@Override
	protected void sections(final IManagedForm managedForm) {
		final FormToolkit toolkit = managedForm.getToolkit();
		new TableModelGcActivity(logFile, tableSection(managedForm, toolkit,
				Messages.JRockitAnalysisEditorPageSummary_3, Messages.JRockitAnalysisEditorPageSummary_4));

		new TableModelHeapCapacity(logFile, tableSection(managedForm, toolkit,
				Messages.JRockitAnalysisEditorPageSummary_1, Messages.JRockitAnalysisEditorPageSummary_2));

		new TableModelOverallStatistics(logFile, tableSection(managedForm, toolkit,
				Messages.JRockitAnalysisEditorPageSummary_5, Messages.JRockitAnalysisEditorPageSummary_6));
	}

	private Table tableSection(final IManagedForm managedForm, final FormToolkit toolkit, final String title,
			final String description) {
		final Composite section = createGridSection(managedForm, title, description, 1, true);
		final Table table = managedForm.getToolkit().createTable(section, SWT.NONE);
		managedForm.getToolkit().paintBordersFor(table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		return table;
	}

}