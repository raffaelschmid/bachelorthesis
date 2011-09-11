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
package com.trivadis.loganalysis.jrockit.ui.domain.profile;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.ui.domain.Configuration;
import com.trivadis.loganalysis.ui.IProfileProvider;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;

public class ProfileProvider implements IProfileProvider {

	private final IConfiguration defaultConfiguration;
	private IConfiguration configuration;

	public ProfileProvider() {
		this(JRockitExtension.getContext().getConfiguration());
	}

	public ProfileProvider(final IConfiguration defaultConfiguration) {
		this.defaultConfiguration = defaultConfiguration;
	}

	public boolean knowsJvm(final IJvmRun jvm) {
		return jvm instanceof JRockitJvmRun;
	}

	public void loadConfiguration(final IMemento parent) {
		this.configuration = Configuration.loadMemento(parent);
	}

	public void saveConfiguration(final IMemento parent, final IConfiguration configuration) {
		configuration.saveMemento(parent);
	}

	public IConfiguration getConfiguration() {
		return configuration != null ? configuration : defaultConfiguration;
	}

}
