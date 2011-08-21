package com.trivadis.loganalysis.jrockit.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.trivadis.loganalysis.jrockit.domain.JRockitLogFile;
import com.trivadis.loganalysis.jrockit.ui.internal.HeapUsageChartPanel;
import com.trivadis.loganalysis.jrockit.ui.internal.HeapUsageControlPanel;
import com.trivadis.loganalysis.jrockit.ui.internal.JRockitLogFileWrapper;

public class PageHeapUsageAfterGC extends Composite {

	public PageHeapUsageAfterGC(Composite parent, int style, JRockitLogFile logFile) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		JRockitLogFileWrapper logFileWrapper = new JRockitLogFileWrapper(logFile);
		
		HeapUsageControlPanel heapUsageControlPanel = new HeapUsageControlPanel(this, SWT.NONE, logFileWrapper);
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
