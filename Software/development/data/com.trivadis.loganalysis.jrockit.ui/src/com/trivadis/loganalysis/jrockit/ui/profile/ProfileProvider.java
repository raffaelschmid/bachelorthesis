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
package com.trivadis.loganalysis.jrockit.ui.profile;

import com.trivadis.loganalysis.jrockit.ui.domain.Configuration;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.extensionpoint.IProfileProvider;

public class ProfileProvider implements IProfileProvider {

	public IConfiguration getConfiguration() {
		return new Configuration("JRockit");
	}

}
