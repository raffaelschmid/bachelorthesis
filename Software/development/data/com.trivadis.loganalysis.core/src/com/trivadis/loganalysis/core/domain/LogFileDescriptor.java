package com.trivadis.loganalysis.core.domain;

import java.io.File;

public class LogFileDescriptor implements ILogFileDescriptor {
	private static final String ICON_PATH = "icons/document.gif";

	private final String path, fileName, rawContent;

	public LogFileDescriptor(String path, String fileName, String rawContent) {
		this.path = path;
		this.fileName = fileName;
		this.rawContent = rawContent;
	}

	public String getPath() {
		return path;
	}

	public String getFileName() {
		return fileName;
	}

	public String getIconPath() {
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
		LogFileDescriptor other = (LogFileDescriptor) obj;
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

	public String getRawContent() {
		return rawContent;
	}

}
