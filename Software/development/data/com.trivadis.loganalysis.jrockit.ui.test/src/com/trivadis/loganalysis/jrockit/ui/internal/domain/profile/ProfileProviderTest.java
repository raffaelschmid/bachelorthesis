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
import static org.junit.Assert.assertNotNull;

import java.awt.Color;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;
import org.junit.Test;

import com.trivadis.loganalysis.ui.IProfileProvider;
import com.trivadis.loganalysis.ui.domain.profile.Axis;
import com.trivadis.loganalysis.ui.domain.profile.Chart;
import com.trivadis.loganalysis.ui.domain.profile.ChartType;
import com.trivadis.loganalysis.ui.domain.profile.Configuration;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.Profile;
import com.trivadis.loganalysis.ui.domain.profile.Serie;

public class ProfileProviderTest {

	private static final Color COLOR_X = Color.red;
	private static final StateValueProvider VALUE_PROVIDER_X = StateValueProvider.TIME;
	private static final StateValueProvider VALUE_PROVIDER_Y = StateValueProvider.MEMORY;
	private static final Color COLOR_Y = Color.blue;
	private static final String Y_AXIS_LABEL = "y-axis";
	private static final String X_AXIS_LABEL = "x-axis";
	private static final String CHART_LABEL = "mychart";
	private static final String PROFILE_LABEL = "profile-label-01";
	private static final String CHART_DESCRIPTION = "desc";
	private static final String PROFILE_TAB_NAME = "tab-name";

	private final IProfileProvider instance = new ProfileProvider();

	@Test
	public void test_loadMemento_list() {
		final IMemento memento = memento();
		instance.getConfiguration(memento);
		final IConfiguration retVal = instance.getConfiguration();
		assertNotNull(retVal);
	}

	protected IMemento memento() {
		return XMLMemento.createWriteRoot("state");
	}

	protected IConfiguration configuration() {
		return new Configuration(new Profile(PROFILE_LABEL, new Chart(ChartType.CUSTOM, PROFILE_TAB_NAME, CHART_LABEL,
				CHART_DESCRIPTION, new Serie("",new Axis(X, X_AXIS_LABEL, COLOR_X, VALUE_PROVIDER_X), new Axis(Y,
						Y_AXIS_LABEL, COLOR_Y, VALUE_PROVIDER_Y)))), new Profile(PROFILE_LABEL, new Chart(ChartType.CUSTOM, 
				PROFILE_TAB_NAME, CHART_LABEL, CHART_DESCRIPTION, new Serie("",new Axis(X, X_AXIS_LABEL, COLOR_X,
						VALUE_PROVIDER_X), new Axis(Y, Y_AXIS_LABEL, COLOR_Y, VALUE_PROVIDER_Y)))));

	}
}
