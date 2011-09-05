package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

import static com.trivadis.loganalysis.core.common.CollectionUtil.avg;
import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;

import java.util.List;

import com.trivadis.loganalysis.core.common.ClosureIO;
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

	public String getLastOccurence() {
		return String.valueOf(hasCollections() ? (collections.get(collections.size() - 1)
				.getStartState().getTimestamp()) : UNDEFINED);
	}

	public String getCount() {
		return String.valueOf(collections.size());
	}

	public String getAverageDuration() {
		return hasCollections() ? String.valueOf(average(collections)) : UNDEFINED;
	}

	private double average(List<GarbageCollection> allOld) {
		double oldDurationAvg = avg(collect(allOld, new ClosureIO<GarbageCollection, Double>() {
			public Double call(GarbageCollection in) {
				return in.getDuration();
			}
		}));
		return oldDurationAvg;
	}

	private boolean hasCollections() {
		return collections.size() > 0;
	}

	public String getAverageInterval() {
		return UNDEFINED;
	}
}
