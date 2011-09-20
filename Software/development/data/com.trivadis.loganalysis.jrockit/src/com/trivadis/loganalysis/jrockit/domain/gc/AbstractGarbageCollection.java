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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.trivadis.loganalysis.core.domain.GarbageCollectionType;
import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.domain.gc.phase.Phase;

public class AbstractGarbageCollection implements GarbageCollection {
	private final List<Phase> phases = new ArrayList<Phase>();
	private final String name;
	private final BigDecimal duration;
	private State startState;
	private State endState;
	private final BigDecimal longestPause;
	private final BigDecimal sumOfPauses;
	private final GarbageCollectionType type;

	public AbstractGarbageCollection(final String name, final GarbageCollectionType type, final BigDecimal duration, final BigDecimal sumOfPauses, final BigDecimal longestPause) {
		this.name = name;
		this.duration = duration;
		this.sumOfPauses = sumOfPauses;
		this.longestPause = longestPause;
		this.type = type;
	}

	public List<Phase> getPhases() {
		return phases;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getDuration() {
		return duration;
	}

	public AbstractGarbageCollection startState(final State state) {
		this.startState = state;
		return this;
	}

	public AbstractGarbageCollection endState(final State state) {
		this.endState = state;
		return this;
	}

	public State getStartState() {
		return startState;
	}

	public State getEndState() {
		return endState;
	}

	public BigDecimal getLongestPause() {
		return longestPause;
	}

	public BigDecimal getSumOfPauses() {
		return sumOfPauses;
	}

	public GarbageCollectionType getType() {
		return type;
	}

}
