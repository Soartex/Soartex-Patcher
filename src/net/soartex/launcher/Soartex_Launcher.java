package net.soartex.launcher;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.prefs.Preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
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
	
	// TODO: SWT Components
	
	private static Display display;
	private static Shell shell;
	
	private static Table table;
	
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
		
		shell.setText(Strings.SOARTEX_LAUNCHER);
		
		shell.setLocation(prefsnode.getInt(Strings.PREF_X, 26), prefsnode.getInt(Strings.PREF_Y, 26));
		shell.setSize(prefsnode.getInt(Strings.PREF_WIDTH, 960), prefsnode.getInt(Strings.PREF_HEIGHT, 717));

		if (prefsnode.getBoolean(Strings.PREF_MAX, false)) shell.setMaximized(true);
		
		shell.addListener(SWT.Close, new ExitListener());
		
		shell.setLayout(new FillLayout());
		
	}
	
	private static void initializeComponents () {
		
	    table = new Table(shell, SWT.BORDER | SWT.CHECK);
	    
	    TableColumn name = new TableColumn(table, SWT.CENTER);
	    TableColumn size = new TableColumn(table, SWT.CENTER);
	    
	    name.setText("Name");
	    size.setText("Size");
	    
	    table.setHeaderVisible(true);
	 
	    TableItem item1 = new TableItem(table, SWT.NONE);
	    item1.setText(new String[] { "Industrial Craft 2", "10 mb" });
	    
	    TableItem item2 = new TableItem(table, SWT.NONE);
	    item2.setText(new String[] { "Red Power 2", "2 mb" });
	    
	    TableItem item3 = new TableItem(table, SWT.NONE);
	    item3.setText(new String[] { "Computer Craft", "7.5 mb" });
	    
	    name.pack();
	    size.pack();
		
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
		
		shell.open();

		while (!shell.isDisposed()) {

			if (!display.readAndDispatch()) display.sleep();

		}

		display.dispose();
		
		System.exit(0);
		
	}
	
	// TODO: Listeners
	
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
			
			event.doit = true;
			
			shell.dispose();
			
		}
		
	}
	
	// TODO: Strings
	
	private static final class Strings {
		
		private static final String SOARTEX_LAUNCHER = "Soartex Launcher";
		
		private static final String PREF_X = "x";
		private static final String PREF_Y = "y";
		
		private static final String PREF_WIDTH = "width";
		private static final String PREF_HEIGHT = "height";
		
		private static final String PREF_MAX = "maximized";
		
		private static final String ICON_NAME = "icon.png";
		
	}

}
