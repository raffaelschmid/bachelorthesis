package com.trivadis.loganalysis.ui.internal.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.trivadis.loganalysis.core.IFileImporter;
import com.trivadis.loganalysis.core.Loganalysis;
import com.trivadis.loganalysis.ui.Messages;
import com.trivadis.loganalysis.ui.internal.Perspective;
import com.trivadis.loganalysis.ui.internal.handler.OpenGcLoganalysisPerspective;

public class ImportGCLogWizard extends Wizard implements IImportWizard {

	private static final String SWITCH_PERSPECTIVE_ON_GC_LOG_IMPORT = "SWITCH_PERSPECTIVE_ON_GC_LOG_IMPORT"; //$NON-NLS-1$

	public static final String ID = ImportGCLogWizard.class.getName();

	private ImportGCLogWizardSelectionPage page;

	private final IFileImporter processor;

	public ImportGCLogWizard() {
		this(Loganalysis.fileImporter(),
				new ImportGCLogWizardSelectionPage());
	}

	ImportGCLogWizard(IFileImporter processor,
			ImportGCLogWizardSelectionPage page) {
		this.processor = processor;
		this.page = page;
	}

	public boolean performFinish() {
		processor.importFiles(page.getFiles());
		Perspective
				.updateWithNotification(
						SWITCH_PERSPECTIVE_ON_GC_LOG_IMPORT,
						Messages.ImportGCLogWizard_1,
						Messages.ImportGCLogWizard_2,
						OpenGcLoganalysisPerspective.PERSPECTIVE_ID);
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Messages.ImportGCLogWizard_4); // NON-NLS-1
		setNeedsProgressMonitor(true);
	}

	public void addPages() {
		super.addPages();
		addPage(page);
	}
}
