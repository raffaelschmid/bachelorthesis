package com.trivadis.loganalysis.jrockit.internal.analyzer;

import java.util.Arrays;
import java.util.List;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.core.common.Assert;
import com.trivadis.loganalysis.jrockit.domain.JRockitLog;

public class CompositeModuleProcessor implements IModuleProcessor {

	private final List<IModuleProcessor> chain;

	public CompositeModuleProcessor(IModuleProcessor... members) {
		chain = Arrays.asList(members);
		Assert.assertTrue(members.length>0);
	}

	public ModuleResult proceed(JRockitLog logFile, String line) {
		ModuleResult retVal = ModuleResult.RETURN;
		for (IModuleProcessor handler : chain) {
			retVal = handler.proceed(logFile, line);
			if (retVal == ModuleResult.RETURN) {
				break;
			}
		}
		return retVal;
	}

}
