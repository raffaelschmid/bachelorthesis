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

import static com.trivadis.loganalysis.ui.domain.profile.AxisType.X;
import static com.trivadis.loganalysis.ui.domain.profile.AxisType.Y;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;
import org.junit.Test;

import com.trivadis.loganalysis.jrockit.ui.internal.domain.profile.ProfileProvider;
import com.trivadis.loganalysis.jrockit.ui.internal.domain.profile.ValueProvider;
import com.trivadis.loganalysis.ui.IProfileProvider;
import com.trivadis.loganalysis.ui.domain.profile.Axis;
import com.trivadis.loganalysis.ui.domain.profile.Chart;
import com.trivadis.loganalysis.ui.domain.profile.Configuration;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.Profile;

public class ProfileProviderTest {

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

	private final IProfileProvider instance = new ProfileProvider();

	@Test
	public void test_saveMementoList() throws Exception {
		instance.saveConfiguration(memento(), configuration(JROCKIT_R28));
	}

	@Test
	public void test_loadMemento_list() {
		final IMemento memento = memento();
		instance.saveConfiguration(memento, configuration(JROCKIT_R28));

		instance.loadConfiguration(memento);
		final IConfiguration retVal = instance.getExtension();
		assertNotNull(retVal);

		assertEquals(JROCKIT_R28, retVal.getLabel());
	}

	protected IMemento memento() {
		return XMLMemento.createWriteRoot("state");
	}

	protected IConfiguration configuration(final String profileLabel) {
		return new Configuration(profileLabel, new Profile(PROFILE_LABEL, new Chart(CHART_LABEL, CHART_DESCRIPTION,
				new Axis(X, X_AXIS_LABEL, COLOR_X, VALUE_PROVIDER_X), new Axis(Y, Y_AXIS_LABEL, COLOR_Y,
						VALUE_PROVIDER_Y))), new Profile(PROFILE_LABEL, new Chart(CHART_LABEL, CHART_DESCRIPTION,
				new Axis(X, X_AXIS_LABEL, COLOR_X, VALUE_PROVIDER_X), new Axis(Y, Y_AXIS_LABEL, COLOR_Y,
						VALUE_PROVIDER_Y))));

	}
}
