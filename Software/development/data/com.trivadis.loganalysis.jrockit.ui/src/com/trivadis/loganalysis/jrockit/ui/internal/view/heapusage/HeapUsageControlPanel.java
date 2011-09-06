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

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.trivadis.loganalysis.jrockit.file.ValueType;
import com.trivadis.loganalysis.jrockit.ui.internal.view.DataWrapper;
import com.trivadis.loganalysis.jrockit.ui.internal.view.Axis;

public class HeapUsageControlPanel extends Composite {

	private final DataWrapper logFileWrapper;

	public HeapUsageControlPanel(Composite parent, int style, DataWrapper logFileWrapper) {
		super(parent, style);
		setLayout(new GridLayout(5, false));
		this.logFileWrapper = logFileWrapper;

		dataTypeSelection(this, Axis.X);
		dataTypeSelection(this, Axis.Y);

	}

	private ComboViewer dataTypeSelection(Composite parent, final Axis axis) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(axis.getMsg());
		final ComboViewer viewer = new ComboViewer(parent, SWT.READ_ONLY);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return super.getText(element);
			}
		});

		viewer.setInput(ValueType.values());
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection sselection = (IStructuredSelection) viewer.getSelection();
					logFileWrapper.setSelection(axis, ((ValueType) sselection.getFirstElement()));
				}
			}
		});
		viewer.setSelection(new StructuredSelection(logFileWrapper.getAxisSelection(axis)));
		return viewer;
	}
}
