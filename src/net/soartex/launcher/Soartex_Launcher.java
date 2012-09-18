package net.soartex.launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import java.net.URL;

import java.util.HashMap;
import java.util.prefs.Preferences;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import org.eclipse.swt.graphics.Image;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class Soartex_Launcher {
	
	// TODO: Program Variables

	private static final Preferences prefsnode = Preferences.userNodeForPackage(Soartex_Launcher.class).node(Strings.SOARTEX_LAUNCHER);
	
	private static URL tabledata;
	
	private static HashMap<TableItem, String> moddatamap;
	
	private static boolean debug;
	
	// TODO: SWT Components
	
	private static Display display;
	private static Shell shell;
	
	private static Table table;
	
	private static Button patch;
	
	// TODO: Methods
	
	public static void main (final String[] args) {
		
		debug = args.length > 0 ? Boolean.parseBoolean(args[0]) : false;

		initializeShell();
		
		initializeComponents();
		
		loadIcon();
		
		startEventLoop();
		
	}
	
	private static void initializeShell () {
		
		display = Display.getDefault();

		shell = new Shell(display);
		
		shell.setText(Strings.SOARTEX_LAUNCHER);
		
		shell.setLocation(prefsnode.getInt(Strings.PREF_X, 26), prefsnode.getInt(Strings.PREF_Y, 26));

		if (prefsnode.getBoolean(Strings.PREF_MAX, false)) shell.setMaximized(true);
		
		shell.addListener(SWT.Close, new ExitListener());
		
		final GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		shell.setLayout(layout);
		
	}
	
	private static void initializeComponents () {
		
		// TODO: Mod Table
		
	    table = new Table(shell, SWT.BORDER | SWT.CHECK);
	    
	    final TableColumn name = new TableColumn(table, SWT.CENTER);
	    final TableColumn size = new TableColumn(table, SWT.CENTER);
	    
	    name.setText(Strings.NAME_COLUMN);
	    size.setText(Strings.SIZE_COLUMN);
	    
	    table.setHeaderVisible(true);
	    
	    loadTable();
	    
	    name.pack();
	    size.pack();
	    
	    GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		
		table.setLayoutData(gd);
	    
	    // TODO: Patch Button
	    
	    gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
	    
	    patch = new Button(shell, SWT.PUSH);
	    
	    patch.setText(Strings.PATCH_BUTTON);
	    
	    patch.addSelectionListener(new PatchListener());
		
	    patch.setLayoutData(gd);
	    
	}
	
	private static void loadTable () {
		
		try {
			
			tabledata = new URL(Strings.TABLE_DATA_URL);
			
			moddatamap = new HashMap<>();
			
			final BufferedReader in = new BufferedReader(debug ? new StringReader(Strings.DEBUG_TABLE) : new InputStreamReader(tabledata.openStream()));
			
			String readline = in.readLine();
			
			while (readline != null) {
				
				final TableItem item = new TableItem(table, SWT.NONE);
				
			    item.setText(new String[] { readline.split(Strings.COMMA)[0], readline.split(Strings.COMMA)[1] });
				
			    moddatamap.put(item, debug ? "C:\\Users\\redx3_000\\Downloads\\GoodMorningCraftv4.0.zip" : readline.split(Strings.COMMA)[2]);
			    
				readline = in.readLine();
				
			}
			
		} catch (final IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	private static void loadIcon () {
		
		try {

			final Image i = new Image(display, Soartex_Launcher.class.getClassLoader().getResourceAsStream(Strings.ICON_NAME));

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
		
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {

			if (!display.readAndDispatch()) display.sleep();

		}

		display.dispose();
		
		System.exit(0);
		
	}
	
	// TODO: Listeners
	
	private static final class PatchListener implements SelectionListener {

		@Override public void widgetSelected (final SelectionEvent e) {
			
			final byte[] buffer = new byte[1048576];
			
			new File(Strings.TEMPORARY_DATA_LOCATION).deleteOnExit();
			
			for (final TableItem item : table.getItems()) {
				
				if (item.getChecked()) {
					
					try (ZipInputStream in = new ZipInputStream(debug ? new FileInputStream(moddatamap.get(item)) : new URL(moddatamap.get(item)).openStream());) {
						
						ZipEntry zipentry = in.getNextEntry();
						
						while (zipentry != null) {

							final String entryName = zipentry.getName();

							final File destinationFile = new File(Strings.TEMPORARY_DATA_LOCATION + File.separator + entryName).getAbsoluteFile();
							destinationFile.getParentFile().mkdirs();
							destinationFile.deleteOnExit();
							destinationFile.getParentFile().deleteOnExit();
							
							if (zipentry.isDirectory()) {
								
								zipentry = in.getNextEntry();
								
								continue;
								
							}
							
							System.out.println(destinationFile);

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
			
		}

		@Override public void widgetDefaultSelected (final SelectionEvent e) {}
		
	}
	
	private static final class ExitListener implements Listener {

		@Override public void handleEvent (final Event event) {
			
			if (shell.getMaximized()) {

				prefsnode.putBoolean(Strings.PREF_MAX, true);

			} else {

				prefsnode.putInt(Strings.PREF_X, shell.getLocation().x);
				prefsnode.putInt(Strings.PREF_Y, shell.getLocation().y);

				prefsnode.putBoolean(Strings.PREF_MAX, false);

			}
			
			event.doit = true;
			
			shell.dispose();
			
		}
		
	}
	
	// TODO: Strings
	
	private static final class Strings {
		
		private static final String SOARTEX_LAUNCHER = "Soartex Launcher";
		
		private static final String OS = System.getProperty("os.name").toUpperCase();
		
		private static final String PREF_X = "x";
		private static final String PREF_Y = "y";
		
		private static final String PREF_MAX = "maximized";
		
		private static final String ICON_NAME = "icon.png";
		
		private static final String NAME_COLUMN = "Name";
		private static final String SIZE_COLUMN = "Size";
		
		private static final String TABLE_DATA_URL = "http://www.soartex.net/moddedpack/tabledata.csv";
		
		private static final String DEBUG_TABLE = "Industrial Craft 2,12345 mb,http://www.soartex.net/moddedpack/ic2.zip" + System.lineSeparator() + "Red Power 2,54321 mb,http://www.soartex.net/moddedpack/rp2.zip";
		
		private static final String COMMA = ",";
		
		private static final String PATCH_BUTTON = "Patch!";
		
		public static final String TEMPORARY_DATA_LOCATION = getTMP() + File.separator + ".Soartex_Launcher";;
		
		private static String getTMP () {

			if (OS.contains("WIN")) return System.getenv("TMP");

			else if (OS.contains("MAC") || OS.contains("DARWIN")) return System.getProperty("user.home") + "/Library/Caches/";
			else if (OS.contains("NUX")) return System.getProperty("user.home");

			return System.getProperty("user.dir");

		}
		
	}

}
