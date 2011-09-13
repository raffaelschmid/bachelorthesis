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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.internal.Messages;

public class NewProfileWizard extends Wizard implements INewWizard {

	private final NewProfileWizardPage page;

	public NewProfileWizard() {
		this(new NewProfileWizardPage("asadf"));
	}

	public NewProfileWizard(final NewProfileWizardPage newProfileWizardPage) {
		this.page = newProfileWizardPage;
	}
	
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		setWindowTitle(Messages.ImportGCLogWizard_4); // NON-NLS-1
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		addPage(page);
	}
	
	@Override
	public boolean performFinish() {
		boolean retVal = false;
		final IConfiguration configuration = page.getSelectedConfiguration();
		final String label = page.getProfileLabel();
		if(configuration !=null && label!=null && label.length()>0){
			configuration.addNewDefaultProfile(label);
			retVal = true;
		}
		else{
			page.setErrorMessage("You must select a configuration and/or define a name for the profile.");
			retVal = false;
		}
		
		return retVal;
	}

}
