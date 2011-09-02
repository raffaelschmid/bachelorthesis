package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.prepend;
import static com.trivadis.loganalysis.jrockit.ui.internal.view.TableUtil.column;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.trivadis.loganalysis.core.common.Closure;
import com.trivadis.loganalysis.jrockit.domain.JRockitLog;
import com.trivadis.loganalysis.jrockit.domain.SummaryItem;

public class TableModelActivity extends OverviewAbstractTableModel {
	private final JRockitLog logFile;

	public TableModelActivity(JRockitLog logFile, final Table table) {
		this.logFile = logFile;
		initialize(table);
	}

	@Override
	protected void getData(final Table table) {
		new TableItem(table, SWT.NONE).setText("old collection");
		new TableItem(table, SWT.NONE).setText("young collection");
	}

	@Override
	protected List<TableColumn> getColumns(final Table table) {
		List<TableColumn> columns = prepend(column(table, ""),
				collect(logFile.getGcActivitySummary(), new Closure<SummaryItem, TableColumn>() {
					public TableColumn call(SummaryItem in) {
						return column(table,in.getName());
					}
				}));
		return columns;
	}
}
