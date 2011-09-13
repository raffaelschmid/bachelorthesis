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

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.prepend;
import static java.util.Arrays.asList;

import java.awt.Color;
import java.util.List;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.common.Assert;
import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.ui.internal.IJRockitUiContext;
import com.trivadis.loganalysis.jrockit.ui.internal.JRockitExtension;
import com.trivadis.loganalysis.ui.IProfileProvider;
import com.trivadis.loganalysis.ui.domain.profile.Axis;
import com.trivadis.loganalysis.ui.domain.profile.AxisType;
import com.trivadis.loganalysis.ui.domain.profile.Chart;
import com.trivadis.loganalysis.ui.domain.profile.Configuration;
import com.trivadis.loganalysis.ui.domain.profile.IAxis;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;
import com.trivadis.loganalysis.ui.domain.profile.Profile;

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
		context.setExtension(getExtension(parent));
	}

	public void saveConfiguration(final IMemento parent, final IConfiguration configuration) {
		configuration.save(parent);
	}

	public IConfiguration getExtension() {
		Assert.assertNotNull(context.getExtension());
		return context.getExtension();
	}

	private IConfiguration getExtension(final IMemento parent) {
		final IMemento jrockitConfig = parent.getChild(Configuration.MEMENTO_ELEMENT_NAME);
		final List<IProfile> list = collect(asList(jrockitConfig.getChildren(Profile.MEMENTO_ELEMENT_NAME)),
				new ClosureIO<IMemento, IProfile>() {
					public IProfile call(final IMemento in) {
						return getProfile(in);
					}

				});
		return new Configuration("JRockit R28", prepend(new StandardProfile("Standard Profile"), list));
	}

	private IProfile getProfile(final IMemento in) {
		return new Profile(in.getString(Profile.ATTRIBUTE_LABEL), collect(
				asList(in.getChildren(Chart.MEMENTO_ELEMENT_NAME)), new ClosureIO<IMemento, IChart>() {
					public IChart call(final IMemento in) {
						return getChart(in);
					}
				}));
	}

	private IAxis getAxis(final IMemento in) {
		return new Axis(AxisType.valueOf(in.getString(Axis.ATTRIBUTE_AXIS_TYPE)), in.getString(Axis.ATTRIBUTE_LABEL),
				new Color(in.getInteger(Axis.ATTRIBUTE_COLOR)), ValueProvider.valueOf(in
						.getString(Axis.ATTRIBUTE_VALUE_PROVIDER)));
	}

	private IChart getChart(final IMemento in) {
		return new Chart(in.getString(Chart.ATTRIBUTE_LABEL), in.getString(Chart.ATTRIBUTE_DESCRIPTION), collect(
				asList(in.getChildren(Axis.MEMENTO_ELEMENT_NAME)), new ClosureIO<IMemento, IAxis>() {
					public IAxis call(final IMemento in) {
						return getAxis(in);
					}
				}));
	}

}
