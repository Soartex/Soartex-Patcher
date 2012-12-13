package net.soartex.patcher;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Main {

	public static void main(String[] args) {

		//custom colors
		UIManager.put("nimbusBase", Color.BLACK);      // Base color
		UIManager.put("nimbusBlueGrey", Color.GRAY);  // Secondary Color
		UIManager.put("control", Color.DARK_GRAY);         // Control
		
		//load new skin
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available
		    try {
		        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		    } catch (Exception ex) {
		        e.printStackTrace();
		    }
		}

		//Start making the real program
		Soartex_Patcher patcher = new Soartex_Patcher();

		//make frame to display things in
		patcher.initializeWindow();

		//get all data from the Internet and display waiting screen
		patcher.downloadHttpFiles();
		
		//Construct all components
		patcher.initializeComponents();

		//display the window
		patcher.showWindow();
	}

}
