package com.trivadis.loganalysis.jrockit.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.trivadis.loganalysis.core.domain.AbstractLogFile;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.jrockit.internal.analyzer.memory.JRockitExtractor;

public class JRockitLog extends AbstractLogFile {

	private final List<JRockitLogData> data = new ArrayList<JRockitLogData>();
	private List<Space> spaces = Arrays.asList(new Space[] { new YoungCollection(), new KeepArea(),
			new OldCollection() });

	public JRockitLog(IFileDescriptor logFileDescriptor) {
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

	public List<Space> getSpaces() {
		return spaces;
	}

	public List<SummaryItem> getGcActivitySummary() {
		return Arrays.asList(new SummaryItem[] { new SummaryItem("last occurence", 3d),
				new SummaryItem("count", 3d), new SummaryItem("average interval", 3d),
				new SummaryItem("average duration", 3d),
				new SummaryItem("average rate of collection", 3d) });
	}

}
