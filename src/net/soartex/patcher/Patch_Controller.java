package net.soartex.patcher;

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
		System.out.println("Downloading Mods");
		System.out.println("==================");
		downloadModTextures();

		//updateProgress(10, 25);

		System.out.println("==================");
		System.out.println("Extracting Main Zip");
		System.out.println("==================");
		extractTexturePack();

		//updateProgress(25, 35);

		System.out.println("==================");
		System.out.println("Compiling New Modlist");
		System.out.println("==================");
		makeNewCSVFile();

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
		// TODO Auto-generated method stub
		
	}

	private void extractTexturePack() {
		// TODO Auto-generated method stub
		
	}

	private void makeNewCSVFile() {
		// TODO Auto-generated method stub
		
	}
	
	private void extractModTextures() {
		// TODO Auto-generated method stub
		
	}

	private void compressPatchedFiles() {
		// TODO Auto-generated method stub
		
	}
}
