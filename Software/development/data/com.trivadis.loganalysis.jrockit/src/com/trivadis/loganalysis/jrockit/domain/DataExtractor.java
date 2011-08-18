package com.trivadis.loganalysis.jrockit.domain;

public enum DataExtractor {
	//@formatter:off
	//[INFO ][memory ] [OC#1] 0.830-0.833: OC 428KB->78423KB (117108KB), 0.003 s, sum of pauses 1.753 ms, longest pause 1.753 ms.
	//@formatter:on
	LOG_LEVEL, MODULE, TYPE1, INDEX, START_TIME, END_TIME, TYPE2, MEMORY_AFTER, MEMORY_BEFORE, HEAP_SIZE_AFTER, TOTAL_COLLECTION_TIME, TOTAL_SUM_PAUSE, LONGEST_PAUSE;
}