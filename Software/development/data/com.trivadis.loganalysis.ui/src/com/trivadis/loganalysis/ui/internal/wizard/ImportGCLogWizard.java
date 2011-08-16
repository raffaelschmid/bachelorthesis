package com.trivadis.loganalysis.ui.internal.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.trivadis.loganalysis.core.Loganalysis;
import com.trivadis.loganalysis.core.process.IFileProcessor;
import com.trivadis.loganalysis.ui.internal.Perspective;
import com.trivadis.loganalysis.ui.internal.handler.OpenGcLoganalysisPerspective;

public class ImportGCLogWizard extends Wizard implements IImportWizard {

	private static final String SWITCH_PERSPECTIVE_ON_GC_LOG_IMPORT = "SWITCH_PERSPECTIVE_ON_GC_LOG_IMPORT";

	public static final String ID = ImportGCLogWizard.class.getName();

	private ImportGCLogWizardSelectionPage page;

	private final IFileProcessor processor;

	public ImportGCLogWizard() {
		this(Loganalysis.importProcessor(),
				new ImportGCLogWizardSelectionPage());
	}

	ImportGCLogWizard(IFileProcessor processor,
			ImportGCLogWizardSelectionPage page) {
		this.processor = processor;
		this.page = page;
	}

	public boolean performFinish() {
		processor.processFiles(page.getFiles());
		Perspective
				.updateWithNotification(
						SWITCH_PERSPECTIVE_ON_GC_LOG_IMPORT,
						"Open Associated Perspective?",
						"Garbage Collection Log Files are imported into the Log Files view which is also available within the "
								+ "Garbage Collection Analysis Perspective. Do you want to open this perspective now?",
						OpenGcLoganalysisPerspective.PERSPECTIVE_ID);
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("Garbage Collection Log File Import Wizard"); // NON-NLS-1
		setNeedsProgressMonitor(true);
	}

	public void addPages() {
		super.addPages();
		addPage(page);
	}
}
