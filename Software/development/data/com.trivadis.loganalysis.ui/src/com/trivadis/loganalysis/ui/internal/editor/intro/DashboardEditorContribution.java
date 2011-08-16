package com.trivadis.loganalysis.ui.internal.editor.intro;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.ITextEditor;

public class DashboardEditorContribution extends
		MultiPageEditorActionBarContributor {

	public DashboardEditorContribution() {
		super();
	}

	protected IAction getAction(ITextEditor editor, String actionID) {
		return (editor == null ? null : editor.getAction(actionID));
	}

	public void setActivePage(IEditorPart part) {

	}

}
