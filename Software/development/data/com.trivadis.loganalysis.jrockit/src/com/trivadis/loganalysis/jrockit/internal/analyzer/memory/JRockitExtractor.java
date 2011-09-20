/**
 * Copyright (c) 2011 Loganalysis team and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Raffael Schmid - initial API and implementation
 */
package com.trivadis.loganalysis.jrockit.internal.analyzer.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trivadis.loganalysis.jrockit.file.Value;

public class JRockitExtractor {
	private final Pattern heapInfoLine, infoGeneralLine, infoSpecificLine, infoPlainLine, dataLine;

	public enum HeapInfoGroups {
		LOG_LEVEL, MODULE,HEAP_SIZE, MAXIMAL_HEAP_SIZE, NURSERY_SIZE;
	}

	/*
	 * CHECKERS
	 */
	public boolean checkHeapInfo(final String line) {
		return heapInfoLine.matcher(line).matches();
	}

	public boolean checkPatternInfoGeneral(final String line) {
		return infoGeneralLine.matcher(line).matches();
	}

	public boolean checkPatternInfoSpecific(final String line) {
		return infoSpecificLine.matcher(line).matches();
	}

	public boolean checkPatternInfoPlain(final String line) {
		return infoPlainLine.matcher(line).matches();
	}

	public boolean checkDataLine(final String line) {
		return dataLine.matcher(line).matches();
	}

	/*
	 * EXTRACTORS
	 */
	public Map<HeapInfoGroups, Value> extractHeapInfo(final String line) {
		return extractGroups(HeapInfoGroups.values(), line, heapInfoLine);
	}

	public Map<DataGroups, Value> extractDataLine(final String line) {
		return extractGroups(DataGroups.values(), line, dataLine);
	}

	private <T extends Enum<?>> Map<T, Value> extractGroups(final T[] enums, final String line, final Pattern p) {
		final List<String> list = new ArrayList<String>();
		final Matcher matcher = p.matcher(line);
		final boolean matchFound = matcher.find();
		if (matchFound) {
			for (int i = 1; i <= matcher.groupCount(); i++) {
				list.add(matcher.group(i));
			}
		}
		final Map<T, Value> retVal = new HashMap<T, Value>();
		for (final T d : enums) {
			retVal.put(d, new Value(list.get(d.ordinal())));
		}
		return retVal;
	}

	/**
	 * Instantiates all Patterns which are thread safe
	 */
	public JRockitExtractor() {
		heapInfoLine = Pattern.compile(heapInfo());
		infoGeneralLine = Pattern.compile(patternInfoGeneral());
		infoSpecificLine = Pattern.compile(patternInfoSpecific());
		infoPlainLine = Pattern.compile(patternInfoPlain());
		dataLine = Pattern.compile(patternData());
	}

	/*
	 * Heap Info Line
	 */
	private String heapInfo() {
		return prefix() + heapSize() + ", " + maximalHeapSize() + ", " + nurserySize() + ".";
	}

	private String maximalHeapSize() {
		return "maximal heap size:\\s" + size() + "";
	}

	private String nurserySize() {
		return "nursery size:\\s" + size() + "";
	}

	private String heapSize() {
		return "Heap size:\\s" + size() + "";
	}

	/*
	 * Pattern Line general
	 */
	public String patternInfoGeneral() {
		return prefix()
				+ "<start>-<end>: <type> <before>KB-><after>KB \\(<heap>KB\\), <time> ms, sum of pauses <pause> ms.";
	}

	/*
	 * Pattern Line Specific
	 */
	private String patternInfoSpecific() {
		return prefix() + "<.+>\\s+-\\s+[a-zA-Z0-9\\(\\)\\.\\s]+";
	}

	/*
	 * Pattern Line Plain
	 * 
	 * TODO probably varies specific to the settings
	 */
	private String patternInfoPlain() {
		return prefix() + "\\s+Run with -Xverbose:gcpause to see individual phases.";
	}

	/**
	 * Pattern data
	 */
	private String patternData() {
		return prefix() + "\\[" + type() + index() + "\\] " + time() + "-" + time() + ": " + type() + " " + size()
				+ "->" + size() + " \\(" + size() + "\\), " + time() + " s, sum of pauses " + time()
				+ " ms, longest pause " + time() + " ms.";
	}

	private String type() {
		return "([O|Y]C)";
	}

	private String index() {
		return "#(\\d+)";
	}

	/*
	 * Generic elements
	 */
	public static String prefix() {
		return "\\[" + logLevel() + "\\]\\[" + module() + "\\]\\s";
	}

	private static String logLevel() {
		return "(.+)\\s";
	}

	private static String module() {
		return "(.+)\\s";
	}

	private String size() {
		return "(\\d+)KB";
	}

	private String time() {
		return "(\\d+.\\d{3})";
	}

}
