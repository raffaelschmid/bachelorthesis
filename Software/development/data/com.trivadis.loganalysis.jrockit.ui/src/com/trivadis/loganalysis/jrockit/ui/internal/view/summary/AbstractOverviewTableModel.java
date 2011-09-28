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

import java.util.List;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public abstract class AbstractOverviewTableModel {

	protected void initialize(Table table) {
		List<TableColumn> columns = getColumns(table);
		table.setRedraw(false);
		getData(table);
		table.setRedraw(true);
		for (TableColumn column : columns) {
			column.pack();
		}
	}

	protected abstract void getData(Table table);

	protected abstract List<TableColumn> getColumns(Table table);

}
