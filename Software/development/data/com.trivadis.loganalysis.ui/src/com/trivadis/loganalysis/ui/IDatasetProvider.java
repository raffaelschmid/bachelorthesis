package com.trivadis.loganalysis.ui;

import org.jfree.data.xy.XYSeries;

import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.ui.domain.profile.Serie;

public interface IDatasetProvider {

	XYSeries getDataset(IJvmRun ijvm, Serie o);

}
