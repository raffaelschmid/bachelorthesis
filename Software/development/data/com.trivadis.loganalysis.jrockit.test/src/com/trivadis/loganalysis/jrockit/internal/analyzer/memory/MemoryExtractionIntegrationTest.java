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
package com.trivadis.loganalysis.jrockit.internal.analyzer.memory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import com.trivadis.loganalysis.jrockit.file.Token;
import com.trivadis.loganalysis.jrockit.internal.analyzer.ILexer;
import com.trivadis.loganalysis.jrockit.internal.analyzer.JRockitLexer;
import com.trivadis.loganalysis.jrockit.internal.analyzer.TokenType;
import com.trivadis.loganalysis.jrockit.internal.analyzer.memory.HeapInfoLine.TokenTypeHeapInfo;
import com.trivadis.loganalysis.jrockit.internal.analyzer.memory.MemoryUsageLine.TokenTypeMemoryUsage;
//TODO fix tests
public class MemoryExtractionIntegrationTest {

	private final ILexer heapInfo = new JRockitLexer(new HeapInfoLine());
	private final ILexer instance = new JRockitLexer(new MemoryUsageLine());
	

	@Test
	public void test_matches() {
		assertTrue(heapInfo.matches(LOG[1]));
		assertFalse(heapInfo.matches(LOG[0]));
	}

	@Test
	public void test_extractHeapInfo() {
		final Map<TokenType, Token> data = heapInfo.tokenize(LOG[1]);
		assertNotNull(heapInfo);
		assertEquals(5, data.size());

		assertEquals("INFO", data.get(TokenTypeHeapInfo.LOG_LEVEL).toString());
		assertEquals("memory", data.get(TokenTypeHeapInfo.MODULE).toString());
		assertEquals("65536", data.get(TokenTypeHeapInfo.HEAP_SIZE).toString());
		assertEquals("1048576", data.get(TokenTypeHeapInfo.MAXIMAL_HEAP_SIZE).toString());
		assertEquals("32768", data.get(TokenTypeHeapInfo.NURSERY_SIZE).toString());
	}

	@Test
	public void test_checkDataLine() {
		for (int line = 0; line < 12; line++) {
			assertFalse(instance.matches(LOG[line]));
		}
		assertTrue(instance.matches(LOG[12]));
		assertTrue(instance.matches(LOG[13]));
		assertTrue(instance.matches(LOG[14]));
		assertTrue(instance.matches(LOG[15]));
		assertTrue(instance.matches(LOG[16]));

	}

	@Test
	public void test_extractDataLine() throws Exception {
		final Map<TokenType, Token> data = instance.tokenize(LOG[15]);
		assertEquals("INFO", data.get(TokenTypeMemoryUsage.LOG_LEVEL).toString());
		assertEquals("memory", data.get(TokenTypeMemoryUsage.MODULE).toString());
		assertEquals("YC", data.get(TokenTypeMemoryUsage.TYPE1).toString());
		assertEquals("1.531", data.get(TokenTypeMemoryUsage.START_TIME).toString());
		assertEquals("1.532", data.get(TokenTypeMemoryUsage.END_TIME).toString());
		assertEquals("YC", data.get(TokenTypeMemoryUsage.TYPE2).toString());
		assertEquals("156652", data.get(TokenTypeMemoryUsage.MEMORY_BEFORE).toString());
		assertEquals("156691", data.get(TokenTypeMemoryUsage.MEMORY_AFTER).toString());
		assertEquals("233624", data.get(TokenTypeMemoryUsage.HEAP_SIZE_AFTER).toString());
		assertEquals("0.001", data.get(TokenTypeMemoryUsage.TOTAL_COLLECTION_TIME).toString());
		assertEquals("0.564", data.get(TokenTypeMemoryUsage.TOTAL_SUM_PAUSE).toString());
		assertEquals("0.564", data.get(TokenTypeMemoryUsage.LONGEST_PAUSE).toString());
	}

	//@formatter:off
	private static final String[] LOG = new String[] {
		/* 00 */"[INFO ][memory ] GC mode: Garbage collection optimized for throughput, strategy: Generational Parallel Mark & Sweep.",
		/* 01 */"[INFO ][memory ] Heap size: 65536KB, maximal heap size: 1048576KB, nursery size: 32768KB.",
		/* 02 */"[INFO ][memory ] <start>-<end>: <type> <before>KB-><after>KB (<heap>KB), <time> ms, sum of pauses <pause> ms.",
		/* 03 */"[INFO ][memory ] <start>  - start time of collection (seconds since jvm start).",
		/* 04 */"[INFO ][memory ] <type>   - OC (old collection) or YC (young collection).",
		/* 05 */"[INFO ][memory ] <end>    - end time of collection (seconds since jvm start).",
		/* 06 */"[INFO ][memory ] <before> - memory used by objects before collection (KB).",
		/* 07 */"[INFO ][memory ] <after>  - memory used by objects after collection (KB).",
		/* 08 */"[INFO ][memory ] <heap>   - size of heap after collection (KB).",
		/* 09 */"[INFO ][memory ] <time>   - total time of collection (milliseconds).",
		/* 10 */"[INFO ][memory ] <pause>  - total sum of pauses during collection (milliseconds).",
		/* 11 */"[INFO ][memory ]            Run with -Xverbose:gcpause to see individual phases.",
		/* 12 */"[INFO ][memory ] [OC#1] 0.830-0.833: OC 428KB->78423KB (117108KB), 0.003 s, sum of pauses 1.753 ms, longest pause 1.753 ms.",
		/* 13 */"[INFO ][memory ] [OC#2] 0.892-0.982: OC 78450KB->156488KB (233624KB), 0.090 s, sum of pauses 88.419 ms, longest pause 88.419 ms.",
		/* 14 */"[INFO ][memory ] [YC#1] 1.530-1.531: YC 156524KB->156628KB (233624KB), 0.001 s, sum of pauses 1.275 ms, longest pause 1.275 ms.",
		/* 15 */"[INFO ][memory ] [YC#2] 1.531-1.532: YC 156652KB->156691KB (233624KB), 0.001 s, sum of pauses 0.564 ms, longest pause 0.564 ms.",
		/* 16 */"[INFO ][memory ] [OC#19] 47.364-48.205: OC 1048576KB->428984KB (1048576KB), 0.841 s, sum of pauses 831.651 ms, longest pause 831.651 ms."};

}
