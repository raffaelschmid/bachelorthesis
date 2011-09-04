package com.trivadis.loganalysis.ui.internal.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.trivadis.loganalysis.ui.internal.ImportWizard;
import com.trivadis.loganalysis.ui.internal.wizard.ImportGCLogWizard;

public class OpenImportDialogHandler extends AbstractHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ImportWizard.open(new ImportGCLogWizard());
		return null;
	}
}
