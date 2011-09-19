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
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.ui.DiagramAnalysisPage;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class CustomDiagramAnalysisPage extends DiagramAnalysisPage {

	private static final String SECTION_KEY_GENERAL = "section.general.customization";

	private static final String SECTION_KEY_CHART = "section.chart.customization";

	private final IChart chart;

	private final ExpansionAdapter chartSectionExpansionListener = new ExpansionAdapter() {
		@Override
		public void expansionStateChanged(final ExpansionEvent e) {
			chart.setMeta(SECTION_KEY_CHART, String.valueOf(e.getState()));
		}
	};

	private final ExpansionAdapter generalSectionExpansionListener = new ExpansionAdapter() {
		@Override
		public void expansionStateChanged(final ExpansionEvent e) {
			chart.setMeta(SECTION_KEY_GENERAL, String.valueOf(e.getState()));
		}
	};

	public CustomDiagramAnalysisPage(final FormEditor editor, final IJvmRun logFile,
			final IProfile profile, final IChart chart) {
		super(editor, logFile, profile, chart,new JRockitDatasetProvider());
		this.chart = chart;
	}

	@Override
	protected void sections(final IManagedForm managedForm) {
		final FormToolkit toolkit = managedForm.getToolkit();
		createCustomizationGeneral(managedForm, toolkit);
		createCustomizationChart(managedForm, toolkit);
		createDiagramSection(managedForm, toolkit);
	}

	protected void createCustomizationChart(final IManagedForm managedForm, final FormToolkit toolkit) {
		new ChartCustomizationPanel(createGridSection(managedForm, "Chart Customization", "", 2,
				Boolean.valueOf(chart.getMeta(SECTION_KEY_CHART,"true")), chartSectionExpansionListener), SWT.NONE, toolkit,
				chart);
	}

	protected void createCustomizationGeneral(final IManagedForm managedForm, final FormToolkit toolkit) {
		new GeneralCustomizationPanel(createGridSection(managedForm, "General Customization", "", 2,
				Boolean.valueOf(chart.getMeta(SECTION_KEY_GENERAL,"true")), generalSectionExpansionListener), SWT.NONE, chart);
	}
}
