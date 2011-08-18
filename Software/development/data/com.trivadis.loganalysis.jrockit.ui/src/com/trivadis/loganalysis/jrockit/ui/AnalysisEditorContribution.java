package com.trivadis.loganalysis.jrockit.ui;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;

public class AnalysisEditorContribution extends
		MultiPageEditorActionBarContributor {
	private IEditorPart activeEditorPart;


	public void setActivePage(IEditorPart part) {
		if (activeEditorPart == part)
			return;
		activeEditorPart = part;
	}

}
