package com.trivadis.loganalysis.ui.internal;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class ImportWizard {
	public static void open(Wizard wizard) {
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), wizard);
		dialog.create();
		dialog.open();
	}
}
