package com.trivadis.loganalysis.jrockit.internal.analyzer;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;

public interface IModuleProcessor {
	ModuleResult proceed(JRockitJvmRun logFile, String line);
}
