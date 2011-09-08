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

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.findAll;
import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;
import static com.trivadis.loganalysis.core.common.CollectionUtil.prepend;
import static com.trivadis.loganalysis.jrockit.ui.internal.util.FormatUtil.seconds;
import static com.trivadis.loganalysis.jrockit.ui.internal.util.TableUtil.column;
import static java.util.Arrays.asList;

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

public class TableModelGcActivity extends OverviewAbstractTableModel {
	private final JRockitJvmRun jvm;

	private final Predicate<List<GarbageCollection>> whereListIsNotEmpty = new Predicate<List<GarbageCollection>>() {
		public boolean matches(List<GarbageCollection> item) {
			return item.size() > 0;
		}
	};
	private ClosureIO<List<GarbageCollection>, GcActivityAggregation> toAggregation = new ClosureIO<List<GarbageCollection>, GcActivityAggregation>() {
		public GcActivityAggregation call(List<GarbageCollection> in) {
			return new GcActivityAggregation(in);
		}
	};

	public TableModelGcActivity(JRockitJvmRun logFile, final Table table) {
		this.jvm = logFile;
		initialize(table);
	}

	@Override
	protected void getData(final Table table) {
		List<GarbageCollection> list = jvm.getGarbageCollections();
		List<GarbageCollection> youngCollections = findAll(list, new Predicate<GarbageCollection>() {
			public boolean matches(GarbageCollection item) {
				return (item instanceof YoungCollection);
			}
		});
		List<GarbageCollection> oldCollections = findAll(list, new Predicate<GarbageCollection>() {
			public boolean matches(GarbageCollection item) {
				return (item instanceof OldCollection);
			}
		});

		@SuppressWarnings("unchecked")
		List<GcActivityAggregation> aggregation = collect(
				findAll(asList(youngCollections, oldCollections), whereListIsNotEmpty), toAggregation);

		foreach(aggregation, new Closure<GcActivityAggregation>() {
			public void call(GcActivityAggregation gcAggregation) {
				new TableItem(table, SWT.NONE).setText(new String[] { gcAggregation.getName(),
						seconds(gcAggregation.getLastOccurence().getSeconds()), gcAggregation.getCount().toString(),
						seconds(gcAggregation.getAverageInterval().getSeconds()),
						seconds(gcAggregation.getAverageDuration().getSeconds()) });
			}
		});
	}

	@Override
	protected List<TableColumn> getColumns(final Table table) {
		List<TableColumn> columns = prepend(
				column(table, ""),
				collect(Arrays
						.asList(new String[] { "Last occurence", "Count", "Average interval", "Average Duration" }),
						new ClosureIO<String, TableColumn>() {
							public TableColumn call(String in) {
								return column(table, in);
							}
						}));
		return columns;
	}
}
