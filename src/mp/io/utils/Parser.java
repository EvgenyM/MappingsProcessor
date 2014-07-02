package mp.io.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mp.dataclasses.Infobox;
import mp.dataclasses.InfoboxAttribute;
import mp.dataclasses.LanguageCodes;
import mp.dataclasses.WikiLink;
import mp.io.dataclasses.InfoboxExtractionObject;

/**
 * Implements basic operations for parsing unstructured text.
 * @author Evgeny Mitichkin
 *
 */
public class Parser {
	
	private static final String textBeginTag = "<text";
	private static final String textEndTag = "</text>";
	
	private static final String INFOBOX_BEGINNING = "{{Infobox";
	private static final String DOUBLE_OPEN = "{{";
	private static final String INFOBOX_END = "}}";
	
	private static final String[] forbiddenParts = new String[] {};//Parts that are considered stopwords for attribute extraction
																   //(if the phrase sontains it, it is excluded)
	private static final String[] partsToBeCleaned = new String[] {"&lt", "&gt", ";", "!", "&", "-", "\\|", ","};//Parts that should be cleaned,
																												 //i.e. replaced by "" during attribute extraction
	private static final String[] forbiddenLangCodes = new String[] {"WP"};//symbols that are considered forbidden as langiage codes
	private static final String[] languageCodes = new String[] {
		LanguageCodes.ENGLISH,
		LanguageCodes.GERMAN,
		LanguageCodes.FRENCH,
		LanguageCodes.DUTCH,
		LanguageCodes.RUSSIAN };

	/**
	 * Completes the ckunk of XML code to make the XML valid for parsers
	 * @param chunk Chunk of code
	 * @param withHeader Whether a header template should be appended
	 */
	public static String completeChunk(String chunk, boolean withHeader) {
		String res = chunk;
		String header = "<mediawiki xmlns=\"http://www.mediawiki.org/xml/export-0.8/\""+
		" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""+
		" xsi:schemaLocation=\"http://www.mediawiki.org/xml/export-0.8/ http://www.mediawiki.org/xml/export-0.8.xsd\""+
		" version=\"0.8\" xml:lang=\"en\">";
		String footer = "</mediawiki>";
		
		res += footer;
		if (withHeader)
			res = header + res;
		
		return res;
	}
	
	/**
	 * Returns the data inside a particular tag
	 * @param data Raw data as String
	 * @param beginTag Opening tag part, e.g. {@code <page} or {@code <title}
	 * @param endTag Closing tag, e.g. {@code </page> or </title>}
	 * @return
	 */
	public static List<String> getContents(String data, String beginTag, String endTag) {
		int indexOfBeginning = 0;
		int filelength = data.length();
		List<String> result = new ArrayList<String>();
		
		boolean chunkEndExceeded = false;
		do {
			int indexPos = data.indexOf(beginTag, indexOfBeginning);
			int initialPosition = 0;
			if (indexPos==-1) {
				chunkEndExceeded = true;
				break;
			} else {
				initialPosition = indexPos + beginTag.length()+1;
			}
				
			int endPosition = initialPosition;
			int numberOfOccurences = 1;
						
			while (numberOfOccurences>0 && endPosition < filelength-endTag.length()) {
				String s1 = data.substring(endPosition, endPosition+endTag.length());
				if (s1.equals(endTag))
					numberOfOccurences--;
				endPosition++;
				
				if (endPosition>=filelength-endTag.length()) {
					chunkEndExceeded = true;
					break;
				}
			}
			
			if (!chunkEndExceeded) {
				String resultingChunk = data.substring(initialPosition, endPosition-1).trim();
				result.add(resultingChunk);				
				indexOfBeginning = endPosition+1;
				//System.out.println(indexOfBeginning);
			}				
		} while (indexOfBeginning < filelength && !chunkEndExceeded);
		return result;
	}
	
