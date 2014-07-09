package mp.dataclasses;

/**
 * Wraps statistics for one item
 * @author Evgeny Mitichkin
 *
 */
public class RawItemStatistics {
	private int numberOfILLs = 0;
	private int numberOfAttributes= 0;
	private String infoboxClass = "";
	
	public RawItemStatistics() {
		
	}

	public int getNumberOfILLs() {
		return numberOfILLs;
	}

	public void setNumberOfILLs(int numberOfILLs) {
		this.numberOfILLs = numberOfILLs;
	}

	public int getNumberOfAttributes() {
		return numberOfAttributes;
	}

	public void setNumberOfAttributes(int numberOfAttributes) {
		this.numberOfAttributes = numberOfAttributes;
	}

	public String getInfoboxClass() {
		return infoboxClass;
	}

	public void setInfoboxClass(String infoboxClass) {
		this.infoboxClass = infoboxClass;
	}
}
