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
import com.trivadis.loganalysis.ui.domain.profile.IExtension;


public interface IProfileProvider {

	boolean knowsJvm(IJvmRun jvm);

	void saveConfiguration(IMemento memento, IExtension configuration);

	void loadConfiguration(IMemento memento);

	IExtension getConfiguration();


}
