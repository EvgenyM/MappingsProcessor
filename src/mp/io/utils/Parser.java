package mp.io.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DebugGraphics;

import mp.dataclasses.Infobox;
import mp.dataclasses.InfoboxAttribute;
import mp.dataclasses.LanguageCodes;
import mp.dataclasses.WikiLink;
import mp.global.GlobalVariables;
import mp.io.dataclasses.InfoboxExtractionObject;

/**
 * Implements basic operations for parsing unstructured text.
 * @author Evgeny Mitichkin
 *
 */
public class Parser {
	
	private static final int ILL_SET_INITIAL_CAPACITY = 4;
	private static final int INFOBOX_SET_INITIAL_CAPACITY = 2;
	private static final int ATTRIBUTE_SET_INITIAL_CAPACITY = 10;
	
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
		StringBuilder builder = new StringBuilder();
		
		if (withHeader) {
			builder.append("<mediawiki xmlns=\"http://www.mediawiki.org/xml/export-0.8/\"");
			builder.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
			builder.append(" xsi:schemaLocation=\"http://www.mediawiki.org/xml/export-0.8/ http://www.mediawiki.org/xml/export-0.8.xsd\"");
			builder.append(" version=\"0.8\" xml:lang=\"en\">");
		}
		builder.append(chunk);
		builder.append("</mediawiki>");
	
