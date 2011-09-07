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

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;

import java.util.List;

import com.trivadis.loganalysis.core.ExtensionPoint;
import com.trivadis.loganalysis.core.Loganalysis;
import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.extensionpoint.IProfileProvider;
import com.trivadis.loganalysis.ui.internal.UiContext;

public class UiLoganalysis {
	private static final String ELEMENT_NAME = "profileprovider";

	private static class Holder {
		private static IUiContext INSTANCE = new UiContext();
	}

	public static IUiContext getUiContext() {
		return Holder.INSTANCE;
	}

	public static List<IConfiguration> getConfigurations() {
		final List<IProfileProvider> findExtensionInstances = Loganalysis.findExtensionInstances(
				ExtensionPoint.PROVIDER, ELEMENT_NAME);
		return collect(findExtensionInstances, new ClosureIO<IProfileProvider, IConfiguration>() {
			public IConfiguration call(final IProfileProvider in) {
				return in.getConfiguration();
			}
		});
	}
}
