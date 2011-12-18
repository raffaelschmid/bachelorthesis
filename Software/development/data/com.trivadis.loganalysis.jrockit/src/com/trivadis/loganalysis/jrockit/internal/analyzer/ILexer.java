package com.trivadis.loganalysis.jrockit.internal.analyzer;

import java.util.Map;

import com.trivadis.loganalysis.jrockit.file.Token;

public interface ILexer {

	Map<TokenType, Token> tokenize(String logLine);

	boolean matches(String logLine);

}