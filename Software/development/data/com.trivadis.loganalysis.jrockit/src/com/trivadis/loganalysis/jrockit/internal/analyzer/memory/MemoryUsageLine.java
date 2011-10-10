package com.trivadis.loganalysis.jrockit.internal.analyzer.memory;

import static java.util.Arrays.asList;

import com.trivadis.loganalysis.jrockit.internal.analyzer.AbstractLine;
import com.trivadis.loganalysis.jrockit.internal.analyzer.TokenType;
public class MemoryUsageLine extends AbstractLine {

	enum TokenTypeMemoryUsage implements TokenType{
		LOG_LEVEL, MODULE, TYPE1, INDEX, START_TIME, END_TIME, TYPE2, MEMORY_BEFORE,MEMORY_AFTER, HEAP_SIZE_AFTER, TOTAL_COLLECTION_TIME, TOTAL_SUM_PAUSE, LONGEST_PAUSE;
	}
	
	public MemoryUsageLine() {
		super(asList((TokenType[])TokenTypeMemoryUsage.values()),new JRockitR28Regex().getMemoryUsage());
	}
}
