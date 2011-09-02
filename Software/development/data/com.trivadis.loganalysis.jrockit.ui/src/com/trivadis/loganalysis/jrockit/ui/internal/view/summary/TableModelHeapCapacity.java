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
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.space.Space;
import com.trivadis.loganalysis.jrockit.ui.internal.view.OverviewAbstractTableModel;

public class TableModelHeapCapacity extends OverviewAbstractTableModel {

	private final JRockitJvmRun jvm;

	public TableModelHeapCapacity(JRockitJvmRun jvm, final Table table) {
		this.jvm = jvm;
		initialize(table);
	}

	@Override
	protected void getData(final Table table) {
		line(table, jvm.getHeap().getSpaces(), "Initial capacity", new ClosureIO<Space, Double>() {
			public Double call(Space in) {
				return in.getInitialCapacity();
			}
		});
		line(table, jvm.getHeap().getSpaces(), "Final capacity", new ClosureIO<Space, Double>() {
			public Double call(Space in) {
				return in.getFinalCapacity();
			}
		});

		line(table, jvm.getHeap().getSpaces(), "Peak usage capacity",
				new ClosureIO<Space, Double>() {
					public Double call(Space in) {
						return in.getPeakUsageCapacity();
					}
				});

		line(table, jvm.getHeap().getSpaces(), "Average capacity", new ClosureIO<Space, Double>() {
			public Double call(Space in) {
				return in.getAverageCapacity();
			}
		});
		line(table, jvm.getHeap().getSpaces(), "Average usage capacity",
				new ClosureIO<Space, Double>() {
					public Double call(Space in) {
						return in.getAverageUsageCapacity();
					}
				});
	}

	@Override
	protected List<TableColumn> getColumns(final Table table) {
		List<TableColumn> columns = prepend(column(table, ""),
				append(collect(jvm.getHeap().getSpaces(), new ClosureIO<Space, TableColumn>() {
					public TableColumn call(Space in) {
						return column(table, in.getName());
					}
				}), column(table, "total")));
		return columns;
	}

	private void line(Table table, List<Space> spaces, String label,
			ClosureIO<Space, Double> closure) {
		List<Double> list = collect(spaces, closure);
		new TableItem(table, SWT.NONE).setText(toArray(prepend(label,
				stringList(append(list, sum(list))))));
	}

}
