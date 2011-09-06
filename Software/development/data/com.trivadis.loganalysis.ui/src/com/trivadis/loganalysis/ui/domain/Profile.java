package com.trivadis.loganalysis.ui.domain;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

public class Profile implements IProfile {

	private final String name;
	private final List<IChart> charts = new ArrayList<IChart>();

	public Profile(String name, IChart... c) {
		this.name = name;
		this.charts.addAll(asList(c));
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public void addChart(IChart chart) {
		this.charts.add(chart);
	}
}
