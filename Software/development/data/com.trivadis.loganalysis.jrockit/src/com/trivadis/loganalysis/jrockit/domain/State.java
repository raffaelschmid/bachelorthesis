package com.trivadis.loganalysis.jrockit.domain;

import com.trivadis.loganalysis.jrockit.domain.gc.Transition;

public class State {

	private final double timestamp;
	private Transition transitionStart;
	private Transition transitionEnd;
	private long memoryUsed, memoryCapacity;

	public State(double timestamp) {
		this.timestamp = timestamp;
	}

	public double getTimestamp() {
		return timestamp;
	}

	public State transitionStart(Transition transition) {
		transitionStart = transition;
		transition.startState(this);
		return this;
	}

	public State transitionEnd(Transition transition) {
		transitionEnd = transition;
		return this;
	}

	public State memoryUsed(long memoryUsed) {
		this.memoryUsed = memoryUsed;
		return this;
	}

	public Transition getTransitionStart() {
		return transitionStart;
	}

	public Transition getTransitionEnd() {
		return transitionEnd;
	}

	public long getMemoryUsed() {
		return memoryUsed;
	}

	public State memoryCapacity(long memoryCapacity) {
		this.memoryCapacity = memoryCapacity;
		return this;
	}

	public long getMemoryCapacity() {
		return memoryCapacity;
	}

}
