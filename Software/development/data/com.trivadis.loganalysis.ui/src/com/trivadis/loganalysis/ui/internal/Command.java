package com.trivadis.loganalysis.ui.internal;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

public class Command {
	public static void execute(String commandId) {
		try {
			IHandlerService handlerService = (IHandlerService) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getService(IHandlerService.class);
			handlerService.executeCommand(commandId, null);
		} catch (Exception e) {
			// TODO verify exception handling
			throw new RuntimeException(e);
		}
	}
}
