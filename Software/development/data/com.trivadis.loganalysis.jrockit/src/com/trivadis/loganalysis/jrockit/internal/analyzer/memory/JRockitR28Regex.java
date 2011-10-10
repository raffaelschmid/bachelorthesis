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

import java.util.regex.Pattern;

public class JRockitR28Regex {

	private final Pattern heapInfoLine;
	private final Pattern memoryUsage;

	/**
	 * Instantiates {@link Pattern Patterns} for the log lines the
	 * {@link JRockitR28Regex} is interested in.
	 */
	public JRockitR28Regex() {
		heapInfoLine = Pattern.compile(heapInfo());
		memoryUsage = Pattern.compile(patternData());
	}

	/**
	 * Regular expression for the pattern line
	 */
	public String getGeneralInfo() {
		return prefix()
				+ "<start>-<end>: <type> <before>KB-><after>KB \\(<heap>KB\\), <time> ms, sum of pauses <pause> ms.";
	}

	/**
	 * Regular expression for the heap info line
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

	/**
	 * Pattern memory Usage
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

	private String prefix() {
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

	public Pattern getHeapInfoLine() {
		return heapInfoLine;
	}

	public Pattern getMemoryUsage() {
		return memoryUsage;
	}

}
