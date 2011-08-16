package com.trivadis.loganalysis.ui.internal.editor.intro;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

public class DashboardEditor extends FormEditor {
	public static final String ID = DashboardEditor.class.getName();

	public DashboardEditor() {
	}

	protected void addPages() {
		try {
			addPage(new DashboardEditorPageOverview(this));
		} catch (PartInitException e) {
		}
	}

	public void doSave(IProgressMonitor monitor) {
	}

	public void doSaveAs() {
	}

	public boolean isSaveAsAllowed() {
		return false;
	}
}