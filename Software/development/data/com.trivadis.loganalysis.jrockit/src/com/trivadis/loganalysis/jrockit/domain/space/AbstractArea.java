package com.trivadis.loganalysis.jrockit.domain.space;

import java.util.ArrayList;
import java.util.List;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.domain.gc.GarbageCollection;

public class AbstractArea implements Area {

	private final List<State> states = new ArrayList<State>();
	private final JRockitJvmRun jvm;
	
	public AbstractArea(JRockitJvmRun jvm) {
		this.jvm = jvm;
	}

	public void addStates(GarbageCollection transition, State from, State to) {
		from.transitionStart(transition);
		to.transitionEnd(transition);
		states.add(from);
		states.add(to);
		jvm.addTransition(transition);
	}

	public List<State> getStates() {
		return states;
	}

	public JRockitJvmRun getJvm() {
		return jvm;
	}
}
