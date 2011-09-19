package com.trivadis.loganalysis.ui;

import org.jfree.data.xy.XYSeries;

import com.trivadis.loganalysis.core.domain.IJvmRun;

public interface IDatasetProvider {

	XYSeries getDataset(IJvmRun ijvm, Object o);

}
