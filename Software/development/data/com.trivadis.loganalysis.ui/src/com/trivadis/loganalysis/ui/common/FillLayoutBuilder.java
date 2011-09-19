package com.trivadis.loganalysis.ui.common;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Layout;

public class FillLayoutBuilder {
	private int marginWidth = 0;

	public FillLayout vertical() {
		return layout(SWT.VERTICAL);
	}

	public Layout horizontal() {
		return layout(SWT.HORIZONTAL);
	}

	private FillLayout layout(final int type) {
		final FillLayout layout = new FillLayout(type);
		layout.marginWidth = marginWidth;
		return layout;
	}

	public FillLayoutBuilder marginWidth(final int marginWidth) {
		this.marginWidth = marginWidth;
		return this;
	}

}
