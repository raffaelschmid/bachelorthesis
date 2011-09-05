package com.trivadis.loganalysis.jrockit.domain;

import com.trivadis.loganalysis.core.domain.unit.Size;
import com.trivadis.loganalysis.core.domain.unit.Time;
import com.trivadis.loganalysis.jrockit.domain.gc.Transition;

public class State {

	private final Time timestamp;
	private Transition transitionStart;
	private Transition transitionEnd;
	private Size memoryUsed, memoryCapacity;

	public State(double timeInSeconds){
		this(new Time(timeInSeconds));
	}
	public State(Time timestamp) {
		this.timestamp = timestamp;
	}

	public Time getTimestamp() {
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

	public State memoryUsed(Size memoryUsed) {
		this.memoryUsed = memoryUsed;
		return this;
	}

	public Transition getTransitionStart() {
		return transitionStart;
	}

	public Transition getTransitionEnd() {
		return transitionEnd;
	}

	public Size getMemoryUsed() {
		return memoryUsed;
	}

	public State memoryCapacity(Size memoryCapacity) {
		this.memoryCapacity = memoryCapacity;
		return this;
	}

	public Size getMemoryCapacity() {
		return memoryCapacity;
	}

}
