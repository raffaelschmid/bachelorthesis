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
package com.trivadis.loganalysis.core;

import com.trivadis.loganalysis.core.internal.Context;

public class Loganalysis implements ILoganalysis {

	private static class Holder {
		private static ILoganalysis INSTANCE = new Loganalysis(new Context());
	}

	public static ILoganalysis getDefault() {
		return Holder.INSTANCE;
	}

	private final Context context;

	public Loganalysis(final Context context) {
		this.context = context;
	}

	public IContext getContext() {
		return context;
	}

}
