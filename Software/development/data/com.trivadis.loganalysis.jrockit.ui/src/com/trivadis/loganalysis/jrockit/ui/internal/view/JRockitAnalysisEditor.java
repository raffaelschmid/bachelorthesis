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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.ui.internal.view.summary.JRockitAnalysisEditorPageSummary;
import com.trivadis.loganalysis.ui.AnalysisEditor;
import com.trivadis.loganalysis.ui.EditorInput;
import com.trivadis.loganalysis.ui.IProfileListener;
import com.trivadis.loganalysis.ui.Ui;
import com.trivadis.loganalysis.ui.domain.profile.ChartType;
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
			addPage(new JRockitAnalysisEditorPageSummary(this, jvm, profile));
			for (final IChart chart : profile.getCharts()) {
				final AbstractJRockitAnalysisEditorPage page = ChartType.CUSTOM.equals(chart.getType()) ? new CustomJRockitAnalysisEditorPage(
						this, jvm, profile, chart) : new JRockitAnalysisEditorPage(this, jvm, profile, chart);
				chart.addPropertyChangeListener("tabName", chartListener(page));
				addPage(page);
			}
			profile.addChartListener(profileListener());
		} catch (final PartInitException e) {
			Ui.getDefault().handleException(e);
		}
	}

	protected IProfileListener profileListener() {
		return new IProfileListener() {
			public void added(final IChart chart) {
				try {
					final AbstractJRockitAnalysisEditorPage page = ChartType.CUSTOM.equals(chart.getType()) ? new CustomJRockitAnalysisEditorPage(
							JRockitAnalysisEditor.this, jvm, profile, chart) : new JRockitAnalysisEditorPage(
							JRockitAnalysisEditor.this, jvm, profile, chart);
					chart.addPropertyChangeListener("tabName", chartListener(page));
					addPage(page);
				} catch (final PartInitException e) {
					Ui.getDefault().handleException(e);
				}
			}

			public void removed(final int index) {
				removePage(index + 1);
			}
		};
	}

	protected PropertyChangeListener chartListener(final AbstractJRockitAnalysisEditorPage page) {
		return new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent evt) {
				JRockitAnalysisEditor.this.setPageText(page.getIndex(), (String) evt.getNewValue());
			}
		};
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