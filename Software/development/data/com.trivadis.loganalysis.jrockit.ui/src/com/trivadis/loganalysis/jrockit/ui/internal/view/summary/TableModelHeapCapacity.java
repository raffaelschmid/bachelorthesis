package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

import static com.trivadis.loganalysis.core.common.CollectionUtil.append;
import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.prepend;
import static com.trivadis.loganalysis.core.common.CollectionUtil.stringList;
import static com.trivadis.loganalysis.core.common.CollectionUtil.sum;
import static com.trivadis.loganalysis.core.common.CollectionUtil.toArray;
import static com.trivadis.loganalysis.jrockit.ui.internal.view.TableUtil.column;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.jrockit.file.JRockitLog;
import com.trivadis.loganalysis.jrockit.old.HeapSpace;
import com.trivadis.loganalysis.jrockit.ui.internal.view.OverviewAbstractTableModel;

public class TableModelHeapCapacity extends OverviewAbstractTableModel {
	private List<HeapSpace> spaces;

	public TableModelHeapCapacity(JRockitLog logFile, final Table table) {
		this.spaces = logFile.getSpaces();
		initialize(table);
	}

	@Override
	protected void getData(final Table table) {
		line(table, this.spaces, "Initial capacity", new ClosureIO<HeapSpace, Integer>() {
			public Integer call(HeapSpace in) {
				return (int) in.getInitialCapacity().getValue();
			}
		});
		line(table, this.spaces, "Final capacity", new ClosureIO<HeapSpace, Integer>() {
			public Integer call(HeapSpace in) {
				return (int) in.getFinalCapacity().getValue();
			}
		});

		line(table, this.spaces, "Peak usage capacity", new ClosureIO<HeapSpace, Integer>() {
			public Integer call(HeapSpace in) {
				return (int) in.getPeakUsageCapacity().getValue();
			}
		});

		line(table, this.spaces, "Average capacity", new ClosureIO<HeapSpace, Integer>() {
			public Integer call(HeapSpace in) {
				return (int) in.getAverageCapacity().getValue();
			}
		});
		line(table, this.spaces, "Average usage capacity", new ClosureIO<HeapSpace, Integer>() {
			public Integer call(HeapSpace in) {
				return (int) in.getAverageUsageCapacity().getValue();
			}
		});
	}

	@Override
	protected List<TableColumn> getColumns(final Table table) {
		List<TableColumn> columns = prepend(column(table, ""),
				append(collect(this.spaces, new ClosureIO<HeapSpace, TableColumn>() {
					public TableColumn call(HeapSpace in) {
						return column(table, in.getName());
					}
				}), column(table, "total")));
		return columns;
	}

	private void line(Table table, List<HeapSpace> spaces, String label, ClosureIO<HeapSpace, Integer> closure) {
		List<Integer> list = collect(spaces, closure);
		new TableItem(table, SWT.NONE).setText(toArray(prepend(label,
				stringList(append(list, (int) sum(list))))));
	}

}
