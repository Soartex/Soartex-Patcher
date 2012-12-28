package net.soartex.patcher.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JTable;

import net.soartex.patcher.Soartex_Patcher;

public class CellMouse implements MouseListener,MouseMotionListener{
	
	JTable table;
	public CellMouse(JTable table1){
		table=table1;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = table.rowAtPoint(e.getPoint());
	    int col = table.columnAtPoint(e.getPoint());	    
	    //if it is the checkbox colum.
	    if(col==0){
	    	Soartex_Patcher.tableData[row][0]= new Boolean(!(Boolean)Soartex_Patcher.tableData[row][0]);
			table.updateUI();
	    }		
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
