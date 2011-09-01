package com.trivadis.loganalysis.jrockit.domain;

public class AbstractSpace implements Space {
	private final String name;

	public AbstractSpace(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getInitialCapacity() {
		return 1000;
	}

	public int getFinalCapacity() {
		return 2000;
	}

	public int getPeakUsageCapacity() {
		return 3000;
	}

	public int getAverageCapacity() {
		return 4000;
	}

	public int getAverageUsageCapacity() {
		return 5000;
	}
}
