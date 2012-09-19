package net.soartex.launcher;

import java.io.File;

import java.lang.reflect.Field;

class Strings {
	
	// TODO: Important Strings
	
	static final String SOARTEX_LAUNCHER = "Soartex Launcher";
	
	static final String OS = System.getProperty("os.name").toUpperCase();
	
	static final String ICON_NAME = "icon.png";
	
	static final String TABLE_DATA_URL = "http://www.soartex.net/moddedpack/tabledata.csv";
	
	static final String DEBUG_TABLE = "Industrial Craft 2,12345 mb,http://www.soartex.net/moddedpack/ic2.zip" + System.lineSeparator() + "Red Power 2,54321 mb,http://www.soartex.net/moddedpack/rp2.zip";
	
	static final String COMMA = ",";
	
	static final String SPACE = " ";
	
	static final String TEMPORARY_DATA_LOCATION = getTMP() + File.separator + ".Soartex_Launcher";;
	
	// TODO: Preferences Keys
	
	static final String PREF_X = "x";
	static final String PREF_Y = "y";
	
	static final String PREF_WIDTH = "width";
	static final String PREF_HEIGHT = "height";
	
	static final String PREF_MAX = "maximized";
	
	static final String PREF_LANG = "language";

	static String getString (final StringNames name, final Languages language) throws Exception {
		
		Field field;
		
		try {
		
			field = Class.forName("net.soartex.launcher.Strings$" + language.toString()).getField(name.toString());
			
		} catch (final NoSuchFieldException | ClassNotFoundException e) {
			
			field = Class.forName("net.soartex.launcher.Strings$" + Languages.English.toString()).getField(name.toString());
			
		}
		
		return (String) field.get(null);
			
	}
	
	private static String getTMP () {

		if (OS.contains("WIN")) return System.getenv("TMP");

		else if (OS.contains("MAC") || OS.contains("DARWIN")) return System.getProperty("user.home") + "/Library/Caches/";
		else if (OS.contains("NUX")) return System.getProperty("user.home");

		return System.getProperty("user.dir");

	}
	
	public static final class English {
		
		public static final String NAME_COLUMN = "Name";
		public static final String SIZE_COLUMN = "Size";
		
		public static final String PATCH_BUTTON = "Patch!";
		
		public static final String LANGUAGE_ITEM = "Language";
		
		public static final String ENGLISH_ITEM = "English";
		public static final String FRENCH_ITEM = "French";
		public static final String SPANISH_ITEM = "Spanish";
		public static final String ITALIAN_ITEM = "Italian";
		public static final String GERMAN_ITEM = "German";
		public static final String HEBREW_ITEM = "Hebrew";
		public static final String ARABIC_ITEM = "Arabic";
		public static final String CHINESE_ITEM = "Chinese";
		public static final String JAPANESE_ITEM = "Japanese";

		public static final String HELP_ITEM = "Help";
		
	}
	
	public static final class French {
		
		public static final String NAME_COLUMN = "Nom";
		public static final String SIZE_COLUMN = "Taille";
		
		public static final String PATCH_BUTTON = "Réparer!";
		
		public static final String LANGUAGE_ITEM = "Language";
		
		public static final String ENGLISH_ITEM = "Anglais";
		public static final String FRENCH_ITEM = "Français";
		public static final String SPANISH_ITEM = "Espagnol";
		public static final String ITALIAN_ITEM = "Italien";
		public static final String GERMAN_ITEM = "Allemand";
		public static final String HEBREW_ITEM = "Hebrew";
		public static final String ARABIC_ITEM = "Arabe";
		public static final String CHINESE_ITEM = "Chinois";
		public static final String JAPANESE_ITEM = "Japonais";
		
		public static final String HELP_ITEM = "Assistance";
		
	}
	
	public static final class Spanish {
		
		public static final String NAME_COLUMN = "Nombre";
		public static final String SIZE_COLUMN = "Tamaño";
		
		public static final String PATCH_BUTTON = "Remendar";
		
	}
	
	public static final class Italian {
		
		public static final String NAME_COLUMN = "Nome";
		public static final String SIZE_COLUMN = "Dimensione";
		
		public static final String PATCH_BUTTON = "Toppa";
		
	}
	
	public static final class German {
		
		//public static final String NAME_COLUMN = "Nome";
		//public static final String SIZE_COLUMN = "Dimensione";
		
		//public static final String PATCH_BUTTON = "Toppa";
		
	}
	
	public static final class Arabic {
		
		public static final String NAME_COLUMN = "اسم";
		public static final String SIZE_COLUMN = "حجم";
		
		public static final String PATCH_BUTTON = "حل!";
		
		public static final String LANGUAGE_ITEM = "لغة";
		
		public static final String ENGLISH_ITEM = "الإنجليزية";
		public static final String FRENCH_ITEM = "فرنسي";
		public static final String SPANISH_ITEM = "الأسباني";
		public static final String ITALIAN_ITEM = "الإيطالي";
		public static final String GERMAN_ITEM = "ألماني";
		public static final String HEBREW_ITEM = "العبرية";
		public static final String ARABIC_ITEM = "العربية";
		public static final String CHINESE_ITEM = "الصينية";
		public static final String JAPANESE_ITEM = "اليابانية";
		
		public static final String HELP_ITEM = "مساعدة";
		
	}

}
