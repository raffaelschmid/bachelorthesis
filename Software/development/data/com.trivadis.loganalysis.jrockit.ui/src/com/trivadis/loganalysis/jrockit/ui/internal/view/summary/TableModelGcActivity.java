package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.findAll;
import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;
import static com.trivadis.loganalysis.core.common.CollectionUtil.prepend;
import static com.trivadis.loganalysis.jrockit.ui.internal.view.TableUtil.column;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.trivadis.loganalysis.core.common.Closure;
import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.gc.GarbageCollection;
import com.trivadis.loganalysis.jrockit.domain.gc.OldCollection;
import com.trivadis.loganalysis.jrockit.domain.gc.YoungCollection;
import com.trivadis.loganalysis.jrockit.ui.internal.view.OverviewAbstractTableModel;

public class TableModelGcActivity extends OverviewAbstractTableModel {
	private final JRockitJvmRun jvm;

	public TableModelGcActivity(JRockitJvmRun logFile, final Table table) {
		this.jvm = logFile;
		initialize(table);
	}

	@Override
	protected void getData(final Table table) {
		List<GarbageCollection> list = jvm.getGarbageCollections();
		GcActivityAggregation young = new GcActivityAggregation(findAll(list, new Predicate<GarbageCollection>() {
			public boolean matches(GarbageCollection item) {
				return (item instanceof YoungCollection);
			}
		}));
		GcActivityAggregation old = new GcActivityAggregation(findAll(list, new Predicate<GarbageCollection>() {
			public boolean matches(GarbageCollection item) {
				return (item instanceof OldCollection);
			}
		}));
		List<Tuple> aggregation = Arrays.asList(new Tuple[] {
				new Tuple(young.getName(), young.getLastOccurence(), young.getCount(),
						"avg intervals", young.getAverageDuration(), "df"),
				new Tuple(old.getName(), old.getLastOccurence(), old.getCount(), "avg intervals",
						old.getAverageDuration(), "") });
		foreach(aggregation, new Closure<Tuple>() {
			public void call(Tuple in) {
				new TableItem(table, SWT.NONE).setText(in.toArray());
			}
		});
	}

	@Override
	protected List<TableColumn> getColumns(final Table table) {
		List<TableColumn> columns = prepend(
				column(table, ""),
				collect(Arrays.asList(new String[] { "Last occurence", "Count", "Average interval",
						"Average Duration", "Average rate of collection" }),
						new ClosureIO<String, TableColumn>() {
							public TableColumn call(String in) {
								return column(table, in);
							}
						}));
		return columns;
	}
}
