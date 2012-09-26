package net.soartex.patcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import org.eclipse.swt.graphics.Image;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class Soartex_Patcher {
	
	// TODO: Program Variables

	private static final Preferences prefsnode = Preferences.userNodeForPackage(Soartex_Patcher.class).node(Strings.SOARTEX_PATCHER);
	
	private static URL tabledata;
	
	private static HashMap<TableItem, String> moddatamap;
	
	private static Languages language = Languages.English;
	
	private static File texturepack;
	
	// TODO: SWT Components
	
	private static Display display;
	private static Shell shell;
	
	private static Button technic;
	private static Button ftb;
	private static Button all;
	private static Button none;
	
	private static Table table;
	
	private static TableColumn name;
	private static TableColumn version;
	private static TableColumn size;
	private static TableColumn modified;
	
	private static Text path;
	private static Button browse;
	
	private static Button patch;
	
	private static ProgressBar progress;
	
	// TODO: Menu Items

	private static Menu menubar;

	private static MenuItem languageitem;
	private static Menu languagemenu;
	
	private static MenuItem englishitem;
	private static MenuItem frenchitem;
	private static MenuItem spanishitem;
	private static MenuItem italianitem;
	private static MenuItem germanitem;
	private static MenuItem hebrewitem;
	private static MenuItem arabicitem;
	private static MenuItem chineseitem;
	private static MenuItem japaneseitem;
	
	private static MenuItem helpitem;
	
	// TODO: Methods
	
	public static void main (final String[] args) {
		
		initializeShell();
		
		initializeComponents();
		
		initializeMenus();
		
		loadIcon();
		
		startEventLoop();
		
	}
	
	private static void initializeShell () {
		
		display = Display.getDefault();

		shell = new Shell(display);
		
		shell.setText(Strings.SOARTEX_PATCHER);
		
		shell.setLocation(prefsnode.getInt(Strings.PREF_X, 100), prefsnode.getInt(Strings.PREF_Y, 100));
		shell.setSize(prefsnode.getInt(Strings.PREF_WIDTH, 500), prefsnode.getInt(Strings.PREF_HEIGHT, 300));

		if (prefsnode.getBoolean(Strings.PREF_MAX, false)) shell.setMaximized(true);
		
		shell.addListener(SWT.Close, new ExitListener());
		
		final GridLayout layout = new GridLayout(4, false);
		layout.makeColumnsEqualWidth = true;
		shell.setLayout(layout);
		
	}
	
	private static void initializeComponents () {
		
		// TODO: Selection Buttons
		
		technic = new Button(shell, SWT.PUSH);
		ftb = new Button(shell, SWT.PUSH);
		all = new Button(shell, SWT.PUSH);
		none = new Button(shell, SWT.PUSH);
		
		technic.setText(getString(StringNames.TECHNIC_BUTTON));
		ftb.setText(getString(StringNames.FTB_BUTTON));
		all.setText(getString(StringNames.ALL_BUTTON));
		none.setText(getString(StringNames.NONE_BUTTON));
		
		final SelectButtonsListener sblistener = new SelectButtonsListener();
		
		technic.addSelectionListener(sblistener);
		ftb.addSelectionListener(sblistener);
		all.addSelectionListener(sblistener);
		none.addSelectionListener(sblistener);
		
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		
		technic.setLayoutData(gd);
		ftb.setLayoutData(gd);
		all.setLayoutData(gd);
		none.setLayoutData(gd);
		
		ftb.setEnabled(false);
		
		// TODO: Mod Table
		
	    table = new Table(shell, SWT.BORDER | SWT.CHECK);
	    
	    name = new TableColumn(table, SWT.CENTER);
	    version = new TableColumn(table, SWT.CENTER);
	    size = new TableColumn(table, SWT.CENTER);
	    modified = new TableColumn(table, SWT.CENTER);
	    
	    name.setText(getString(StringNames.NAME_COLUMN));
	    version.setText(getString(StringNames.VERSION_COLUMN));
	    size.setText(getString(StringNames.SIZE_COLUMN));
	    modified.setText(getString(StringNames.MODIFIED_COLUMN));
	    
	    table.setHeaderVisible(true);
	    
	    loadTable();
	    
	    name.pack();
	    version.pack();
	    size.pack();
	    modified.pack();
	    
	    gd = new GridData();
	    gd.horizontalSpan = 4;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		
		table.setLayoutData(gd);
		
		// TODO: Path Field and Browse Button
		
		path = new Text(shell, SWT.BORDER);
		
		gd = new GridData();
	    gd.horizontalSpan = 3;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		
		path.setLayoutData(gd);
		
		browse = new Button(shell, SWT.PUSH);
		
		browse.setText(getString(StringNames.BROWSE_BUTTON));
		
		browse.addSelectionListener(new BrowseListener());
		
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		
		browse.setLayoutData(gd);
		
		// TODO: Patch Button
	    
	    patch = new Button(shell, SWT.PUSH);
	    
	    patch.setText(getString(StringNames.PATCH_BUTTON));
	    
	    patch.addSelectionListener(new PatchListener());
	    
	    gd = new GridData();
	    gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		
	    patch.setLayoutData(gd);
	    
	    patch.setEnabled(false);
	    
	    // TODO: Progress Bar
	    
	    progress = new ProgressBar(shell, SWT.NORMAL);
	    
	    progress.setLayoutData(gd);

	}
	
	private static void initializeMenus () {

		menubar = new Menu(shell, SWT.BAR);
		
		// TODO: Language
		
		languageitem = new MenuItem(menubar, SWT.CASCADE);
		languageitem.setText(getString(StringNames.LANGUAGE_ITEM));
		
		languagemenu = new Menu(menubar);
		languageitem.setMenu(languagemenu);
		
		final LanguageListener langlistener = new LanguageListener();
		
		englishitem = new MenuItem(languagemenu, SWT.RADIO);
		englishitem.setText(getString(StringNames.ENGLISH_ITEM));
		englishitem.addSelectionListener(langlistener);
		
		frenchitem = new MenuItem(languagemenu, SWT.RADIO);
		frenchitem.setText(getString(StringNames.FRENCH_ITEM));
		frenchitem.addSelectionListener(langlistener);
		
		spanishitem = new MenuItem(languagemenu, SWT.RADIO);
		spanishitem.setText(getString(StringNames.SPANISH_ITEM));
		spanishitem.addSelectionListener(langlistener);
		
		italianitem = new MenuItem(languagemenu, SWT.RADIO);
		italianitem.setText(getString(StringNames.ITALIAN_ITEM));
		italianitem.addSelectionListener(langlistener);
		
		germanitem = new MenuItem(languagemenu, SWT.RADIO);
		germanitem.setText(getString(StringNames.GERMAN_ITEM));
		germanitem.addSelectionListener(langlistener);
		
		hebrewitem = new MenuItem(languagemenu, SWT.RADIO);
		hebrewitem.setText(getString(StringNames.HEBREW_ITEM));
		hebrewitem.addSelectionListener(langlistener);
		
		arabicitem = new MenuItem(languagemenu, SWT.RADIO);
		arabicitem.setText(getString(StringNames.ARABIC_ITEM));
		arabicitem.addSelectionListener(langlistener);
		
		chineseitem = new MenuItem(languagemenu, SWT.RADIO);
		chineseitem.setText(getString(StringNames.CHINESE_ITEM));
		chineseitem.addSelectionListener(langlistener);
		
		japaneseitem = new MenuItem(languagemenu, SWT.RADIO);
		japaneseitem.setText(getString(StringNames.JAPANESE_ITEM));
		japaneseitem.addSelectionListener(langlistener);
		
		final String preflang = prefsnode.get(Strings.PREF_LANG, Languages.English.toString());
		
		if (preflang.equals(Languages.English.toString())) englishitem.setSelection(true);
		if (preflang.equals(Languages.French.toString())) frenchitem.setSelection(true);
		if (preflang.equals(Languages.Spanish.toString())) spanishitem.setSelection(true);
		if (preflang.equals(Languages.German.toString())) germanitem.setSelection(true);
		if (preflang.equals(Languages.Hebrew.toString())) hebrewitem.setSelection(true);
		if (preflang.equals(Languages.Arabic.toString())) arabicitem.setSelection(true);
		if (preflang.equals(Languages.Chinese.toString())) chineseitem.setSelection(true);
		if (preflang.equals(Languages.Japanese.toString())) japaneseitem.setSelection(true);
		
		// TODO: Help
		
		helpitem = new MenuItem(menubar, SWT.NONE);
		helpitem.setText(getString(StringNames.HELP_ITEM));
		//helpitem.addSelectionListener(new HelpListener());
		helpitem.setAccelerator(SWT.F1);
		
		langlistener.widgetSelected(null);
		
		shell.setMenuBar(menubar);
		
	}
	
	private static void loadTable () {
		
		final ProgressDialog progressDialog = new ProgressDialog(shell);
		
		progressDialog.open();
		
	}
	
	private static void loadIcon () {
		
		try {

			final Image i = new Image(display, Soartex_Patcher.class.getClassLoader().getResourceAsStream(Strings.ICON_NAME));

			shell.setImage(i);

		} catch (final SWTException | IllegalArgumentException e) {

			try {

				final FileInputStream in = new FileInputStream(Strings.ICON_NAME);

				final Image i = new Image(display, in);

				shell.setImage(i);

				in.close();

			} catch (final IOException | SWTException | IllegalArgumentException e1) {}

		}
		
	}
	
	private static void startEventLoop () {
		
		shell.open();

		while (!shell.isDisposed()) {

			if (!display.readAndDispatch()) display.sleep();

		}
		
	}
	
	private static String getString (final StringNames name) {
		
		try {
			
			return Strings.getString(name, language);
			
		} catch (final Exception e) {
			
			e.printStackTrace();
			
			return Strings.SPACE;
			
		}
		
	}
	
	// TODO: Listeners
	
	private static final class SelectButtonsListener implements SelectionListener {

		@Override public void widgetSelected (final SelectionEvent e) {
			
			if (e.widget == all || e.widget == none) {
				
				for (final TableItem item : table.getItems()) {
					
					item.setChecked(e.widget == all);
					
				}
				
			} else if (e.widget == technic) {
				
				try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(Strings.MODDED_URL + Strings.TECHNIC_LIST).openStream()))) {
					
					String readline = in.readLine();
					
					while (readline != null) {

						for (final TableItem item : table.getItems()) {
							
							if (readline.contains(item.getText())) item.setChecked(true);
							
						}
						
						readline = in.readLine();
						
					}
					
				} catch (final IOException e1) {
					
					e1.printStackTrace();
					
				}
				
			}
			
		}

		@Override public void widgetDefaultSelected (final SelectionEvent e) {}
		
	}
	
	private static final class BrowseListener implements SelectionListener {

		@Override public void widgetSelected (final SelectionEvent e) {
			
			final FileDialog dialog = new FileDialog(shell, SWT.OPEN);

			dialog.setFilterNames(new String[] { getString(StringNames.ZIP_FILES) });
			dialog.setFilterExtensions(new String[] { Strings.ZIP_FILES_EXT });
			dialog.setFilterPath(System.getProperty(Strings.getMinecraftDir()));

			final String selectedfile = dialog.open();
			
			if (selectedfile != null) {
				
				path.setText(selectedfile);
				patch.setEnabled(true);
				
				texturepack = new File(selectedfile);
				
			}
			
		}

		@Override public void widgetDefaultSelected (final SelectionEvent e) {}
		
	}
	
	private static final class PatchListener implements SelectionListener {

		@Override public void widgetSelected (final SelectionEvent e) {
			
			technic.setEnabled(false);
			//ftb.setEnabled(false);
			all.setEnabled(false);
			none.setEnabled(false);
			table.setEnabled(false);
			path.setEnabled(false);
			browse.setEnabled(false);
			patch.setEnabled(false);
			
			progress.setSelection(0);
			
			downloadModTextures();
			
			for (int i = 0 ; i < 25 ; i++) {
				
				progress.setSelection(i);
				
				try { TimeUnit.MILLISECONDS.sleep(5); } catch (final InterruptedException e1) {}
				
			}
			
			progress.setSelection(25);
			
			extractModTextures();
			
			for (int i = 25 ; i < 50 ; i++) {
				
				progress.setSelection(i);
				
				try { TimeUnit.MILLISECONDS.sleep(5); } catch (final InterruptedException e1) {}
				
			}
			
			progress.setSelection(50);
			
			extractTexturePack();
			
			for (int i = 50 ; i < 75 ; i++) {
				
				progress.setSelection(i);
				
				try { TimeUnit.MILLISECONDS.sleep(5); } catch (final InterruptedException e1) {}
				
			}
			
			progress.setSelection(75);
			
			compressPatchedFiles();
			
			for (int i = 75 ; i < 100 ; i++) {
				
				progress.setSelection(i);
				
				try { TimeUnit.MILLISECONDS.sleep(5); } catch (final InterruptedException e1) {}
				
			}
			
			progress.setSelection(100);
			
			technic.setEnabled(true);
			//ftb.setEnabled(true);
			all.setEnabled(true);
			none.setEnabled(true);
			table.setEnabled(true);
			path.setEnabled(true);
			browse.setEnabled(true);
			patch.setEnabled(true);
			
			new Thread(new Runnable() {
				
				@Override public void run () {
					
					try {
						
						TimeUnit.SECONDS.sleep(3);
						
					} catch (final InterruptedException e) {
						
						e.printStackTrace();
						
					} finally {
						
						display.asyncExec(new Runnable() {
							
							@Override public void run () {
								
								progress.setSelection(0);
								
							}
							
						});
						
					}
					
				}
				
			}).start();
			
		}

		@Override public void widgetDefaultSelected (final SelectionEvent e) {}
		
		private static void downloadModTextures () {
			
			final byte[] buffer = new byte[1048576];
			
			new File(Strings.TEMPORARY_DATA_LOCATION_A).mkdirs();
			new File(Strings.TEMPORARY_DATA_LOCATION_A).deleteOnExit();
			
			for (final TableItem item : table.getItems()) {
				
				if (item.getChecked()) {
					
					new Thread(new Runnable() {
						
						@Override public void run () {
					
							try (InputStream in = new URL(moddatamap.get(item)).openStream()) {
								
								final File destinationFile = new File(Strings.TEMPORARY_DATA_LOCATION_A + File.separator + new File(moddatamap.get(item)).getName()).getAbsoluteFile();
								destinationFile.getParentFile().mkdirs();
								destinationFile.deleteOnExit();
								destinationFile.getParentFile().deleteOnExit();
								
								final FileOutputStream out = new FileOutputStream(destinationFile);
		
								int len;
								
								while ((len = in.read(buffer)) > -1) {
		
									out.write(buffer, 0, len);
		
								}
		
								out.close();						
								
							} catch (final IOException e) {
								
								e.printStackTrace();
								
							}
					
						}
						
					}).start();
					
				}
				
			}
			
		}
		
		private static void extractModTextures () {
			
			final byte[] buffer = new byte[1048576];
			
			new File(Strings.TEMPORARY_DATA_LOCATION_B).deleteOnExit();
			
			final ArrayList<File> files = new ArrayList<>();
					
			getFiles(new File(Strings.TEMPORARY_DATA_LOCATION_A), files);
			
			for (final File file : files) {
				
				try (ZipInputStream in = new ZipInputStream(new FileInputStream(file))) {
					
					ZipEntry zipentry = in.getNextEntry();
					
					while (zipentry != null) {

						final String entryName = zipentry.getName();

						final File destinationFile = new File(Strings.TEMPORARY_DATA_LOCATION_B + File.separator + entryName).getAbsoluteFile();
						destinationFile.getParentFile().mkdirs();
						destinationFile.deleteOnExit();
						destinationFile.getParentFile().deleteOnExit();
						
						if (zipentry.isDirectory()) {
							
							zipentry = in.getNextEntry();
							
							continue;
							
						}
						
						final FileOutputStream out = new FileOutputStream(destinationFile);

						int len;
						
						while ((len = in.read(buffer)) > -1) {

							out.write(buffer, 0, len);

						}

						out.close();

						in.closeEntry();
						zipentry = in.getNextEntry();

					}
					
				} catch (final IOException e1) {

					e1.printStackTrace();
					
				}
					
			}
			
		}
		
		private static void extractTexturePack () {
			
			final byte[] buffer = new byte[1048576];
			
			try (ZipInputStream in = new ZipInputStream(new FileInputStream(texturepack));) {
				
				ZipEntry zipentry = in.getNextEntry();
				
				while (zipentry != null) {

					final String entryName = zipentry.getName();

					final File destinationFile = new File(Strings.TEMPORARY_DATA_LOCATION_B + File.separator + entryName).getAbsoluteFile();
					destinationFile.getParentFile().mkdirs();
					destinationFile.deleteOnExit();
					destinationFile.getParentFile().deleteOnExit();
					
					if (zipentry.isDirectory()) {
						
						zipentry = in.getNextEntry();
						
						continue;
						
					}
					
					final FileOutputStream out = new FileOutputStream(destinationFile);

					int len;
					
					while ((len = in.read(buffer)) > -1) {

						out.write(buffer, 0, len);

					}

					out.close();

					in.closeEntry();
					zipentry = in.getNextEntry();

				}
				
			} catch (final IOException e1) {

				e1.printStackTrace();
				
			}
			
		}
		
		private static void compressPatchedFiles () {
			
			try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(texturepack));) {
			
				final ArrayList<File> files = new ArrayList<>();
				getFiles(new File(Strings.TEMPORARY_DATA_LOCATION_B), files);

				for (final File fromfile : files) {

					final String toname = fromfile.getAbsolutePath().substring(fromfile.getAbsolutePath().lastIndexOf(Strings.TEMPORARY_DATA_LOCATION_B) + Strings.TEMPORARY_DATA_LOCATION_B.length() + 1);
	
					final FileInputStream in = new FileInputStream(fromfile);
	
					final int length = (int) fromfile.length();
	
					final byte[] buffer = new byte[length];
	
					out.putNextEntry(new ZipEntry(toname));
	
					int len;
	
					while ((len = in.read(buffer, 0, length)) > 0) {
	
						out.write(buffer, 0, len);
	
					}
	
					out.closeEntry();
	
					out.flush();
	
					in.close();
	
				}

			} catch (final IOException e) {
				
				e.printStackTrace();
				
			}
			
		}

		private static void getFiles (final File f, final ArrayList<File> files) {
			
			if (f.isFile()) return;

			final File[] afiles = f.getAbsoluteFile().listFiles();

			if (afiles == null) return;
			
			for (final File file : afiles) {
				
				System.out.println(f.getAbsolutePath());

				if (file.isDirectory()) getFiles(file, files);

				else files.add(file.getAbsoluteFile());

			}
			
		}
		
	}
	
	private static final class ExitListener implements Listener {

		@Override public void handleEvent (final Event event) {
			
			if (shell.getMaximized()) {

				prefsnode.putBoolean(Strings.PREF_MAX, true);

			} else {

				prefsnode.putInt(Strings.PREF_X, shell.getLocation().x);
				prefsnode.putInt(Strings.PREF_Y, shell.getLocation().y);
				
				prefsnode.putInt(Strings.PREF_WIDTH, shell.getSize().x);
				prefsnode.putInt(Strings.PREF_HEIGHT, shell.getSize().y);

				prefsnode.putBoolean(Strings.PREF_MAX, false);

			}
			
			prefsnode.put(Strings.PREF_LANG, language.toString());
			
			event.doit = true;
			
			shell.dispose();
			
			display.dispose();
			
			System.exit(0);
			
		}
		
	}
	
	private static final class LanguageListener implements SelectionListener {

		@Override public void widgetSelected (final SelectionEvent e) {

			if (frenchitem.getSelection()) language = Languages.French;
			else if (spanishitem.getSelection()) language = Languages.Spanish;
			else if (italianitem.getSelection()) language = Languages.Italian;
			else if (germanitem.getSelection()) language = Languages.German;
			else if (hebrewitem.getSelection()) language = Languages.Hebrew;
			else if (arabicitem.getSelection()) language = Languages.Arabic;
			else if (chineseitem.getSelection()) language = Languages.Chinese;
			else if (japaneseitem.getSelection()) language = Languages.Japanese;
			else language = Languages.English;
			
			if (language == Languages.Hebrew | language == Languages.Arabic) {
				
				menubar.setOrientation(SWT.RIGHT_TO_LEFT);
				languagemenu.setOrientation(SWT.RIGHT_TO_LEFT);
				patch.setOrientation(SWT.RIGHT_TO_LEFT);
				table.setOrientation(SWT.RIGHT_TO_LEFT);
				
			}
			
			else {
				
				menubar.setOrientation(SWT.LEFT_TO_RIGHT);
				languagemenu.setOrientation(SWT.LEFT_TO_RIGHT);
				patch.setOrientation(SWT.LEFT_TO_RIGHT);
				table.setOrientation(SWT.LEFT_TO_RIGHT);
				
			}
			
			name.setText(getString(StringNames.NAME_COLUMN));
			size.setText(getString(StringNames.SIZE_COLUMN));
			
			patch.setText(getString(StringNames.PATCH_BUTTON));
			
			languageitem.setText(getString(StringNames.LANGUAGE_ITEM));
			
			englishitem.setText(getString(StringNames.ENGLISH_ITEM));
			frenchitem.setText(getString(StringNames.FRENCH_ITEM));
			spanishitem.setText(getString(StringNames.SPANISH_ITEM));
			italianitem.setText(getString(StringNames.ITALIAN_ITEM));
			germanitem.setText(getString(StringNames.GERMAN_ITEM));
			hebrewitem.setText(getString(StringNames.HEBREW_ITEM));
			arabicitem.setText(getString(StringNames.ARABIC_ITEM));
			chineseitem.setText(getString(StringNames.CHINESE_ITEM));
			japaneseitem.setText(getString(StringNames.JAPANESE_ITEM));
			
			helpitem.setText(getString(StringNames.HELP_ITEM));
			
			name.pack();
			version.pack();
			size.pack();
			modified.pack();
			
		}

		@Override public void widgetDefaultSelected (final SelectionEvent e) {}
		
	}
	
	private static final class ProgressDialog extends Dialog {
		
		private static String readline = null;
		
		private static volatile boolean done = false;
		
		public ProgressDialog (final Shell parent) {
			
			super(parent);
			
		}

		public void open () {
			
			final Shell parent = getParent();
			
			final Shell shell = new Shell(parent, SWT.DIALOG_TRIM & ~SWT.CLOSE);
			shell.setText("Loading...");

			final ProgressBar progress = new ProgressBar(shell, SWT.INDETERMINATE);
			progress.setSize(250, 50);
			progress.setToolTipText("Please wait patiently while we compile the mods list.");
			
			shell.pack();
			shell.open();
			final Display display = parent.getDisplay();
			
			new Thread(new Runnable() {
				
				@Override public void run () { loadTable(); }
				
			}).start();
			
			while (!shell.isDisposed() && !done) {
				
				if (!display.readAndDispatch()) display.sleep();
				
			}
			
			shell.dispose();
			
		}
		
		private static void loadTable () {
			
			try {
				
				tabledata = new URL(Strings.MODDED_URL + Strings.MOD_CSV);
				
				moddatamap = new HashMap<>();
				
				final BufferedReader in = new BufferedReader(new InputStreamReader(tabledata.openStream()));
				
				readline = in.readLine();
				
				while (readline != null) {
					
					final URL zipurl = new URL(Strings.MODDED_URL + readline.split(Strings.COMMA)[0].replace(Strings.SPACE, Strings.UNDERSCORE) + Strings.ZIP_FILES_EXT.substring(1));
					
					try {
						
						zipurl.openStream();
					
					} catch (final IOException e) { 
						
						e.printStackTrace();
						
						readline = in.readLine();
						
						continue;
					
					}
					
					final String[] itemtext = new String[4];
					
					itemtext[0] = readline.split(Strings.COMMA)[0];
					
					System.out.println(itemtext[0]);
					
					itemtext[1] = readline.split(Strings.COMMA)[1];
					
					final long size = zipurl.openConnection().getContentLengthLong();
					
					if (size > 1024) itemtext[2] = String.valueOf(size / 1024) + Strings.KILOBYTES;
					
					else if (size > 1024 * 1024) itemtext[2] = String.valueOf(size / (1024 * 1024)) + Strings.MEGABYTES;
					
					else itemtext[2] = String.valueOf(size) + Strings.BYTES;
					
					itemtext[3] = new SimpleDateFormat(Strings.DATE_FORMAT).format(new Date(zipurl.openConnection().getLastModified()));
					
					display.asyncExec(new Runnable() {
						
						@Override public void run () {
							
							try {
								
								if (readline == null) return;
					
								final TableItem item = new TableItem(table, SWT.NONE);
								
							    item.setText(itemtext);
								
							    moddatamap.put(item, zipurl.toString());
							    
								readline = in.readLine();
							
							} catch (final IOException e) {
								
								e.printStackTrace();
								
							}
							
						}
					
					});
					
				}
				
			} catch (final IOException e) {
				
				e.printStackTrace();
				
			} finally {
				
				done = true;
				
			}
			
		}
		
	}

}
