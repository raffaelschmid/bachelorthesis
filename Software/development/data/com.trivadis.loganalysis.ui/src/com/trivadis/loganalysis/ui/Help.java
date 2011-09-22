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
package com.trivadis.loganalysis.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

public class Help {
	private static class Holder {
		private static Help INSTANCE = new Help();
	}

	private Help() {

	}

	public static Help getDefault() {
		return Holder.INSTANCE;
	}

	public void register(final Composite component, final String contextId) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(component, contextId);
	}
	
	public void displayHelpResource(final String href){
		PlatformUI.getWorkbench().getHelpSystem().displayHelpResource(href);
	}
}
