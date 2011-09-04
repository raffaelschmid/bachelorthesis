package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.findAll;
import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;
import static com.trivadis.loganalysis.core.common.CollectionUtil.prepend;
import static com.trivadis.loganalysis.jrockit.ui.internal.view.TableUtil.column;

import java.util.ArrayList;
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
		List<GarbageCollection> youngCollections = findAll(list,
				new Predicate<GarbageCollection>() {
					public boolean matches(GarbageCollection item) {
						return (item instanceof YoungCollection);
					}
				});
		List<GarbageCollection> oldCollections = findAll(list, new Predicate<GarbageCollection>() {
			public boolean matches(GarbageCollection item) {
				return (item instanceof OldCollection);
			}
		});

		List<Tuple> aggregation = new ArrayList<Tuple>();
		aggregate(youngCollections, aggregation);
		aggregate(oldCollections, aggregation);

		foreach(aggregation, new Closure<Tuple>() {
			public void call(Tuple in) {
				new TableItem(table, SWT.NONE).setText(in.toArray());
			}
		});
	}

	private void aggregate(List<GarbageCollection> youngCollections, List<Tuple> aggregation) {
		if (youngCollections.size() > 0) {
			GcActivityAggregation gcAggregation = new GcActivityAggregation(youngCollections);
			aggregation.add(new Tuple(gcAggregation.getName(), gcAggregation.getLastOccurence(),
					gcAggregation.getCount(), gcAggregation.getAverageInterval(), gcAggregation
							.getAverageDuration(), "df"));
		}
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
