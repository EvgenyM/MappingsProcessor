package mp.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import mp.dataclasses.WikiPage;
import mp.exceptions.PageConversionException;
import mp.global.GlobalVariables;
import mp.io.dataclasses.PageMapEntry;

import com.google.gson.Gson;

/**
 * Enables buffered String-to-JSON and JSON-to-String IO operations
 * @author Evgeny Mitichkin
 *
 */
public class JsonIOManager extends FileIO {
	
	public static final int WRITE_BUFFER_CHUNK = 300000;
	public static final int READ_BUFFER_CHUNK = 20000;
	
	public JsonIOManager() { 
		
	}
	
	/**
	 * Writes an object ({@code HashMap<String, WikiPage> wikiData}) to a JSON file
	 * @param <K>
	 * @param <V>
	 * @param wikiData
	 * @param path
	 */
	public <K, V> void writeToJson(HashMap<K, V> wikiData, String path) {
		int dumpIter = 0;
		boolean appendToFile = false;
		HashMap<K, V> buffdata = new HashMap<K, V>();
		for (Map.Entry<K, V> page : wikiData.entrySet()) {
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
	 * @param <K>
	 * @param <V>
	 * @param wikiData
	 * @param path
	 * @param isFirstChunk whether the writer is called at the first time in the series.
	 */
	public <K, V> void writeToJson(HashMap<K, V> wikiData, String path, boolean isFirstChunk) {
		int dumpIter = 0;
		boolean appendToFile = true;
		if (isFirstChunk) {
			appendToFile = false;//file just started
		}
		HashMap<K, V> buffdata = new HashMap<K, V>();
		for (Map.Entry<K,V> page : wikiData.entrySet()) {
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
	 * @param <V>
	 * @param <K>
	 * @param <T>
	 * @param path
	 * @return
	 * @throws IOException 
	 */
	public <K, V, T> HashMap<K, V> readFromJson(String path, Class<T> classOfPage) throws IOException {	
		HashMap<K, V> result = new HashMap<K, V>();
		HashMap<K, V> readBuffer = new HashMap<K, V>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		Gson gson = new Gson();
		int cnt = 0;
		long totalRead = 0;
		String pageAsString = "";
		while ((pageAsString = br.readLine()) != null) {
			try {
				//Page<K, V>
				T pg = gson.fromJson(pageAsString, classOfPage);
				readBuffer.put(((Map.Entry<K, V>) pg).getKey(), ((Map.Entry<K, V>) pg).getValue());
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
				exxx.printStackTrace();
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
	
	/**
	 * Reads an object ({@code HashMap<String, WikiPage> wikiData}) from a String file in JSON. Passes the result to a notifier object.
	 * @param <K>
	 * @param <V>
	 * @param <T>
	 * @param path
	 * @param notifier
	 * @return 
	 * @return
	 * @throws IOException 
	 * @throws PageConversionException 
	 */
	public <K, V, T> void readFromJson(String path, JsonIONotifier notifier, Class<T> classOfPage) throws IOException, PageConversionException {
		HashMap<K, V> readBuffer = new HashMap<K, V>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		Gson gson = new Gson();
		int cnt = 0;
		long totalRead = 0;
		String pageAsString = "";
		while ((pageAsString = br.readLine()) != null) {
			try {
				T pg = gson.fromJson(pageAsString, classOfPage);
				readBuffer.put(((Map.Entry<K, V>) pg).getKey(), ((Map.Entry<K, V>) pg).getValue());
				cnt++;
				if (cnt>=READ_BUFFER_CHUNK) {
					boolean success = notifier.onChunkProcessed(readBuffer);
					readBuffer.clear();
					totalRead+=cnt;
					cnt = 0;
					if (GlobalVariables.IS_DEBUG) {
						System.out.println("Lines read: " + totalRead);
					}
					if (!success) {
						throw new PageConversionException("Exception happened in notifire object");
					}
				}
			} catch (Exception exxx) {
				System.out.println("Json parsing error line " + cnt + " line :"+pageAsString);
				cnt++;
			}				
		}
		br.close();
		if (cnt>0) {
			boolean success = notifier.onChunkProcessed(readBuffer);
			if (!success) {
				throw new PageConversionException("Exception happened in notifire object");
			}
			readBuffer = null;
		}	
		if (GlobalVariables.IS_DEBUG)
			System.out.println("Json parsing done.");
	}
	
	private <K, V> void writeJson(HashMap<K, V> data, String path, boolean append) {
		boolean shouldAppend = append;
		Gson gson = new Gson();	
		List<String> result = new ArrayList<String>();// "";
		int i=0;
		for (Map.Entry<K,V> page : data.entrySet()) {
			result.add(gson.toJson(page)+"\n");
			i++;
		}
		StringBuilder builder = new StringBuilder();
		for (String value : result) {
		    builder.append(value);
		}
		String text = builder.toString();
			
		writeToFile(path, text, shouldAppend);
	}
}
