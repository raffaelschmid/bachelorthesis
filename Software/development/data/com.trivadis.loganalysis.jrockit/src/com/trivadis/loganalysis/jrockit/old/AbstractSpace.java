package com.trivadis.loganalysis.jrockit.old;

import java.util.Arrays;
import java.util.List;

public class AbstractSpace implements HeapSpace {
	private final String name;
	private final Statistics statistics = new Statistics();
	private final List<Capacity> capacities = Arrays.asList(new Capacity[] { new Capacity(1111111),
			new Capacity(2222222), new Capacity(3333333) });

	public AbstractSpace(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Statistics getStatistics() {
		return statistics;
	}

	public Capacity getInitialCapacity() {
		return capacities.get(0);
	}

	public Capacity getFinalCapacity() {
		return capacities.get(capacities.size()-1);
	}

	public Capacity getPeakUsageCapacity() {
		return capacities.get(2);
	}

	public Capacity getAverageCapacity() {
		return capacities.get(1);
	}

	public Capacity getAverageUsageCapacity() {
		return capacities.get(2);
	}

}
