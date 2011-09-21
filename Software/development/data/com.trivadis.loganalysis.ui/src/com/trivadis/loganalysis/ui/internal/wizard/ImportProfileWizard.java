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
		this.page = new ImportProfileWizardPage();
		this.context = UiLoganalysis.getUiContext();
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
