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

import static com.trivadis.loganalysis.ui.domain.profile.AxisType.X;
import static com.trivadis.loganalysis.ui.domain.profile.AxisType.Y;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;

import org.eclipse.ui.XMLMemento;
import org.junit.Before;
import org.junit.Test;

import com.trivadis.loganalysis.jrockit.ui.internal.domain.profile.ValueProvider;
import com.trivadis.loganalysis.ui.domain.profile.Axis;
import com.trivadis.loganalysis.ui.domain.profile.Chart;
import com.trivadis.loganalysis.ui.domain.profile.ChartType;
import com.trivadis.loganalysis.ui.domain.profile.Configuration;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;
import com.trivadis.loganalysis.ui.domain.profile.IValueProvider;
import com.trivadis.loganalysis.ui.domain.profile.Profile;
import com.trivadis.loganalysis.ui.domain.profile.Serie;

public class ConfigurationFactoryTest {
	private final IConfigurationFactory instance = new ConfigurationFactory();
	private static final Color COLOR_X = Color.red;
	private static final IValueProvider VALUE_PROVIDER_X = ValueProvider.TIME;
	private static final IValueProvider VALUE_PROVIDER_Y = ValueProvider.MEMORY;
	private static final Color COLOR_Y = Color.blue;
	private static final String Y_AXIS_LABEL = "y-axis";
	private static final String X_AXIS_LABEL = "x-axis";
	private static final String CHART_LABEL = "mychart";
	private static final String PROFILE_LABEL = "profile-label-01";
	private static final String CHART_DESCRIPTION = "desc";
	private static final String PROFILE_TAB_NAME = "tab-name";
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLoadConfigurationFrom_empty() {
		final IConfiguration loadConfigurationFrom = instance.loadConfigurationFrom(XMLMemento.createWriteRoot("empty"));
		assertNotNull(loadConfigurationFrom);
	}
	@Test
	public void testLoadConfigurationFrom_null() {
		final IConfiguration loadConfigurationFrom = instance.loadConfigurationFrom(null);
		assertNotNull(loadConfigurationFrom);
	}
	
	@Test
	public void test_loadConfigurationFrom() throws Exception {
		configuration("foo").save(XMLMemento.createWriteRoot("empty"));
		final IConfiguration retVal = instance.loadConfigurationFrom(XMLMemento.createWriteRoot("empty"));
		assertNotNull(retVal);
		assertEquals(1, retVal.getProfiles().size());
		
		final IProfile profile = retVal.getProfiles().get(0);
		assertEquals(2,profile.getCharts().size());
		
		final IChart chart = profile.getCharts().get(0);
		assertEquals(1, chart.getSeries().size());
		
		final Serie serie = chart.getSeries().get(0);
		assertEquals(VALUE_PROVIDER_X, serie.getXaxis().getValueProvider());
		assertEquals(VALUE_PROVIDER_Y, serie.getYaxis().getValueProvider());
	}
	
	protected IConfiguration configuration(final String profileLabel) {
		return new Configuration(profileLabel, new Profile(PROFILE_LABEL, new Chart(ChartType.CUSTOM, PROFILE_TAB_NAME, CHART_LABEL,
				CHART_DESCRIPTION, new Serie("",new Axis(X, X_AXIS_LABEL, COLOR_X, VALUE_PROVIDER_X), new Axis(Y,
						Y_AXIS_LABEL, COLOR_Y, VALUE_PROVIDER_Y)))), new Profile(PROFILE_LABEL, new Chart(ChartType.CUSTOM, 
				PROFILE_TAB_NAME, CHART_LABEL, CHART_DESCRIPTION, new Serie("",new Axis(X, X_AXIS_LABEL, COLOR_X,
						VALUE_PROVIDER_X), new Axis(Y, Y_AXIS_LABEL, COLOR_Y, VALUE_PROVIDER_Y)))));

	}

}
