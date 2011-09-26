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

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.findFirst;
import static com.trivadis.loganalysis.core.common.CollectionUtil.flatten;
import static com.trivadis.loganalysis.core.common.ExtensionUtil.findExtensionInstances;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;

import com.trivadis.loganalysis.core.ExtensionPoint;
import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.ui.IExtensionFacade;
import com.trivadis.loganalysis.ui.IProfileProvider;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class ExtensionFacade implements IExtensionFacade {

	private static final String ELEMENT_NAME = "profileprovider";

	public List<IConfiguration> getConfigurationsFor(final IMemento memento) {
		final List<IProfileProvider> profileProviders = findExtensionInstances(ExtensionPoint.PROVIDER, ELEMENT_NAME);
		return collect(profileProviders, new ClosureIO<IProfileProvider, IConfiguration>() {
			public IConfiguration call(final IProfileProvider in) {
				return in.getConfiguration(memento);
			}
		});
	}

	public IConfiguration getConfigurationFor(final IJvmRun jvm) {
		final List<IProfileProvider> profileProviders = findExtensionInstances(ExtensionPoint.PROVIDER, ELEMENT_NAME);
		return findFirst(profileProviders, new Predicate<IProfileProvider>() {
			public boolean matches(final IProfileProvider profileProvider) {
				return profileProvider.knowsJvm(jvm);
			}
		}).getConfiguration();
	}

	public List<IProfile> getProfilesFromFile(final String fileName) {
		final List<IProfileProvider> profileProviders = findExtensionInstances(ExtensionPoint.PROVIDER, ELEMENT_NAME);
		return flatten(collect(profileProviders, new ClosureIO<IProfileProvider, List<IProfile>>() {
			public List<IProfile> call(final IProfileProvider profileProvider) {
				try {
					return profileProvider.getConfiguration(
							XMLMemento.createReadRoot(new BufferedReader(new FileReader(new File(fileName)))), false)
							.getProfiles();
				} catch (final Exception e) {
					throw new RuntimeException(e);
				}
			}
		}));
	}

	public List<IConfiguration> getConfigurations() {
		return getConfigurationsFor(null);
	}

}
