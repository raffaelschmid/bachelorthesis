package com.trivadis.loganalysis.core.domain;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.Assert;

import com.trivadis.loganalysis.core.IContentReader;

public class LogFileDescriptor implements ILogFileDescriptor {
	private static final String ICON_PATH = "icons/document.gif";

	private final String path, fileName;
	private List<String> rawContent;

	public LogFileDescriptor(String path, String fileName) {
		this(path, fileName, null);
	}

	public LogFileDescriptor(String path, String fileName, List<String> rawContent) {
		this.path = path;
		this.fileName = fileName;
		this.rawContent = rawContent;
	}

	public boolean isLoaded(){
		return (rawContent != null);
	}
	
	@Override
	public String toString() {
		return getAbsolutePath();
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

	public List<String> getListContent(IContentReader reader) {
		List<String> retVal;
		if(isLoaded()){
			retVal = this.rawContent;
		}
		else{
			retVal = reader.contentAsList(this);
		}
		return retVal;
	}

	public String getAbsolutePath() {
		return getPath() + File.separator + getFileName();
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

	public File toFile() {
		return new File(getAbsolutePath());
	}

	public static ILogFileDescriptor fromFile(File file) {
		Assert.isTrue(file.exists(),file.getAbsolutePath() + " does not exist");
		return new LogFileDescriptor(file.getParentFile().getAbsolutePath(), file.getName());
	}

	public static ILogFileDescriptor fromFile(String filePath) {
		return fromFile(new File(filePath));
	}

}
