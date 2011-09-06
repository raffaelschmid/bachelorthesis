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

import static org.junit.Assert.assertNotNull;

import java.awt.Color;

import org.junit.Test;

public class UiDomainIntegrationTest {

	@Test
	public void test_create_domain_model_long() {
		IChart chart = new Chart("");
		IProfile profile = new Profile("Default");
		profile.addChart(chart);

		assertNotNull(profile);
	}

	@Test
	public void test_create_domain_model_short() {
		IProfile profile = new Profile("default", new Chart("mychart", new XAxis("x-axis", Color.red, ValueProvider.MEMORY), new YAxis(
				"y-axis", Color.blue)));
		assertNotNull(profile);
	}

}
