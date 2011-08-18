package com.trivadis.loganalysis.jrockit.analyzer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class JRockitExtractorTest {

	private JRockitExtractor instance;

	@Before
	public void test() {
		 instance = JRockitExtractor.getDefault();
	}
	
	@Test
	public void test_checkGcInfo() {
		assertTrue(instance.checkGcInfo(LOG[0]));
	}
	
	@Test
	public void test_checkHeapInfo(){
		assertTrue(instance.checkHeapInfo(LOG[1]));
	}
	
	@Test
	public void test_extractHeapInfo(){
		List<String> heapInfo = instance.extractHeapInfo(LOG[1]);
		assertNotNull(heapInfo);
		System.out.println(heapInfo);
		assertEquals(3,heapInfo.size());
		
	}
	
	@Test
	public void test_checkPatternInfoGeneral(){
		assertTrue(instance.checkPatternInfoGeneral(LOG[2]));
	}
	
	@Test
	public void test_checkPatternInfoSpecific(){
		assertTrue(instance.checkPatternInfoSpecific(LOG[3]));
		assertTrue(instance.checkPatternInfoSpecific(LOG[4]));
		assertTrue(instance.checkPatternInfoSpecific(LOG[5]));
		assertTrue(instance.checkPatternInfoSpecific(LOG[6]));
		assertTrue(instance.checkPatternInfoSpecific(LOG[7]));
		assertTrue(instance.checkPatternInfoSpecific(LOG[8]));
		assertTrue(instance.checkPatternInfoSpecific(LOG[9]));
		assertTrue(instance.checkPatternInfoSpecific(LOG[10]));
	}
	
	@Test
	public void test_checkPatternInfoPlain(){
		assertTrue(instance.checkPatternInfoPlain(LOG[11]));
	}
	
	@Test
	public void test_checkDataLine(){
		assertTrue(instance.checkDataLine(LOG[12]));
		assertTrue(instance.checkDataLine(LOG[13]));
		assertTrue(instance.checkDataLine(LOG[14]));
		assertTrue(instance.checkDataLine(LOG[15]));
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
		/* 15 */"[INFO ][memory ] [YC#2] 1.531-1.532: YC 156652KB->156691KB (233624KB), 0.001 s, sum of pauses 0.564 ms, longest pause 0.564 ms." };

}
