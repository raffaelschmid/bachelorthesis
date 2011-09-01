package com.trivadis.loganalysis.jrockit.domain;


public interface Space {

	String getName();

	int getInitialCapacity();

	int getFinalCapacity();

	int getPeakUsageCapacity();

	int getAverageCapacity();

	int getAverageUsageCapacity();

}
