package com.trivadis.loganalysis.ui.internal.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.trivadis.loganalysis.core.IAnalyzer;
import com.trivadis.loganalysis.core.Loganalysis;
import com.trivadis.loganalysis.core.domain.ILogFile;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;
import com.trivadis.loganalysis.ui.EditorInput;

public class OpenAnalysisHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelectionService service = (ISelectionService) HandlerUtil
				.getActiveWorkbenchWindow(event).getService(
						ISelectionService.class);
		ISelection selection = service.getSelection();
		if (selection instanceof StructuredSelection) {
			StructuredSelection sselection = (StructuredSelection) selection;
			Object ofile = sselection.getFirstElement();
			if (ofile instanceof ILogFileDescriptor) {
				ILogFileDescriptor logFileDescriptor = (ILogFileDescriptor) ofile;
				IAnalyzer<ILogFile> fileProcessor = Loganalysis
						.fileProcessor(logFileDescriptor);
				try {
					IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(
							event).getActivePage();
					page.openEditor(
							new EditorInput(
									fileProcessor.process(logFileDescriptor)),
							fileProcessor.getEditorId());
				} catch (PartInitException e) {
					throw new RuntimeException(e);
				}
			}
		}

		return null;
	}

}
