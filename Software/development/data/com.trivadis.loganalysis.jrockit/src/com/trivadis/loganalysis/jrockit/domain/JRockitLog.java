package com.trivadis.loganalysis.jrockit.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.trivadis.loganalysis.core.domain.AbstractLogFile;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;
import com.trivadis.loganalysis.jrockit.internal.analyzer.memory.JRockitExtractor;

public class JRockitLog extends AbstractLogFile {

	private final List<JRockitLogData> data = new ArrayList<JRockitLogData>();
	private List<Space> spaces = Arrays.asList(new Space[] { new YoungCollection(), new KeepArea(),
			new OldCollection() });

	public JRockitLog(ILogFileDescriptor logFileDescriptor) {
		super(logFileDescriptor);
	}

	public void addDataLine(JRockitLogData line) {
		getData().add(line);
	}

	public List<JRockitLogData> getData() {
		return data;
	}

	public void addDataFromLine(String line, JRockitExtractor extractor) {
		List<JRockitLogData> extractDataLine = extractor.extractDataLine(line);
		for (JRockitLogData d : extractDataLine) {
			data.add(d);
		}
	}

	public List<Row> heapCapacitySummary() {
		return Arrays.asList(new Row[] { new Row("Initial Capacity", "N/A0", "N/A1"),
				new Row("Final Capacity", "N/A0", "N/A1"),
				new Row("Peak Capacity", "N/A0", "N/A1"),
				new Row("Average Usage of Capacity", "N/A0", "N/A1"),
				new Row("Peak Usage of Capacity", "N/A0", "N/A1") });
	}

	public List<Space> getSpaces() {
		return spaces;
	}

}
