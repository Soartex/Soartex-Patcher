package net.soartex.patcher;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;

import net.soartex.patcher.console.TextAreaOutputStream;
import net.soartex.patcher.helpers.BooleanTableModel;
import net.soartex.patcher.helpers.Strings;
import net.soartex.patcher.html.HtmlAccessor;
import net.soartex.patcher.listeners.CellMouse;
import net.soartex.patcher.listeners.MainMenu;

public class Soartex_Patcher {

	// TODO: Main Components
	private static JFrame frame;
	private static JTable table;

	// TODO: Program Variables
	private static Object[][] tableData;

	public void initializeWindow() {
		frame = new JFrame();
		frame.setSize(Integer.parseInt(Strings.PREF_WIDTH),Integer.parseInt(Strings.PREF_HEIGHT));

		//layout
		GridBagLayout gd = new GridBagLayout();
		frame.setLayout(gd);
		frame.setFocusableWindowState(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Soartex Patcher");

		// TODO: Icon loading
		try {
			URL url = getClass().getClassLoader().getResource(Strings.ICON_NAME);
			frame.setIconImage(Toolkit.getDefaultToolkit().createImage(url));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void downloadHttpFiles() {
		JFrame frame1 = new JFrame("Loading Patcher Files");
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JProgressBar aJProgressBar = new JProgressBar(JProgressBar.HORIZONTAL);
		aJProgressBar.setStringPainted(false);
		aJProgressBar.setIndeterminate(true);
		frame1.add(aJProgressBar, BorderLayout.NORTH);

		JLabel title = new JLabel("Please Wait While We Load Your Files", JLabel.CENTER);
		title.setForeground(Color.white);
		frame1.add(title, BorderLayout.SOUTH);
		frame1.setIconImage(frame.getIconImage());
		frame1.setSize(300, 80);
		frame1.setVisible(true);

		// TODO: Download Http Files
		HtmlAccessor.loadHtmlString();
		HtmlAccessor.loadWelcomeMsgString();

		// TODO: load table
		//note: checkmarks are stored as booleans
		Object[][] temp = HtmlAccessor.loadTable();
		tableData = new Object[temp.length][];
		for(int i=0; i<temp.length;i++){
			Object[] temp2={new Boolean(false),
					temp[i][0],
					temp[i][1],
					temp[i][2],
					temp[i][3],
					temp[i][4]};
			tableData[i]=temp2;
		}
		//done
		frame1.setVisible(false);
	}

	public void initializeComponents() {
		// TODO: Table
		table = new JTable(new BooleanTableModel(tableData));

		table.setPreferredScrollableViewportSize(new Dimension(500, 100));
		table.setFillsViewportHeight(true);		

		//checkboxes
		table.getColumnModel().getColumn(0).setMaxWidth(25);
		table.addMouseListener(new CellMouse(table));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.ipady = 500;
		c.gridx = 0;
		c.gridy = 0;

		frame.add(new JScrollPane(table), c);

		// TODO: Menu
		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("File");
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem(Strings.MENU_DATA[0]);
		menuItem.addActionListener(new MainMenu());
		menu.add(menuItem);

		menu.addSeparator();

		//hide console
		JCheckBoxMenuItem cbMenuItem = new JCheckBoxMenuItem(Strings.MENU_DATA[1]);
		cbMenuItem.addActionListener(new MainMenu());
		menu.add(cbMenuItem);

		//show lastest updated
		cbMenuItem = new JCheckBoxMenuItem(Strings.MENU_DATA[2]);
		cbMenuItem.addActionListener(new MainMenu());
		menu.add(cbMenuItem);

		//Make second menu for packs
		menu = new JMenu("Pack");

		ButtonGroup group = new ButtonGroup();
		JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem(Strings.MENU_DATA[3]);
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem(Strings.MENU_DATA[4]);
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);
		menuBar.add(menu);

		rbMenuItem = new JRadioButtonMenuItem(Strings.MENU_DATA[5]);
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);
		menu.addActionListener(new MainMenu());

		menuBar.add(menu);

		//patch menu/button
		menuBar.add(Box.createHorizontalGlue());
		menu = new JMenu("Patch");
		menuItem = new JMenuItem(Strings.MENU_DATA[6]);
		menuItem.addActionListener(new MainMenu());
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem(Strings.MENU_DATA[7]);
		menuItem.addActionListener(new MainMenu());
		menu.add(menuItem);

		menuBar.add(menu);

		frame.setJMenuBar(menuBar);

		// TODO: Console
		JTextArea ta = new JTextArea("");
		PrintStream ps = new PrintStream(new TextAreaOutputStream(ta));
		System.setOut(ps);
		System.setErr(ps);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 1;

		frame.add(new JScrollPane(ta),c);


		System.out.println(Strings.WELCOME_MSG+"Program Started Ready to Patch!");
	}

	public void showWindow() {
		frame.setVisible(true);
		frame.setFocusableWindowState(true);
		frame.setResizable(false);
	}

	//this is called by the mouse listener to update the table data that a row in colum 0 has been clicked.
	public static void checkBox(int row, int col){
		tableData[row][0]= new Boolean(!(Boolean)tableData[row][0]);
		table.updateUI();
	}

	//called to get modded checked. Returns string arraylist
	public static ArrayList<String> getCheckedMods() {
		ArrayList<String> temp = new ArrayList<String>();
		for(int i=0; i<tableData.length;i++){
			if(tableData[i][0] != null && (Boolean)tableData[i][0]){
				temp.add((String) tableData[i][1]);
			}
		}
		return temp;
	}

	public static void browseFiles() {
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(frame);
		if (returnVal != JFileChooser.APPROVE_OPTION) return;
		File chosenFile = fc.getSelectedFile();
		
		if(chosenFile.getAbsolutePath().endsWith(Strings.ZIP_FILES_EXT.substring(1))){
			Strings.setModdedZipLocation(chosenFile.getAbsolutePath());
		}
		else{
			// TODO: show error dialog
		}
	}
}