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


public class UiLoganalysis {
	private static class Holder {
		private static IUiContext INSTANCE = new UiContext();
	}
	
	public static IUiContext getContext(){
		return Holder.INSTANCE;
	}
}
