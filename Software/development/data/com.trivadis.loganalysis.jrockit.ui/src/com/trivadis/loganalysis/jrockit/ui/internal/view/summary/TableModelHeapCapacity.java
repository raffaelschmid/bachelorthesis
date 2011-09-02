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

import com.trivadis.loganalysis.core.common.Closure;
import com.trivadis.loganalysis.jrockit.domain.JRockitLog;
import com.trivadis.loganalysis.jrockit.domain.Space;

public class TableModelHeapCapacity extends OverviewAbstractTableModel {
	private List<Space> spaces;

	public TableModelHeapCapacity(JRockitLog logFile, final Table table) {
		this.spaces = logFile.getSpaces();
		initialize(table);
	}

	@Override
	protected void getData(final Table table) {
		line(table, this.spaces, "Initial capacity", new Closure<Space, Integer>() {
			public Integer call(Space in) {
				return in.getInitialCapacity();
			}
		});
		line(table, this.spaces, "Final capacity", new Closure<Space, Integer>() {
			public Integer call(Space in) {
				return in.getFinalCapacity();
			}
		});

		line(table, this.spaces, "Peak usage capacity", new Closure<Space, Integer>() {
			public Integer call(Space in) {
				return in.getPeakUsageCapacity();
			}
		});

		line(table, this.spaces, "Average capacity", new Closure<Space, Integer>() {
			public Integer call(Space in) {
				return in.getAverageCapacity();
			}
		});
		line(table, this.spaces, "Average usage capacity", new Closure<Space, Integer>() {
			public Integer call(Space in) {
				return in.getAverageUsageCapacity();
			}
		});
	}

	@Override
	protected List<TableColumn> getColumns(final Table table) {
		List<TableColumn> columns = prepend(column(table, ""),
				append(collect(this.spaces, new Closure<Space, TableColumn>() {
					public TableColumn call(Space in) {
						return column(table, in.getName());
					}
				}), column(table, "total")));
		return columns;
	}

	private void line(Table table, List<Space> spaces, String label, Closure<Space, Integer> closure) {
		List<Integer> list = collect(spaces, closure);
		new TableItem(table, SWT.NONE).setText(toArray(prepend(label,
				stringList(append(list, (int) sum(list))))));
	}

}
