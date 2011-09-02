package com.trivadis.loganalysis.jrockit.ui.internal.view;

import java.util.List;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public abstract class OverviewAbstractTableModel {

	protected void initialize(Table table) {
		List<TableColumn> columns = getColumns(table);
		table.setRedraw(false);
		getData(table);
		table.setRedraw(true);
		for (TableColumn column : columns) {
			column.pack();
		}
	}

	protected abstract void getData(Table table);

	protected abstract List<TableColumn> getColumns(Table table);

}
