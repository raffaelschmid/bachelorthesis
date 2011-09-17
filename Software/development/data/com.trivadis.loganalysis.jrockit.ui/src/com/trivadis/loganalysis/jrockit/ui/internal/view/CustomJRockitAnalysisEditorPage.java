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

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class CustomJRockitAnalysisEditorPage extends AbstractJRockitAnalysisEditorPage {

	public CustomJRockitAnalysisEditorPage(final JRockitAnalysisEditor editor, final JRockitJvmRun logFile, final IProfile profile, final IChart chart) {
		super(editor, logFile, profile, chart);
	}

	@Override
	protected void sections(final IManagedForm managedForm) {
		final FormToolkit toolkit = managedForm.getToolkit();
		createCustomizationGeneral(managedForm, toolkit);
		createCustomizationChart(managedForm, toolkit);
		createDiagramSection(managedForm, toolkit);
	}

}
