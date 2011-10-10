package com.trivadis.loganalysis.jrockit.internal.analyzer.memory;

import java.math.BigDecimal;
import java.util.Map;

import com.trivadis.loganalysis.core.domain.unit.Size;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.domain.gc.GarbageCollection;
import com.trivadis.loganalysis.jrockit.domain.gc.OldCollection;
import com.trivadis.loganalysis.jrockit.domain.gc.YoungCollection;
import com.trivadis.loganalysis.jrockit.file.Token;
import com.trivadis.loganalysis.jrockit.internal.analyzer.ISyntacticAnalyzer;
import com.trivadis.loganalysis.jrockit.internal.analyzer.TokenType;
import com.trivadis.loganalysis.jrockit.internal.analyzer.memory.MemoryUsageLine.TokenTypeMemoryUsage;

public class MemorySyntacticAnalyzer implements ISyntacticAnalyzer<JRockitJvmRun> {
	public void process(final JRockitJvmRun jvmRun, final Map<TokenType, Token> extraction) {
		final BigDecimal duration = extraction.get(TokenTypeMemoryUsage.TOTAL_COLLECTION_TIME).toBigDecimal();
		final BigDecimal longestPause = extraction.get(TokenTypeMemoryUsage.TOTAL_SUM_PAUSE).toBigDecimal();
		final BigDecimal sumOfPauses = extraction.get(TokenTypeMemoryUsage.LONGEST_PAUSE).toBigDecimal();
		final GarbageCollection transition = ("oc".equalsIgnoreCase(extraction.get(TokenTypeMemoryUsage.TYPE1)
				.toString())) ? new OldCollection(duration, sumOfPauses, longestPause) : new YoungCollection(duration,
				sumOfPauses, longestPause);

		final State startState = new State(extraction.get(TokenTypeMemoryUsage.START_TIME).toDouble()).memoryUsed(
				new Size(extraction.get(TokenTypeMemoryUsage.MEMORY_BEFORE).toBigDecimal()))
				.transitionStart(transition);
		final State endState = new State(extraction.get(TokenTypeMemoryUsage.END_TIME).toBigDecimal())
				.memoryUsed(new Size(extraction.get(TokenTypeMemoryUsage.MEMORY_AFTER).toDouble()))
				.memoryCapacity(new Size(extraction.get(TokenTypeMemoryUsage.HEAP_SIZE_AFTER).toDouble()))
				.transitionEnd(transition);

		jvmRun.getHeap().addStates(transition, startState, endState);
	}
}
