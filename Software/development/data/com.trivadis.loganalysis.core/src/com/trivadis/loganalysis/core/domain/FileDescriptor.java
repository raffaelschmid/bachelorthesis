/**
 * Copyright (c) 2011 Loganalysis team and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Raffael Schmid - initial API and implementation
 */
package com.trivadis.loganalysis.core.domain;

import java.io.File;
import java.util.List;

import com.trivadis.loganalysis.core.IContentReader;
import com.trivadis.loganalysis.core.common.Assert;

public class FileDescriptor implements IFileDescriptor {

	private final String path, fileName;
	private List<String> rawContent;

	public FileDescriptor(String path, String fileName) {
		this(path, fileName, null);
	}

	public FileDescriptor(String path, String fileName, List<String> rawContent) {
		this.path = path;
		this.fileName = fileName;
		this.rawContent = rawContent;
	}

	public boolean isLoaded(){
		return (rawContent != null);
	}
	
	@Override
	public String toString() {
		return getFileName();
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
		FileDescriptor other = (FileDescriptor) obj;
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

	public File toFile() {
		return new File(getAbsolutePath());
	}

	public static IFileDescriptor fromFile(File file) {
		Assert.assertTrue(file.exists(),file.getAbsolutePath() + " does not exist");
		return new FileDescriptor(file.getParentFile().getAbsolutePath(), file.getName());
	}

	public static IFileDescriptor fromFile(String filePath) {
		return fromFile(new File(filePath));
	}

}
