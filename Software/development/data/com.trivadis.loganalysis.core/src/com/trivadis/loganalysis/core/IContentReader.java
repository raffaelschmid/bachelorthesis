package com.trivadis.loganalysis.core;

import java.util.List;

import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;

public interface IContentReader {

	String contentAsString(ILogFileDescriptor logFileDescriptor);

	List<String> contentAsList(ILogFileDescriptor file);

}
