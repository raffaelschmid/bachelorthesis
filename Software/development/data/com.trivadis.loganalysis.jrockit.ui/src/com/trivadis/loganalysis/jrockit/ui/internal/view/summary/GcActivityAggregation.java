package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

import static com.trivadis.loganalysis.core.common.CollectionUtil.avg;
import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;

import java.util.List;

import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.jrockit.domain.gc.GarbageCollection;

public class GcActivityAggregation {

	private final List<GarbageCollection> collections;

	public GcActivityAggregation(List<GarbageCollection> collections) {
		this.collections = collections;
	}

	public String getName() {
		return collections.get(0).getName();
	}

	public String getLastOccurence() {
		return String.valueOf(collections.get(collections.size() - 1).getStartState()
				.getTimestamp());
	}

	public String getCount() {
		return String.valueOf(collections.size());
	}

	public String getAverageDuration() {
		return String.valueOf(average(collections));
	}

	private double average(List<GarbageCollection> allOld) {
		double oldDurationAvg = avg(collect(allOld, new ClosureIO<GarbageCollection, Double>() {
			public Double call(GarbageCollection in) {
				return in.getDuration();
			}
		}));
		return oldDurationAvg;
	}
}
