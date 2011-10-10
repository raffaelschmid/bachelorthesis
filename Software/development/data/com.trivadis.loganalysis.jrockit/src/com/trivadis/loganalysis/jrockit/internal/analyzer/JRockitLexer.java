package com.trivadis.loganalysis.jrockit.internal.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import com.trivadis.loganalysis.jrockit.file.Token;

public class JRockitLexer {

	private final Line line;

	public JRockitLexer(final Line line) {
		this.line = line;
	}

	public Map<TokenType, Token> tokenize(final String logLine) {
		final List<String> list = new ArrayList<String>();
		final Matcher matcher = line.getPattern().matcher(logLine);
		final boolean matchFound = matcher.find();
		if (matchFound) {
			for (int i = 1; i <= matcher.groupCount(); i++) {
				list.add(matcher.group(i));
			}
		}
		final Map<TokenType, Token> retVal = new HashMap<TokenType, Token>();
		for (final TokenType d : line.getTokenTypes()) {
			retVal.put(d, new Token(list.get(d.ordinal())));
		}
		return retVal;
	}

	public boolean matches(final String logLine) {
		return line.getPattern().matcher(logLine).matches();
	}
}