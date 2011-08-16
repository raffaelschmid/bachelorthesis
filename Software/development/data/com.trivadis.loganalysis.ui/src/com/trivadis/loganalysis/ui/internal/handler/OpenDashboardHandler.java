package com.trivadis.loganalysis.ui.internal.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.trivadis.loganalysis.ui.internal.editor.intro.DashboardEditor;
import com.trivadis.loganalysis.ui.internal.editor.intro.DashboardEditorInput;

public class OpenDashboardHandler extends AbstractHandler {

	public static String ID = OpenDashboardHandler.class.getName();

	private static class Holder {
		private static DashboardEditorInput INSTANCE = new DashboardEditorInput();
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IWorkbenchWindow workbenchWindow = HandlerUtil
					.getActiveWorkbenchWindow(event);
			IWorkbenchPage page = workbenchWindow.getActivePage();
			page.openEditor(Holder.INSTANCE, DashboardEditor.ID);
		} catch (PartInitException e) {

		}
		return null;
	}
}
