package com.trivadis.loganalysis.jrockit.domain;

import com.trivadis.loganalysis.core.ModuleResult;

public interface ILogModule {
	ModuleResult proceed(JRockitLog logFile, String line);
}
