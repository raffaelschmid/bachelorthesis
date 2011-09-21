package com.trivadis.loganalysis.ui.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public interface IProfileExporter {

	void export(List<IProfile> profiles, String fileName) throws IOException;

	void export(List<IProfile> profiles, Writer sw) throws IOException;

}
