package com.trivadis.loganalysis.jrockit.domain;

import java.util.ArrayList;
import java.util.List;

import com.trivadis.loganalysis.core.domain.AbstractLogFile;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;
import com.trivadis.loganalysis.jrockit.analyzer.JRockitExtractor;

public class JRockitLogFile extends AbstractLogFile {

	private final List<DataLine> data = new ArrayList<DataLine>();

	public JRockitLogFile(ILogFileDescriptor logFileDescriptor) {
		super(logFileDescriptor);
	}

	public void addDataLine(DataLine line) {
		getData().add(line);
	}

	public List<DataLine> getData() {
		return data;
	}

	public void addDataFromLine(String line, JRockitExtractor extractor) {
		List<DataLine> extractDataLine = extractor.extractDataLine(line);
		for (DataLine d : extractDataLine) {
			data.add(d);
		}
	}

}
