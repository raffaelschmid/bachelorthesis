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
package com.trivadis.loganalysis.jrockit.internal.analyzer;

import java.util.Arrays;
import java.util.List;

import com.trivadis.loganalysis.core.ModuleResult;
import com.trivadis.loganalysis.core.common.Assert;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;

public class CompositeModuleProcessor implements IProcessor {

	private final List<IProcessor> chain;
	
	public CompositeModuleProcessor(final IProcessor... members) {
		chain = Arrays.asList(members);
		Assert.assertTrue(members.length>0);
	}

	public ModuleResult process(final JRockitJvmRun jvmRun, final String line) {
		ModuleResult retVal = ModuleResult.RETURN;
		for (final IProcessor handler : chain) {
			retVal = handler.process(jvmRun, line);
			if (retVal == ModuleResult.RETURN) {
				break;
			}
		}
		return retVal;
	}

}
