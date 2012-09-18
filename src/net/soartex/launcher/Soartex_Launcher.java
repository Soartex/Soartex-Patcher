package net.soartex.launcher;

import java.util.prefs.Preferences;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Soartex_Launcher {

	private static final Preferences prefsnode = Preferences.userNodeForPackage(Soartex_Launcher.class).node(Strings.SOARTEX_LAUNCHER);
	
	private static Display display;
	private static Shell shell;
	
	public static void main (final String[] args) {

		initializeShell();
		
		startEventLoop();
		
	}
	
	private static void initializeShell () {
		
		display = Display.getDefault();

		shell = new Shell(display);
		
		shell.setText(Strings.SOARTEX_LAUNCHER);
		
		shell.setLocation(prefsnode.getInt(Strings.PREF_X, 26), prefsnode.getInt(Strings.PREF_Y, 26));
		shell.setSize(prefsnode.getInt(Strings.PREF_WIDTH, 960), prefsnode.getInt(Strings.PREF_HEIGHT, 717));

		if (prefsnode.getBoolean(Strings.PREF_MAX, false)) shell.setMaximized(true);
		
	}
	
	private static void startEventLoop () {
		
		shell.open();

		while (!shell.isDisposed()) {

			if (!display.readAndDispatch()) display.sleep();

		}

		display.dispose();
		
		System.exit(0);
		
	}
	
	private static final class Strings {
		
		private static final String SOARTEX_LAUNCHER = "Soartex Launcher";
		
		private static final String PREF_X = "x";
		private static final String PREF_Y = "y";
		
		private static final String PREF_WIDTH = "width";
		private static final String PREF_HEIGHT = "height";
		
		private static final String PREF_MAX = "maximized";
		
	}

}
