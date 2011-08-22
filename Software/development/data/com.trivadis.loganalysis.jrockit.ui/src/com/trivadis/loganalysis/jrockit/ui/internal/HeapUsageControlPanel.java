package com.trivadis.loganalysis.jrockit.ui.internal;

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

import com.trivadis.loganalysis.jrockit.domain.ValueType;

public class HeapUsageControlPanel extends Composite {

	private final JRockitLogFileWrapper logFileWrapper;

	public HeapUsageControlPanel(Composite parent, int style, JRockitLogFileWrapper logFileWrapper) {
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
