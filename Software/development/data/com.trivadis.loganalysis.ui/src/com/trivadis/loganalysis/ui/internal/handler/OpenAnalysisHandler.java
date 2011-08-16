package com.trivadis.loganalysis.ui.internal.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.trivadis.loganalysis.domain.ILogFile;
import com.trivadis.loganalysis.ui.internal.editor.analysis.AnalysisEditor;

public class OpenAnalysisHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		try {
			page.openEditor(new AnalysisEditorInput(new ILogFile() {
				public String getPath() {
					return "foo";
				}

				public String getFileName() {
					return "bar";
				}
			}), AnalysisEditor.ID);
		} catch (PartInitException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

}
