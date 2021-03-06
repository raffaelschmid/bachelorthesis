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
package com.trivadis.loganalysis.ui.internal.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;

import com.trivadis.loganalysis.core.IAnalyzer;
import com.trivadis.loganalysis.core.IContext;
import com.trivadis.loganalysis.core.Loganalysis;
import com.trivadis.loganalysis.core.common.progress.Progress;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.core.exception.FileProcessingException;
import com.trivadis.loganalysis.ui.AnalysisEditorInput;
import com.trivadis.loganalysis.ui.IUiContext;
import com.trivadis.loganalysis.ui.ResultRunnableWithProgress;
import com.trivadis.loganalysis.ui.Ui;
import com.trivadis.loganalysis.ui.UiLoganalysis;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;
import com.trivadis.loganalysis.ui.internal.Messages;

public class OpenAnalysisHandler extends AbstractHandler {

	private final IUiContext uiContext;
	private final IContext context;

	public OpenAnalysisHandler() {
		this(Loganalysis.getDefault().getContext(), UiLoganalysis.getDefault().getUiContext());
	}

	public OpenAnalysisHandler(final IContext context, final IUiContext uiContext) {
		this.context = context;
		this.uiContext = uiContext;
	}

	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelectionService service = (ISelectionService) HandlerUtil.getActiveWorkbenchWindow(event).getService(
				ISelectionService.class);
		final ISelection selection = service.getSelection();
		if (selection instanceof StructuredSelection) {
			final StructuredSelection sselection = (StructuredSelection) selection;
			final Object oProfile = sselection.getFirstElement();
			if (oProfile instanceof IFileDescriptor) {
				final IFileDescriptor logFileDescriptor = (IFileDescriptor) oProfile;
				openAnalysis(event, logFileDescriptor, uiContext.getSelectedProfile());
			} else if (oProfile instanceof IProfile) {
				if (uiContext.getSelectedLogFile() != null) {
					final IProfile profile = (IProfile) oProfile;
					openAnalysis(event, uiContext.getSelectedLogFile(), profile);
				} else {
					// TODO handle case where file is not selected and double
					// click on profile occures
				}

			}
		}
		return null;
	}

	private void openAnalysis(final ExecutionEvent event, final IFileDescriptor fileDescriptor, final IProfile profile) {
		try {
			final IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
			final IAnalyzer<IJvmRun> analyzer = context.findAnalyzer(fileDescriptor);
			if (analyzer != null) {
				showAnalysis(fileDescriptor, page, analyzer, profile);
			} else {
				showNoAnalyzerFoundMessage(page);
			}
		} catch (final FileProcessingException e) {
			Ui.getDefault().handleException(e);
		}
	}

	private void showAnalysis(final IFileDescriptor fileDescriptor, final IWorkbenchPage page,
			final IAnalyzer<IJvmRun> analyzer, final IProfile profile) {
		try {
			final IJvmRun jvmRun = Ui.getDefault().busyCursorWithResultWhile(new ResultRunnableWithProgress<IJvmRun>() {
				@Override
				public IJvmRun result(final IProgressMonitor monitor) {
					return analyzer.process(fileDescriptor, new Progress(monitor,
							Messages.OpenGcLoganalysis_progress_message));
				}
			});
			final AnalysisEditorInput editorInput = new AnalysisEditorInput(jvmRun, profile != null ? profile : uiContext
					.getExtensionFacade().getConfigurationFor(jvmRun).getDefaultProfile());
			page.openEditor(editorInput, analyzer.getEditorId());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void showNoAnalyzerFoundMessage(final IWorkbenchPage page) {
		MessageDialog.openError(page.getWorkbenchWindow().getShell(),
				Messages.OpenGcLoganalysis_implementation_not_found_title,
				Messages.OpenGcLoganalysis_implementation_not_found_text);
	}

}
