package mp.io.json;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIOManager {

	/**
	 * Creates a folder in a given path
	 * @param dirPath
	 */
	public static String createDirectory(String dirPath, String folderName) {
		Path path = Paths.get(dirPath + "/" + folderName);
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return dirPath + "/" + folderName;
        }
        return dirPath + "/" + folderName;
	}
	
	/**
	 * Writes a string into a text file (UTF-8) 
	 * @param path
	 * @param data
	 * @param append
	 */
	public static boolean writeToFile(String path, String data, boolean append) {
		boolean success = false;
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, append), "UTF-8"));
			out.write(data);
			success = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			success = false;
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
		} finally {
		    try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;
	}
}