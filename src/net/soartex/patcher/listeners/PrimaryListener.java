package net.soartex.patcher.listeners;

import java.awt.Desktop;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class PrimaryListener implements SelectionListener {

	@Override public void widgetDefaultSelected (final SelectionEvent arg0) {}

	@Override public void widgetSelected (final SelectionEvent arg0) {

		try {

			Desktop.getDesktop().browse(new URI( "http://soartex.net/pages/download/"));

		} catch (final IOException e) {

			e.printStackTrace();

		} catch (final URISyntaxException e) {

			e.printStackTrace();

		}

	}

}