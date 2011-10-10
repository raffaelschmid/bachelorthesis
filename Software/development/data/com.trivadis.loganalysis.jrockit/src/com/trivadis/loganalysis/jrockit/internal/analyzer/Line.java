package com.trivadis.loganalysis.jrockit.internal.analyzer;

import java.util.List;
import java.util.regex.Pattern;


public interface Line {

	List<TokenType> getTokenTypes();

	Pattern getPattern();

}
