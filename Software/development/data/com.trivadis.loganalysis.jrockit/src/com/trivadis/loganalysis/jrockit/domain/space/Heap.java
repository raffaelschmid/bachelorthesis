package com.trivadis.loganalysis.jrockit.domain.space;

import java.util.Arrays;
import java.util.List;


public class Heap extends AbstractArea {
	private final List<Space> spaces;

	public Heap(Space... spaces) {
		this.spaces = Arrays.asList(spaces);
	}

	public List<Space> getSpaces() {
		return spaces;
	}

}
