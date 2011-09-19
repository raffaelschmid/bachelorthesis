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

import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.ui.common.GridDataBuilder;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class DiagramAnalysisPage extends AnalysisPage {
	private static final String ID = DiagramAnalysisPage.class.getName();
	private final IJvmRun jvm;
	private final IChart chart;
	private ChartPanel chartPanel;
	
	private static final String SECTION_KEY_DIAGRAM = "section.diagram.customization";


	public DiagramAnalysisPage(final FormEditor editor, final IJvmRun jvm,
			final IProfile profile, final IChart chart, final IDatasetProvider datasetProvider) {
		super(editor,ID, chart.getTabName(), profile, chart);
		this.jvm = jvm;
		this.chart = chart;
		this.datasetProvider = datasetProvider;
	}

	@Override
	protected void sections(final IManagedForm managedForm) {
		final FormToolkit toolkit = managedForm.getToolkit();
		createDiagramSection(managedForm, toolkit);
	}

	private final ExpansionAdapter sectionExpansionListener = new ExpansionAdapter() {
		@Override
		public void expansionStateChanged(final ExpansionEvent e) {
			chart.setMeta(SECTION_KEY_DIAGRAM, String.valueOf(e.getState()));
		}
	};
	private final IDatasetProvider datasetProvider;

	protected void createDiagramSection(final IManagedForm managedForm, final FormToolkit toolkit) {
		chartPanel = new ChartPanel(createGridSection(managedForm, "Chart", chart.getDescription(), 1, SWT.FILL, 800,
				Boolean.valueOf(chart.getMeta(SECTION_KEY_DIAGRAM, "true")), sectionExpansionListener), SWT.BORDER,
				jvm, chart, datasetProvider);
		chartPanel.setLayoutData(new GridDataBuilder().fill().build());
	}

}
