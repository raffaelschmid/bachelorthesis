package com.trivadis.loganalysis.jrockit.domain.space;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;


public interface Area {

	JRockitJvmRun getJvm();

	double getMaximumCapacity();

	double getInitialCapacity();

	double getAverageCapacity();

	double getAverageUsageCapacity();

	double getPeakUsageCapacity();

	String getName();


}
