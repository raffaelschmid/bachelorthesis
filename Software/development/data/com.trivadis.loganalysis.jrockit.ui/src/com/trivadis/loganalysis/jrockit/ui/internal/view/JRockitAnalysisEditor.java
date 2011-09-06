/**
 * Copyright (c) 2011 Loganalysis team and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Raffael Schmid - initial API and implementation
 */
package com.trivadis.loganalysis.jrockit.ui.internal.view;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.ui.internal.view.custom.JRockitAnalysisEditorPageCustom;
import com.trivadis.loganalysis.jrockit.ui.internal.view.duration.JRockitAnalysisEditorPageDuration;
import com.trivadis.loganalysis.jrockit.ui.internal.view.heapusage.JRockitAnalysisEditorPageHeapUsage;
import com.trivadis.loganalysis.jrockit.ui.internal.view.summary.JRockitAnalysisEditorPageSummary;
import com.trivadis.loganalysis.ui.AnalysisEditor;
import com.trivadis.loganalysis.ui.EditorInput;

public class JRockitAnalysisEditor extends FormEditor implements AnalysisEditor {
	public static final String ID = JRockitAnalysisEditor.class.getName();
	private JRockitJvmRun jvm;

	public JRockitAnalysisEditor() {
	}

	@Override
	public IFormPage setActivePage(String pageId) {
		return super.setActivePage(pageId);
	}

	protected void addPages() {
		try {
			addPage(new JRockitAnalysisEditorPageCustom(this, this.jvm));
			addPage(new JRockitAnalysisEditorPageSummary(this, this.jvm));
			addPage(new JRockitAnalysisEditorPageHeapUsage(this, this.jvm));
			addPage(new JRockitAnalysisEditorPageDuration(this, this.jvm));
		} catch (PartInitException e) {
		}
	}

	@Override
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		if (!(editorInput instanceof EditorInput))
			throw new PartInitException("Invalid Input: Must be AnalysisEditorInput");
		EditorInput input = (EditorInput) editorInput;
		if (!(input.getLogFile() instanceof JRockitJvmRun))
			throw new PartInitException("Invalid Log File Input");
		this.jvm = (JRockitJvmRun) input.getLogFile();
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