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
package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

import static com.trivadis.loganalysis.core.common.CollectionUtil.avg;
import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.intervals;

import java.math.BigDecimal;
import java.util.List;

import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.core.domain.unit.Time;
import com.trivadis.loganalysis.jrockit.domain.gc.GarbageCollection;

public class GcActivityAggregation {

	private static final String UNDEFINED = "-";

	private final List<GarbageCollection> collections;

	public GcActivityAggregation(List<GarbageCollection> collections) {
		this.collections = collections;
	}

	public String getName() {
		return hasCollections() ? collections.get(0).getName() : UNDEFINED;
	}

	public Time getLastOccurence() {
		return hasCollections() ? (collections.get(collections.size() - 1).getStartState().getTimestamp()) : null;
	}

	public BigDecimal getCount() {
		return new BigDecimal(collections.size());
	}

	public Time getAverageDuration() {
		return hasCollections() ? new Time(average(collections)) : null;
	}

	private BigDecimal average(List<GarbageCollection> allOld) {
		BigDecimal oldDurationAvg = avg(collect(allOld, new ClosureIO<GarbageCollection, BigDecimal>() {
			public BigDecimal call(GarbageCollection in) {
				return in.getDuration();
			}
		}));
		return oldDurationAvg;
	}

	private boolean hasCollections() {
		return collections.size() > 0;
	}

	public Time getAverageInterval() {
		return new Time(avg(intervals(collect(collections, new ClosureIO<GarbageCollection, BigDecimal>() {
			public BigDecimal call(GarbageCollection in) {
				return in.getStartState().getTimestamp().getSeconds();
			}
		}))));
	}
}
