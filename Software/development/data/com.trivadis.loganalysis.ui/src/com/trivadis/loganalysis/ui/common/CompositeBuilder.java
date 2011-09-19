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
