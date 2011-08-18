package com.trivadis.loganalysis.core.domain;

import java.io.File;

public interface ILogFileDescriptor {

	String getPath();

	String getFileName();

	String getAbsolutePath();

	File toFile();

}
