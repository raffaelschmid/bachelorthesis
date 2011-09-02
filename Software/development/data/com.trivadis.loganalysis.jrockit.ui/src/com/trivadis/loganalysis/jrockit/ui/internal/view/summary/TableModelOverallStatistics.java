package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;
import static com.trivadis.loganalysis.jrockit.ui.internal.view.TableUtil.column;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.trivadis.loganalysis.core.common.Closure;
import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.ui.internal.view.OverviewAbstractTableModel;

public class TableModelOverallStatistics extends OverviewAbstractTableModel {

	@SuppressWarnings("unused")
	private final JRockitJvmRun jvm;

	public TableModelOverallStatistics(JRockitJvmRun logFile, final Table table) {
		this.jvm = logFile;
		initialize(table);
	}

	@Override
	protected void getData(final Table table) {
		List<Tuple> tuples = Arrays.asList(new Tuple[] {
				new Tuple("Duration of the measurment", "-"),
				new Tuple("Total bytes allocated", "-"), new Tuple("Number of gc events", "-"),
				new Tuple("Average bytes allocated per gc", "-"),
				new Tuple("Average ideal allocation rate", "-"), new Tuple("Residual bytes", "-"),
				new Tuple("Time spent in gc", "-"), new Tuple("Percentage of time in gc", "-"),
				new Tuple("Time spent in full gc", "-"),
				new Tuple("Percentage of time in full gc", "-"),
				new Tuple("Average allocation rate", "-") });
		foreach(tuples, new Closure<Tuple>() {
			public void call(Tuple in) {
				new TableItem(table, SWT.NONE).setText(in.toArray());
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
