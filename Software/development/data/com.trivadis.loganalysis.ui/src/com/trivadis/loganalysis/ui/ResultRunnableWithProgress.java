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
