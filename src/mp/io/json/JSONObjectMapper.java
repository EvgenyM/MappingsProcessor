package mp.io.json;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;

import mp.dataclasses.WikiPage;

public class JSONObjectMapper {
	
	public static String asJson(WikiPage page) {
		StringBuilder sb = new StringBuilder(StringUtils.EMPTY);
		sb.append("{\"pageId\":")
		  .append(page.getPageId())
		  .append(",\"pageContent\":\"")
		  .append(page.getPageContent())
		  .append("\"}\n");
		return sb.toString();
	}
	
	public static WikiPage getWikiPage(String jsonAsString) {
		return (WikiPage) getJsonObject(jsonAsString, WikiPage.class);
	}
	
	public static String getWikiPageAsString(WikiPage wp) {
		return asJsonString(wp);
	}
	
	@SuppressWarnings("unchecked")
	public static <K> String asJsonString(Object obj) {
		String str = null;
		try {
			str = getObjectMapper().writeValueAsString((K) obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static <K> Object getJsonObject(String jsonAsString, Class<K> valueType) {

		K obj = null;

		try {
			obj = getObjectMapper().readValue(jsonAsString, valueType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}

	private static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper;
	}
}