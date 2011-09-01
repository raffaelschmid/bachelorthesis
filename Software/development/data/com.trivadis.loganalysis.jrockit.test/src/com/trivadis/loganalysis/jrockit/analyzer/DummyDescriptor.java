package com.trivadis.loganalysis.jrockit.analyzer;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.trivadis.loganalysis.core.IContentReader;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;

public class DummyDescriptor implements ILogFileDescriptor {

	private final List<String> logs;

	public DummyDescriptor(String... logs) {
		this.logs = Arrays.asList(logs);
	}

	public String getPath() {
		throw new UnsupportedOperationException(
				"descriptors method should not be used");
	}

	public String getFileName() {
		throw new UnsupportedOperationException(
				"descriptors method should not be used");
	}

	public String getAbsolutePath() {
		throw new UnsupportedOperationException(
				"descriptors method should not be used");
	}

	public File toFile() {
		throw new UnsupportedOperationException(
				"descriptors method should not be used");
	}

	public List<String> getListContent(IContentReader reader) {
		return this.logs;
	}

}
