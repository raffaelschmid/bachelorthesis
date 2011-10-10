package com.trivadis.loganalysis.jrockit.internal.analyzer;

import java.util.List;
import java.util.regex.Pattern;


public class AbstractLine implements Line {

	private final List<TokenType> tokenTypes;
	private final Pattern pattern;

	public AbstractLine(final List<TokenType> tokenTypes, final Pattern pattern) {
		this.tokenTypes = tokenTypes;
		this.pattern = pattern;
	}

	public List<TokenType> getTokenTypes() {
		return tokenTypes;
	}

	public Pattern getPattern() {
		return pattern;
	}
}