		return builder.toString();
	}
	
	/**
	 * Returns the data inside a given pair of strings
	 * @param data Raw data as String
	 * @param beginTag Opening string
	 * @param endTag Closing string
	 * @param keepLevel Whether the same level should be kept (useful for complex "superposition" cases)
	 * @return
	 */
	public static List<String> getContentsNoOffset(String data, String beginString, String endString, boolean keepLevel) {
		int indexOfBeginning = 0;
		int filelength = data.length();
		List<String> result = new ArrayList<String>();
		
		boolean chunkEndExceeded = false;
		do {
			int indexPos = data.indexOf(beginString, indexOfBeginning);
			int initialPosition = 0;
			if (indexPos==-1) {
				chunkEndExceeded = true;
				break;
			} else {
				initialPosition = indexPos + beginString.length();
			}
				
			int endPosition = initialPosition;
			int numberOfOccurences = 1;
						
			while (numberOfOccurences>0 && endPosition < filelength-endString.length()) {
				String s1 = data.substring(endPosition, endPosition+endString.length());
				if (s1.equals(endString))
					numberOfOccurences--;
				if (keepLevel) {
					if (s1.equals(beginString))
						numberOfOccurences++;
				}
				
				endPosition++;
				
				if (endPosition>filelength-endString.length()) {
					chunkEndExceeded = true;
					break;
				}
			}
			
			if (!chunkEndExceeded) {
				/*if (numberOfOccurences==0)
					endPosition--;//Weird correction*/
				String resultingChunk = data.substring(initialPosition, endPosition).trim();
				result.add(resultingChunk);				
				indexOfBeginning = endPosition+1;
				System.out.println(resultingChunk);
			}				
		} while (indexOfBeginning < filelength && !chunkEndExceeded);
		data = null;
		return result;
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
				
				if (endPosition>filelength-endTag.length()) {
					chunkEndExceeded = true;
					break;
				}
			}
			
			if (!chunkEndExceeded) {
				try {
					String resultingChunk = data.substring(initialPosition, endPosition-1).trim();
					result.add(resultingChunk);		
				} catch (Exception e) {
					
				}
				indexOfBeginning = endPosition+1;		
			}				
		} while (indexOfBeginning < filelength && !chunkEndExceeded);
		data = null;
		return result;
	}
	
	/**
	 * Returns a set of Interlanguage Links on for given page
	 * @param pageData page raw data (which was inside the {@code <page>...</page>} tag
	 * @param applyFilter Whether language codes should be filtered (e.g.  {@code "WP"}, which is not a language code)
	 * @return
	 */
	public static HashMap<String, WikiLink> getILLs(String pageData, boolean applyFilter) {
		HashMap<String, WikiLink> result = new HashMap<String, WikiLink>(ILL_SET_INITIAL_CAPACITY);
		
		List<String> textFragments = Parser.getContents(pageData, textBeginTag, textEndTag);//Page contains only "<text>" element
		
		if (textFragments.size()>0) {
			String data = textFragments.get(0);
			textFragments = null;
			
			//Extract all ILLs here
			String[] patternStandard = new String[] {"(\\[\\[\\s*..\\s*:)(.*)(\\]\\])", "]]"};
			String[] patternFeatured = new String[] {"(\\{\\{)(Link)(\\s*)(..)", "}}"};
			
			List<String[]> regexPatterns = new ArrayList<String[]>();
			regexPatterns.add(patternStandard);
			regexPatterns.add(patternFeatured);
			
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
									if (ILLComponents.length<3 && ILLComponents.length>1) { //Filter the links that matched the template by mistake, 
																							//or "incomplete" links like "ar:"
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
									if (ILLComponents.length>1) {
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
								}						
								matchPos = illEnd;
								if (link.isInitialized()) {
									result.put(link.getSameAsPageTitle(), link);
								} else {
									if (link.isHasSpecialMark()) {
										StringBuilder nameBuilder = new StringBuilder();
										nameBuilder.append(link.getSameAsPageTitle());
										
										if (link.isGood())
											nameBuilder.append("#GA");
										if (link.isFeatured())
											nameBuilder.append("#FA");
										result.put(nameBuilder.toString(), link);
									}
								}
							} else {
								matchPos += regexPatterns.get(i)[1].length();
							}
						}					
					} else {
						isMatch = false;
					}					
					link = null;
				} while (matchPos>0 && matchPos <= data.length() && isMatch);
				matcher = null;
				pattern = null;
			}
			regexPatterns = null;
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
	 * Trackings of last begin position and remaining chunks are removed.
	 * @param wikiDataAsString Chunk of the wikidata
	 * @param ignoreUnnamedAttributes Whether unnamed attributes should be ignored
	 * @param isNamesToLowerCase Whether attribute names should be translated to lower case
	 * @return
	 */
	public static InfoboxExtractionObject extractInfboxesFromUnstructuredText(String wikiDataAsString, boolean ignoreUnnamedAttributes, boolean isNamesToLowerCase) {
		//Parse the wikidata, extract infoboxes
		List<Infobox> infoboxesRetrieved = new ArrayList<Infobox>(INFOBOX_SET_INITIAL_CAPACITY);
		int indexOfBeginning = 0;
		int filelength = wikiDataAsString.length();
		
		//String remainderString = "";
		//int lastBeginPosition = 0;
		//Parsing the chunk
		boolean fileEndExceeded = false;
		do {
			int initialPosition = wikiDataAsString.indexOf(INFOBOX_BEGINNING, indexOfBeginning) + DOUBLE_OPEN.length();
			if (initialPosition==1 || initialPosition==-1) {
				fileEndExceeded = true;
				//remainderString = wikiDataAsString.substring(lastBeginPosition, filelength);
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
					//remainderString = wikiDataAsString.substring(lastBeginPosition, filelength);
					break;
				}
			}
			
			if (!fileEndExceeded) {
				String infoboxAsString = wikiDataAsString.substring(initialPosition, endPosition-DOUBLE_OPEN.length()+1);
				Infobox box = getInfoBox(infoboxAsString, ignoreUnnamedAttributes, isNamesToLowerCase);
				if (box!=null)
					infoboxesRetrieved.add(box);
				indexOfBeginning = endPosition+1;
				//lastBeginPosition = endPosition+1;
			}				
		} while (indexOfBeginning < filelength && !fileEndExceeded);
		
		return new InfoboxExtractionObject(infoboxesRetrieved, "", 0);
	}
	
	/**
	 * Returns an {@link Infobox} object from a given string representation
	 * @param infoboxAsString 
	 * @return
	 */
	private static Infobox getInfoBox(String infoboxAsString, boolean mIgnoreUnnamedAttributes, boolean mToLowerCase) {
		String[] arr = infoboxAsString.split("\n");
		String infoboxClass = "";
		List<InfoboxAttribute> attributes = new ArrayList<InfoboxAttribute>(ATTRIBUTE_SET_INITIAL_CAPACITY);
		try {
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
					if (arrAtt.length>0) {
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
							attributes.add(attribute);
						}
					}
				}
			}
			
			if (mToLowerCase)
				infoboxClass = infoboxClass.toLowerCase();
			
			infoboxClass = infoboxClass.trim();
		} catch (Exception ex) {
			if (GlobalVariables.IS_DEBUG){
				ex.printStackTrace();
			}
		}
		
		return new Infobox(infoboxClass, attributes);
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
