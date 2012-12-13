package net.soartex.patcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import net.soartex.patcher.helpers.AppZip;
import net.soartex.patcher.helpers.Strings;
import net.soartex.patcher.helpers.UnZip;

public class Patch_Controller {

	String zipLocation;

	//final URL zipurl = 
	//new URL(Strings.MODDED_URL + readline.split(Strings.COMMA)[0].replace(Strings.SPACE, Strings.UNDERSCORE) + 
	//Strings.ZIP_FILES_EXT.substring(1));


	public Patch_Controller(String path){
		zipLocation=path;
	}

	public void run(){
		System.out.println("==================");
		System.out.println("Compiling New Modlist");
		System.out.println("==================");
		makeNewCSVFile();

		//updateProgress(10, 25);

		System.out.println("==================");
		System.out.println("Downloading Mods");
		System.out.println("==================");
		downloadModTextures();

		//updateProgress(25, 35);

		System.out.println("==================");
		System.out.println("Extracting Main Zip");
		System.out.println("==================");
		extractTexturePack();

		//updateProgress(35, 60);

		System.out.println("==================");
		System.out.println("Extracting Mods");
		System.out.println("==================");
		extractModTextures();

		//updateProgress(60, 75);

		System.out.println("==================");
		System.out.println("Compiling....");
		System.out.println("==================");
		compressPatchedFiles();

		//updateProgress(75, 100);

		System.out.println("==================");
		System.out.println("Done!");
		System.out.println("==================");
	}

	private void downloadModTextures() {
		final byte[] buffer = new byte[1048576];

		new File(Strings.TEMPORARY_DATA_LOCATION_A).mkdirs();
		new File(Strings.TEMPORARY_DATA_LOCATION_A).deleteOnExit();

		for (String mod: Soartex_Patcher.getCheckedMods()) {
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
