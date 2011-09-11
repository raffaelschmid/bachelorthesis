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

import com.trivadis.loganalysis.ui.domain.profile.IAxis;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IExtension;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class UiDomainTest {

	private static final String JROCKIT_R28 = "JRockit R28";
	private static final Color COLOR_X = Color.red;
	private static final ValueProvider VALUE_PROVIDER_X = ValueProvider.TIME;
	private static final ValueProvider VALUE_PROVIDER_Y = ValueProvider.MEMORY;
	private static final Color COLOR_Y = Color.blue;
	private static final String Y_AXIS_LABEL = "y-axis";
	private static final String X_AXIS_LABEL = "x-axis";
	private static final String CHART_LABEL = "mychart";
	private static final String PROFILE_LABEL = "profile-label-01";
	private static final String CHART_DESCRIPTION = "desc";

	@Test
	public void test_create_domain_model_long() {
		final IChart chart01 = new Chart(CHART_LABEL, CHART_DESCRIPTION);
		final IAxis xAxis = new Axis(X, X_AXIS_LABEL, COLOR_X, VALUE_PROVIDER_X);
		final IAxis yAxis = new Axis(Y, Y_AXIS_LABEL, COLOR_Y, VALUE_PROVIDER_Y);
		chart01.addAxis(xAxis);
		chart01.addAxis(yAxis);

		final IProfile profile = new Profile(PROFILE_LABEL);
		profile.addChart(chart01);

		final IExtension configuration = new Configuration(JROCKIT_R28);
		configuration.addProfile(profile);

		verifyConfiguration(configuration);
	}

	@Test
	public void test_create_domain_model_short() {
		verifyConfiguration(new Configuration(JROCKIT_R28, new Profile(PROFILE_LABEL, new Chart(CHART_LABEL,
				CHART_DESCRIPTION, new Axis(X, X_AXIS_LABEL, COLOR_X, VALUE_PROVIDER_X), new Axis(Y, Y_AXIS_LABEL,
						COLOR_Y, VALUE_PROVIDER_Y)))));
	}

	protected void verifyConfiguration(final IExtension configuration) {
		final IProfile profile = configuration.getProfiles().get(0);
		assertEquals(1, profile.getCharts().size());
		assertEquals(PROFILE_LABEL, profile.getLabel());

		final IChart chart02 = profile.getCharts().get(0);
		assertEquals(CHART_LABEL, chart02.getLabel());

		final IAxis xAxis = chart02.getXAxes().get(0);
		assertEquals(X_AXIS_LABEL, xAxis.getLabel());

		final IAxis yAxis = chart02.getYAxes().get(0);
		assertEquals(Y_AXIS_LABEL, yAxis.getLabel());
	}

	@Test
	public void test_saveMemento() throws IOException {
		final XMLMemento memento = (XMLMemento) memento();
		configuration(PROFILE_LABEL).save(memento);
		assertNotNull(memento);
	}

	@Test
	public void test_loadMemento() throws Exception {
		final IMemento memento = memento();
		configuration(JROCKIT_R28).save(memento);

		final IExtension configuration = Configuration.loadMemento(memento);
		assertNotNull(configuration);
		assertEquals(JROCKIT_R28, configuration.getLabel());
	}

	

	protected IExtension configuration(final String profileLabel) {
		return new Configuration(profileLabel, new Profile(PROFILE_LABEL, new Chart(CHART_LABEL, CHART_DESCRIPTION,
				new Axis(X, X_AXIS_LABEL, COLOR_X, VALUE_PROVIDER_X), new Axis(Y, Y_AXIS_LABEL, COLOR_Y,
						VALUE_PROVIDER_Y))), new Profile(PROFILE_LABEL, new Chart(CHART_LABEL, CHART_DESCRIPTION,
				new Axis(X, X_AXIS_LABEL, COLOR_X, VALUE_PROVIDER_X), new Axis(Y, Y_AXIS_LABEL, COLOR_Y,
						VALUE_PROVIDER_Y))));

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
