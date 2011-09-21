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
package com.trivadis.loganalysis.ui;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;


public interface IProfileProvider {

	String getKey();
	
	boolean knowsJvm(IJvmRun jvm);

	void saveConfiguration(IMemento memento, IConfiguration configuration);

	IConfiguration getConfiguration(IMemento memento);

	IConfiguration getConfiguration();

	IConfiguration getConfiguration(IMemento memento, boolean store);


}
