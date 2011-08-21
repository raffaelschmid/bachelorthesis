package com.trivadis.loganalysis.jrockit.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trivadis.loganalysis.jrockit.domain.DataLine;
import com.trivadis.loganalysis.jrockit.domain.HeapInfoExtractor;
import com.trivadis.loganalysis.jrockit.domain.MetaInfo;
import com.trivadis.loganalysis.jrockit.domain.Value;

public class JRockitExtractor {
	private final Pattern firstLine, heapInfoLine, infoGeneralLine, infoSpecificLine, infoPlainLine, dataLine;

	private enum Groups {
		LOG_LEVEL, MODULE, TYPE1, INDEX, START_TIME, END_TIME, TYPE2, MEMORY_AFTER, MEMORY_BEFORE, HEAP_SIZE_AFTER, TOTAL_COLLECTION_TIME, TOTAL_SUM_PAUSE, LONGEST_PAUSE;
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
		return infoGeneralLine.matcher(line).matches();
	}

	public boolean checkPatternInfoSpecific(String line) {
		return infoSpecificLine.matcher(line).matches();
	}

	public boolean checkPatternInfoPlain(String line) {
		return infoPlainLine.matcher(line).matches();
	}

	public boolean checkDataLine(String line) {
		return dataLine.matcher(line).matches();
	}

	/*
	 * EXTRACTORS
	 */
	public Map<HeapInfoExtractor, Value> extractHeapInfo(String line) {
		return extractGroups(HeapInfoExtractor.values(), line, heapInfoLine);
	}

	public List<DataLine> extractDataLine(String line) {
		Map<Groups, Value> extraction = extractGroups(Groups.values(), line, dataLine);

		DataLine before = new DataLine();
		before.put(MetaInfo.LOG_LEVEL, extraction.get(Groups.LOG_LEVEL));
		before.put(MetaInfo.MODULE, extraction.get(Groups.MODULE));
		before.put(MetaInfo.TYPE, extraction.get(Groups.TYPE1));
		before.put(MetaInfo.INDEX, extraction.get(Groups.INDEX));
		before.put(MetaInfo.TIME, extraction.get(Groups.START_TIME));
		before.put(MetaInfo.MEMORY, extraction.get(Groups.MEMORY_BEFORE));

		DataLine after = new DataLine();
		after.put(MetaInfo.LOG_LEVEL, extraction.get(Groups.LOG_LEVEL));
		after.put(MetaInfo.MODULE, extraction.get(Groups.MODULE));
		after.put(MetaInfo.TYPE, extraction.get(Groups.TYPE1));
		after.put(MetaInfo.INDEX, extraction.get(Groups.INDEX));
		after.put(MetaInfo.TIME, extraction.get(Groups.END_TIME));
		after.put(MetaInfo.MEMORY, extraction.get(Groups.MEMORY_AFTER));
		after.put(MetaInfo.HEAP_SIZE, extraction.get(Groups.HEAP_SIZE_AFTER));
		after.put(MetaInfo.TOTAL_COLLECTION_TIME, extraction.get(Groups.TOTAL_COLLECTION_TIME));
		after.put(MetaInfo.TOTAL_SUM_PAUSE, extraction.get(Groups.TOTAL_SUM_PAUSE));
		after.put(MetaInfo.LONGEST_PAUSE, extraction.get(Groups.LONGEST_PAUSE));
		return Arrays.asList(new DataLine[] { before, after });
	}

	private <T extends Enum<?>> Map<T, Value> extractGroups(T[] enums, String line, Pattern p) {
		List<String> list = new ArrayList<String>();
		Matcher matcher = p.matcher(line);
		boolean matchFound = matcher.find();
		if (matchFound) {
			for (int i = 1; i <= matcher.groupCount(); i++) {
				list.add(matcher.group(i));
			}
		}
		Map<T, Value> retVal = new HashMap<T, Value>();
		for (T d : enums) {
			retVal.put(d, new Value(list.get(d.ordinal())));
		}
		return retVal;
	}

	/**
	 * Instantiates all Patterns which are thread safe
	 */
	public JRockitExtractor() {
		firstLine = Pattern.compile(infoLine());
		heapInfoLine = Pattern.compile(heapInfo());
		infoGeneralLine = Pattern.compile(patternInfoGeneral());
		infoSpecificLine = Pattern.compile(patternInfoSpecific());
		infoPlainLine = Pattern.compile(patternInfoPlain());
		dataLine = Pattern.compile(patternData());
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
	private String prefix() {
		return "\\[" + logLevel() + "\\]\\[" + module() + "\\]\\s";
	}

	private String logLevel() {
		return "(.+)\\s";
	}

	private String module() {
		return "(.+)\\s";
	}

	private String size() {
		return "(\\d+)KB";
	}

	private String time() {
		return "(\\d+.\\d{3})";
	}

}
