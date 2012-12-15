package net.soartex.patcher.helpers;

import java.io.File;
import java.util.ArrayList;

public class Strings {

	// TODO: Important Strings

	public static final String GOD_URL = "http://soartex.net/patcher/data/";
	
	public static final String GOD_TABLE = "tablelocation.txt";
	public static final String GOD_ZIP = "ziplocation.txt";
	public static final String GOD_PACKS = "packlocations.txt";
	public static final String GOD_UPDATE = "patcherversions.txt";
	public static final String GOD_WELCOME = "welcomemsg.txt";
	
	public static final String SOARTEX_PATCHER = "Soartex Patcher";
	public static final String OS = System.getProperty("os.name").toUpperCase();
	public static final String ICON_NAME = "icon.png";

	//menu stuff
	public static final String[] COLUMN_NAMES = {"","Mod Name","Mod Version","MC Version","File Size","Date Modified"};
	
	public static final String[] MENU_DATA = {
		"Soartex Website",
		"Hide Console",
		"Show Latest Textures",
		"Browse",
		"Patch!"};

	//these are downloaded from the interweb
	public static String WELCOME_MSG = "";
	public static String MODDED_URL = "";
	public static String MODDED_CVS = "";	
	public static String PACKS_CVS = "";	
	
	//global files
	public static String MODDEDZIP_LOCATION = "";
	public static ArrayList<String> PACK_TITLES = new ArrayList<String>();
	public static ArrayList<String> PACK_URLS = new ArrayList<String>();
	
	//Final Variables
	public static final String COMMA = ",";
	public static final String SPACE = " ";
	public static final String UNDERSCORE = "_";

	public static final String BYTES = " bytes";
	public static final String KILOBYTES = " kilobytes";
	public static final String MEGABYTES = " megabytes";

	public static final String DATE_FORMAT = "MM/dd/yyyy";

	public static final String TEMPORARY_DATA_LOCATION_A = getTMP() + File.separator + ".Soartex_Launcher_A";
	public static final String TEMPORARY_DATA_LOCATION_B = getTMP() + File.separator + ".Soartex_Launcher_B";

	public static final String ZIP_FILES_EXT = "*.zip";

	// TODO: Preferences
	public static final String PREF_WIDTH = "800";
	public static final String PREF_HEIGHT = "800";

	// TODO: Methods
	public static void setWelcomeMsgStrings(String msg){
		WELCOME_MSG=msg;
	}
	
	public static void setModdedZipLocation(String loc){
		MODDEDZIP_LOCATION=loc;
	}
	
	public static void setUrlStrings(String moddedDir, String modCvsDir, String packsUrl){
		MODDED_URL=moddedDir;
		MODDED_CVS=modCvsDir;
		PACKS_CVS=packsUrl;
	}
	
	public static void setPackData(ArrayList<String> temp, ArrayList<String> temp2){
		PACK_TITLES=temp;
		PACK_URLS=temp2;
	}
	
	private static String getTMP () {

		if (OS.contains("WIN")) return System.getenv("TMP");

		else if (OS.contains("MAC") || OS.contains("DARWIN")) return System.getProperty("user.home") + "/Library/Caches/";
		else if (OS.contains("NUX")) return System.getProperty("user.home");

		return System.getProperty("user.dir");

	}
}