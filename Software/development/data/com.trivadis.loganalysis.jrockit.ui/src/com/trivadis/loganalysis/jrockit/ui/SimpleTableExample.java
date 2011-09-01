package com.trivadis.loganalysis.jrockit.ui;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class SimpleTableExample {

	private List<Row> rows = Arrays.asList(new Row[] {
			new Row("Initial Capacity", "N/A0", "N/A1"), new Row("Final Capacity", "N/A0", "N/A1"),
			new Row("Peak Capacity", "N/A0", "N/A1"),
			new Row("Average Usage of Capacity", "N/A0", "N/A1"),
			new Row("Peak Usage of Capacity", "N/A0", "N/A1") });

	public void run() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Heap Capacity");
		createContents(shell);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private void createContents(Composite composite) {
		composite.setLayout(new FillLayout());
		final Table table = new Table(composite, SWT.NONE);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableColumn[] columns = new TableColumn[]{
				column(table, ""),

				column(table, "Young Collection"),

				column(table, "Keep Area"),

				column(table, "Old Collection")
		};

		fillTable(table);
		for (TableColumn column : columns) {
			column.pack();
		}
	}

	private TableColumn column(Table table, String string) {
		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText(string);
		return column;
	}

	private void fillTable(Table table) {
		table.setRedraw(false);

		for (Row player : rows) {
			TableItem item = new TableItem(table, SWT.NONE);
			int c = 0;
			item.setText(c, player.getValueAt(c++));
			item.setText(c, player.getValueAt(c++));
			item.setText(c, player.getValueAt(c++));
		}
		table.setRedraw(true);
	}

	public static void main(String[] args) {
		new SimpleTableExample().run();
	}
}

class Row {
	private String[] values;

	public Row(String... values) {
		this.values = values;
	}

	public String getValueAt(int index) {
		return this.values[index];
	}
}