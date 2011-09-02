package com.trivadis.loganalysis.jrockit.ui.internal.view.heapusage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.ui.internal.view.JRockitAnalysisEditor;
import com.trivadis.loganalysis.ui.GridFormPage;
import com.trivadis.loganalysis.ui.Messages;

public class JRockitAnalysisEditorPageHeapUsage extends GridFormPage {

	public static final String ID = JRockitAnalysisEditorPageHeapUsage.class.getName();
	private JRockitJvmRun jvm;

	public JRockitAnalysisEditorPageHeapUsage(JRockitAnalysisEditor editor, JRockitJvmRun logFile) {
		super(editor, ID, Messages.JRockitAnalysisEditorPageHeapUsage_0, 1, 1);
		this.jvm = logFile;
	}

	protected void createSections(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		createGeneralSection(managedForm, toolkit);

	}

	private void createGeneralSection(IManagedForm managedForm, FormToolkit toolkit) {
		Composite composite = createGridSection(managedForm,
				Messages.DashboardEditor_Section_General,
				Messages.JRockitAnalysisEditorPageHeapUsage_1, 1, SWT.FILL, 800);
		composite.setLayout(new GridLayout(1, false));

		HeapUsageDataWrapper logWrapper = new HeapUsageDataWrapper(jvm);
		HeapUsageChartPanel chartPanel = new HeapUsageChartPanel(composite, SWT.BORDER, logWrapper);
		chartPanel.setLayoutData(GridLayoutUtil.fill());
	}
}