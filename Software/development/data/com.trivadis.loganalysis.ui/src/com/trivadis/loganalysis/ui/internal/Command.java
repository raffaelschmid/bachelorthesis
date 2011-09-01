package com.trivadis.loganalysis.ui.internal;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

import com.trivadis.loganalysis.ui.Ui;

public class Command {
	public static void execute(String commandId) {
		try {
			IHandlerService handlerService = (IHandlerService) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getService(IHandlerService.class);
			handlerService.executeCommand(commandId, null);
		} catch (Exception e) {
			Ui.getDefault().handleException(e);
		}
	}
}
