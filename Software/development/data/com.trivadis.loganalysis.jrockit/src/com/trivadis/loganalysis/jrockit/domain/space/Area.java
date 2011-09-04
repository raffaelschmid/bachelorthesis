package com.trivadis.loganalysis.jrockit.domain.space;

import com.trivadis.loganalysis.core.domain.unit.Size;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;


public interface Area {

	JRockitJvmRun getJvm();

	Size getMaximumCapacity();

	Size getInitialCapacity();

	Size getAverageCapacity();

	Size getAverageUsageCapacity();

	Size getPeakUsageCapacity();

	String getName();


}
