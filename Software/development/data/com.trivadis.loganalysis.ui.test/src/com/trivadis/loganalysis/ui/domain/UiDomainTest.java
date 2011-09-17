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
package com.trivadis.loganalysis.ui.domain;

import static com.trivadis.loganalysis.ui.domain.profile.AxisType.X;
import static com.trivadis.loganalysis.ui.domain.profile.AxisType.Y;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;
import org.junit.Test;

import com.trivadis.loganalysis.ui.domain.profile.Axis;
import com.trivadis.loganalysis.ui.domain.profile.AxisType;
import com.trivadis.loganalysis.ui.domain.profile.Chart;
import com.trivadis.loganalysis.ui.domain.profile.ChartType;
import com.trivadis.loganalysis.ui.domain.profile.Configuration;
import com.trivadis.loganalysis.ui.domain.profile.IAxis;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;
import com.trivadis.loganalysis.ui.domain.profile.IValueProvider;
import com.trivadis.loganalysis.ui.domain.profile.Profile;
import com.trivadis.loganalysis.ui.domain.profile.Serie;

public class UiDomainTest {

	private static final String JROCKIT_R28 = "JRockit R28";
	private static final Color COLOR_X = Color.red;
	private static final IValueProvider VALUE_PROVIDER_X = new DummyValueProvider("x");
	private static final IValueProvider VALUE_PROVIDER_Y = new DummyValueProvider("y");
	private static final Color COLOR_Y = Color.blue;
	private static final String Y_AXIS_LABEL = "y-axis";
	private static final String X_AXIS_LABEL = "x-axis";
	private static final String CHART_LABEL = "mychart";
	private static final String PROFILE_LABEL = "profile-label-01";
	private static final String CHART_DESCRIPTION = "desc";
	private static final String PROFILE_TAB_NAME = "tab-name";

	@Test
	public void test_create_domain_model_long() {
		final IChart chart01 = new Chart(ChartType.CUSTOM, PROFILE_TAB_NAME, CHART_LABEL, CHART_DESCRIPTION);
		final IAxis xAxis = new Axis(X, X_AXIS_LABEL, COLOR_X, VALUE_PROVIDER_X);
		final IAxis yAxis = new Axis(Y, Y_AXIS_LABEL, COLOR_Y, VALUE_PROVIDER_Y);
		chart01.addSerie(new Serie(xAxis, yAxis));

		final IProfile profile = new Profile(PROFILE_LABEL);
		profile.addChart(chart01);

		final IConfiguration configuration = new Configuration(JROCKIT_R28);
		configuration.addProfile(profile);

		verifyConfiguration(configuration);
	}

	@Test
	public void test_create_domain_model_short() {
		verifyConfiguration(new Configuration(JROCKIT_R28, new Profile(PROFILE_LABEL, new Chart(ChartType.CUSTOM,
				PROFILE_TAB_NAME, CHART_LABEL, CHART_DESCRIPTION, new Serie(new Axis(X, X_AXIS_LABEL, COLOR_X,
						VALUE_PROVIDER_X), new Axis(Y, Y_AXIS_LABEL, COLOR_Y, VALUE_PROVIDER_Y))))));
	}

	protected void verifyConfiguration(final IConfiguration configuration) {
		final IProfile profile = configuration.getProfiles().get(0);
		assertEquals(1, profile.getCharts().size());
		assertEquals(PROFILE_LABEL, profile.getLabel());

		final IChart chart02 = profile.getCharts().get(0);
		assertEquals(CHART_LABEL, chart02.getLabel());

		final IAxis xAxis = chart02.getSeries().get(0).getAxes(AxisType.X).get(0);
		assertEquals(X_AXIS_LABEL, xAxis.getLabel());

		final IAxis yAxis = chart02.getSeries().get(0).getAxes(AxisType.Y).get(0);
		assertEquals(Y_AXIS_LABEL, yAxis.getLabel());
	}

	@Test
	public void test_saveMemento() throws IOException {
		final XMLMemento memento = (XMLMemento) memento();
		configuration(PROFILE_LABEL).save(memento);
		showXmlContent(memento);
		assertNotNull(memento);
	}

	protected IConfiguration configuration(final String profileLabel) {
		return new Configuration(profileLabel, new Profile(PROFILE_LABEL, new Chart(ChartType.CUSTOM, PROFILE_TAB_NAME,
				CHART_LABEL, CHART_DESCRIPTION, new Serie(new Axis(X, X_AXIS_LABEL, COLOR_X, VALUE_PROVIDER_X),
						new Axis(Y, Y_AXIS_LABEL, COLOR_Y, VALUE_PROVIDER_Y)))), new Profile(PROFILE_LABEL, new Chart(
				ChartType.CUSTOM, PROFILE_TAB_NAME, CHART_LABEL, CHART_DESCRIPTION, new Serie(new Axis(X, X_AXIS_LABEL,
						COLOR_X, VALUE_PROVIDER_X), new Axis(Y, Y_AXIS_LABEL, COLOR_Y, VALUE_PROVIDER_Y)))));

	}

	protected IMemento memento() {
		return XMLMemento.createWriteRoot("state");
	}

	protected void showXmlContent(final XMLMemento memento) throws IOException {
		final Writer sw = new StringWriter();
		memento.save(sw);
		System.out.println(sw.toString());
	}

}
