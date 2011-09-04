package com.trivadis.loganalysis.core.domain.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.trivadis.loganalysis.core.domain.unit.Size.SizeType;

public class SizeTest {

	@Test
	public void testGetByte() {
		assertEquals(133, new Size(133, SizeType.BYTE).getByte(), 0.0);
	}

	@Test
	public void testGetKiloByte() {
		assertEquals(133, new Size(133, SizeType.KILOBYTE).getKiloByte(), 0.0);
	}

	@Test
	public void testGetMegaByte() {
		assertEquals(133, new Size(133, SizeType.MEGABYTE).getMegaByte(), 0.0);
	}

	@Test
	public void testGetGigaByte() {
		assertEquals(133, new Size(133, SizeType.GIGABYTE).getGigaByte(), 0.0);
	}

}
