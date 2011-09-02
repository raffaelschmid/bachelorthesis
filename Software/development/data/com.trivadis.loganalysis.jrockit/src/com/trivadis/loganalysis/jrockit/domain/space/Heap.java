package com.trivadis.loganalysis.jrockit.domain.space;

import java.util.Arrays;
import java.util.List;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;


public class Heap extends AbstractArea {
	private final List<Space> spaces;

	public Heap(JRockitJvmRun jvm, Space... spaces) {
		super(jvm);
		this.spaces = Arrays.asList(spaces);
	}


	public List<Space> getSpaces() {
		return spaces;
	}
	
}
