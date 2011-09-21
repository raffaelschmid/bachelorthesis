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
package com.trivadis.loganalysis.ui.internal;

import java.awt.Color;
import java.io.IOException;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

import com.trivadis.loganalysis.ui.domain.DummyValueProvider;
import com.trivadis.loganalysis.ui.domain.profile.Axis;
import com.trivadis.loganalysis.ui.domain.profile.AxisType;
import com.trivadis.loganalysis.ui.domain.profile.Chart;
import com.trivadis.loganalysis.ui.domain.profile.ChartType;
import com.trivadis.loganalysis.ui.domain.profile.Configuration;
import com.trivadis.loganalysis.ui.domain.profile.Profile;
import com.trivadis.loganalysis.ui.domain.profile.Serie;

public class ProfileExporterTest {
	private final IProfileExporter instance = new ProfileExporter();
	private Configuration configuration;

	@Before
	public void setUp() throws Exception {
		final Profile a = new Profile("exported-profile", new Chart(ChartType.CUSTOM, "tabName",
				"label", "description", new Serie("points", new Axis(AxisType.X, "label", Color.red,
						new DummyValueProvider("valueprovider")), new Axis(AxisType.Y, "label", Color.red,
						new DummyValueProvider("valueprovider")))));
		final Profile b = new Profile("exported-profile", new Chart(ChartType.CUSTOM, "tabName",
				"label", "description", new Serie("points", new Axis(AxisType.X, "label", Color.red,
						new DummyValueProvider("valueprovider")), new Axis(AxisType.Y, "label", Color.red,
								new DummyValueProvider("valueprovider")))));
		configuration = new Configuration(a,b);
	}

	@Test
	public void testExport() throws IOException {
		final StringWriter sw = new StringWriter();
		instance.export(configuration.getProfiles(), sw);
		System.out.println(sw);
	}

}
