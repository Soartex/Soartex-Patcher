package net.soartex.patcher.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class AppZip {

	static List<String> fileList;
	private static String SOURCE_FOLDER;

	/**
	 * Zip it
	 * @param zipFile output ZIP file location
	 */
	public static void zipIt (String zipFile) {

		zipFile=zipFile.replaceAll("\\\\", "/");

		final byte[] buffer = new byte[1024];

		try {

			final FileOutputStream fos = new FileOutputStream(zipFile);
			final ZipOutputStream zos = new ZipOutputStream(fos);

			System.out.println("Output to Zip : " + zipFile);

			for(String file : fileList){

				file = file.replaceAll("\\\\", "/");
				//System.out.println("File Added : " + file);
				final ZipEntry ze= new ZipEntry(file);
				zos.putNextEntry(ze);

				final FileInputStream in =
						new FileInputStream(SOURCE_FOLDER + File.separator + file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			//remember close it
			zos.close();
			fileList = new ArrayList<String>();

		} catch(final IOException ex) {

			ex.printStackTrace();

		}

	}

	public static void  makeList (final File node) {

		SOURCE_FOLDER=node.getAbsolutePath();
		fileList = new ArrayList<String>();

		generateFileList(node);

	}
	/**
	 * Traverse a directory and get all files,
	 * and add the file into fileList
	 * @param node file or directory
	 */
	public static void generateFileList (File node) {

		final String temp = node.getAbsolutePath().replaceAll("\\\\", "//");
		//temp = temp.replaceAll("\\", "/");
		node = new File(temp);
		//add file only
		if(node.isFile()){
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
		}

		if(node.isDirectory()){
			final String[] subNote = node.list();
			for(final String filename : subNote){
				generateFileList(new File(node, filename));
			}
		}

	}

	/**
	 * Format the file path for zip
	 * @param file file path
	 * @return Formatted file path
	 */
	private static String generateZipEntry (final String file) {

		return file.substring(SOURCE_FOLDER.length()+1, file.length());

	}

}