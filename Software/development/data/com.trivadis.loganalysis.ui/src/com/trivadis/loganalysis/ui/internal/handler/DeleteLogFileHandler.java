package com.trivadis.loganalysis.ui.internal.handler;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.trivadis.loganalysis.core.IContext;
import com.trivadis.loganalysis.core.Loganalysis;
import com.trivadis.loganalysis.ui.internal.dialog.DeleteFileDialog;

public class DeleteLogFileHandler extends AbstractHandler {

	private final IContext context;

	public DeleteLogFileHandler() {
		this(Loganalysis.getContext());
	}

	public DeleteLogFileHandler(IContext context) {
		this.context = context;
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selected = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getSelectionService()
				.getSelection();
		if (!selected.isEmpty() && selected instanceof IStructuredSelection) {
			Object selectedObject = ((IStructuredSelection) selected)
					.getFirstElement();
			if (selectedObject instanceof File) {
				File file = (File) selectedObject;
				DeleteFileDialog dialog = new DeleteFileDialog(file,
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

	private void removeFileFromView(File file) {
		context.remove(file);
	}

	private boolean removeFileFromDisk(File file) {
		boolean delete = !file.isDirectory();
		if (delete) {
			file.delete();
		}
		return delete;
	}

	private boolean isOk(int code) {
		return code == DeleteFileDialog.OK;
	}
}
