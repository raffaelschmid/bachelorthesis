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
package com.trivadis.loganalysis.jrockit.ui.internal.view.custom;

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;
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
import com.trivadis.loganalysis.jrockit.ui.internal.view.summary.OverviewAbstractTableModel;
import com.trivadis.loganalysis.jrockit.ui.internal.view.summary.Tuple;

public class TableModelAxisSelection extends OverviewAbstractTableModel {


	public TableModelAxisSelection(final Table table) {
		initialize(table);
	}

	@Override
	protected void getData(final Table table) {

		@SuppressWarnings("unchecked")
		final List<Tuple<String, String>> tuples = asList(new Tuple<String, String>("Percentage of time in full gc",
				"adsf"));

		foreach(tuples, new ClosureI<Tuple<String, String>>() {
			public void call(final Tuple<String, String> in) {
				new TableItem(table, SWT.NONE).setText(in.toArray());
			}
		});
	}

	@Override
	protected List<TableColumn> getColumns(final Table table) {
		final List<TableColumn> columns = collect(Arrays.asList(new String[] { "X-Axis", "Y-Axis" }),
				new ClosureIO<String, TableColumn>() {
					public TableColumn call(final String in) {
						return column(table, in);
					}
				});
		return columns;
	}
}
