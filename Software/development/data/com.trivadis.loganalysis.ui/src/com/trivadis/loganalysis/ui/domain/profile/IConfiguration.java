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
package com.trivadis.loganalysis.ui.domain.profile;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.ui.common.binding.BindingArrayList;


public interface IConfiguration {

	BindingArrayList<IProfile> getProfiles();

	void addProfile(IProfile profile);

	void save(IMemento memento);

	String getLabel();

	IProfile getDefaultProfile();

	void addNewDefaultProfile(String label);

	void removeProfile(IProfile profile);

	String getKey();
}
