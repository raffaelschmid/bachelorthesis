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
package com.trivadis.loganalysis.ui.common;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

public class CompositeBuilder {
	public static Composite composite(final Composite parent, final int style, final Layout layout) {
		final Composite retVal = new Composite(parent, style);
		retVal.setLayout(layout);
		return retVal;
	}
}
