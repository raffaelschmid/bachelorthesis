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

import com.trivadis.loganalysis.core.common.Assert;
import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.ui.IJRockitUiContext;
import com.trivadis.loganalysis.jrockit.ui.domain.Configuration;
import com.trivadis.loganalysis.ui.IProfileProvider;
import com.trivadis.loganalysis.ui.domain.profile.IExtension;

public class ProfileProvider implements IProfileProvider {

	private final IJRockitUiContext context;

	public ProfileProvider() {
		this(JRockitExtension.getContext());
	}

	public ProfileProvider(final IJRockitUiContext context) {
		this.context = context;
	}

	public boolean knowsJvm(final IJvmRun jvm) {
		return jvm instanceof JRockitJvmRun;
	}

	public void loadConfiguration(final IMemento parent) {
		context.setExtension(Configuration.loadMemento(parent));
	}

	public void saveConfiguration(final IMemento parent, final IExtension configuration) {
		configuration.save(parent);
	}

	public IExtension getConfiguration() {
		Assert.assertNotNull(context.getExtension());
		return context.getExtension();
	}

}
