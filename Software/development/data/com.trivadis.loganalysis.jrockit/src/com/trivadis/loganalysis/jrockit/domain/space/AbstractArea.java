package com.trivadis.loganalysis.jrockit.domain.space;

import static com.trivadis.loganalysis.core.common.CollectionUtil.avg;
import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.findAll;
import static com.trivadis.loganalysis.core.common.CollectionUtil.max;

import java.util.ArrayList;
import java.util.List;

import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.domain.gc.GarbageCollection;

public class AbstractArea implements Area {

	private final List<State> states = new ArrayList<State>();
	private State startState, endState, maximumState;
	private final JRockitJvmRun jvm;
	private final String name;

	private Predicate<Long> notNull = new Predicate<Long>() {
		public boolean matches(Long item) {
			return item != null && 0 != item;
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

	public double getInitialCapacity() {
		return startState != null ? startState.getMemoryCapacity() : Double.NaN;
	}

	public double getMaximumCapacity() {
		return maximumState != null ? maximumState.getMemoryCapacity() : Double.NaN;
	}

	public double getAverageCapacity() {
		return avg(findAll(collect(getStates(), new ClosureIO<State, Long>() {
			public Long call(State in) {
				long ret = in.getMemoryCapacity();
				return ret;
			}
		}), notNull));
	}

	public double getAverageUsageCapacity() {
		return avg(findAll(collect(getStates(), new ClosureIO<State, Long>() {
			public Long call(State in) {
				return in.getMemoryUsed();
			}
		}), notNull));
	}

	public double getPeakUsageCapacity() {
		return max(findAll(collect(getStates(), new ClosureIO<State, Long>() {
			public Long call(State in) {
				return in.getMemoryUsed();
			}
		}), notNull));
	}

	public String getName() {
		return this.name;
	}
}
