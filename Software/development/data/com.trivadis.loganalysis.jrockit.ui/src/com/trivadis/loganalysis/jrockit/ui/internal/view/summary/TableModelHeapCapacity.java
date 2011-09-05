/**
 * Copyright (c) 2011 Loganalysis team and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Raffael Schmid - initial API and implementation
 */
package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

import static com.trivadis.loganalysis.core.common.CollectionUtil.append;
import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.prepend;
import static com.trivadis.loganalysis.core.common.CollectionUtil.stringList;
import static com.trivadis.loganalysis.core.common.CollectionUtil.toArray;
import static com.trivadis.loganalysis.jrockit.ui.internal.util.FormatUtil.size;
import static com.trivadis.loganalysis.jrockit.ui.internal.util.TableUtil.column;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.domain.space.Area;
import com.trivadis.loganalysis.jrockit.domain.space.Heap;
import com.trivadis.loganalysis.jrockit.ui.internal.view.OverviewAbstractTableModel;

public class TableModelHeapCapacity extends OverviewAbstractTableModel {

	private final JRockitJvmRun jvm;

	public TableModelHeapCapacity(JRockitJvmRun jvm, final Table table) {
		this.jvm = jvm;
		initialize(table);
	}

	@Override
	protected void getData(final Table table) {
		line(table, jvm.getHeap(), "Initial capacity", new ClosureIO<Area, String>() {
			public String call(Area in) {
				return size(in.getInitialCapacity());
			}
		});
		line(table, jvm.getHeap(), "Maximum capacity", new ClosureIO<Area, String>() {
			public String call(Area in) {
				return size(in.getMaximumCapacity());
			}
		});

		line(table, jvm.getHeap(), "Peak usage capacity", new ClosureIO<Area, String>() {
			public String call(Area in) {
				return size(in.getPeakUsageCapacity());
			}
		});

		line(table, jvm.getHeap(), "Average capacity", new ClosureIO<Area, String>() {
			public String call(Area in) {
				return size(in.getAverageCapacity());
			}
		});
		line(table, jvm.getHeap(), "Average usage capacity", new ClosureIO<Area, String>() {
			public String call(Area in) {
				return size(in.getAverageUsageCapacity());
			}
		});
	}

	@Override
	protected List<TableColumn> getColumns(final Table table) {
		List<TableColumn> columns = prepend(column(table, ""),
				append(collect(jvm.getHeap().getSpaces(), new ClosureIO<Area, TableColumn>() {
					public TableColumn call(Area in) {
						return column(table, in.getName());
					}
				}), column(table, "Total")));
		return columns;
	}

	private void line(Table table, Heap heap, String label, ClosureIO<Area, String> closure) {
		List<Area> spaces = heap.getSpacesAndHeap();
		List<String> list = collect(spaces, closure);
		new TableItem(table, SWT.NONE).setText(toArray(prepend(label, stringList(list))));
	}

}
