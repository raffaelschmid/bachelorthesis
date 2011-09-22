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
package com.trivadis.loganalysis.ui.internal;

import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;

import java.util.ArrayList;
import java.util.List;

import com.trivadis.loganalysis.core.common.Assert;
import com.trivadis.loganalysis.core.common.ClosureI;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.ui.IExtensionFacade;
import com.trivadis.loganalysis.ui.IUiContext;
import com.trivadis.loganalysis.ui.common.binding.BindingArrayList;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class UiContext implements IUiContext {

	private IProfile selectedProfile = null;
	private IFileDescriptor selectedLogFile = null;
	private final BindingArrayList<IConfiguration> configurations;
	private final BindingArrayList<IFileDescriptor> selectedFiles;
	private IConfiguration selectedConfiguration;
	private final IProfileExporter exporter;
	private final IExtensionFacade extensionFacade;

	public UiContext() {
		this(new BindingArrayList<IFileDescriptor>(new ArrayList<IFileDescriptor>()),
				new BindingArrayList<IConfiguration>(new ArrayList<IConfiguration>()), new ProfileExporter(),
				new ExtensionFacade());
	}

	public UiContext(final BindingArrayList<IFileDescriptor> selectedFiles,
			final BindingArrayList<IConfiguration> configurations, final ProfileExporter exporter,
			final IExtensionFacade extensionFacade) {
		this.selectedFiles = selectedFiles;
		this.configurations = configurations;
		this.exporter = exporter;
		this.extensionFacade = extensionFacade;

	}

	public BindingArrayList<IFileDescriptor> getSelectedFiles() {
		return selectedFiles;
	}

	public void addSelectedFile(final IFileDescriptor file) {
		if (!selectedFiles.contains(file)) {
			selectedFiles.add(file);
		}
		Assert.assertTrue(selectedFiles.contains(file));
	}

	public void remove(final IFileDescriptor file) {
		if (selectedFiles.contains(file)) {
			selectedFiles.remove(file);
		}
		Assert.assertTrue(!selectedFiles.contains(file));
	}

	public void addConfiguration(final IConfiguration configuration) {
		if (!configurations.contains(configuration)) {
			configurations.add(configuration);
		}
		Assert.assertTrue(configurations.contains(configuration));
	}

	public BindingArrayList<IConfiguration> getConfigurations() {
		return configurations;
	}

	public void addConfigurations(final List<IConfiguration> configurations) {
		foreach(configurations, new ClosureI<IConfiguration>() {
			public void call(final IConfiguration in) {
				addConfiguration(in);
			}
		});
	}

	public IProfile getSelectedProfile() {
		return selectedProfile;
	}

	public void setSelectedProfile(final IProfile selectedProfile) {
		this.selectedProfile = selectedProfile;
		this.selectedConfiguration = null;
	}

	public IFileDescriptor getSelectedLogFile() {
		return selectedLogFile;
	}

	public void setSelectedLogFile(final IFileDescriptor selectedLogFile) {
		this.selectedLogFile = selectedLogFile;
	}

	public void setSelectedConfiguration(final IConfiguration configuration) {
		this.selectedConfiguration = configuration;
		this.selectedProfile = null;
	}

	public IConfiguration getSelectedConfiguration() {
		return selectedConfiguration;
	}

	public IProfileExporter getProfileExporter() {
		return exporter;
	}

	public IExtensionFacade getExtensionFacade() {
		return extensionFacade;
	}

}
