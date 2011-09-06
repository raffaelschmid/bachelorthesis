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
package com.trivadis.loganalysis.jrockit.ui.internal.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.trivadis.loganalysis.core.domain.unit.Size;

public class FormatUtil {
	private static final BigDecimal HUNDRED = new BigDecimal(100);
	private static final String UNDEFINED = "-";

	public static String seconds(BigDecimal bd) {
		return bd.setScale(3, RoundingMode.HALF_UP).toString() + " sec";
	}

	public static String percentage(BigDecimal bd) {
		return bd.multiply(HUNDRED).setScale(1, RoundingMode.HALF_UP).toString();
	}

	public static String size(Size d) {
		return (d != null && d.getByte() != null && d.getByte().doubleValue() != 0.0) ? d.getKiloByte().setScale(1,
				RoundingMode.HALF_UP)
				+ " KB" : UNDEFINED;
	}
}
