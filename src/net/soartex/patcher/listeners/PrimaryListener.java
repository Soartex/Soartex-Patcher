package net.soartex.patcher.listeners;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class PrimaryListener implements SelectionListener {

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		

	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		try {
			Desktop.getDesktop().browse(new URI( "http://soartex.net/pages/download/"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}