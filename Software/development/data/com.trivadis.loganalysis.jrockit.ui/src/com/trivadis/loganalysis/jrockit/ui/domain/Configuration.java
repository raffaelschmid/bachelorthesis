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
package com.trivadis.loganalysis.jrockit.ui.domain;

import static com.trivadis.loganalysis.core.common.CollectionUtil.findAll;
import static com.trivadis.loganalysis.core.common.CollectionUtil.findFirst;
import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;
import static com.trivadis.loganalysis.core.common.CollectionUtil.prepend;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.common.Closure;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.jrockit.ui.domain.profile.DefaultProfile;
import com.trivadis.loganalysis.ui.common.binding.BindingArrayList;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class Configuration implements IConfiguration {

	public static final String MEMENTO_ELEMENT_NAME = "jrockit-r28";
	private final BindingArrayList<IProfile> profiles = new BindingArrayList<IProfile>(new ArrayList<IProfile>());
	private final String label;

	public Configuration(final String label, final IProfile... profiles) {
		this(label, asList(profiles));
	}

	public Configuration(final String label, final List<IProfile> profiles) {
		this.label = label;
		for (final IProfile profile : profiles) {
			addProfile(profile);
		}
	}

	public BindingArrayList<IProfile> getProfiles() {
		return profiles;
	}

	public void addProfile(final IProfile profile) {
		profiles.add(profile);
		profile.setConfiguration(this);
	}

	public void saveMemento(final IMemento memento) {
		final IMemento configuration = memento.createChild(MEMENTO_ELEMENT_NAME);
		foreach(findAll(profiles, new Predicate<IProfile>() {
			public boolean matches(final IProfile item) {
				return !(item instanceof DefaultProfile);
			}
		}), new Closure<IProfile>() {
			public void call(final IProfile in) {
				in.saveMemento(configuration);
			}
		});
	}

	public static IConfiguration loadMemento(final IMemento memento) {
		final List<IProfile> list = new ArrayList<IProfile>();
		final IMemento jrockitConfig = memento.getChild(Configuration.MEMENTO_ELEMENT_NAME);
		foreach(asList(jrockitConfig.getChildren(Profile.MEMENTO_ELEMENT_NAME)), new Closure<IMemento>() {
			public void call(final IMemento in) {
				list.add(Profile.loadMemento(in));
			}
		});
		return new Configuration("JRockit R28", prepend(new DefaultProfile("Standard Profile"), list));
	}

	@Override
	public String toString() {
		return "Configuration [profiles=" + profiles + "]";
	}

	public String getLabel() {
		return label;
	}

	public IProfile getDefaultProfile() {
		return findFirst(profiles, new Predicate<IProfile>() {
			public boolean matches(final IProfile item) {
				return item instanceof DefaultProfile;
			}
		});
	}

	public void addNewDefaultProfile(final String label) {
		this.addProfile(new Profile(label));
	}

	public void removeProfile(final IProfile profile) {
		this.profiles.remove(profile);
	}

}
