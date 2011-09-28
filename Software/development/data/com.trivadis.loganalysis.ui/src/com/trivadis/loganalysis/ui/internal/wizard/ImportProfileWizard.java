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

import static com.trivadis.loganalysis.core.common.CollectionUtil.findFirst;
import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import com.trivadis.loganalysis.core.common.ClosureI;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.ui.IUiContext;
import com.trivadis.loganalysis.ui.UiLoganalysis;
import com.trivadis.loganalysis.ui.common.binding.BindingArrayList;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class ImportProfileWizard extends Wizard implements IExportWizard {

	private final ImportProfileWizardPage page;
	private final IUiContext context;

	public ImportProfileWizard() {
		this(new ImportProfileWizardPage(), UiLoganalysis.getDefault().getUiContext());
	}

	public ImportProfileWizard(final ImportProfileWizardPage page, final IUiContext context) {
		this.page = page;
		this.context = context;
		setWindowTitle("Import Garbage Collection Analysis Profile");
	}

	public void init(final IWorkbench workbench, final IStructuredSelection selection) {

	}

	@Override
	public boolean performFinish() {
		final BindingArrayList<IConfiguration> configurations = context.getConfigurations();
		foreach(page.getProfiles(), new ClosureI<IProfile>() {
			public void call(final IProfile profileToAdd) {
				final IConfiguration configuration = findFirst(configurations, new Predicate<IConfiguration>() {
					public boolean matches(final IConfiguration item) {
						return item.getKey().equals(profileToAdd.getConfiguration().getKey());
					}
				});
				if (configuration != null)
					configuration.addProfile(profileToAdd);
			}
		});
		return true;
	}

	@Override
	public void addPages() {
		addPage(page);
	}

}
