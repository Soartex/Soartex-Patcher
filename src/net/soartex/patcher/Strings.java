package net.soartex.patcher;

import java.io.File;

import java.lang.reflect.Field;

class Strings {
	
	// TODO: Important Strings
	
	static final String SOARTEX_PATCHER = "Soartex Patcher";
	
	static final String OS = System.getProperty("os.name").toUpperCase();
	
	static final String ICON_NAME = "icon.png";
	
	static final String TABLE_DATA_URL = "http://www.soartex.net/moddedpack/tabledata.csv";
	
	static final String DEBUG_TABLE = "Industrial Craft 2,12345 mb,http://www.soartex.net/moddedpack/ic2.zip" + System.lineSeparator() + "Red Power 2,54321 mb,http://www.soartex.net/moddedpack/rp2.zip";
	
	static final String COMMA = ",";
	
	static final String SPACE = " ";
	
	static final String TEMPORARY_DATA_LOCATION = getTMP() + File.separator + ".Soartex_Launcher";;
	
	static final String ZIP_FILES_EXT = "*.zip";
	
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
		
			field = Class.forName("net.soartex.patcher.Strings$" + language.toString()).getField(name.toString());
			
		} catch (final NoSuchFieldException | ClassNotFoundException e) {
			
			field = Class.forName("net.soartex.patcher.Strings$" + Languages.English.toString()).getField(name.toString());
			
		}
		
		return (String) field.get(null);
			
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
	
	public static final class English {
		
		public static final String TECHNIC_BUTTON = "Technic";
		public static final String YOGSBOX_BUTTON = "Yogsbox";
		public static final String ALL_BUTTON = "Select All";
		public static final String NONE_BUTTON = "Select None";
		
		public static final String NAME_COLUMN = "Name";
		public static final String SIZE_COLUMN = "Size";
		
		public static final String BROWSE_BUTTON = "Browse";
		
		public static final String ZIP_FILES = "Texture Packs (*.zip)";
		
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
		
		public static final String LANGUAGE_ITEM = "Idioma";
		
		public static final String ENGLISH_ITEM = "Inglés";
		public static final String FRENCH_ITEM = "Francés";
		public static final String SPANISH_ITEM = "Español";
		public static final String ITALIAN_ITEM = "Italiano";
		public static final String GERMAN_ITEM = "Alemán";
		public static final String HEBREW_ITEM = "Hebrew";
		public static final String ARABIC_ITEM = "Arabe";
		public static final String CHINESE_ITEM = "Chino";
		public static final String JAPANESE_ITEM = "Japonés";
		
		public static final String HELP_ITEM = "Ayuda";
		
	}
	
	public static final class Italian {
		
		public static final String NAME_COLUMN = "Nome";
		public static final String SIZE_COLUMN = "Dimensione";
		
		public static final String PATCH_BUTTON = "Toppa";
		
		public static final String LANGUAGE_ITEM = "Lingua";
		
		public static final String ENGLISH_ITEM = "Inglese";
		public static final String FRENCH_ITEM = "Francese";
		public static final String SPANISH_ITEM = "Spagnolo";
		public static final String ITALIAN_ITEM = "Italiano";
		public static final String GERMAN_ITEM = "Tedesco";
		public static final String HEBREW_ITEM = "Ebraeo";
		public static final String ARABIC_ITEM = "Arabo";
		public static final String CHINESE_ITEM = "Cinese";
		public static final String JAPANESE_ITEM = "Giapponese";

		public static final String HELP_ITEM = "Aiuto";
		
	}
	
	public static final class German {
		
		public static final String NAME_COLUMN = "Name";
		public static final String SIZE_COLUMN = "Größe";
		
		public static final String PATCH_BUTTON = "Flicken";
		
		public static final String LANGUAGE_ITEM = "Sprache";
		
		public static final String ENGLISH_ITEM = "Englisch";
		public static final String FRENCH_ITEM = "Französisch";
		public static final String SPANISH_ITEM = "Spanisch";
		public static final String ITALIAN_ITEM = "Italienisch";
		public static final String GERMAN_ITEM = "Deutsch";
		public static final String HEBREW_ITEM = "Hebräisch";
		public static final String ARABIC_ITEM = "Arabisch";
		public static final String CHINESE_ITEM = "Chinese";
		public static final String JAPANESE_ITEM = "Japanisch";
		
		public static final String HELP_ITEM = "Beistand";
		
	}
	
	public static final class Hebrew {
		
		public static final String NAME_COLUMN = "ש�?";
		public static final String SIZE_COLUMN = "גודל";
		
		public static final String PATCH_BUTTON = "להטלי�?!";
		
		public static final String LANGUAGE_ITEM = "שפה";
		
		public static final String ENGLISH_ITEM = "�?נגלית";
		public static final String FRENCH_ITEM = "צרפתי";
		public static final String SPANISH_ITEM = "ספרדית";
		public static final String ITALIAN_ITEM = "�?יטלקי";
		public static final String GERMAN_ITEM = "גרמני";
		public static final String HEBREW_ITEM = "עברית";
		public static final String ARABIC_ITEM = "ערבית";
		public static final String CHINESE_ITEM = "סיני";
		public static final String JAPANESE_ITEM = "יפני";
		
		public static final String HELP_ITEM = "עזרה";
		
	}
	
	public static final class Arabic {
		
		public static final String NAME_COLUMN = "اسم";
		public static final String SIZE_COLUMN = "حجم";
		
		public static final String PATCH_BUTTON = "حل!";
		
		public static final String LANGUAGE_ITEM = "لغة";
		
		public static final String ENGLISH_ITEM = "الإنجليزية";
		public static final String FRENCH_ITEM = "�?رنسي";
		public static final String SPANISH_ITEM = "الأسباني";
		public static final String ITALIAN_ITEM = "الإيطالي";
		public static final String GERMAN_ITEM = "ألماني";
		public static final String HEBREW_ITEM = "العبرية";
		public static final String ARABIC_ITEM = "العربية";
		public static final String CHINESE_ITEM = "الصينية";
		public static final String JAPANESE_ITEM = "اليابانية";
		
		public static final String HELP_ITEM = "مساعدة";
		
	}

	public static final class Chinese {
		
		public static final String NAME_COLUMN = "�??稱";
		public static final String SIZE_COLUMN = "大�?";
		
		public static final String PATCH_BUTTON = "補�?�?";
		
		public static final String LANGUAGE_ITEM = "語";
		
		public static final String ENGLISH_ITEM = "英語";
		public static final String FRENCH_ITEM = "法國人";
		public static final String SPANISH_ITEM = "西�?�牙的";
		public static final String ITALIAN_ITEM = "�?大利的";
		public static final String GERMAN_ITEM = "德國的";
		public static final String HEBREW_ITEM = "希伯來文";
		public static final String ARABIC_ITEM = "阿拉伯語";
		public static final String CHINESE_ITEM = "中國的";
		public static final String JAPANESE_ITEM = "日本";
		
		public static final String HELP_ITEM = "�?�助";
		
	}

	public static final class Japanese {

		public static final String NAME_COLUMN = "�??�?";
		public static final String SIZE_COLUMN = "大�??�?�";
		
		public static final String PATCH_BUTTON = "パッ�?�?";
		
		public static final String LANGUAGE_ITEM = "言語";
		
		public static final String ENGLISH_ITEM = "英語";
		public static final String FRENCH_ITEM = "フランス語";
		public static final String SPANISH_ITEM = "スペイン語";
		public static final String ITALIAN_ITEM = "イタリア語";
		public static final String GERMAN_ITEM = "ドイツ語";
		public static final String HEBREW_ITEM = "ヘブライ語�?�";
		public static final String ARABIC_ITEM = "アラビア語";
		public static final String CHINESE_ITEM = "中国人";
		public static final String JAPANESE_ITEM = "日本人";
		
		public static final String HELP_ITEM = "手助�?�";
		
	}
	
}