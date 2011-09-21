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
import static com.trivadis.loganalysis.core.common.CollectionUtil.findFirst;
import static com.trivadis.loganalysis.core.common.CollectionUtil.prepend;
import static java.util.Arrays.asList;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.jrockit.ui.internal.domain.profile.StandardProfile;
import com.trivadis.loganalysis.jrockit.ui.internal.domain.profile.StateValueProvider;
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

	final Predicate<IAxis> xType = typePredicate(AxisType.X);
	final Predicate<IAxis> yType = typePredicate(AxisType.Y);

	public IConfiguration loadConfigurationFrom(final IMemento memento) {
		final IMemento jrockitConfig = (memento != null) ? memento.getChild(Configuration.KEY) : null;
		final List<IProfile> list = (jrockitConfig != null) ? collect(
				asList(jrockitConfig.getChildren(Profile.MEMENTO_ELEMENT_NAME)), new ClosureIO<IMemento, IProfile>() {
					public IProfile call(final IMemento in) {
						return getProfile(in);
					}
				}) : new ArrayList<IProfile>();
		return new Configuration(prepend(new StandardProfile("Standard Profile"), list));
	}

	public IProfile getProfile(final IMemento in) {
		return new Profile(emptyIfNull(in.getString(Profile.ATTRIBUTE_LABEL)), collect(
				asList(in.getChildren(Chart.MEMENTO_ELEMENT_NAME)), new ClosureIO<IMemento, IChart>() {
					public IChart call(final IMemento in) {
						return getChart(in);
					}
				}));
	}

	private IAxis getAxis(final IMemento in) {
		return new Axis(AxisType.valueOf(in.getString(Axis.ATTRIBUTE_AXIS_TYPE)), in.getString(Axis.ATTRIBUTE_LABEL),
				new Color(in.getInteger(Axis.ATTRIBUTE_COLOR)), StateValueProvider.valueOf(in
						.getString(Axis.ATTRIBUTE_VALUE_PROVIDER)));
	}

	private IChart getChart(final IMemento in) {
		final Map<String, String> meta = new HashMap<String, String>();
		final IMemento metaMemento = in.getChild(Chart.MEMENTO_ELEMENT_META);
		if (metaMemento != null) {
			for (final String key : metaMemento.getAttributeKeys()) {
				meta.put(key, metaMemento.getString(key));
			}
		}

		final Chart chart = new Chart(ChartType.CUSTOM, emptyIfNull(in.getString(Chart.ATTRIBUTE_TAB_NAME)),
				emptyIfNull(in.getString(Chart.ATTRIBUTE_LABEL)), in.getString(Chart.ATTRIBUTE_DESCRIPTION), meta,
				collect(asList(in.getChildren(Serie.MEMENTO_ELEMENT_NAME)), new ClosureIO<IMemento, Serie>() {
					public Serie call(final IMemento in) {
						return getSerie(in);
					}
				}));
		chart.setShowOldCollections(falseIfNull(in.getBoolean(Chart.ATTRIBUTE_SHOW_OLD_COLLECTIONS)));
		chart.setShowYoungCollections(falseIfNull(in.getBoolean(Chart.ATTRIBUTE_SHOW_YOUNG_COLLECTIONS)));
		return chart;
	}

	private Serie getSerie(final IMemento in) {
		final List<IAxis> axis = collect(asList(in.getChildren(Axis.MEMENTO_ELEMENT_NAME)),
				new ClosureIO<IMemento, IAxis>() {
					public IAxis call(final IMemento in) {
						return getAxis(in);
					}
				});

		final IAxis xAxis = findFirst(axis, xType);
		final IAxis yAxis = findFirst(axis, yType);
		final Serie serie = new Serie(emptyIfNull(in.getString(Serie.ATTRIBUTE_LABEL)), xAxis, yAxis);
		serie.setDotted(falseIfNull(in.getBoolean(Serie.ATTRIBUTE_DOTTED)));
		return serie;
	}

	private boolean falseIfNull(final Boolean in) {
		return in != null ? in : false;
	}

	private String emptyIfNull(final String in) {
		return in != null ? in : "";
	}

	protected Predicate<IAxis> typePredicate(final AxisType type) {
		return new Predicate<IAxis>() {
			public boolean matches(final IAxis item) {
				return type.equals(item.getAxisType());
			}
		};
	}

}
