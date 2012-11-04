package net.soartex.patcher.helpers;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class AppZip
{
	static List<String> fileList;
	private static String SOURCE_FOLDER;

	/**
	 * Zip it
	 * @param zipFile output ZIP file location
	 */
	public static void zipIt(String zipFile){

		zipFile=zipFile.replaceAll("\\\\", "/");
		
		byte[] buffer = new byte[1024];

		try{

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			
			System.out.println("Output to Zip : " + zipFile);

			for(String file : fileList){
				
				file = file.replaceAll("\\\\", "/");
				//System.out.println("File Added : " + file);
				ZipEntry ze= new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = 
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
		}catch(IOException ex){
			ex.printStackTrace();   
		}
	}

	public static void  makeList(File node){
		SOURCE_FOLDER=node.getAbsolutePath();
		fileList = new ArrayList<String>();
		generateFileList(node);
	}
	/**
	 * Traverse a directory and get all files,
	 * and add the file into fileList  
	 * @param node file or directory
	 */
	public static void generateFileList(File node){
		
		String temp = node.getAbsolutePath().replaceAll("\\\\", "//");
		//temp = temp.replaceAll("\\", "/");
		node = new File(temp);
		//add file only
		if(node.isFile()){
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
		}

		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				generateFileList(new File(node, filename));
			}
		}

	}

	/**
	 * Format the file path for zip
	 * @param file file path
	 * @return Formatted file path
	 */
	private static String generateZipEntry(String file){
		return file.substring(SOURCE_FOLDER.length()+1, file.length());
	}
}