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
package com.trivadis.loganalysis.jrockit.file;

import java.math.BigDecimal;

import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.domain.gc.GarbageCollection;
import com.trivadis.loganalysis.jrockit.domain.gc.Transition;

public enum ValueType {
	TIME {
		public BigDecimal data(State state) {
			return state.getTimestamp().getSeconds();
		}
	},
	MEMORY {
		public BigDecimal data(State state) {
			return state.getMemoryUsed().getKiloByte();
		};
	},
	DURATION {
		@Override
		public BigDecimal data(State state) {
			GarbageCollection gc = getGarbageCollection(state.getTransitionEnd());
			return gc != null ? gc.getDuration() : null;
		}

	};
	public abstract BigDecimal data(State state);

	protected GarbageCollection getGarbageCollection(Transition gc) {
		return (gc != null && gc instanceof GarbageCollection) ? ((GarbageCollection) gc) : null;
	}
}