	/**
	 * Returns a set of Interlanguage Links on for given page
	 * @param pageData page raw data (which was inside the {@code <page>...</page>} tag
	 * @param applyFilter Whether language codes should be filtered (e.g.  {@code "WP"}, which is not a language code)
	 * @return
	 */
	public static HashMap<String, WikiLink> getILLs(String pageData, boolean applyFilter) {
		HashMap<String, WikiLink> result = new HashMap<String, WikiLink>();
		
		String data = Parser.getContents(pageData, textBeginTag, textEndTag).get(0);//Page contains only "<text>" element
		//Extract all ILLs here
		String[] patternStandard = new String[] {"(\\[\\[\\s*..\\s*:)(.*)(\\]\\])", "]]"};
		String[] patternFeatured = new String[] {"(\\{\\{)(Link)(\\s*)(..)", "}}"};
		//String[] patternGood = new String[] { "(\\{\\{)(Link)(\\s*)(GA)", "}}"};
		
		List<String[]> regexPatterns = new ArrayList<String[]>();
		regexPatterns.add(patternStandard);
		regexPatterns.add(patternFeatured);
		//regexPatterns.add(patternGood);
		
		for (int i=0;i<regexPatterns.size();i++) {
			Pattern pattern = Pattern.compile(regexPatterns.get(i)[0]);
			Matcher matcher = pattern.matcher(data);
			
			int matchPos = 0;
			boolean isMatch = false;
			do {
				WikiLink link = new WikiLink();
				if (matcher.find(matchPos)) {
					isMatch = true;
					matchPos = matcher.start();
					if (matchPos!=0) {
						int illEnd = data.indexOf(regexPatterns.get(i)[1], matchPos);//finding the end of the ILL
						if (illEnd!=-1) {
							String illString = data.substring(matchPos, illEnd);
							illString = illString.substring(2,illString.length());
							if (i==0) {//Parsing standard pattern
								String[] ILLComponents = illString.split(":");
								if (ILLComponents.length<3) { //Filter the links that matched the template by mistake
									boolean isClean = true;
									if (applyFilter) {//Cut off codes like "WP" which match the pattern occasionally
										isClean = checkForbiddenLangCode(ILLComponents[0]);
									}
									if (isClean) {
										link.setInitialized(true);
										link.setLangCode(ILLComponents[0].trim());
										link.setSameAsPageTitle(ILLComponents[1].trim());
										ILLComponents = ILLComponents[1].split("#");
										if (ILLComponents.length>1) {
											link.setSubCategory(ILLComponents[1].trim());
										}	
									}
								}															
							} else {//Additional patterns
								String[] ILLComponents = illString.split("\\|");
								if (ILLComponents[0].contains("FA")) {//Featured article
									link.setHasSpecialMark(true);
									link.setFeatured(true);
									link.setFeaturedLangCode(ILLComponents[1].trim());
								}
								if (ILLComponents[0].contains("GA")) {//Featured article
									link.setHasSpecialMark(true);
									link.setGood(true);
									link.setGoodLangCode(ILLComponents[1].trim());
								}
							}						
							matchPos = illEnd;
							if (link.isInitialized()) {
								result.put(link.getSameAsPageTitle(), link);
							} else {
								if (link.isHasSpecialMark()) {
									String name = "";
									name += link.getSameAsPageTitle();
									if (link.isGood())
										name += "#GA";
									if (link.isFeatured())
										name += "#FA";
									result.put(name, link);
								}
							}
						}
					}					
				} else {
					isMatch = false;
				}
			} while (matchPos>0 && matchPos <= data.length() && isMatch);
		}
		
		return result;
	}
	
	/**
	 * Filters the unwanted language codes or "wrong" codes like "WP", that are not language codes, but match the same pattern 
	 * @param str
	 * @return
	 */
	private static boolean checkForbiddenLangCode(String str) {
		boolean res = true;
		for (int i=0;i<forbiddenLangCodes.length;i++) {
			if (str.equals(forbiddenLangCodes[i])) {
				res = false;
				break;
			}
		}
		return res;
	}
	
