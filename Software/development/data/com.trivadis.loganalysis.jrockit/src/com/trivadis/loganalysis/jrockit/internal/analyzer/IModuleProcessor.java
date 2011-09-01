package com.trivadis.loganalysis.jrockit.internal.analyzer;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.jrockit.domain.JRockitLog;

public interface IModuleProcessor {
	ModuleResult proceed(JRockitLog logFile, String line);
}
