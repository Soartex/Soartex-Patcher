package net.soartex.patcher.html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


import net.soartex.patcher.helpers.Strings;

public class HtmlAccessor {

	public static void loadWelcomeMsgString(){
		try {
			URL data = new URL(Strings.GOD_URL+Strings.GOD_WELCOME);
			final BufferedReader in = new BufferedReader(new InputStreamReader(data.openStream()));
			String readline = in.readLine();
			String temp="";
			while(readline!=null){
				temp+=readline+"\n";
				readline = in.readLine();
			}
			Strings.setWelcomeMsgStrings(temp);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void loadHtmlString(){
		try {
			URL data = new URL(Strings.GOD_URL+Strings.GOD_ZIP);
			final BufferedReader in = new BufferedReader(new InputStreamReader(data.openStream()));
			String readline = in.readLine();

			URL data2 = new URL(Strings.GOD_URL+Strings.GOD_TABLE);
			final BufferedReader in2 = new BufferedReader(new InputStreamReader(data2.openStream()));
			String readline2 = in2.readLine();
			Strings.setStrings(readline, readline2);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static Object[][] loadTable () {

		//iteminfo storage
		final ArrayList<String[]> itemsInfo = new ArrayList<String[]>();
		final ArrayList<String> itemsInfoUrl = new ArrayList<String>();

		try {
			URL tabledata = new URL(Strings.MODDED_CVS);
			final BufferedReader in = new BufferedReader(new InputStreamReader(tabledata.openStream()));
			String readline = in.readLine();

			while (readline != null) {

				final URL zipurl = new URL(Strings.MODDED_URL + readline.split(Strings.COMMA)[0].replace(Strings.SPACE, Strings.UNDERSCORE) + Strings.ZIP_FILES_EXT.substring(1));
				
				// TODO: uncomment when done testing
				//test to see if file is there
				/*try {
					zipurl.openStream();
				} catch (final IOException e) {
					e.printStackTrace();
					readline = in.readLine();
					continue;
				}*/
				
				//add file info
				final String[] itemtext = new String[5];
				itemtext[0] = readline.split(Strings.COMMA)[0];

				//modversion
				try{
					itemtext[1] = readline.split(Strings.COMMA)[1];
				} catch(final Exception e){
					itemtext[1] = "Unknown";
				}

				//gameversion
				try{
					itemtext[2] = readline.split(Strings.COMMA)[2];
				} catch(final Exception e){
					itemtext[2] = "Unknown";
				}

				//size
				try{
					final String temp= readline.split(Strings.COMMA)[3];
					final long size = Integer.parseInt(temp);

					if (size > 1024 && size < 1048576 ) itemtext[3] = String.valueOf(size / 1024) + Strings.KILOBYTES;
					else if (size > 1048576) itemtext[3] = String.valueOf(size / 1048576) + Strings.MEGABYTES;
					else itemtext[3] = String.valueOf(size) + Strings.BYTES;
				} catch(final Exception e){
					itemtext[3] = "Unknown";
				}

				//date modified
				try{
					itemtext[4] = readline.split(Strings.COMMA)[4];
				} catch(final Exception e){
					itemtext[4] = "Unknown";
				}
				//save info
				itemsInfo.add(itemtext);
				itemsInfoUrl.add(zipurl.toString());

				readline = in.readLine();
			}
		} catch (final IOException e) {

			e.printStackTrace();

		} 

		Object[][] temp = new Object[itemsInfo.size()][];
		for(int i=0; i<itemsInfo.size();i++){
			temp[i]= itemsInfo.get(i);
		}
		return temp;
	}

}
