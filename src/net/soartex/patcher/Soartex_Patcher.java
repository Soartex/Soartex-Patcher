package net.soartex.patcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.concurrent.TimeUnit;

import java.util.prefs.Preferences;

import net.soartex.patcher.helpers.AppZip;
import net.soartex.patcher.helpers.UnZip;
import net.soartex.patcher.listeners.PrimaryListener;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class Soartex_Patcher {

	// TODO: Program Variables

	private static final Preferences prefsnode = Preferences.userNodeForPackage(Soartex_Patcher.class).node(Strings.Common.SOARTEX_PATCHER);

	private static URL tabledata;

	private static HashMap<TableItem, String> moddatamap;

	public static Languages language = Languages.English;

	private static File texturepack = null;

	// TODO: SWT Components

	private static Display display;
	private static Shell shell;

	private static Button technic;
	private static Button ftb;
	private static Button all;
	private static Button none;

	public static Table table;

	public static TableColumn name;
	public static TableColumn version;
	public static TableColumn gameversion;
	public static TableColumn size;
	public static TableColumn modified;

	private static Button primary;

	private static Text path;
	private static Button browse;

	public static Button patch;

	private static ProgressBar progress;

	// TODO: Menu Items

	// TODO: Methods

	public static void main (final String[] args) {

		initializeShell();

		initializeComponents();

		loadIcon();

		startEventLoop();

	}

	private static void initializeShell () {


		display = Display.getDefault();

		shell = new Shell(display);

		shell.setText(Strings.Common.SOARTEX_PATCHER);

		shell.setLocation(prefsnode.getInt(Strings.Common.PREF_X, 100), prefsnode.getInt(Strings.Common.PREF_Y, 100));
		shell.setSize(prefsnode.getInt(Strings.Common.PREF_WIDTH, 500), prefsnode.getInt(Strings.Common.PREF_HEIGHT, 300));

		if (prefsnode.getBoolean(Strings.Common.PREF_MAX, false)) shell.setMaximized(true);

		shell.addListener(SWT.Close, new ExitListener());

		shell.setLayout(new GridLayout(4, true));

	}

	private static void initializeComponents () {

		// TODO: Primary Pack Check Box

		primary = new Button(shell, SWT.BUTTON1);
		primary.setText("Download The Primary Pack From Our Website To Patch");
		primary.addSelectionListener(new PrimaryListener());

		GridData gd = new GridData();
		gd = new GridData();
		gd.horizontalSpan = 4;
		gd.verticalSpan = 4;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = false;
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;

		primary.setLayoutData(gd);

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

		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;

		technic.setLayoutData(gd);
		ftb.setLayoutData(gd);
		all.setLayoutData(gd);
		none.setLayoutData(gd);


		// TODO: Mod Table

		table = new Table(shell, SWT.BORDER | SWT.CHECK);

		name = new TableColumn(table, SWT.CENTER);
		version = new TableColumn(table, SWT.CENTER);
		gameversion = new TableColumn(table, SWT.CENTER);
		size = new TableColumn(table, SWT.CENTER);
		modified = new TableColumn(table, SWT.CENTER);

		name.setText(getString(StringNames.NAME_COLUMN));
		version.setText(getString(StringNames.VERSION_COLUMN));
		gameversion.setText(getString(StringNames.GAMEVERSION_COLUMN));
		size.setText(getString(StringNames.SIZE_COLUMN));
		modified.setText(getString(StringNames.MODIFIED_COLUMN));

		table.setHeaderVisible(true);

		loadTable();

		name.pack();
		gameversion.pack();
		version.pack();
		size.pack();
		modified.pack();

		gd = new GridData();
		gd.horizontalSpan = 5;
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


	private static void loadTable () {
		final ProgressDialog progressDialog = new ProgressDialog(shell);
		progressDialog.open();
	}

	public static void loadTableInfo (final ArrayList<String[]> a, final ArrayList<String> b) {

		display.syncExec(new Runnable() {
			@Override public void run () {
				for(int i=0; i<a.size();i++){
					final TableItem item = new TableItem(table, SWT.NONE);
					item.setText(a.get(i));
					moddatamap.put(item, b.get(i));
				}
				name.pack();
				gameversion.pack();
				version.pack();
				size.pack();
				modified.pack();
			}});
	}

	private static void loadIcon () {

		try {

			final Image i = new Image(display, Soartex_Patcher.class.getClassLoader().getResourceAsStream(Strings.Common.ICON_NAME));

			shell.setImage(i);

		} catch (final SWTException | IllegalArgumentException e) {

			try {

				final FileInputStream in = new FileInputStream(Strings.Common.ICON_NAME);

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

	public static String getString (final StringNames name) {

		try {

			return Strings.getString(name, language);

		} catch (final Exception e) {

			e.printStackTrace();

			return Strings.Common.SPACE;

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

				try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(Strings.Common.MODDED_URL + Strings.Common.TECHNIC_LIST).openStream()))) {

					for (final TableItem item : table.getItems()) {

						item.setChecked(false);

					}

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

			} else if (e.widget == ftb) {

				try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(Strings.Common.MODDED_URL + Strings.Common.FTB_LIST).openStream()))) {

					for (final TableItem item : table.getItems()) {

						item.setChecked(false);

					}

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

			//final FileDialog dialog = new FileDialog(shell, primary.getSelection() ? SWT.SAVE : SWT.OPEN);
			final FileDialog dialog = new FileDialog(shell, SWT.OPEN);

			dialog.setFilterNames(new String[] { getString(StringNames.ZIP_FILES) });
			dialog.setFilterExtensions(new String[] { Strings.Common.ZIP_FILES_EXT });
			dialog.setFilterPath(System.getProperty(Strings.Common.getMinecraftDir()));

			final String selectedfile = dialog.open();

			if (selectedfile != null) {

				path.setText(selectedfile);
				patch.setEnabled(true);

				texturepack = new File(selectedfile);

			}

			/*if (!primary.getSelection() && !texturepack.exists()) {

				path.setText("");

				texturepack = null;

				patch.setEnabled(false);

			}*/

		}

		@Override public void widgetDefaultSelected (final SelectionEvent e) {}

	}

	private static final class PatchListener implements SelectionListener, Runnable {

		private static int count = 0;

		private static boolean[] checked;
		private static String[] moddata;

		//private static boolean primary;

		@Override public void run () {
			new Thread(new Runnable() {

				@Override public void run () { 
					setAll(false);
					updateProgress(0, 10);

					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (final InterruptedException e) {
						e.printStackTrace();
					}

					/*display.syncExec(new Runnable() {
						@Override public void run () {
							primary = Soartex_Patcher.primary.getSelection();
						}});*/


					System.out.println("==================");
					System.out.println("Downloading Mods");
					System.out.println("==================");
					downloadModTextures();

					updateProgress(10, 25);

					/*if (primary){
					    System.out.println("==================");
						System.out.println("Downloading Primary");
						System.out.println("==================");
						downloadTexturePack();
					}*/

					updateProgress(25, 35);
					
					
					System.out.println("==================");
					System.out.println("Extracting Main Zip");
					System.out.println("==================");
					extractTexturePack();
					
					updateProgress(35, 60);

					System.out.println("==================");
					System.out.println("Extracting Mods");
					System.out.println("==================");
					extractModTextures();
					
					updateProgress(60, 75);
					
					System.out.println("==================");
					System.out.println("Compiling....");
					System.out.println("==================");
					compressPatchedFiles();

					updateProgress(75, 100);
					
					System.out.println("==================");
					System.out.println("Done!");
					System.out.println("==================");
					setAll(true);

					try {

						TimeUnit.SECONDS.sleep(2);

					} catch (final InterruptedException e) {

						e.printStackTrace();

					} finally {

						updateProgress(0, 0);

					}
				}

			}).start();
		}

		@Override public void widgetSelected (final SelectionEvent e) {

			new Thread(this).start();

		}

		@Override public void widgetDefaultSelected (final SelectionEvent e) {}

		private static void downloadModTextures () {

			final byte[] buffer = new byte[1048576];

			new File(Strings.Common.TEMPORARY_DATA_LOCATION_A).mkdirs();
			new File(Strings.Common.TEMPORARY_DATA_LOCATION_A).deleteOnExit();

			display.syncExec(new Runnable() {

				@Override public void run () {

					count = 0;
					checked = new boolean[table.getItems().length];
					moddata = new String[table.getItems().length];

					for (final TableItem item : table.getItems()) {

						checked[count++] = item.getChecked();

					}

					count = 0;

					for (final TableItem item : table.getItems()) {

						moddata[count++] = moddatamap.get(item);

					}


					count = 0;
					for (final boolean ischecked: checked) {

						if (ischecked) {

							try (InputStream in = new URL(moddata[count]).openStream()) {

								System.out.println("Downloading: "+moddata[count]);

								final File destinationFile = new File(Strings.Common.TEMPORARY_DATA_LOCATION_A + File.separator + new File(moddata[count]).getName()).getAbsoluteFile();
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

						count++;

					}

					count = 0;
				}

			});
		}

		private static void extractModTextures () {
			new File(Strings.Common.TEMPORARY_DATA_LOCATION_B).deleteOnExit();
			final ArrayList<File> files = new ArrayList<>();
			getFiles(new File(Strings.Common.TEMPORARY_DATA_LOCATION_A), files);

			for (final File file : files) {
				//debug
				System.out.print("Extracting: "+file.getName());
				UnZip.unZipIt(file.getAbsolutePath(), Strings.Common.TEMPORARY_DATA_LOCATION_B);				
			}
			delete(new File(Strings.Common.TEMPORARY_DATA_LOCATION_A));
		}

		/*private static void downloadTexturePack () {

			final byte[] buffer = new byte[1048576];

			new File(Strings.Common.TEMPORARY_DATA_LOCATION_A).mkdirs();
			new File(Strings.Common.TEMPORARY_DATA_LOCATION_A).deleteOnExit();

			try (InputStream in = new URL("http://www.soartex.net/Soartex%20Fanver.zip").openStream()) {

				final File destinationFile = texturepack.getAbsoluteFile();
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

		}*/

		private static void extractTexturePack () {
			UnZip.unZipIt(texturepack.getAbsolutePath(), Strings.Common.TEMPORARY_DATA_LOCATION_B);
		}


		private static void compressPatchedFiles () {
			AppZip.makeList(new File(Strings.Common.TEMPORARY_DATA_LOCATION_B));
			AppZip.zipIt(texturepack.getAbsolutePath());
			delete(new File(Strings.Common.TEMPORARY_DATA_LOCATION_B));
		}

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

		private static void setAll (final boolean b) {

			display.asyncExec(new Runnable() {

				@Override public void run () {

					technic.setEnabled(b);
					ftb.setEnabled(b);
					all.setEnabled(b);
					none.setEnabled(b);
					path.setEnabled(b);
					browse.setEnabled(b);
					patch.setEnabled(b);
					table.setEnabled(b);

				}

			});

		}

		private static void updateProgress (final int from, final int to) {

			display.asyncExec(new Runnable() {

				@Override public void run () {

					for (int i = from ; i < to ; i++) {

						progress.setSelection(i);

						try { TimeUnit.MILLISECONDS.sleep(5); } catch (final InterruptedException e1) {}

					}

					progress.setSelection(to);

				}

			});

		}

	}

	private static final class ExitListener implements Listener {

		@Override public void handleEvent (final Event event) {

			if (!shell.isDisposed()) {

				if (shell.getMaximized()) {

					prefsnode.putBoolean(Strings.Common.PREF_MAX, true);

				} else {

					prefsnode.putInt(Strings.Common.PREF_X, shell.getLocation().x);
					prefsnode.putInt(Strings.Common.PREF_Y, shell.getLocation().y);

					prefsnode.putInt(Strings.Common.PREF_WIDTH, shell.getSize().x);
					prefsnode.putInt(Strings.Common.PREF_HEIGHT, shell.getSize().y);

					prefsnode.putBoolean(Strings.Common.PREF_MAX, false);

				}

			}

			prefsnode.put(Strings.Common.PREF_LANG, language.toString());

			event.doit = true;

			shell.dispose();

			display.dispose();

			System.exit(0);

		}

	}

	private static final class ProgressDialog extends Dialog {

		private static String readline = null;

		private static volatile boolean done = false;

		public static Label info1;
		public static Label info2;

		private static int tempCount;

		public ProgressDialog (final Shell parent) {

			super(parent);

		}

		public void open () {

			final Shell parent = getParent();

			final Shell shell = new Shell(parent, SWT.DIALOG_TRIM);

			shell.setLayout(new GridLayout(1, false));

			shell.setText("Loading...");
			shell.addListener(SWT.Close, new ExitListener());

			GridData gd = new GridData();
			gd.horizontalAlignment = SWT.FILL;
			gd.verticalAlignment = SWT.FILL;

			final Label message = new Label(shell, SWT.NONE);
			message.setText("Please wait patiently while we compile the mods list");

			final ProgressBar progress = new ProgressBar(shell, SWT.INDETERMINATE);

			gd = new GridData();
			gd.horizontalSpan = 4;
			gd.grabExcessHorizontalSpace = true;
			gd.grabExcessVerticalSpace = true;
			gd.horizontalAlignment = SWT.FILL;
			gd.verticalAlignment = SWT.FILL;

			progress.setLayoutData(gd);

			info1 = new Label(shell, SWT.NONE);
			info1.setText(tempCount + " mods loaded");
			info2 = new Label(shell, SWT.NONE);

			gd = new GridData();
			gd.horizontalAlignment = SWT.FILL;
			gd.verticalAlignment = SWT.FILL;

			info1.setLayoutData(gd);
			info2.setLayoutData(gd);

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
			//iteminfo storage
			final ArrayList<String[]> itemsInfo = new ArrayList<String[]>();		
			final ArrayList<String> itemsInfoUrl = new ArrayList<String>();	
			try {
				tabledata = new URL(Strings.Common.MODDED_URL + Strings.Common.MOD_CSV);
				moddatamap = new HashMap<>();
				final BufferedReader in = new BufferedReader(new InputStreamReader(tabledata.openStream()));
				readline = in.readLine();

				while (readline != null) {

					final URL zipurl = new URL(Strings.Common.MODDED_URL + readline.split(Strings.Common.COMMA)[0].replace(Strings.Common.SPACE, Strings.Common.UNDERSCORE) + Strings.Common.ZIP_FILES_EXT.substring(1));
					//test to see if file is there
					try {
						zipurl.openStream();
					} catch (final IOException e) { 
						e.printStackTrace();
						readline = in.readLine();
						continue;
					}
					//add file info
					final String[] itemtext = new String[5];
					itemtext[0] = readline.split(Strings.Common.COMMA)[0];

					//debug
					System.out.println("Loading: "+itemtext[0]);

					//modversion
					try{
						itemtext[1] = readline.split(Strings.Common.COMMA)[1];
					} catch(final Exception e){
						itemtext[1] = "Unknown";
					}

					//gameversion
					try{
						itemtext[2] = readline.split(Strings.Common.COMMA)[2];
					} catch(final Exception e){
						itemtext[1] = "Unknown";
					}

					//size
					try{
						String temp= readline.split(Strings.Common.COMMA)[3];
						final long size = Integer.parseInt(temp);

						if (size > 1024 && size < 1048576 ) itemtext[3] = String.valueOf(size / 1024) + Strings.Common.KILOBYTES;
						else if (size > 1048576) itemtext[3] = String.valueOf(size / (1048576)) + Strings.Common.MEGABYTES;
						else itemtext[3] = String.valueOf(size) + Strings.Common.BYTES;
					} catch(final Exception e){
						itemtext[1] = "Unknown";
					}					

					//date modified
					try{
						itemtext[4] = readline.split(Strings.Common.COMMA)[4];
					} catch(final Exception e){
						itemtext[4] = "Unknown";
					}					

					if (readline == null) return;
					//save info
					itemsInfo.add(itemtext);
					itemsInfoUrl.add(zipurl.toString());

					display.syncExec(new Runnable() {
						@Override public void run () {
							info1.setText(tempCount++ + " mods loaded");
							info2.setText("Loading: " + readline.split(Strings.Common.COMMA)[0]);							
						}});

					readline = in.readLine();							
				}		
			} catch (final IOException e) {

				e.printStackTrace();

			} finally {

				done = true;

			}

			new Thread(new Runnable() {

				@Override public void run () { 
					display.syncExec(new Runnable() {
						@Override public void run () {
							name.setWidth(100);
							gameversion.setWidth(50);
							version.setWidth(50);
							size.setWidth(50);
							modified.setWidth(75);
							for(int i=0; i<itemsInfo.size();i++){
								final TableItem item = new TableItem(table, SWT.NONE);
								item.setText(itemsInfo.get(i));
								moddatamap.put(item, itemsInfoUrl.get(i));
								try {
									Thread.sleep(10);
									shell.update();
								} catch (InterruptedException e) {}
							}
							name.pack();
							gameversion.pack();
							version.pack();
							size.pack();
							modified.pack();
							System.out.println("=======DONE=======");
						}});
				}
			}).start();
		}
	}


}
