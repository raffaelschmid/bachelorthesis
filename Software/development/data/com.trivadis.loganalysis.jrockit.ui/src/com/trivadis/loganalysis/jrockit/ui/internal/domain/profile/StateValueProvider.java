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
package com.trivadis.loganalysis.jrockit.ui.internal.domain.profile;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;

import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.domain.gc.GarbageCollection;
import com.trivadis.loganalysis.jrockit.domain.gc.Transition;
import com.trivadis.loganalysis.ui.domain.profile.IValueProvider;

public enum StateValueProvider implements IValueProvider {
	TIME("Time", "Seconds") {
		public BigDecimal data(final Object o) {
			final State state = (State) o;
			return state.getTimestamp().getSeconds();
		}
	},
	MEMORY("Memory used", "KB") {
		public BigDecimal data(final Object o) {
			final State state = (State) o;
			return state.getMemoryUsed().getKiloByte();
		};
	},
	DURATION("Garbage Collection Time", "Seconds") {
		public BigDecimal data(final Object o) {
			final State state = (State) o;
			final GarbageCollection gc = getGarbageCollection(state.getTransitionEnd());
			return gc != null ? gc.getDuration() : null;
		}
	},
	GC_TOTAL_SUM_OF_PAUSES("Sum of GC Pauses", "Seconds") {
		public BigDecimal data(final Object o) {
			BigDecimal retVal = null;
			final State state = (State) o;
			final Transition transition = state.getTransitionEnd();
			if (transition != null && transition instanceof GarbageCollection) {
				final GarbageCollection gc = (GarbageCollection) transition;
				retVal = gc.getSumOfPauses();
			}
			return retVal;
		}
	},
	GC_LONGEST_PAUSE("Longest Pause", "Seconds") {
		public BigDecimal data(final Object o) {
			BigDecimal retVal = null;
			final State state = (State) o;
			final Transition transition = state.getTransitionEnd();
			if (transition != null && transition instanceof GarbageCollection) {
				final GarbageCollection gc = (GarbageCollection) transition;
				retVal = gc.getLongestPause();
			}
			return retVal;
		}
	};

	private final String unit, label;

	private StateValueProvider(final String label, final String unit) {
		this.label = label;
		this.unit = unit;
	}

	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	protected GarbageCollection getGarbageCollection(final Transition gc) {
		return (gc != null && gc instanceof GarbageCollection) ? ((GarbageCollection) gc) : null;
	}

	public String getUnit() {
		return unit;
	}

	public String getLabel() {
		return label;
	}

}
