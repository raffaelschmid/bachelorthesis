package com.trivadis.loganalysis.jrockit.internal.analyzer;

import java.util.Map;

import com.trivadis.loganalysis.jrockit.file.Token;


public interface ISyntacticAnalyzer<A> {

	void process(A jvmRun, Map<TokenType, Token> extraction);

}
