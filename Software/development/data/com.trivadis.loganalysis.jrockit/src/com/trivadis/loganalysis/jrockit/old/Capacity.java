package com.trivadis.loganalysis.jrockit.old;

import java.util.Date;

public class Capacity {
	private final Date timestamp;
	private final double value;

	
	public Capacity(double value) {
		this.value = value;
		this.timestamp=null;
	}

	public Capacity(Date timestamp, double value) {
		this.timestamp = timestamp;
		this.value = value;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public double getValue() {
		return value;
	}
}
