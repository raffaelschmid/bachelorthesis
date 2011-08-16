package com.trivadis.loganalysis.ui.internal.handler;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.handlers.HandlerUtil;

import com.trivadis.loganalysis.core.IFileProcessor;
import com.trivadis.loganalysis.core.Loganalysis;
import com.trivadis.loganalysis.core.domain.ILogFile;

public class OpenAnalysisHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IFileProcessor<? extends ILogFile> analyzer = null;
		ISelectionService service = (ISelectionService) HandlerUtil
				.getActiveWorkbenchWindow(event).getService(
						ISelectionService.class);
		ISelection selection = service.getSelection();
		if (selection instanceof StructuredSelection) {
			StructuredSelection sselection = (StructuredSelection) selection;
			Object ofile = sselection.getFirstElement();
			if (ofile instanceof File) {
				File file = (File) ofile;
				IFileProcessor<? extends ILogFile> fileProcessor = Loganalysis.fileProcessor();
				fileProcessor.process(null, "content");
			}

		}

		// IWorkbenchPage page =
		// HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		// try {
		// page.openEditor(new AnalysisEditorInput(new ILogFileDescriptor() {
		// public String getPath() {
		// return "foo";
		// }
		//
		// public String getFileName() {
		// return "bar";
		// }
		// }), AnalysisEditor.ID);
		// } catch (PartInitException e) {
		// throw new RuntimeException(e);
		// }
		return null;
	}

}
