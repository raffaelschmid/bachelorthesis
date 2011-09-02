package com.trivadis.loganalysis.jrockit.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.trivadis.loganalysis.core.domain.AbstractLogFile;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.jrockit.old.HeapSpace;
import com.trivadis.loganalysis.jrockit.old.KeepArea;
import com.trivadis.loganalysis.jrockit.old.Measurment;
import com.trivadis.loganalysis.jrockit.old.OldCollection;
import com.trivadis.loganalysis.jrockit.old.YoungCollection;

public class JRockitLog extends AbstractLogFile {

	private final List<JRockitLogData> data = new ArrayList<JRockitLogData>();
	private List<HeapSpace> spaces = Arrays.asList(new HeapSpace[] { new YoungCollection(), new KeepArea(),
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

//	public void addDataFromLine(String line, JRockitExtractor extractor) {
//		List<JRockitLogData> extractDataLine = extractor.extractDataLine(line);
//		for (JRockitLogData d : extractDataLine) {
//			data.add(d);
//		}
//	}

	public List<HeapSpace> getSpaces() {
		return spaces;
	}

	public List<Measurment> getOverallStatistic() {
		return Arrays.asList(new Measurment[] { new Measurment("Duration of measurment", 32.5),
				new Measurment("Total bytes allocated (KB)", 4838459),
				new Measurment("Number of Garbage Collection events", 17),
				new Measurment("Average bytes allocated per Garbage Collection", 423214),
				new Measurment("Average ideal allocation rate", 213213),
				new Measurment("Residual bytes", 234223),
				new Measurment("Time spent in Garbage Collection", 6.5),
				new Measurment("Percentage of time in Garbage Collection", 20),
				new Measurment("Time spent in Old Garbage Collection", 3.4),
				new Measurment("Percentage of time in Old Garbage Collection", 45),
				new Measurment("Average allocation rate", 123412) });
	}
}
