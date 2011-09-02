package com.trivadis.loganalysis.jrockit.old;


public interface HeapSpace {

	String getName();

	Capacity getInitialCapacity();

	Capacity getFinalCapacity();

	Capacity getPeakUsageCapacity();

	Capacity getAverageCapacity();

	Capacity getAverageUsageCapacity();

	Statistics getStatistics();

}
