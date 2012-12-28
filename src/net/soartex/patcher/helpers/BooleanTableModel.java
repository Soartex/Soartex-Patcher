package net.soartex.patcher.helpers;

import javax.swing.table.AbstractTableModel;


public class BooleanTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 3299794889949477876L;
	Object[][]data;
	
	public BooleanTableModel(Object[][] temp){
		data=temp;
	}

	public int getRowCount() {
		return data.length;
	}

	public int getColumnCount() {
		return Strings.COLUMN_NAMES.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

	@Override
	public String getColumnName(int column) {
		return Strings.COLUMN_NAMES[column];
	}

	//
	// This method is used by the JTable to define the default
	// renderer or editor for each cell. For example if you have
	// a boolean data it will be rendered as a check box. A
	// number value is right aligned.
	//
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return data[0][columnIndex].getClass();
	}

}
