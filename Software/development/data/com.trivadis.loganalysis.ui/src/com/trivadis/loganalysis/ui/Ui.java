package com.trivadis.loganalysis.ui;

import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;

public class Ui {
	private static class Holder {
		private static Ui INSTANCE = new Ui();
	}

	private Ui() {

	}

	public static Ui getDefault() {
		return Holder.INSTANCE;
	}

	public void asyncExec(Runnable runnable) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(runnable);
	}

	public <T> T busyCursorWithResultWhile(ResultRunnableWithProgress<T> runnable) {
		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(runnable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return runnable.get();
	}
	public void busyCursorWhile(IRunnableWithProgress runnable){
		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(runnable);
		} catch (Exception e) {
			Ui.getDefault().handleException(e);
		}
	}

	public void handleException(Exception e) {
		// TODO alter exception handling
		e.printStackTrace();
	}
}
