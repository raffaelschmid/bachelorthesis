package com.trivadis.loganalysis.jrockit.internal.analyzer.memory;
import static java.util.Arrays.asList;

import com.trivadis.loganalysis.jrockit.internal.analyzer.AbstractLine;
import com.trivadis.loganalysis.jrockit.internal.analyzer.TokenType;
public class HeapInfoLine extends AbstractLine {
	public enum TokenTypeHeapInfo implements TokenType {
		LOG_LEVEL, MODULE, HEAP_SIZE, MAXIMAL_HEAP_SIZE, NURSERY_SIZE;
	}

	public HeapInfoLine() {
		super(asList((TokenType[])TokenTypeHeapInfo.values()), new JRockitR28Regex().getHeapInfoLine());
	}

}
