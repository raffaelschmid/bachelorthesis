package com.trivadis.loganalysis.ui.internal.dialog;

import java.io.File;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class DeleteFileDialog extends MessageDialog {

	private boolean deleteOnDisk;

	public DeleteFileDialog(File file, Shell shell) {
		super(shell, getTitle(file), null, getMessage(file),
				MessageDialog.QUESTION,
				new String[] { IDialogConstants.YES_LABEL,
						IDialogConstants.NO_LABEL }, 0);
		setShellStyle(getShellStyle() | SWT.SHEET);
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		final Button chkDeleteOnDisk = new Button(composite, SWT.CHECK);
		chkDeleteOnDisk.setText("Delete File on Disk (cannot be undone)");
		chkDeleteOnDisk.setFont(parent.getFont());
		chkDeleteOnDisk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteOnDisk = chkDeleteOnDisk.getSelection();
			}
		});

		return composite;
	}

	private static String getMessage(File file) {
		return "Are you sure you want to remove the Garbage Collection Log File '' from the workspace.";
	}

	private static String getTitle(File file) {
		return "Delete Garbage Collection Log File";
	}

	public boolean isDeleteFileOnDisk() {
		return deleteOnDisk;
	}
}
