package com.trivadis.loganalysis.core;

import java.util.List;

import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;

public interface IContentReader {

	List<String> contentAsList(ILogFileDescriptor file);

}
