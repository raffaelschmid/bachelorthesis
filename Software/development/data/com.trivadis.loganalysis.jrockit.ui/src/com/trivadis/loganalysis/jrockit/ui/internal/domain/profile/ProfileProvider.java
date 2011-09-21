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
package com.trivadis.loganalysis.jrockit.ui.internal.domain.profile;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.common.Assert;
import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.ui.internal.IConfigurationFactory;
import com.trivadis.loganalysis.jrockit.ui.internal.IJRockitUiContext;
import com.trivadis.loganalysis.jrockit.ui.internal.JRockitExtension;
import com.trivadis.loganalysis.ui.IProfileProvider;
import com.trivadis.loganalysis.ui.domain.profile.Configuration;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class ProfileProvider implements IProfileProvider {

	private final IJRockitUiContext context;
	private final IConfigurationFactory configurationFactory;

	public ProfileProvider() {
		this(JRockitExtension.getContext(), JRockitExtension.getContext().getConfigurationFactory());
	}

	public ProfileProvider(final IJRockitUiContext context, final IConfigurationFactory configurationFactory) {
		this.context = context;
		this.configurationFactory = configurationFactory;
	}

	public boolean knowsJvm(final IJvmRun jvm) {
		return jvm instanceof JRockitJvmRun;
	}

	public IProfile getProfile(final IMemento memento) {
		return configurationFactory.getProfile(memento);
	}

	public void saveConfiguration(final IMemento parent, final IConfiguration configuration) {
		configuration.save(parent);
	}

	public IConfiguration getConfiguration(final IMemento memento) {
		return getConfiguration(memento, true);
	}

	public IConfiguration getConfiguration(final IMemento memento, final boolean store) {
		final IConfiguration configuration = configurationFactory.loadConfigurationFrom(memento);
		if (store)
			context.setConfiguration(configuration);
		return configuration;
	}

	public IConfiguration getConfiguration() {
		Assert.assertNotNull(context.getConfiguration());
		return context.getConfiguration();
	}

	public String getKey() {
		return Configuration.KEY;
	}
}
