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
import com.trivadis.loganalysis.jrockit.ui.internal.view.summary.JRockitAnalysisEditorPageSummary;
import com.trivadis.loganalysis.ui.AnalysisEditor;
import com.trivadis.loganalysis.ui.EditorInput;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class JRockitAnalysisEditor extends FormEditor implements AnalysisEditor {
	public static final String ID = JRockitAnalysisEditor.class.getName();
	private JRockitJvmRun jvm;
	private IProfile profile;

	public JRockitAnalysisEditor() {
	}

	@Override
	public IFormPage setActivePage(final String pageId) {
		return super.setActivePage(pageId);
	}

	@Override
	protected void addPages() {
		try {
			addPage(new JRockitAnalysisEditorPageSummary(this, jvm));
			for (final IChart chart : profile.getCharts()) {
				addPage(new JRockitAnalysisEditorPage(this, jvm, chart));
			}
			//TODO add page only in custom profile
			addPage(new JRockitAnalysisEditorPageCustom(this, jvm));
		} catch (final PartInitException e) {
		}
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput editorInput) throws PartInitException {
		if (!(editorInput instanceof EditorInput))
			throw new PartInitException("Invalid Input: Must be AnalysisEditorInput");
		final EditorInput input = (EditorInput) editorInput;
		if (!(input.getLogFile() instanceof JRockitJvmRun))
			throw new PartInitException("Invalid Log File Input");
		this.jvm = (JRockitJvmRun) input.getLogFile();
		this.profile = input.getProfile();
		super.init(site, input);
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

}