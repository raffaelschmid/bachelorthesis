package com.trivadis.loganalysis.ui.internal.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.trivadis.loganalysis.ui.internal.Perspective;

public class OpenGcLoganalysisPerspective extends AbstractHandler {

	public static final String PERSPECTIVE_ID = "com.trivadis.loganalysis.ui.inernal.perspective.GarbageCollectionAnalysisPerspective";

	public Object execute(ExecutionEvent event) throws ExecutionException {
		Perspective.update(PERSPECTIVE_ID);
		return null;
	}
}
