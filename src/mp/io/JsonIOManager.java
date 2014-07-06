package mp.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import mp.dataclasses.WikiPage;
import mp.global.GlobalVariables;
import mp.io.dataclasses.PageMapEntry;

import com.google.gson.Gson;

/**
 * Enables buffered String-to-JSON and JSON-to-String IO operations
 * @author Evgeny Mitichkin
 *
 */
public class JsonIOManager extends FileIO {
	
	public static final int WRITE_BUFFER_CHUNK = 10000;
	public static final int READ_BUFFER_CHUNK = 10000;
	
	public JsonIOManager() { 
		
	}

	/**
	 * Writes an object ({@code HashMap<String, WikiPage> wikiData}) to a JSON file
	 * @param wikiData
	 * @param path
	 */
	public void writeToJson(HashMap<String, WikiPage> wikiData, String path) {
		int dumpIter = 0;
		boolean appendToFile = false;
		HashMap<String, WikiPage> buffdata = new HashMap<String, WikiPage>();
		for (Map.Entry<String,WikiPage> page : wikiData.entrySet()) {
			if (page!=null) {
				buffdata.put(page.getKey(), page.getValue());
				dumpIter++;
				if (dumpIter >= WRITE_BUFFER_CHUNK) {
					writeJson(buffdata, path, appendToFile);
					if (!appendToFile) {
						appendToFile = true;
					}
					buffdata.clear();
					dumpIter = 0;
				}
			}
		}
		if (dumpIter>0) {
			writeJson(buffdata, path, appendToFile);
		}
	}
	
	/**
	 * Writes an object ({@code HashMap<String, WikiPage> wikiData}) to a JSON file (best for multiple calls
	 * @param wikiData
	 * @param path
	 * @param isFirstChunk whether the writer is called at the first time in the series.
	 */
	public void writeToJson(HashMap<String, WikiPage> wikiData, String path, boolean isFirstChunk) {
		int dumpIter = 0;
		boolean appendToFile = true;
		if (isFirstChunk) {
			appendToFile = false;//file just started
		}
		HashMap<String, WikiPage> buffdata = new HashMap<String, WikiPage>();
		for (Map.Entry<String,WikiPage> page : wikiData.entrySet()) {
			if (page!=null) {
				buffdata.put(page.getKey(), page.getValue());
				dumpIter++;
				if (dumpIter >= WRITE_BUFFER_CHUNK) {
					writeJson(buffdata, path, appendToFile);
					if (!appendToFile) {
						appendToFile = true;
					}
					buffdata.clear();
					dumpIter = 0;
				}
			}
		}
		if (dumpIter>0) {
			writeJson(buffdata, path, appendToFile);
		}
	}
	
	/**
	 * Reads an object ({@code HashMap<String, WikiPage> wikiData}) from a String file in JSON
	 * @param path
	 * @return
	 * @throws IOException 
	 */
	public HashMap<String, WikiPage> readFromJson(String path) throws IOException {	
		HashMap<String, WikiPage> result = new HashMap<String, WikiPage>();
		HashMap<String, WikiPage> readBuffer = new HashMap<String, WikiPage>();
		
		BufferedReader br = new BufferedReader(new FileReader(path));
		Gson gson = new Gson();
		int cnt = 0;
		long totalRead = 0;
		String pageAsString = "";
		while ((pageAsString = br.readLine()) != null) {
			try {
				PageMapEntry<String,WikiPage> pg = gson.fromJson(pageAsString, PageMapEntry.class);
				readBuffer.put(pg.getKey(), pg.getValue());
				cnt++;
				if (cnt>=READ_BUFFER_CHUNK) {
					result.putAll(readBuffer);
					readBuffer.clear();
					totalRead+=cnt;
					cnt = 0;
					if (GlobalVariables.IS_DEBUG) {
						System.out.println("Lines read: " + totalRead);
					}
				}
			} catch (Exception exxx) {
				System.out.println("Json parsing error line " + cnt + " line :"+pageAsString);
				cnt++;
				//br.close();
			}				
		}
		br.close();
		if (cnt>0) {
			result.putAll(readBuffer);
		}	
		if (GlobalVariables.IS_DEBUG)
			System.out.println("Json parsing done.");
		
		return result;
	}
	
	private void writeJson(HashMap<String, WikiPage> data, String path, boolean append) {
		boolean shouldAppend = append;
		Gson gson = new Gson();	
		List<String> result = new ArrayList<String>();// "";
		int i=0;
		for (Map.Entry<String,WikiPage> page : data.entrySet()) {
			result.add(gson.toJson(page)+"\n");
			i++;
		}
		StringBuilder builder = new StringBuilder();
		for (String value : result) {
		    builder.append(value);
		}
		String text = builder.toString();
			
		writeToFile(path, text, shouldAppend);
		/*for (String str : result) {
			writeToFile(path, str, shouldAppend);
			if (!shouldAppend) 
				shouldAppend = true;			
		}*/
	}
}
