package net.soartex.patcher.listeners;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.soartex.patcher.Patch_Controller;
import net.soartex.patcher.Soartex_Patcher;
import net.soartex.patcher.helpers.Strings;

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
		//show outdated textures
		else if(e.getActionCommand().equals(Strings.MENU_DATA[1])){
			System.out.println("This feature is not implemented yet");
			//needs to unzip the texture pack
			//look for the mods.cvs
			//read in and compare to mods on the table.
			//if they match see if it is outdated
			//if it is outdated highlight it
		}
		//show last updated
		else if(e.getActionCommand().equals(Strings.MENU_DATA[2])){
			System.out.println("This feature is not implemented yet");
			//get date modify from each mod
			//compare to a date from 2 weeks ago
			//if it has been updated, then highlight it
		}
		//browse button
		else if(e.getActionCommand().equals(Strings.MENU_DATA[3])){
			JFileChooser fc = new JFileChooser();
			fc.setPreferredSize(new Dimension(600,600));
			int returnVal = fc.showOpenDialog(Soartex_Patcher.frame);
			if (returnVal != JFileChooser.APPROVE_OPTION) return;
			File chosenFile = fc.getSelectedFile();

			if(chosenFile.getAbsolutePath().endsWith(Strings.ZIP_FILES_EXT.substring(1))){
				Strings.setModdedZipLocation(chosenFile.getAbsolutePath());
			}
			else{
				// TODO: show error dialog
				System.out.println("==================");
				System.out.println("Error: Not a valid file!");
				System.out.println("==================");
			}
		}
		//patch button
		else if(e.getActionCommand().equals(Strings.MENU_DATA[4])){
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

}
