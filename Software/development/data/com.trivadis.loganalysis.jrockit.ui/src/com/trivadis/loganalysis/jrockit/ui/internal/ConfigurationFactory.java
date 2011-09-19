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

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.prepend;
import static java.util.Arrays.asList;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.jrockit.ui.internal.domain.profile.StandardProfile;
import com.trivadis.loganalysis.jrockit.ui.internal.domain.profile.ValueProvider;
import com.trivadis.loganalysis.ui.domain.profile.Axis;
import com.trivadis.loganalysis.ui.domain.profile.AxisType;
import com.trivadis.loganalysis.ui.domain.profile.Chart;
import com.trivadis.loganalysis.ui.domain.profile.ChartType;
import com.trivadis.loganalysis.ui.domain.profile.Configuration;
import com.trivadis.loganalysis.ui.domain.profile.IAxis;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;
import com.trivadis.loganalysis.ui.domain.profile.Profile;
import com.trivadis.loganalysis.ui.domain.profile.Serie;

public class ConfigurationFactory implements IConfigurationFactory {

	public IConfiguration loadConfigurationFrom(final IMemento memento) {
		final IMemento jrockitConfig = (memento != null) ? memento.getChild(Configuration.MEMENTO_ELEMENT_NAME) : null;
		final List<IProfile> list = (jrockitConfig != null) ? collect(
				asList(jrockitConfig.getChildren(Profile.MEMENTO_ELEMENT_NAME)), new ClosureIO<IMemento, IProfile>() {
					public IProfile call(final IMemento in) {
						return getProfile(in);
					}
				}) : new ArrayList<IProfile>();
		return new Configuration("JRockit R28", prepend(new StandardProfile("Standard Profile"), list));
	}

	private IProfile getProfile(final IMemento in) {
		return new Profile(emptyIfNull(in.getString(Profile.ATTRIBUTE_LABEL)), collect(
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
		return new Chart(ChartType.CUSTOM, emptyIfNull(in.getString(Chart.ATTRIBUTE_TAB_NAME)),
				emptyIfNull(in.getString(Chart.ATTRIBUTE_LABEL)), in.getString(Chart.ATTRIBUTE_DESCRIPTION), collect(
						asList(in.getChildren(Serie.MEMENTO_ELEMENT_NAME)), new ClosureIO<IMemento, Serie>() {
							public Serie call(final IMemento in) {
								return getSerie(in);
							}

						}));
	}

	private Serie getSerie(final IMemento in) {
		return new Serie(emptyIfNull(in.getString(Serie.ATTRIBUTE_LABEL)), collect(
				asList(in.getChildren(Axis.MEMENTO_ELEMENT_NAME)), new ClosureIO<IMemento, IAxis>() {
					public IAxis call(final IMemento in) {
						return getAxis(in);
					}
				}));
	}

	private String emptyIfNull(final String in) {
		return in != null ? in : "";
	}
}
