package com.trivadis.loganalysis.core.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.trivadis.loganalysis.core.IContentReader;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;

public class ContentReader implements IContentReader {

	public String contentAsString(ILogFileDescriptor logFileDescriptor) {
		File aFile = logFileDescriptor.toFile();
		StringBuilder contents = new StringBuilder();

		try {
			String lineSeparator = System.getProperty("line.separator");
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(lineSeparator);
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		return contents.toString();
	}
	

	public List<String> contentAsList(ILogFileDescriptor logFileDescriptor) {
		List<String> list = new ArrayList<String>();
		File aFile = logFileDescriptor.toFile();
		try {
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					list.add(line);
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return list;
	}

}
