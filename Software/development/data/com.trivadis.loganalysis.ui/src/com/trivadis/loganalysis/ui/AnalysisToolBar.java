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

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.trivadis.loganalysis.core.common.Assert;
import com.trivadis.loganalysis.ui.domain.profile.Chart;
import com.trivadis.loganalysis.ui.domain.profile.ChartType;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;
import com.trivadis.loganalysis.ui.domain.profile.IStandardProfile;
import com.trivadis.loganalysis.ui.internal.Activator;

public class AnalysisToolBar {
	private static AtomicInteger sequence = new AtomicInteger();
	private static final String CHART_ICON_ADD = "icons/chart_add.gif";
	private static final String CHART_ICON_REMOVE = "icons/chart_remove.gif";

	public AnalysisToolBar(final IManagedForm managedForm, final IProfile profile, final IChart chart) {
		boolean first = true;
		final FormToolkit toolkit = managedForm.getToolkit();
		final Composite composite = toolkit.createComposite(managedForm.getForm().getBody());
		final GridLayout rowLayout = new GridLayout(3, false);
		composite.setLayout(rowLayout);

		if (chart != null) {
			final Button btnRemoveChart = toolkit.createButton(composite, null, SWT.NONE);
			btnRemoveChart.setImage(Activator.getDefault().getImage(CHART_ICON_REMOVE));
			btnRemoveChart.setLayoutData(new GridData(SWT.END, SWT.TOP, first, false));
			btnRemoveChart.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					Assert.assertTrue(profile.removeChart(chart));
				}
			});
			first = false;
		}

		if (!(profile instanceof IStandardProfile)) {
			final Button btnAddChart = toolkit.createButton(composite, null, SWT.NONE);
			btnAddChart.setImage(Activator.getDefault().getImage(CHART_ICON_ADD));
			btnAddChart.setLayoutData(new GridData(SWT.END, SWT.TOP, first, false));
			btnAddChart.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					profile.addChart(new Chart(ChartType.CUSTOM, "New Chart " + sequence.getAndIncrement(), "", ""));
				}
			});
			first = false;
		}
	}
}
