package com.trivadis.loganalysis.jrockit.old;

public class Statistics {

	private double lastOccurence;
	private int count;
	private double averageInterval;
	private double averageDuration;
	private double averageRateOfCollection;

	public Statistics() {
	}

	public double getLastOccurence() {
		return lastOccurence;
	}

	public int getCount() {
		return count;
	}

	public double getAverageInterval() {
		return averageInterval;
	}

	public double getAverageDuration() {
		return averageDuration;
	}

	public double getAverageRateOfCollection() {
		return averageRateOfCollection;
	}

	public void setLastOccurence(double lastOccurence) {
		this.lastOccurence = lastOccurence;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setAverageInterval(double averageInterval) {
		this.averageInterval = averageInterval;
	}

	public void setAverageDuration(double averageDuration) {
		this.averageDuration = averageDuration;
	}

	public void setAverageRateOfCollection(double averageRateOfCollection) {
		this.averageRateOfCollection = averageRateOfCollection;
	}

}