	/**
	 * Extracts infoboxes from a given dataset. Capable of processing medium-sized (up to 200Mb) chunks
	 * @param wikiDataAsString Chunk of the wikidata
	 * @param ignoreUnnamedAttributes Whether unnamed attributes should be ignored
	 * @param isNamesToLowerCase Whether attribute names should be translated to lower case
	 * @return
	 */
	public static InfoboxExtractionObject extractInfboxesFromUnstructuredText(String wikiDataAsString, boolean ignoreUnnamedAttributes, boolean isNamesToLowerCase) {
		//Parse the wikidata, extract infoboxes
		HashMap<String, Infobox> infoboxesRetrieved = new HashMap<String, Infobox>();
		int indexOfBeginning = 0;
		int filelength = wikiDataAsString.length();
		
		String remainderString = "";
		int lastBeginPosition = 0;
		//Parsing the chunk
		boolean fileEndExceeded = false;
		do {
			int initialPosition = wikiDataAsString.indexOf(INFOBOX_BEGINNING, indexOfBeginning) + DOUBLE_OPEN.length();
			if (initialPosition==1 || initialPosition==-1) {
				fileEndExceeded = true;
				remainderString = wikiDataAsString.substring(lastBeginPosition, filelength);
				break;
			}
			int endPosition = initialPosition;
			int numberOfOccurences = 1;
						
			while (numberOfOccurences>0 && endPosition < filelength-2) {
				String s1 = wikiDataAsString.substring(endPosition, endPosition+2);
				if (s1.equals(DOUBLE_OPEN))
					numberOfOccurences++;
				if (s1.equals(INFOBOX_END))
					numberOfOccurences --;
				endPosition++;
				
				if (endPosition>=filelength-2) {
					fileEndExceeded = true;
					remainderString = wikiDataAsString.substring(lastBeginPosition, filelength);
					break;
				}
			}
			
			if (!fileEndExceeded) {
				String infoboxAsString = wikiDataAsString.substring(initialPosition, endPosition-DOUBLE_OPEN.length()+1);
				Infobox box = getInfoBox(infoboxAsString, ignoreUnnamedAttributes, isNamesToLowerCase);
				if (box!=null)
					infoboxesRetrieved.put(box.getID(), box);
				indexOfBeginning = endPosition+1;
				lastBeginPosition = endPosition+1;
			}				
		} while (indexOfBeginning < filelength && !fileEndExceeded);
		
		return new InfoboxExtractionObject(infoboxesRetrieved, remainderString, lastBeginPosition);
	}
	
	/**
	 * Returns an {@link Infobox} object from a given string representation
	 * @param infoboxAsString 
	 * @return
	 */
	private static Infobox getInfoBox(String infoboxAsString, boolean mIgnoreUnnamedAttributes, boolean mToLowerCase) {
		String[] arr = infoboxAsString.split("\n");
		String infoboxClass = "";
		HashMap<String, InfoboxAttribute> attributes = new HashMap<String, InfoboxAttribute>();
		for (int i=0;i<arr.length;i++) {
			if (i==0) {
				//Getting the class name of the infobox
				String[] nameData = arr[i].split(" ");	
				//Composing the name
				if (nameData.length>1) {
					for (int j=1;j<nameData.length;j++) {
						String part = filter(nameData[j]);//Filtering out wrong parts and characters
						if (part.length()>0)						
							infoboxClass += part+" ";
					}					
				}
			} else {
				//Getting the attributes
				String[] arrAtt = arr[i].split("=");
				String attrName = arrAtt[0].replaceAll("\\|", " ").trim();
				if (!attrName.equals("") || !mIgnoreUnnamedAttributes) {//If one should ignore the infoboxes with no attribute name
					String attrValue = "";
					if (arrAtt.length>1)
						attrValue = arrAtt[1].trim();
					else {
						//Look it up, could be a list
						//TODO parse the lists
						boolean nextAttributeFound = false;
						int cntr = 0;
						while ((!nextAttributeFound) && (i+cntr<arr.length)) {
							String line = arr[i+cntr].trim();
							if (!line.startsWith("|")) {
								attrValue +=line+"\n";
								cntr++;
							} else {
								nextAttributeFound = true;
							}
						}
						i+=cntr;
					}
					
					if (infoboxClass.equals("") && attrName.contains("title")) {
						infoboxClass = attrValue;
					}
					
					if (mToLowerCase)
						attrName.toLowerCase();
					InfoboxAttribute attribute = new InfoboxAttribute(attrName, attrValue.trim());
					attributes.put(attribute.getName(), attribute);
				} 
			}
		}
		if (mToLowerCase)
			infoboxClass = infoboxClass.toLowerCase();
		return new Infobox(infoboxClass.trim(), attributes);
	}
	
	private static String filter(String src) {
		String res = "";
		boolean isClean = true;
		
		for (int i=0;i<forbiddenParts.length;i++) {
			if (src.contains(forbiddenParts[i])) {
				isClean = false;
				break;
			}
		}
		
		if (isClean) {
			for (int i=0;i<partsToBeCleaned.length;i++) {
				src = src.replaceAll(partsToBeCleaned[i], "");
			}
			res = src;
		}
		
		return res;
	}
	
}
