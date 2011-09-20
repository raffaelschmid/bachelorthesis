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

import java.math.BigDecimal;

import com.trivadis.loganalysis.ui.domain.profile.IValueProvider;

public enum TransitionValueProvider implements IValueProvider {
	TODO("", "") {
		public BigDecimal data(final Object o) {
			return null;
		}
	};

	private final String unit, label;

	private TransitionValueProvider(final String label, final String unit) {
		this.label = label;
		this.unit = unit;
	}

	public String getUnit() {
		return unit;
	}

	public String getLabel() {
		return label;
	}
}
