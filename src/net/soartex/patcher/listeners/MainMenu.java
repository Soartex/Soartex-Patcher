package net.soartex.patcher.listeners;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import net.soartex.patcher.Patch_Controller;
import net.soartex.patcher.Soartex_Patcher;
import net.soartex.patcher.helpers.HighlightCell;
import net.soartex.patcher.helpers.Strings;
import net.soartex.patcher.helpers.UnZip;

public class MainMenu implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(Strings.MENU_DATA[0])){
			try {
				Desktop.getDesktop().browse(new URI( "http://soartex.net/"));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getActionCommand().equals(Strings.MENU_DATA[1])){
			HighlightCell temp = new HighlightCell(new ArrayList<Integer>(), Color.black);
			Soartex_Patcher.table.getColumnModel().getColumn(1).setCellRenderer(temp);
			Soartex_Patcher.table.getColumnModel().getColumn(2).setCellRenderer(temp);
			Soartex_Patcher.table.getColumnModel().getColumn(3).setCellRenderer(temp);
			Soartex_Patcher.table.getColumnModel().getColumn(4).setCellRenderer(temp);
			Soartex_Patcher.table.getColumnModel().getColumn(5).setCellRenderer(temp);
			Soartex_Patcher.table.updateUI();
		}
		//show outdated textures
		else if(e.getActionCommand().equals(Strings.MENU_DATA[2])){
			Thread thread = new Thread(){
				public void run(){
					if(!Strings.MODDEDZIP_LOCATION.equals("")){
						//info for user
						final JFrame frame = new JFrame("Loading");
						frame.setLocationRelativeTo(Soartex_Patcher.frame);
						frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						GridLayout g1 = new GridLayout(3,1);
						frame.setLayout(g1);
						JLabel title = new JLabel("Please Wait We Are", JLabel.CENTER);
						title.setForeground(Color.white);
						JLabel title2 = new JLabel("Compiling Your List", JLabel.CENTER);
						title2.setForeground(Color.white);

						final JProgressBar aJProgressBar = new JProgressBar(JProgressBar.HORIZONTAL);
						aJProgressBar.setStringPainted(false);
						aJProgressBar.setIndeterminate(true);

						frame.add(title, BorderLayout.NORTH);
						frame.add(title2);
						frame.add(aJProgressBar, BorderLayout.SOUTH);
						frame.setSize(200, 100);
						frame.setResizable(false);
						frame.setFocusableWindowState(true);
						frame.setVisible(true);

						Soartex_Patcher.frame.setEnabled(false);
						//get the old mods
						getOldMods();

						//disable frame
						Soartex_Patcher.frame.setEnabled(true);
						frame.setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(Soartex_Patcher.frame,
								"Please select a valid soartex texture pack .zip",
								"Error",
								JOptionPane.ERROR_MESSAGE);
						System.err.println("Error: nonvalid .zip");

					}
				}
			};
			thread.start();

		}
		//show last updated
		else if(e.getActionCommand().equals(Strings.MENU_DATA[3])){
			ArrayList<Integer> rows = new ArrayList<Integer>();
			Calendar twoWeeks = Calendar.getInstance(); // current date/time
			twoWeeks.add(Calendar.DATE, -14);

			for(int i=0; i<Soartex_Patcher.tableData.length;i++){

				//convert date and compare
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyy");  
				try {
					Date modDate = formatter.parse((String)Soartex_Patcher.tableData[i][5] );
					if(modDate.after(twoWeeks.getTime())){
						rows.add(i);
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}

			HighlightCell tableRender = new HighlightCell(rows, Color.red);
			Soartex_Patcher.table.getColumnModel().getColumn(1).setCellRenderer(tableRender);
			Soartex_Patcher.table.updateUI();
		}
		//browse button
		else if(e.getActionCommand().equals(Strings.MENU_DATA[4])){
			JFileChooser fc = new JFileChooser();
			fc.setPreferredSize(new Dimension(600,600));
			int returnVal = fc.showOpenDialog(Soartex_Patcher.frame);
			if (returnVal != JFileChooser.APPROVE_OPTION) return;
			File chosenFile = fc.getSelectedFile();

			if(chosenFile.getAbsolutePath().endsWith(Strings.ZIP_FILES_EXT.substring(1))){
				Strings.setModdedZipLocation(chosenFile.getAbsolutePath());
				//show old mods if possible
				Thread thread = new Thread(){
					public void run(){
						//info for user
						System.out.println("\nFinding Installed Mod Table");
						final JFrame frame = new JFrame("Loading");
						frame.setLocationRelativeTo(Soartex_Patcher.frame);
						frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						GridLayout g1 = new GridLayout(3,1);
						frame.setLayout(g1);
						JLabel title = new JLabel("Please Wait We Are", JLabel.CENTER);
						title.setForeground(Color.white);
						JLabel title2 = new JLabel("Finding Your List", JLabel.CENTER);
						title2.setForeground(Color.white);

						final JProgressBar aJProgressBar = new JProgressBar(JProgressBar.HORIZONTAL);
						aJProgressBar.setStringPainted(false);
						aJProgressBar.setIndeterminate(true);

						frame.add(title, BorderLayout.NORTH);
						frame.add(title2);
						frame.add(aJProgressBar, BorderLayout.SOUTH);
						frame.setSize(200, 100);
						frame.setResizable(false);
						frame.setFocusableWindowState(true);
						frame.setVisible(true);

						Soartex_Patcher.frame.setEnabled(false);
						//get the old mods
						getOldMods();

						//disable frame
						Soartex_Patcher.frame.setEnabled(true);
						frame.setVisible(false);
					}
				};
				thread.start();
			}
			else{
				JOptionPane.showMessageDialog(Soartex_Patcher.frame,
						"Not a valid .zip",
						"Error",
						JOptionPane.ERROR_MESSAGE);
				System.err.println("Error: Not a valid .zip");
			}
		}
		//patch button
		else if(e.getActionCommand().equals(Strings.MENU_DATA[5])){
			Thread thread = new Thread(){
				public void run(){
					if(!Strings.MODDEDZIP_LOCATION.equals("")){
						Patch_Controller temp= new Patch_Controller(Strings.MODDEDZIP_LOCATION);
						temp.run();
					}
					else{
						JOptionPane.showMessageDialog(Soartex_Patcher.frame,
								"Your path has not been set to a valid .zip file.",
								"Error",
								JOptionPane.ERROR_MESSAGE);
						System.err.println("Error: Path not Set!");
					}
				}
			};
			thread.start();
		}
		//packs
		else if(e.getActionCommand().equals("Select All")){
			for(int i=0; i<Soartex_Patcher.tableData.length;i++){
				Soartex_Patcher.tableData[i][0]= new Boolean(true);
			}
			Soartex_Patcher.table.updateUI();
		}
		else if(e.getActionCommand().equals("Select None")){
			for(int i=0; i<Soartex_Patcher.tableData.length;i++){
				Soartex_Patcher.tableData[i][0]= new Boolean(false);
			}
			Soartex_Patcher.table.updateUI();
		}
		else{
			for(int z=0; z<Strings.PACK_URLS.size();z++){
				if(e.getActionCommand().equals(Strings.PACK_TITLES.get(z))){

					try {
						final BufferedReader in = new BufferedReader(new InputStreamReader(new URL(Strings.PACK_URLS.get(z)).openStream()));
						for(int i=0; i<Soartex_Patcher.tableData.length;i++){
							Soartex_Patcher.tableData[i][0]= new Boolean(false);
						}
						Soartex_Patcher.table.updateUI();

						String readline = in.readLine();
						while (readline != null) {
							for(int i=0; i<Soartex_Patcher.tableData.length;i++){
								try{
									if (readline.equals((String)Soartex_Patcher.tableData[i][1])){
										Soartex_Patcher.tableData[i][0] = new Boolean(true);
									}
								}
								catch (Exception e2) {
									e2.printStackTrace();
								}
							}
							readline = in.readLine();
						}

					} catch (final IOException e1) {
						e1.printStackTrace();
					}
					return;
				}
			}
		}
	}


	private void getOldMods(){
		Patch_Controller.delete(new File(Strings.TEMPORARY_DATA_LOCATION_B));
		UnZip.unZipIt(Strings.MODDEDZIP_LOCATION, Strings.TEMPORARY_DATA_LOCATION_B);	
		ArrayList<Integer> rows = new ArrayList<Integer>();
		ArrayList<Integer> rows1 = new ArrayList<Integer>();
		try {
			ArrayList<String[]> data = new ArrayList<String[]>();
			BufferedReader in = new BufferedReader(new FileReader(Strings.TEMPORARY_DATA_LOCATION_B+ File.separator +Strings.MODTABLE_EXPORT));
			String readline = in.readLine();
			while (readline != null) {

				//add file info
				String[] text = new String[2];
				text[0] = readline.split(Strings.COMMA)[0];
				text[1] = readline.split(Strings.COMMA)[1];
				data.add(text);

				readline = in.readLine();
			}
			in.close();
			//compare dates
			for(int j=0; j<data.size();j++){
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyy");  
				Date oldDate = formatter.parse(data.get(j)[1]);
				for(int i=0; i<Soartex_Patcher.tableData.length;i++){
					try {
						Date modDate = formatter.parse((String)Soartex_Patcher.tableData[i][5] );
						if(((String)Soartex_Patcher.tableData[i][1]).equals(data.get(j)[0])){
							if(modDate.after(oldDate)){
								rows.add(i);
								System.out.println("Outdated: "+data.get(j)[0]);
							}
							else{
								rows1.add(i);
							}
						}
					} catch (ParseException e1) {
						System.out.println("Program could NOT get list of installed mods!");
					}
				}
			}
		}
		catch(Exception e1){
			System.out.println("Program could NOT get list of installed mods!");
		}
		HighlightCell tableRender = new HighlightCell(rows, rows1, Color.RED, Color.ORANGE);
		Soartex_Patcher.table.getColumnModel().getColumn(1).setCellRenderer(tableRender);
		Soartex_Patcher.table.updateUI();
		Patch_Controller.delete(new File(Strings.TEMPORARY_DATA_LOCATION_B));
	}
}
