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
