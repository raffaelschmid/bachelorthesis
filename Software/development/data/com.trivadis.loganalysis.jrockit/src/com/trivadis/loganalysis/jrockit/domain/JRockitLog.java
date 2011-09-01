package com.trivadis.loganalysis.jrockit.domain;

import java.util.ArrayList;
import java.util.List;

import com.trivadis.loganalysis.core.domain.AbstractLogFile;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;
import com.trivadis.loganalysis.jrockit.internal.analyzer.memory.JRockitExtractor;

public class JRockitLog extends AbstractLogFile {

	private final List<JRockitLogData> data = new ArrayList<JRockitLogData>();

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

}
