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

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.findFirst;
import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;

import com.trivadis.loganalysis.core.common.Closure;
import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.jrockit.ui.profile.DefaultProfile;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class Configuration implements IConfiguration {

	private static final String MEMENTO_ELEMENT_NAME = "configuration";
	private static final String ATTRIBUTE_LABEL = "label";
	private final List<IProfile> profiles = new ArrayList<IProfile>();
	private final String label;

	public Configuration(final String label, final IProfile... profiles) {
		this(label, asList(profiles));
	}

	public Configuration(final String label, final List<IProfile> profiles) {
		this.label = label;
		addProfile(new DefaultProfile("Default"));
		for(final IProfile profile : profiles){
			addProfile(profile);
		}
	}

	public List<IProfile> getProfiles() {
		return profiles;
	}

	public void addProfile(final IProfile profile) {
		profiles.add(profile);
		profile.setConfiguration(this);
	}

	public void saveMemento(final XMLMemento memento) {
		final IMemento configuration = memento.createChild(MEMENTO_ELEMENT_NAME);
		configuration.putString(ATTRIBUTE_LABEL, getLabel());
		foreach(profiles, new Closure<IProfile>() {
			public void call(final IProfile in) {
				in.saveMemento(configuration);
			}
		});
	}

	public static IConfiguration loadMemento(final IMemento memento) {
		final IMemento configuration = memento.getChild(MEMENTO_ELEMENT_NAME);
		return new Configuration(configuration.getString(ATTRIBUTE_LABEL), collect(
				asList(configuration.getChildren(Profile.MEMENTO_ELEMENT_NAME)), new ClosureIO<IMemento, IProfile>() {
					public IProfile call(final IMemento in) {
						return Profile.loadMemento(in);
					}
				}));
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

}
