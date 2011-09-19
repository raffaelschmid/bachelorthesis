package com.trivadis.loganalysis.jrockit.ui.internal.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;

public class FillLayoutBuilder {
	private int marginWidth = 0;
	
	public FillLayout vertical(){
		final FillLayout layout = new FillLayout(SWT.VERTICAL);
		layout.marginWidth = marginWidth;
		return layout;
	}

	public FillLayoutBuilder marginWidth(final int marginWidth) {
		this.marginWidth = marginWidth;
		return this;
	}
}
