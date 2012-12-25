package net.soartex.patcher.helpers;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class HighlightCell extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> rows;
	private Color rowColor;
	
	public HighlightCell(ArrayList<Integer> arrayList, Color c){
		rows=arrayList;
		rowColor=c;
	}
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        // everything as usual
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // added behavior
        for(int i: rows){
	        if(row == i) {
	            // this will customize that kind of border that will be use to highlight a row
	            setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, rowColor));
	        }
        }
        return this;
    }
}