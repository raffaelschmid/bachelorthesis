/**
 * Copyright (c) 2011 Loganalysis team and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Raffael Schmid - initial API and implementation
 */
package com.trivadis.loganalysis.jrockit.domain;

import java.math.BigDecimal;

import com.trivadis.loganalysis.core.domain.unit.Size;
import com.trivadis.loganalysis.core.domain.unit.Time;
import com.trivadis.loganalysis.jrockit.domain.gc.Transition;

public class State {

	private final Time timestamp;
	private Transition transitionStart;
	private Transition transitionEnd;
	private Size memoryUsed, memoryCapacity;

	public State(BigDecimal timeInSeconds){
		this(new Time(timeInSeconds));
	}
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
