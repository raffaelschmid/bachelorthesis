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
package com.trivadis.loganalysis.ui.internal.wizard;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.ui.UiLoganalysis;
import com.trivadis.loganalysis.ui.internal.Messages;
import com.trivadis.loganalysis.ui.internal.Perspective;
import com.trivadis.loganalysis.ui.internal.perspective.GarbageCollectionAnalysisPerspective;

public class ImportGCLogWizard extends Wizard implements IImportWizard {

	private static final String SWITCH_PERSPECTIVE_ON_GC_LOG_IMPORT = "SWITCH_PERSPECTIVE_ON_GC_LOG_IMPORT"; //$NON-NLS-1$

	public static final String ID = ImportGCLogWizard.class.getName();

	private final ImportGCLogWizardSelectionPage page;

	public ImportGCLogWizard() {
		this(new ImportGCLogWizardSelectionPage());
	}

	ImportGCLogWizard(ImportGCLogWizardSelectionPage page) {
		this.page = page;
	}

	@Override
	public boolean performFinish() {
		importFiles(page.getFiles());
		Perspective.updateWithNotification(SWITCH_PERSPECTIVE_ON_GC_LOG_IMPORT, Messages.ImportGCLogWizard_1,
				Messages.ImportGCLogWizard_2, GarbageCollectionAnalysisPerspective.ID);
		return true;
	}

	public void importFiles(List<IFileDescriptor> files) {
		for (IFileDescriptor file : files) {
			UiLoganalysis.getUiContext().addSelectedFile(file);
		}
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Messages.ImportGCLogWizard_4); // NON-NLS-1
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		super.addPages();
		addPage(page);
	}
}
