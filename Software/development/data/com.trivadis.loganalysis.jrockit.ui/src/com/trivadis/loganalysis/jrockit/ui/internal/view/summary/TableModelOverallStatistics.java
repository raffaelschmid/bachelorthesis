package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;
import static com.trivadis.loganalysis.core.common.CollectionUtil.stringArray;
import static com.trivadis.loganalysis.jrockit.ui.internal.view.TableUtil.column;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.trivadis.loganalysis.core.common.Closure;
import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.jrockit.file.JRockitLog;
import com.trivadis.loganalysis.jrockit.old.Measurment;
import com.trivadis.loganalysis.jrockit.ui.internal.view.OverviewAbstractTableModel;

public class TableModelOverallStatistics extends OverviewAbstractTableModel {
	private final JRockitLog logFile;

	public TableModelOverallStatistics(JRockitLog logFile, final Table table) {
		this.logFile = logFile;
		initialize(table);
	}

	@Override
	protected void getData(final Table table) {
		foreach(logFile.getOverallStatistic(), new Closure<Measurment>() {
			public void call(Measurment in) {
				new TableItem(table, SWT.NONE).setText(stringArray(new Object[] { in.getName(),
						in.getValue() }));
			}
		});
	}

	@Override
	protected List<TableColumn> getColumns(final Table table) {
		List<TableColumn> columns = collect(Arrays.asList(new String[] { "Name", "Value" }),
				new ClosureIO<String, TableColumn>() {
					public TableColumn call(String in) {
						return column(table, in);
					}
				});
		return columns;
	}
}
