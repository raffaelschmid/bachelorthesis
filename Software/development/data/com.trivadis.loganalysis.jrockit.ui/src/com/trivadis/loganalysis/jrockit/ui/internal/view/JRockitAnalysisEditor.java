package com.trivadis.loganalysis.jrockit.ui.internal.view;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import com.trivadis.loganalysis.jrockit.domain.JRockitLog;
import com.trivadis.loganalysis.ui.EditorInput;

public class JRockitAnalysisEditor extends FormEditor {
	public static final String ID = JRockitAnalysisEditor.class.getName();
	private JRockitLog logFile;

	public JRockitAnalysisEditor() {
	}

	protected void addPages() {
		try {
			addPage(new JRockitAnalysisEditorPageHeapUsage(this, this.logFile));
			addPage(new JRockitAnalysisEditorPageOverview(this));
		} catch (PartInitException e) {
		}
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		if (!(editorInput instanceof EditorInput))
			throw new PartInitException(
					"Invalid Input: Must be AnalysisEditorInput");
		EditorInput input = (EditorInput) editorInput;
		if(!(input.getLogFile() instanceof JRockitLog))
			throw new PartInitException("Invalid Log File Input");
		this.logFile = (JRockitLog)input.getLogFile();
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