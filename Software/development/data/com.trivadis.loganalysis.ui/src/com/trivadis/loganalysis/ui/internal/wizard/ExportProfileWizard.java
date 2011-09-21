package com.trivadis.loganalysis.ui.internal.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import com.trivadis.loganalysis.ui.IUiContext;
import com.trivadis.loganalysis.ui.Ui;
import com.trivadis.loganalysis.ui.UiLoganalysis;
import com.trivadis.loganalysis.ui.internal.IProfileExporter;

public class ExportProfileWizard extends Wizard implements IExportWizard {

	private final ExportProfileWizardPage page;
	private final IUiContext context;
	private final IProfileExporter exporter;

	public ExportProfileWizard() {
		this.page = new ExportProfileWizardPage();
		this.context = UiLoganalysis.getUiContext();
		this.exporter = context.getProfileExporter();
	}

	public void init(final IWorkbench workbench, final IStructuredSelection selection) {

	}

	@Override
	public boolean performFinish() {
		boolean retVal = false;
		try {
			exporter.export(page.getProfiles(), page.getFileName());
			retVal = true;
		} catch (final Exception e) {
			Ui.getDefault().handleException(e);
		}
		return retVal;
	}

	@Override
	public void addPages() {
		addPage(page);
	}

}
