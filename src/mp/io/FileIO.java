package mp.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Timestamp;
import java.util.HashMap;

import mp.dataclasses.InfoboxAttribute;
import mp.dataclasses.WikiPage;
import mp.global.GlobalVariables;
import mp.io.utils.Converter;
import mp.io.utils.Parser;

public class FileIO {
	
	private static final int NUMBER_OF_STRINGS_PER_ITERATION = 10; 
	
	public static void readFileByChunks(String path, FileIONotifier caller) throws IOException {		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
		String str = "";
		long linesTotal = 0;
		int readingIterationsPassed = 0;//number of iterations
		int readingCounter = 0;
		String[] readBuffer = new String[10];
		while ((str = br.readLine())!=null) {
			readBuffer[readingCounter] = str;
			readingCounter++;
			linesTotal++;
			if (readingCounter == NUMBER_OF_STRINGS_PER_ITERATION) {
				//Passing the results
				caller.onChunkRead(readBuffer);
				//Cleaning the buffer
				for (int i=0;i<readBuffer.length;i++) {
					readBuffer[i] = null;
				}
				readingCounter = 0;
				readingIterationsPassed++;
				//Log
				System.out.println("Lines read: " +linesTotal);
			}	
		}
		//Pass the last results
		int num = 0;
		for (int i=0;i<readBuffer.length;i++) {
			if (readBuffer[i]!=null)
				num++;
		}
		String[] bufRemainder = new String[num];
		for (int i=0;i<bufRemainder.length;i++) {
			bufRemainder[i] = readBuffer[i];
		}
		caller.onChunkRead(bufRemainder);		
				
		if (GlobalVariables.IS_DEBUG) {
			java.util.Date date= new java.util.Date();
			System.out.println("Reading iteration " +readingIterationsPassed+ " finished at " + new Timestamp(date.getTime()));
			System.out.println("Lines read: " +linesTotal);
		}
		br.close();
	}
	
	public static void writeToFile(String path, String data, boolean append) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, append), "UTF-8"));
			out.write(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
