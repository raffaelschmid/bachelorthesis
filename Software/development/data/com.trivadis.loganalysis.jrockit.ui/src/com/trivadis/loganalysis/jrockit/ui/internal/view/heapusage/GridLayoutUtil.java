package com.trivadis.loganalysis.jrockit.ui.internal.view.heapusage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

public class GridLayoutUtil {

	public static GridData fill() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		return gridData;
	}

}
