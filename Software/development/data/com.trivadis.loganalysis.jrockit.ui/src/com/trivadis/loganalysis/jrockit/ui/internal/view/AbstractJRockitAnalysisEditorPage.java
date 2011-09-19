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
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.trivadis.loganalysis.core.domain.AbstractJvmRun;
import com.trivadis.loganalysis.jrockit.ui.internal.Messages;
import com.trivadis.loganalysis.ui.AnalysisPage;
import com.trivadis.loganalysis.ui.common.GridDataBuilder;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public abstract class AbstractJRockitAnalysisEditorPage extends AnalysisPage {

	public static final String ID = AbstractJRockitAnalysisEditorPage.class.getName();
	private final AbstractJvmRun jvm;
	private final IChart chart;
	private ChartPanel chartPanel;

	public AbstractJRockitAnalysisEditorPage(final JRockitAnalysisEditor editor, final AbstractJvmRun logFile,
			final IProfile profile, final IChart chart) {
		super(editor, ID, chart.getTabName(), profile, chart);
		this.jvm = logFile;
		this.chart = chart;
	}

	protected void createDiagramSection(final IManagedForm managedForm, final FormToolkit toolkit) {
		chartPanel = new ChartPanel(createGridSection(managedForm, Messages.JRockitAnalysisEditorPageHeapUsage_2,
				chart.getDescription(), 1, SWT.FILL, 800, true), SWT.BORDER, jvm, chart);
		chartPanel.setLayoutData(new GridDataBuilder().fill().build());
	}

}