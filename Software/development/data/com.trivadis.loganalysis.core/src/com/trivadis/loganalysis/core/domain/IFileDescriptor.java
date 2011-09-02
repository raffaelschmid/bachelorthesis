package com.trivadis.loganalysis.core.domain;

import java.io.File;
import java.util.List;

import com.trivadis.loganalysis.core.IContentReader;

public interface IFileDescriptor {

	String getPath();

	String getFileName();

	String getAbsolutePath();

	File toFile();

	List<String> getListContent(IContentReader reader);

}
