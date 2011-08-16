package com.trivadis.loganalysis.core.internal.domain;

import java.io.File;

import com.trivadis.loganalysis.domain.ILogFile;

public class LogFile implements ILogFile {
	private static final String ICON_PATH = "icons/document.gif";

	private final String path;
	private final String fileName;

	public LogFile(String path, String fileName) {
		this.path = path;
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public String getFileName() {
		return fileName;
	}

	public String getIconPath(){
		return ICON_PATH;
	}

	@Override
	public String toString() {
		return path + File.separator + fileName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogFile other = (LogFile) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

}
