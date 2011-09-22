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

import com.trivadis.loganalysis.ui.internal.UiContext;

public class UiLoganalysis implements IUiLoganalysis {
	private final IUiContext context;

	private static class Holder {
		private static IUiLoganalysis INSTANCE = new UiLoganalysis(new UiContext());
	}

	public static IUiLoganalysis getDefault() {
		return Holder.INSTANCE;
	}

	public UiLoganalysis(final UiContext context) {
		this.context = context;
	}

	public IUiContext getUiContext() {
		return context;
	}

	public IExtensionFacade getExtensionFacade() {
		return context.getExtensionFacade();
	}
}
