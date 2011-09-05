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
