package mp.io.dataclasses;

import java.util.HashMap;
import java.util.List;

import mp.dataclasses.Infobox;

public class InfoboxExtractionObject {
	
	private List<Infobox> infoBoxes;
	private String remainingData;
	private int lastScanBeginPosition;
	private boolean isInitialized = false;
	
	public InfoboxExtractionObject(List<Infobox> infoBoxSet, String remainingData, int lastScanBeginPosition) {
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

	public List<Infobox> getInfoBoxes() {
		return infoBoxes;
	}
}
