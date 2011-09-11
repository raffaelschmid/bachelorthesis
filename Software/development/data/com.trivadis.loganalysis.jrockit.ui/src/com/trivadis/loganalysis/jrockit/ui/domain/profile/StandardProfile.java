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
package com.trivadis.loganalysis.jrockit.ui.domain.profile;

import java.util.Arrays;
import java.util.List;

import com.trivadis.loganalysis.jrockit.ui.domain.Profile;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.IDefaultProfile;

public class StandardProfile extends Profile implements IDefaultProfile {

	private static List<IChart> standardCharts = Arrays.asList(new IChart[] { new HeapChart(), new DurationChart() });

	public StandardProfile(final String label) {
		super(label, standardCharts);
	}

}
