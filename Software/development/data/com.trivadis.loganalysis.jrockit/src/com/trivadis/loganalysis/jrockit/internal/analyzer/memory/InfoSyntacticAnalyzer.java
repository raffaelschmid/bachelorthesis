package com.trivadis.loganalysis.jrockit.internal.analyzer.memory;

import java.util.Map;

import com.trivadis.loganalysis.core.domain.unit.Size;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.State;
import com.trivadis.loganalysis.jrockit.file.Token;
import com.trivadis.loganalysis.jrockit.internal.analyzer.ISyntacticAnalyzer;
import com.trivadis.loganalysis.jrockit.internal.analyzer.TokenType;
import com.trivadis.loganalysis.jrockit.internal.analyzer.memory.HeapInfoLine.TokenTypeHeapInfo;

public class InfoSyntacticAnalyzer implements ISyntacticAnalyzer<JRockitJvmRun> {
	public void process(final JRockitJvmRun jvmRun, final Map<TokenType, Token> heapInfo) {
		final long initHeapSize = heapInfo.get(TokenTypeHeapInfo.HEAP_SIZE).toLong();
		final long maxHeapSize = heapInfo.get(TokenTypeHeapInfo.MAXIMAL_HEAP_SIZE).toLong();
		final long initNurserySize = heapInfo.get(TokenTypeHeapInfo.NURSERY_SIZE).toLong();
		final long iniTenuredSize = initHeapSize - initNurserySize;

		jvmRun.getHeap().setStartState(new State(0d).memoryCapacity(new Size(initHeapSize)));
		jvmRun.getHeap().setMaximumState(new State(0d).memoryCapacity(new Size(maxHeapSize)));
		jvmRun.getHeap().getNursery().setStartState(new State(0d).memoryCapacity(new Size(initNurserySize)));
		jvmRun.getHeap().getTenured().setStartState(new State(0d).memoryCapacity(new Size(iniTenuredSize)));
	}
}
