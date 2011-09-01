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
import com.trivadis.loganalysis.core.Loganalysis;
import com.trivadis.loganalysis.core.common.progress.Progress;
import com.trivadis.loganalysis.core.domain.ILogFile;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;
import com.trivadis.loganalysis.core.exception.FileProcessingException;
import com.trivadis.loganalysis.ui.EditorInput;
import com.trivadis.loganalysis.ui.Messages;
import com.trivadis.loganalysis.ui.ResultRunnableWithProgress;
import com.trivadis.loganalysis.ui.Ui;

public class OpenAnalysisHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelectionService service = (ISelectionService) HandlerUtil.getActiveWorkbenchWindow(event)
				.getService(ISelectionService.class);
		ISelection selection = service.getSelection();
		if (selection instanceof StructuredSelection) {
			StructuredSelection sselection = (StructuredSelection) selection;
			Object ofile = sselection.getFirstElement();
			if (ofile instanceof ILogFileDescriptor) {
				ILogFileDescriptor logFileDescriptor = (ILogFileDescriptor) ofile;
				openAnalysis(event, logFileDescriptor);
			}
		}
		return null;
	}

	private void openAnalysis(ExecutionEvent event, final ILogFileDescriptor logFileDescriptor) {
		try {
			final IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
			IAnalyzer<ILogFile> analyzer = Loganalysis.fileProcessor(logFileDescriptor);
			if (analyzer != null) {
				showAnalysis(logFileDescriptor, page, analyzer);
			} else {
				showNoAnalyzerFoundMessage(page);
			}
		} catch (FileProcessingException e) {
			Ui.getDefault().handleException(e);
		}
	}

	private void showAnalysis(final ILogFileDescriptor logFileDescriptor,
			final IWorkbenchPage page, final IAnalyzer<ILogFile> analyzer) {
		try {
			page.openEditor(
					new EditorInput(Ui.getDefault().busyCursorWithResultWhile(
							new ResultRunnableWithProgress<ILogFile>() {
								public ILogFile result(IProgressMonitor monitor) {
									return analyzer.process(logFileDescriptor, new Progress(
											monitor, Messages.OpenGcLoganalysis_progress_message));
								}
							})), analyzer.getEditorId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showNoAnalyzerFoundMessage(IWorkbenchPage page) {
		MessageDialog.openError(page.getWorkbenchWindow().getShell(),
				Messages.OpenGcLoganalysis_implementation_not_found_title,
				Messages.OpenGcLoganalysis_implementation_not_found_text);
	}

}
