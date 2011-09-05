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
package com.trivadis.loganalysis.jrockit.domain.space;

import static com.trivadis.loganalysis.core.common.CollectionUtil.avg;
import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.findAll;
import static com.trivadis.loganalysis.core.common.CollectionUtil.max;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.core.domain.unit.Size;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.domain.gc.GarbageCollection;

public class AbstractArea implements Area {

	private static final BigDecimal ZERO = new BigDecimal(0);
	private final List<State> states = new ArrayList<State>();
	private State startState, endState, maximumState;
	private final JRockitJvmRun jvm;
	private final String name;

	private Predicate<BigDecimal> notNull = new Predicate<BigDecimal>() {
		public boolean matches(BigDecimal item) {
			return item != null;
		}
	};

	public AbstractArea(String name, JRockitJvmRun jvm) {
		this.jvm = jvm;
		this.name = name;
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

	public State getStartState() {
		return startState;
	}

	public void setStartState(State startState) {
		this.startState = startState;
	}

	public State getEndState() {
		return endState;
	}

	public void setEndState(State endState) {
		this.endState = endState;
	}

	public State getMaximumState() {
		return maximumState;
	}

	public void setMaximumState(State maximumState) {
		this.maximumState = maximumState;
	}

	public Size getInitialCapacity() {
		return startState != null ? startState.getMemoryCapacity() : null;
	}

	public Size getMaximumCapacity() {
		return maximumState != null ? maximumState.getMemoryCapacity() : new Size(0);
	}

	public Size getAverageCapacity() {
		return new Size(avg(findAll(collect(getStates(), new ClosureIO<State, BigDecimal>() {
			public BigDecimal call(State in) {
				return (in.getMemoryCapacity() != null) ? in.getMemoryCapacity().getKiloByte() : ZERO;
			}
		}), notNull)));
	}

	public Size getAverageUsageCapacity() {
		return new Size(avg(findAll(collect(getStates(), new ClosureIO<State, BigDecimal>() {
			public BigDecimal call(State in) {
				return in.getMemoryUsed() != null ? in.getMemoryUsed().getKiloByte() : ZERO;
			}
		}), notNull)));
	}

	public Size getPeakUsageCapacity() {
		return new Size(max(findAll(collect(getStates(), new ClosureIO<State, BigDecimal>() {
			public BigDecimal call(State in) {
				return in.getMemoryUsed() != null ? in.getMemoryUsed().getKiloByte() : ZERO;
			}
		}), notNull)));
	}

	public String getName() {
		return this.name;
	}
}
