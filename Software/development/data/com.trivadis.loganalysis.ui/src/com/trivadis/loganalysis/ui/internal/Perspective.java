package com.trivadis.loganalysis.ui.internal;

import static org.eclipse.jface.dialogs.MessageDialogWithToggle.openYesNoQuestion;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

public class Perspective {

	private enum Selection {
		PROMPT, ALWAYS, NEVER;
	}

	public static void updateWithNotification(String key, String title,
			String message, String perspectiveId) {
		if (shouldSwitchPerspective(key, title, message))
			update(perspectiveId);
	}

	private static boolean shouldSwitchPerspective(String key, String title,
			String message) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String preference = store.getString(key);
		Selection guard = (preference != null && !preference.equals("")) ? Selection
				.valueOf(preference) : Selection.PROMPT;
		if (Selection.PROMPT.equals(guard)) {
			MessageDialogWithToggle dialog = openYesNoQuestion(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow().getShell(),
					title, message, null, false, store, key);
			guard = getConfirmCancel(dialog);
			if (dialog.getToggleState()) {
				saveSetting(key, guard);
			}
		}

		return guard.equals(Selection.ALWAYS);
	}

	private static Selection getConfirmCancel(MessageDialogWithToggle dialog) {
		return (dialog.getReturnCode() == IDialogConstants.YES_ID) ? Selection.ALWAYS
				: Selection.NEVER;
	}

	private static void saveSetting(String key, Selection guard) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setValue(key, guard.toString());
	}

	public static void update(String perspectiveId) {
		try {
			PlatformUI.getWorkbench().showPerspective(perspectiveId,
					PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		} catch (WorkbenchException e) {
			throw new RuntimeException(e);
		}
	}
}
