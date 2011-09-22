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
package com.trivadis.loganalysis.ui.internal.handler;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.ui.IUiContext;
import com.trivadis.loganalysis.ui.UiLoganalysis;
import com.trivadis.loganalysis.ui.internal.dialog.DeleteFileDialog;

public class DeleteLogFileHandler extends AbstractHandler {

	private final IUiContext context;

	public DeleteLogFileHandler() {
		this(UiLoganalysis.getDefault().getUiContext());
	}

	public DeleteLogFileHandler(final IUiContext context) {
		this.context = context;
	}

	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selected = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getSelectionService()
				.getSelection();
		if (!selected.isEmpty() && selected instanceof IStructuredSelection) {
			final Object selectedObject = ((IStructuredSelection) selected)
					.getFirstElement();
			if (selectedObject instanceof IFileDescriptor) {
				final IFileDescriptor file = (IFileDescriptor) selectedObject;
				final DeleteFileDialog dialog = new DeleteFileDialog(file,
						HandlerUtil.getActiveWorkbenchWindow(event).getShell());
				if (isOk(dialog.open())) {
					removeFileFromView(file);
					if (dialog.isDeleteFileOnDisk()) {
						removeFileFromDisk(file);
					}
				}
			}
		}
		return null;
	}

	private void removeFileFromView(final IFileDescriptor file) {
		context.remove(file);
	}

	private boolean removeFileFromDisk(final IFileDescriptor fileDescriptor) {
		final File file = fileDescriptor.toFile();
		final boolean delete = !file.isDirectory();
		if (delete) {
			file.delete();
		}
		return delete;
	}

	private boolean isOk(final int code) {
		return code == DeleteFileDialog.OK;
	}
}
