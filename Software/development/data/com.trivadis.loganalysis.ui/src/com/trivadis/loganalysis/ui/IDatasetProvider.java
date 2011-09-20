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
package com.trivadis.loganalysis.ui;

import java.util.List;

import org.jfree.chart.plot.Marker;
import org.jfree.data.xy.XYSeries;

import com.trivadis.loganalysis.core.domain.GarbageCollectionType;
import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.ui.domain.profile.IChart;
import com.trivadis.loganalysis.ui.domain.profile.Serie;

public interface IDatasetProvider {

	XYSeries getDataset(IJvmRun ijvm, Serie o);

	List<Marker> getMarkers(IJvmRun ijvm, IChart serie, GarbageCollectionType type);

}
