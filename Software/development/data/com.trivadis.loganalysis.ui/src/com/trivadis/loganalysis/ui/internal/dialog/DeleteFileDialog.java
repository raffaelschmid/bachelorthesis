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
package com.trivadis.loganalysis.ui.internal.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.ui.internal.Messages;

public class DeleteFileDialog extends MessageDialog {

	private boolean deleteOnDisk;

	public DeleteFileDialog(IFileDescriptor file, Shell shell) {
		super(shell, getTitle(file), null, getMessage(file), MessageDialog.QUESTION, new String[] {
				IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL }, 0);
		setShellStyle(getShellStyle() | SWT.SHEET);
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		final Button chkDeleteOnDisk = new Button(composite, SWT.CHECK);
		chkDeleteOnDisk.setText(Messages.DeleteFileDialog_0);
		chkDeleteOnDisk.setFont(parent.getFont());
		chkDeleteOnDisk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteOnDisk = chkDeleteOnDisk.getSelection();
			}
		});

		return composite;
	}

	private static String getMessage(IFileDescriptor file) {
		return NLS.bind(Messages.DeleteFileDialog_1, file.getAbsolutePath());
	}

	private static String getTitle(IFileDescriptor file) {
		return Messages.DeleteFileDialog_3;
	}

	public boolean isDeleteFileOnDisk() {
		return deleteOnDisk;
	}
}
