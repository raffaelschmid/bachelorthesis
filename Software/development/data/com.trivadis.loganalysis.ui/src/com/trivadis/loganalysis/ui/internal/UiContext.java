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
import com.trivadis.loganalysis.core.common.Closure;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.ui.IUiContext;
import com.trivadis.loganalysis.ui.common.binding.BindingArrayList;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class UiContext implements IUiContext {

	private IProfile selectedProfile = null;
	private IFileDescriptor selectedLogFile = null;
	private final BindingArrayList<IConfiguration> configurations;
	private final BindingArrayList<IFileDescriptor> selectedFiles;

	public UiContext() {
		this.selectedFiles = new BindingArrayList<IFileDescriptor>(new ArrayList<IFileDescriptor>());
		this.configurations = new BindingArrayList<IConfiguration>(new ArrayList<IConfiguration>());
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

	public BindingArrayList<IConfiguration> getProfiles() {
		return configurations;
	}

	public void addConfigurations(final List<IConfiguration> configurations) {
		foreach(configurations, new Closure<IConfiguration>() {
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
	}

	public IFileDescriptor getSelectedLogFile() {
		return selectedLogFile;
	}

	public void setSelectedLogFile(final IFileDescriptor selectedLogFile) {
		this.selectedLogFile = selectedLogFile;
	}
}
