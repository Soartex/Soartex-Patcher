package net.soartex.patcher.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import net.soartex.patcher.Soartex_Patcher;
import net.soartex.patcher.helpers.Strings;

public class Packs implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		//packs
		if(e.getActionCommand().equals("Select All")){
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
		//other packs that are download from the web
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
