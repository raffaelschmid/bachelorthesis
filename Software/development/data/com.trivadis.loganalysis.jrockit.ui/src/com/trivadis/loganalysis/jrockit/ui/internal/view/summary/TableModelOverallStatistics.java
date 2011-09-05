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
import com.trivadis.loganalysis.core.domain.unit.Time;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;
import com.trivadis.loganalysis.jrockit.ui.internal.view.OverviewAbstractTableModel;

public class TableModelOverallStatistics extends OverviewAbstractTableModel {

	private final JRockitJvmRun jvm;

	public TableModelOverallStatistics(JRockitJvmRun logFile, final Table table) {
		this.jvm = logFile;
		initialize(table);
	}

	@Override
	protected void getData(final Table table) {
		Time timeSpentInGc = jvm.getTimeSpentInGc();
		Time durationOfMeasurment = jvm.getDurationOfMeasurment();
		List<Tuple> tuples = Arrays.asList(new Tuple[] {
				new Tuple("Duration of the measurment", durationOfMeasurment.toString()),
				new Tuple("Number of gc events", String.valueOf(jvm.getNumberOfGcEvents())),
				new Tuple("Time spent in gc", timeSpentInGc.toString()),
				new Tuple("Percentage of time in gc", String.valueOf(jvm.getPercentageOfTimeInGc())),
				new Tuple("Time spent in full gc", jvm.getTimeSpentInFullGc().toString()),
				new Tuple("Percentage of time in full gc", String.valueOf(jvm.getPercentageOfTimeInFullGc())) });
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
