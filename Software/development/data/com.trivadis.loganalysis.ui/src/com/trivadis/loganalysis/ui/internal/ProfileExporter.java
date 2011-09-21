package com.trivadis.loganalysis.ui.internal;

import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.eclipse.ui.XMLMemento;

import com.trivadis.loganalysis.core.common.ClosureI;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;
import com.trivadis.loganalysis.ui.domain.profile.IStandardProfile;

public class ProfileExporter implements IProfileExporter {

	public void export(final List<IProfile> profiles, final Writer sw) throws IOException {
		final XMLMemento memento = XMLMemento.createWriteRoot("profiles");
		foreach(profiles, new ClosureI<IProfile>() {
			public void call(final IProfile in) {
				if(!(in instanceof IStandardProfile))
					in.getConfiguration().save(memento);
			}
		});
		memento.save(sw);
	}

	public void export(final List<IProfile> profiles, final String fileName) throws IOException {
		final File file = new File(fileName);
		file.delete();
		if (file.createNewFile()) {
			export(profiles, new BufferedWriter(new FileWriter(file)));
		} else {
			throw new RuntimeException("file was not created");
		}
	}

}
