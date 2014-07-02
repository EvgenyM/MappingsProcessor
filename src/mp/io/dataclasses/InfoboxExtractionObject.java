package mp.io.dataclasses;

import java.util.HashMap;

import mp.dataclasses.Infobox;

public class InfoboxExtractionObject {
	
	private HashMap<String, Infobox> infoBoxes;
	private String remainingData;
	private int lastScanBeginPosition;
	private boolean isInitialized = false;
	
	public InfoboxExtractionObject(HashMap<String, Infobox> infoBoxSet, String remainingData, int lastScanBeginPosition) {
		this.infoBoxes = infoBoxSet;
		this.remainingData = remainingData;
		this.lastScanBeginPosition = lastScanBeginPosition;
		this.isInitialized = true;
	}
	
	public InfoboxExtractionObject() {
		this.isInitialized = false;
	}

	public String getRemainingData() {
		return remainingData;
	}

	public int getLastScanBeginPosition() {
		return lastScanBeginPosition;
	}

	public HashMap<String, Infobox> getInfoBoxes() {
		return infoBoxes;
	}
}
