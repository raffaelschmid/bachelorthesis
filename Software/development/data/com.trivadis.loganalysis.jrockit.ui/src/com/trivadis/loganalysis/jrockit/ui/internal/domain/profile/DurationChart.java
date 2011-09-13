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

import java.awt.Color;

import com.trivadis.loganalysis.jrockit.ui.internal.Messages;
import com.trivadis.loganalysis.ui.domain.profile.Axis;
import com.trivadis.loganalysis.ui.domain.profile.Chart;

public class DurationChart extends Chart {

	public DurationChart() {
		super("Duration", Messages.JRockitAnalysisEditorPageDuration_2, new Axis(X, "Time (Seconds)", Color.red, ValueProvider.TIME), new Axis(Y,
				"Garbage Collection Duration (Seconds)", Color.blue, ValueProvider.DURATION));
	}

}
