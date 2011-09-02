package com.trivadis.loganalysis.jrockit.ui.internal.view;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.ui.internal.view.heapusage.JRockitAnalysisEditorPageHeapUsage;
import com.trivadis.loganalysis.jrockit.ui.internal.view.summary.JRockitAnalysisEditorPageSummary;
import com.trivadis.loganalysis.ui.EditorInput;

public class JRockitAnalysisEditor extends FormEditor {
	public static final String ID = JRockitAnalysisEditor.class.getName();
	private JRockitJvmRun jvm;

	public JRockitAnalysisEditor() {
	}

	protected void addPages() {
		try {
			addPage(new JRockitAnalysisEditorPageSummary(this, this.jvm));
			addPage(new JRockitAnalysisEditorPageHeapUsage(this, this.jvm));
		} catch (PartInitException e) {
		}
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		if (!(editorInput instanceof EditorInput))
			throw new PartInitException(
					"Invalid Input: Must be AnalysisEditorInput");
		EditorInput input = (EditorInput) editorInput;
		if(!(input.getLogFile() instanceof JRockitJvmRun))
			throw new PartInitException("Invalid Log File Input");
		this.jvm = (JRockitJvmRun)input.getLogFile();
		super.init(site, input);
	}

	public void doSave(IProgressMonitor monitor) {
	}

	public void doSaveAs() {
	}

	public boolean isSaveAsAllowed() {
		return false;
	}
}