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
package com.trivadis.loganalysis.jrockit.ui.internal;

import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;

public class JRockitUiContext implements IJRockitUiContext {
	private IConfiguration extension = null;
	private final IConfigurationFactory configurationFactory;

	public JRockitUiContext() {
		this.configurationFactory = new ConfigurationFactory();
	}

	public IConfiguration getExtension() {
		return extension;
	}

	public void setExtension(final IConfiguration extension) {
		this.extension = extension;
	}

	public IConfigurationFactory getConfigurationFactory() {
		return configurationFactory;
	}
}
