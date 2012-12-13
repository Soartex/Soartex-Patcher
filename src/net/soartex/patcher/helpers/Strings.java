package net.soartex.patcher.helpers;

import java.io.File;

public class Strings {

	// TODO: Important Strings

	public static final String GOD_URL = "http://soartex.net/patcher/data/";
	
	public static final String GOD_TABLE = "tablelocation.txt";
	public static final String GOD_ZIP = "ziplocation.txt";
	public static final String GOD_WELCOME = "welcomemsg.txt";
	

	public static final String SOARTEX_PATCHER = "Soartex Patcher";

	public static final String OS = System.getProperty("os.name").toUpperCase();

	public static final String ICON_NAME = "icon.png";

	public static final String[] COLUMN_NAMES = {"","Mod Name","Mod Version","MC Version","File Size","Date Modified"};
	
	public static final String[] MENU_DATA = {
		"Soartex Website",
		"Hide Console",
		"Show Latest Textures",
		"Soartex Modded",
		"Soartex FTB",
		"Soartex Custom",
		"Browse",
		"Patch!"};

	//these are downloaded from the interweb
	public static String WELCOME_MSG = "";
	public static String MODDED_URL = "";
	public static String MODDED_CVS = "";	
	
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

	// TODO: Preferences Keys

	public static final String PREF_WIDTH = "800";
	public static final String PREF_HEIGHT = "800";

	// TODO: Methods

	public static void setWelcomeMsgStrings(String msg){
		WELCOME_MSG=msg;
	}
	
	public static void setStrings(String moddedDir, String modCvsDir){
		MODDED_URL=moddedDir;
		MODDED_CVS=modCvsDir;
	}
	
	private static String getTMP () {

		if (OS.contains("WIN")) return System.getenv("TMP");

		else if (OS.contains("MAC") || OS.contains("DARWIN")) return System.getProperty("user.home") + "/Library/Caches/";
		else if (OS.contains("NUX")) return System.getProperty("user.home");

		return System.getProperty("user.dir");

	}

	static String getMinecraftDir () {

		if (OS.contains("WIN")) return System.getenv("APPDATA") + "\\.minecraft";

		else if (OS.contains("MAC") || OS.contains("DARWIN")) return System.getProperty("user.home") + "/Library/Application Support/minecraft";
		else if (OS.contains("NUX")) return System.getProperty("user.home");

		return System.getProperty("user.dir");


	}


}