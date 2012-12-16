package net.soartex.patcher;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import net.soartex.patcher.helpers.AppZip;
import net.soartex.patcher.helpers.Strings;
import net.soartex.patcher.helpers.UnZip;

public class Patch_Controller {

	String zipLocation;
	JLabel title;
	JProgressBar aJProgressBar;
	JFrame frame1;
	
	public Patch_Controller(String path){
		zipLocation=path;
	}

	private void makeProgressWiondow(){
		frame1 = new JFrame("Patching");
		GridLayout g1 = new GridLayout(2,1);
		frame1.setLayout(g1);
		
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aJProgressBar = new JProgressBar(JProgressBar.HORIZONTAL);
		aJProgressBar.setStringPainted(true);
		frame1.add(aJProgressBar);

		title = new JLabel("", JLabel.CENTER);
		title.setForeground(Color.white);
		frame1.add(title);
		try {
			URL url = getClass().getClassLoader().getResource(Strings.ICON_NAME);
			frame1.setIconImage(Toolkit.getDefaultToolkit().createImage(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame1.setSize(300, 80);
		frame1.setResizable(false);
		frame1.setLocationRelativeTo(Soartex_Patcher.frame);
		frame1.setVisible(true);
	}
	
	public void run(){
		
		makeProgressWiondow();
		Soartex_Patcher.frame.setEnabled(false);
		
		title.setText("Compiling New Modlist");
		aJProgressBar.setValue(0);
		System.out.println("==================");
		System.out.println("Compiling New Modlist");
		System.out.println("==================");
		makeNewCSVFile();		
		
		title.setText("Downloading Mods");
		aJProgressBar.setValue(20);
		System.out.println("==================");
		System.out.println("Downloading Mods");
		System.out.println("==================");
		downloadModTextures();

		title.setText("Extracting Main Zip");
		aJProgressBar.setValue(40);
		System.out.println("==================");
		System.out.println("Extracting Main Zip");
		System.out.println("==================");
		extractTexturePack();

		title.setText("Extracting Mods");
		aJProgressBar.setValue(60);
		System.out.println("==================");
		System.out.println("Extracting Mods");
		System.out.println("==================");
		extractModTextures();
		
		title.setText("Compiling");
		aJProgressBar.setValue(80);
		System.out.println("==================");
		System.out.println("Compiling....");
		System.out.println("==================");
		compressPatchedFiles();

		title.setText("Done!");
		aJProgressBar.setValue(100);
		System.out.println("==================");
		System.out.println("Done!");
		System.out.println("==================");
		
		frame1.setVisible(false);
		Soartex_Patcher.frame.setEnabled(true);
	}

	private void downloadModTextures() {
		final byte[] buffer = new byte[1048576];

		new File(Strings.TEMPORARY_DATA_LOCATION_A).mkdirs();
		new File(Strings.TEMPORARY_DATA_LOCATION_A).deleteOnExit();

		ArrayList<String> temp = new ArrayList<String>();
		for(int i=0; i<Soartex_Patcher.tableData.length;i++){
			if(Soartex_Patcher.tableData[i][0] != null && (Boolean)Soartex_Patcher.tableData[i][0]){
				temp.add((String) Soartex_Patcher.tableData[i][1]);
			}
		}
		for (String mod: temp) {
			try {
				String tempUrl = Strings.MODDED_URL+ mod.replace(Strings.SPACE, Strings.UNDERSCORE) + Strings.ZIP_FILES_EXT.substring(1);
				final InputStream in = new URL(tempUrl).openStream();

				System.out.println("Downloading: "+mod);

				final File destinationFile = new File(Strings.TEMPORARY_DATA_LOCATION_A + File.separator + new File(tempUrl).getName()).getAbsoluteFile();
				destinationFile.getParentFile().mkdirs();
				destinationFile.deleteOnExit();
				destinationFile.getParentFile().deleteOnExit();

				final FileOutputStream out = new FileOutputStream(destinationFile);

				int len;
				while ((len = in.read(buffer)) > -1) {
					out.write(buffer, 0, len);
				}
				out.close();

			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void extractTexturePack() {
		UnZip.unZipIt(zipLocation, Strings.TEMPORARY_DATA_LOCATION_B);		
	}

	// TODO: add this method to keep track of ods added
	private void makeNewCSVFile() {
		// TODO Auto-generated method stub

	}

	private void extractModTextures() {
		new File(Strings.TEMPORARY_DATA_LOCATION_B).deleteOnExit();
		final ArrayList<File> files = new ArrayList<File>();
		getFiles(new File(Strings.TEMPORARY_DATA_LOCATION_A), files);

		for (final File file : files) {
			//debug
			System.out.print("Extracting: "+file.getName());
			UnZip.unZipIt(file.getAbsolutePath(), Strings.TEMPORARY_DATA_LOCATION_B);
		}
		delete(new File(Strings.TEMPORARY_DATA_LOCATION_A));		
	}

	private void compressPatchedFiles() {
		AppZip.makeList(new File(Strings.TEMPORARY_DATA_LOCATION_B));
		AppZip.zipIt(zipLocation);
		delete(new File(Strings.TEMPORARY_DATA_LOCATION_B));		
	}

	// TODO: Helper Methods
	private static void getFiles (final File f, final ArrayList<File> files) {

		if (f.isFile()) return;

		final File[] afiles = f.getAbsoluteFile().listFiles();

		if (afiles == null) return;

		for (final File file : afiles) {

			if (file.isDirectory()) getFiles(file, files);

			else files.add(file.getAbsoluteFile());

		}

	}

	private static void delete (final File f) {

		f.delete();

		if (f.isFile()) return;

		final File[] files = f.getAbsoluteFile().listFiles();

		if (files == null) return;

		for (final File file : files) {

			delete(file);

			f.delete();

		}

	}
}
