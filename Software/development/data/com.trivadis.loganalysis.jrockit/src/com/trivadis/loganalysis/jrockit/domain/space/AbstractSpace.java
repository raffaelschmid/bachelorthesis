package com.trivadis.loganalysis.jrockit.domain.space;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;

public abstract class AbstractSpace implements Space {

	private final JRockitJvmRun jvm;

	public AbstractSpace(JRockitJvmRun jvm) {
		this.jvm = jvm;
	}

	public JRockitJvmRun getJvm() {
		return this.jvm;
	}

	public double getInitialCapacity() {
		return Double.NaN;
	}

	public double getFinalCapacity() {
		return Double.NaN;
	}

	public double getPeakUsageCapacity() {
		return Double.NaN;
	}

	public double getAverageCapacity() {
		return Double.NaN;
	}

	public double getAverageUsageCapacity() {
		return Double.NaN;
	}
}
