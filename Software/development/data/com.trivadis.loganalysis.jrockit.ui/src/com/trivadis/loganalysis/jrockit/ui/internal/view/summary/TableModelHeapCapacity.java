package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.prepend;
import static com.trivadis.loganalysis.core.common.CollectionUtil.toArray;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.trivadis.loganalysis.core.common.Closure;
import com.trivadis.loganalysis.jrockit.domain.JRockitLog;
import com.trivadis.loganalysis.jrockit.domain.Space;

public class TableModelHeapCapacity {
	public TableModelHeapCapacity(JRockitLog logFile, Table table) {
		List<TableColumn> columns = new ArrayList<TableColumn>();
		List<Space> spaces = logFile.getSpaces();
		columns.add(column(table, ""));
		for (Space space : spaces) {
			columns.add(column(table, space.getName()));
		}

		table.setRedraw(false);
		new TableItem(table, SWT.NONE).setText(toArray(prepend("Initial Capacity",
				collect(spaces, new Closure<Space, String>() {
					public String call(Space in) {
						return String.valueOf(in.getInitialCapacity());
					}
				}))));
		new TableItem(table, SWT.NONE).setText(toArray(prepend("Final Capacity",
				collect(spaces, new Closure<Space, String>() {
					public String call(Space in) {
						return String.valueOf(in.getFinalCapacity());
					}
				}))));
		new TableItem(table, SWT.NONE).setText(toArray(prepend("Peak Capacity",
				collect(spaces, new Closure<Space, String>() {
					public String call(Space in) {
						return String.valueOf(in.getPeakUsageCapacity());
					}
				}))));
		new TableItem(table, SWT.NONE).setText(toArray(prepend("Average usage of capacity",
				collect(spaces, new Closure<Space, String>() {
					public String call(Space in) {
						return String.valueOf(in.getAverageUsageCapacity());
					}
				}))));
		
		new TableItem(table, SWT.NONE).setText(toArray(prepend("Peak usage of capacity",
				collect(spaces, new Closure<Space, String>() {
					public String call(Space in) {
						return String.valueOf(in.getAverageCapacity());
					}
				}))));
		
		table.setRedraw(true);
		for (TableColumn column : columns) {
			column.pack();
		}
	}

	private TableColumn column(Table table, String string) {
		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText(string);
		return column;
	}

}
