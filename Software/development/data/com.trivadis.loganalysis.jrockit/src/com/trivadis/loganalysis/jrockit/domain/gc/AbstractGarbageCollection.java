package com.trivadis.loganalysis.jrockit.domain.gc;

import java.util.ArrayList;
import java.util.List;

import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.domain.gc.phase.Phase;

public class AbstractGarbageCollection implements GarbageCollection {
	private final List<Phase> phases = new ArrayList<Phase>();
	private final String name;
	private final double duration;
	private State startState;
	private State endState;

	public AbstractGarbageCollection(String name, double duration) {
		this.name = name;
		this.duration = duration;
	}

	public List<Phase> getPhases() {
		return phases;
	}

	public String getName() {
		return name;
	}

	public double getDuration() {
		return duration;
	}

	public AbstractGarbageCollection startState(State state) {
		this.startState = state;
		return this;
	}

	public AbstractGarbageCollection endState(State state) {
		this.endState = state;
		return this;
	}

	public State getStartState() {
		return startState;
	}

	public State getEndState() {
		return endState;
	}

}
