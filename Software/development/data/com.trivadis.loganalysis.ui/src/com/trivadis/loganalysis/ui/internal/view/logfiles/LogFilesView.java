package com.trivadis.loganalysis.ui.internal.view.logfiles;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import com.trivadis.loganalysis.core.IContext;
import com.trivadis.loganalysis.core.Loganalysis;
import com.trivadis.loganalysis.core.SelectedFilesChangeListener;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;
import com.trivadis.loganalysis.core.domain.LogFileDescriptor;
import com.trivadis.loganalysis.ui.internal.Activator;
import com.trivadis.loganalysis.ui.internal.Command;

public class LogFilesView extends ViewPart implements
		SelectedFilesChangeListener {

	public static final String ID = LogFilesView.class.getName();

	private static final String STORAGE_KEY = ID + ".logFiles";

	private TableViewer viewer;
	private Action doubleClickAction;

	private final IContext context;

	public LogFilesView() {
		this(Loganalysis.getContext());
	}

	public LogFilesView(IContext context) {
		this.context = context;
		context.addLogFilesChangeListener(this);
	}

	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			return context.getSelectedFiles().toArray();
		}
	}

	class ViewLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}

		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		public Image getImage(Object obj) {
			return Activator.getDefault().getImageDescriptor("icons/chart.gif")
					.createImage();
		}
	}

	public void createPartControl(Composite parent) {
		createListViewer(parent);
		makeActions();
		hookDoubleClickAction();
		registerContextMenu();
	}

	private void createListViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new ViewerSorter());
		viewer.setInput(getViewSite());
	}

	private void registerContextMenu() {
		final MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
		getSite().setSelectionProvider(viewer);
	}

	private void makeActions() {
		doubleClickAction = new Action() {
			public void run() {
				Command.execute("com.trivadis.loganalysis.ui.openAnalysis");
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void saveState(IMemento memento) {
		super.saveState(memento);
		StringBuffer buf = new StringBuffer();
		for (ILogFileDescriptor file : context.getSelectedFiles()) {
			buf.append(file.getAbsolutePath() + File.pathSeparator);
		}
		memento.putString(STORAGE_KEY, buf.toString());
	}

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		if (memento != null) {
			String value = memento.getString(STORAGE_KEY);
			if (value != null) {
				for (String filePath : value.split(File.pathSeparator)) {
					context.add(LogFileDescriptor.fromFile(filePath));
				}
			}
		}
	}

	public void fileSelectionChanged() {
		if (viewer != null)
			viewer.refresh();
	}

}