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
package com.trivadis.loganalysis.ui;

import java.util.List;

import com.trivadis.loganalysis.core.SelectedFilesChangeListener;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.ui.domain.IProfile;

public interface IUiContext {
	List<IFileDescriptor> getSelectedFiles();

	void addSelectedFile(IFileDescriptor file);

	void remove(IFileDescriptor file);

	List<IProfile> getProfiles();

	void addProfile(IProfile profile);

	void addLogFilesChangeListener(SelectedFilesChangeListener listener);

}