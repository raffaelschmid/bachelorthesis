package com.trivadis.loganalysis.jrockit.domain;

import java.util.Arrays;
import java.util.List;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.core.common.Assert;

public class LogModuleChain implements ILogModule {

	private final List<ILogModule> chain;

	public LogModuleChain(ILogModule... members) {
		chain = Arrays.asList(members);
		Assert.assertTrue(members.length>0);
	}

	public ModuleResult proceed(JRockitLog logFile, String line) {
		ModuleResult retVal = ModuleResult.RETURN;
		for (ILogModule handler : chain) {
			retVal = handler.proceed(logFile, line);
			if (retVal == ModuleResult.RETURN) {
				break;
			}
		}
		return retVal;
	}

}
