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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public abstract class ResultRunnableWithProgress<T> implements IRunnableWithProgress {
	private T result = null;

	public synchronized T get() {
		return result;
	}

	public void set(T result) {
		this.result = result;
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		this.result = result(monitor);
	}

	abstract public T result(IProgressMonitor monitor) ;

}
