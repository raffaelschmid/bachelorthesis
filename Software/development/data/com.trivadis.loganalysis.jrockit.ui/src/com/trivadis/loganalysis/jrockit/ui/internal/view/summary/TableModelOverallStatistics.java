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
import static com.trivadis.loganalysis.ui.util.FormatUtil.percentage;
import static com.trivadis.loganalysis.ui.util.FormatUtil.seconds;
import static com.trivadis.loganalysis.ui.util.TableUtil.column;
import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.trivadis.loganalysis.core.common.ClosureI;
import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.core.domain.unit.Time;
import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;

public class TableModelOverallStatistics extends OverviewAbstractTableModel {

	private final JRockitJvmRun jvm;

	public TableModelOverallStatistics(JRockitJvmRun logFile, final Table table) {
		this.jvm = logFile;
		initialize(table);
	}

	@Override
	protected void getData(final Table table) {
		Time durationOfMeasurment = jvm.getDurationOfMeasurment();

		@SuppressWarnings("unchecked")
		List<Tuple<String, String>> tuples = asList(new Tuple<String, String>("Duration of the measurment",
				seconds(durationOfMeasurment.getSeconds())),
				new Tuple<String, String>("Number of gc events", String.valueOf(jvm.getNumberOfGcEvents())),
				new Tuple<String, String>("Time spent in gc", seconds(jvm.getTimeSpentInGc().getSeconds())),
				new Tuple<String, String>("Percentage of time in gc", percentage(jvm.getPercentageOfTimeInGc())),
				new Tuple<String, String>("Time spent in full gc", seconds(jvm.getTimeSpentInFullGc().getSeconds())),
				new Tuple<String, String>("Percentage of time in full gc",
						percentage(jvm.getPercentageOfTimeInFullGc())));

		foreach(tuples, new ClosureI<Tuple<String, String>>() {
			public void call(Tuple<String, String> in) {
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
