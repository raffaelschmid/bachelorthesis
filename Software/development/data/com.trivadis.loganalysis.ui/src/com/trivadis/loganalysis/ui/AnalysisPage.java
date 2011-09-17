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
package com.trivadis.loganalysis.ui;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;

import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public abstract class AnalysisPage extends GridFormPage {

	private final IChart chart;
	private final IProfile profile;

	public AnalysisPage(final FormEditor editor, final String id, final String title, final IProfile profile, final IChart chart) {
		super(editor, id, title, 1, 1);
		this.profile = profile;
		this.chart = chart;
	}

	protected abstract void sections(final IManagedForm managedForm);

	@Override
	protected final void createSections(final IManagedForm managedForm) {
		new AnalysisToolBar(managedForm, profile, chart);
		sections(managedForm);
	}

}
