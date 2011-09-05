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
package com.trivadis.loganalysis.core.domain.unit;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.trivadis.loganalysis.core.domain.unit.Size.SizeType;

public class SizeTest {

	@Test
	public void testGetByte() {
		assertEquals(new BigDecimal(133), new Size(133, SizeType.BYTE).getByte());
	}

	@Test
	public void testGetKiloByte() {
		assertEquals(new BigDecimal(133), new Size(133, SizeType.KILOBYTE).getKiloByte());
	}

	@Test
	public void testGetMegaByte() {
		assertEquals(new BigDecimal(133), new Size(133, SizeType.MEGABYTE).getMegaByte());
	}

	@Test
	public void testGetGigaByte() {
		assertEquals(new BigDecimal(133), new Size(133, SizeType.GIGABYTE).getGigaByte());
	}

}
