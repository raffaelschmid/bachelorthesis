package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.ui.internal.view.JRockitAnalysisEditor;
import com.trivadis.loganalysis.ui.GridFormPage;

public class JRockitAnalysisEditorPageSummary extends GridFormPage {

	private final JRockitJvmRun logFile;

	public static final String ID = JRockitAnalysisEditorPageSummary.class.getName();

	public JRockitAnalysisEditorPageSummary(JRockitAnalysisEditor editor, JRockitJvmRun logFile) {
		super(editor, ID, "Summary", 1, 1);
		this.logFile = logFile;
	}

	protected void createSections(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		heapCapacitySection(managedForm, toolkit);
		gcActivitySummary(managedForm, toolkit);
		overallStatistics(managedForm, toolkit);
	}

	private void heapCapacitySection(IManagedForm managedForm, FormToolkit toolkit) {
		Composite section = createGridSection(managedForm, "Heap Capacity", "", 1);
		Table table = managedForm.getToolkit().createTable(section, SWT.NONE);
		managedForm.getToolkit().paintBordersFor(table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		new TableModelHeapCapacity(logFile, table);
	}

	private void gcActivitySummary(IManagedForm managedForm, FormToolkit toolkit) {
		Composite section = createGridSection(managedForm, "Garbage Collection Activity Summary",
				"This section shows the summary of the garbage collection activity.", 1);
		Table table = managedForm.getToolkit().createTable(section, SWT.NONE);
		managedForm.getToolkit().paintBordersFor(table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		new TableModelGcActivity(logFile, table);
	}

	private void overallStatistics(IManagedForm managedForm, FormToolkit toolkit) {
		Composite section = createGridSection(managedForm, "Overall Statistics", "", 1);

		Table table = managedForm.getToolkit().createTable(section, SWT.NONE);
		managedForm.getToolkit().paintBordersFor(table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		new TableModelOverallStatistics(logFile, table);
	}

}