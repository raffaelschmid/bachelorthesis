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
import static com.trivadis.loganalysis.core.common.CollectionUtil.findFirst;

import java.util.List;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.ExtensionPoint;
import com.trivadis.loganalysis.core.Loganalysis;
import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.ui.domain.profile.IExtension;
import com.trivadis.loganalysis.ui.internal.UiContext;

public class UiLoganalysis {
	private static final String ELEMENT_NAME = "profileprovider";

	private static class Holder {
		private static IUiContext INSTANCE = new UiContext();
	}

	public static IUiContext getUiContext() {
		return Holder.INSTANCE;
	}

	public static List<IExtension> getConfigurations() {
		return getConfigurations(null);
	}

	public static List<IExtension> getConfigurations(final IMemento memento) {
		final List<IProfileProvider> findExtensionInstances = Loganalysis.findExtensionInstances(
				ExtensionPoint.PROVIDER, ELEMENT_NAME);
		return collect(findExtensionInstances, new ClosureIO<IProfileProvider, IExtension>() {
			public IExtension call(final IProfileProvider in) {
				if (memento != null)
					in.loadConfiguration(memento);
				return in.getConfiguration();
			}
		});
	}

	public static IExtension getConfigurationForJvm(final IJvmRun jvm) {
		final List<IProfileProvider> findExtensionInstances = Loganalysis.findExtensionInstances(
				ExtensionPoint.PROVIDER, ELEMENT_NAME);
		return findFirst(findExtensionInstances, new Predicate<IProfileProvider>() {
			public boolean matches(final IProfileProvider item) {
				return item.knowsJvm(jvm);
			}
		}).getConfiguration();
	}
}
