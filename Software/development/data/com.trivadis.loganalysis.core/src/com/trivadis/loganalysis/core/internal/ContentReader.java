package com.trivadis.loganalysis.core.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.trivadis.loganalysis.core.IContentReader;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;

public class ContentReader implements IContentReader {

	public List<String> contentAsList(IFileDescriptor logFileDescriptor) {
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
