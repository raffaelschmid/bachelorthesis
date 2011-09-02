package com.trivadis.loganalysis.jrockit.domain.gc;

import java.util.ArrayList;
import java.util.List;

import com.trivadis.loganalysis.jrockit.domain.Transition;
import com.trivadis.loganalysis.jrockit.domain.gc.phase.Phase;

public class AbstractGarbageCollection implements Transition {
	private final List<Phase> phases = new ArrayList<Phase>();

	public List<Phase> getPhases() {
		return phases;
	}

}
