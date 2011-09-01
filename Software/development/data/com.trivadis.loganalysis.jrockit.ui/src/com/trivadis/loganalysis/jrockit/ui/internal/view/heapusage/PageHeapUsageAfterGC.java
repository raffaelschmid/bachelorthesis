package com.trivadis.loganalysis.jrockit.ui.internal.view.heapusage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.trivadis.loganalysis.jrockit.domain.JRockitLog;

public class PageHeapUsageAfterGC extends Composite {

	public PageHeapUsageAfterGC(Composite parent, int style, JRockitLog logFile) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		HeapUsageDataWrapper logFileWrapper = new HeapUsageDataWrapper(logFile);
		
		new HeapUsageControlPanel(this, SWT.NONE, logFileWrapper);
		HeapUsageChartPanel heapUsageChartPanel = new HeapUsageChartPanel(this, SWT.NONE, logFileWrapper );
		heapUsageChartPanel.setLayoutData(fill());
	}

	private GridData fill() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		return gridData;
	}
}
