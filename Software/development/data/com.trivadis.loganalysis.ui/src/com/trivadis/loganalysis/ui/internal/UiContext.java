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

import java.util.ArrayList;
import java.util.List;

import com.trivadis.loganalysis.core.SelectedFilesChangeListener;
import com.trivadis.loganalysis.core.common.Assert;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.ui.IUiContext;
import com.trivadis.loganalysis.ui.domain.IProfile;

public class UiContext implements IUiContext {

	private final ArrayList<IProfile> profiles;
	private final ArrayList<IFileDescriptor> selectedFiles;
	private final List<SelectedFilesChangeListener> listeners;

	public UiContext() {
		this.selectedFiles = new ArrayList<IFileDescriptor>();
		this.profiles = new ArrayList<IProfile>();
		this.listeners = new ArrayList<SelectedFilesChangeListener>();
	}

	public List<IFileDescriptor> getSelectedFiles() {
		return selectedFiles;
	}

	public void addLogFilesChangeListener(SelectedFilesChangeListener listener) {
		listeners.add(listener);
	}

	public void addSelectedFile(IFileDescriptor file) {
		if (!selectedFiles.contains(file)) {
			selectedFiles.add(file);
			notifySelectedFilesListeners();
		}
		Assert.assertTrue(selectedFiles.contains(file));
	}

	public void remove(IFileDescriptor file) {
		if (selectedFiles.contains(file)) {
			selectedFiles.remove(file);
			notifySelectedFilesListeners();
		}
		Assert.assertTrue(!selectedFiles.contains(file));
	}

	private void notifySelectedFilesListeners() {
		for (SelectedFilesChangeListener selectedFilesListeners : listeners) {
			selectedFilesListeners.fileSelectionChanged();
		}
	}

	public void removeProfile(IProfile profile) {
		if (profiles.contains(profile)) {
			profiles.remove(profile);
		}
		Assert.assertTrue(!profiles.contains(profile));
	}

	public void addProfile(IProfile profile) {
		if (!profiles.contains(profile)) {
			profiles.add(profile);
		}
		Assert.assertTrue(profiles.contains(profile));
	}

	public List<IProfile> getProfiles() {
		return profiles;
	}
}
