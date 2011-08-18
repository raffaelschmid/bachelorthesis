package com.trivadis.loganalysis.jrockit.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JRockitExtractor {
	private final Pattern firstLine, heapInfoLine, patternInfoGeneralLine, patternInfoSpecificLine,
			patternInfoPlainLine, patternDataLine;

	private static class Holder {
		private static final JRockitExtractor INSTANCE = new JRockitExtractor();
	}

	public static JRockitExtractor getDefault() {
		return Holder.INSTANCE;
	}

	/*
	 * CHECKERS
	 */
	public boolean checkGcInfo(String line) {
		return firstLine.matcher(line).matches();
	}

	public boolean checkHeapInfo(String line) {
		return heapInfoLine.matcher(line).matches();
	}

	public boolean checkPatternInfoGeneral(String line) {
		return patternInfoGeneralLine.matcher(line).matches();
	}

	public boolean checkPatternInfoSpecific(String line) {
		return patternInfoSpecificLine.matcher(line).matches();
	}

	public boolean checkPatternInfoPlain(String string) {
		return patternInfoPlainLine.matcher(string).matches();
	}

	public boolean checkDataLine(String string) {
		return patternDataLine.matcher(string).matches();
	}

	/*
	 * EXTRACTORS
	 */
	public List<String> extractHeapInfo(String string) {
		return extractGroups(string, heapInfoLine);
	}

	private List<String> extractGroups(String string, Pattern p) {
		List<String> retVal = new ArrayList<String>();
		Matcher matcher = p.matcher(string);
		boolean matchFound = matcher.find();
		if (matchFound) {
		    for (int i=1; i<=matcher.groupCount(); i++) {
		        retVal.add(matcher.group(i));
		    }
		}
		return retVal;
	}

	/**
	 * Instantiates all Patterns which are thread safe
	 */
	private JRockitExtractor() {
		firstLine = Pattern.compile(infoLine());
		heapInfoLine = Pattern.compile(heapInfo());
		patternInfoGeneralLine = Pattern.compile(patternInfoGeneral());
		patternInfoSpecificLine = Pattern.compile(patternInfoSpecific());
		patternInfoPlainLine = Pattern.compile(patternInfoPlain());
		patternDataLine = Pattern.compile(patternData());
	}

	/*
	 * Info line
	 */
	private String infoLine() {
		return prefix() + ".*";
	}

	/*
	 * Heap Info Line
	 */
	private String heapInfo() {
		return prefix() + heapSize() + ", " + maximalHeapSize() + ", " + nurserySize() + ".";
	}

	private String maximalHeapSize() {
		return "maximal heap size:\\s(" + size() + ")";
	}

	private String nurserySize() {
		return "nursery size:\\s(" + size() + ")";
	}

	private String heapSize() {
		return "Heap size:\\s(" + size() + ")";
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
		return prefix() + "\\[" +
				type() + index() + 
				"\\] " + time() + "-" + time() + ": " + type() + " " + size() + "->" + size() + " \\("
				+ size() + "\\), " + time() + " s, sum of pauses " + time() + " ms, longest pause " + time() + " ms.";
	}

	private String type() {
		return "[O|Y]C";
	}
	
	private String index(){
		return "#\\d";
	}

	/*
	 * Generic elements
	 */
	private String prefix() {
		return "\\[" + logLevel() + "\\]\\[" + module() + "\\]\\s";
	}

	private String logLevel() {
		return ".+ ";
	}

	private String module() {
		return ".+ ";
	}

	private String size() {
		return "\\d+KB";
	}

	private String time() {
		return "\\d+.\\d{3}";
	}

}
