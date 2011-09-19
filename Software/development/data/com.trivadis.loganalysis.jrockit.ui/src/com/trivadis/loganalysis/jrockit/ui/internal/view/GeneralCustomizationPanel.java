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

import static org.eclipse.core.databinding.beans.BeanProperties.value;
import static org.eclipse.jface.databinding.swt.WidgetProperties.text;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.trivadis.loganalysis.ui.domain.profile.Chart;
import com.trivadis.loganalysis.ui.domain.profile.IChart;

public class GeneralCustomizationPanel extends Composite {
	public GeneralCustomizationPanel(final Composite composite, final int style, final IChart chart) {
		super(composite, style);
		this.setLayout(new GridLayout(2, false));
		final DataBindingContext ctx = new DataBindingContext();
		final Label label = new Label(this, SWT.NONE);
		label.setText("Tab Name:");
		final Text name = new Text(this, SWT.BORDER);
		ctx.bindValue(text(SWT.Modify).observe(name), value(Chart.class, "tabName").observe(chart));
	}
}
