package com.trivadis.loganalysis.core;

import java.util.List;

import com.trivadis.loganalysis.core.domain.IFileDescriptor;

public interface IContentReader {

	List<String> contentAsList(IFileDescriptor file);

}
