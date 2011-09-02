package com.trivadis.loganalysis.jrockit.domain.space;


public interface Space extends Area {

	double getInitialCapacity();

	double getFinalCapacity();

	double getPeakUsageCapacity();

	double getAverageCapacity();

	double getAverageUsageCapacity();

	String getName();

}
