package com.jyes.www.network;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
/*import com.jyes.www.utils.Aligo;*/
import com.jyes.www.common.Config;

public class Request {
	private String url;
	private final Api api;
	private ArrayList<FilePart> partList;
	private HashMap<String, Object> params = new HashMap<String, Object>();
	private JsonObject jsonParams = new JsonObject();
	private int paramType;
	public static final int JSON_TYPE = 0x01;
	public static final int STRING_TYPE = 0x02;
	public static final int HTTP_READ_TIMEOUT = 15 * 1000;

	public Request(Api api) {
		this.api = api;
		/*if(api.getKind().equals("aligo")) {
			url = Aligo.ALIGO_SERVER_URL + api.getUrl();
		}
		if(api.getKind().equals("unsubscribe")) {
			url = Config.UNSUBSCRIBE_SERVER_URL + api.getUrl();
		}*/
		if(api.getKind().equals("smartro")) {
			url = Config.HOST_SMARTRO_SERVER_URL + api.getUrl();
		}
		if (api.getMethod().equals(Api.GET)) {
			addParamKey();
		}
	}

	public void addParamKey() {
		String[] urlParts = getUrl().split("\\?");
		if (urlParts.length < 2) {
			return;
		}
		String query = urlParts[1];
		for (String param : query.split("&")) {
			String[] pair = param.split("=");
			String key = "";
			try {
				key = URLDecoder.decode(pair[0], "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// skip ?& and &&
			if ("".equals(key)) {
				continue;
			}
			params.put(key, "");
		}
	}
	
	public void addJsonParameter(String key, Object value) {
		/*if (value instanceof JsonArray) {
			type new_name = (JsonArray) value;
		}if (value instanceof String) {
			jsonParams.add(key, (String)value);
		}*/
	}

	public void addParameter(String key, Object value) {
		params.put(key, value);
	}

	public void addUrlParameter(String url) {
		if (url == null) {
			return;
		}
		String[] urlParts = url.split("\\?");
		if (urlParts.length < 2) {
			return;
		}
		String query = urlParts[1];
		for (String param : query.split("&")) {
			String[] pair = param.split("=");
			String key = "";
			try {
				key = URLDecoder.decode(pair[0], "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String value = "";
			if (pair.length > 1) {
				try {
					value = URLDecoder.decode(pair[1], "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			// skip ?& and &&
			if ("".equals(key)) {
				continue;
			}
			params.put(key, value);
		}
	}

	public void addPart(String key, File part) {
		if (part == null) {
			new RuntimeException("File part must not null");
		}
		if (partList == null) {
			partList = new ArrayList<FilePart>();
		}
		partList.add(new FilePart(key, part));
	}

	public ArrayList<FilePart> getPartList() {
		return partList;
	}

	public String getUrl() {
		return url;
	}

	public Api getApi() {
		return api;
	}

	public HashMap<String, Object> getParams() {
		return params;
	}

	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getParamType() {
		return paramType;
	}

	public void setParamType(int paramType) {
		this.paramType = paramType;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("api=[").append(api).append("],url=").append(url).toString();
	}

	public static class FilePart {
		private String key;
		private File part;

		public FilePart(String key, File part) {
			this.setKey(key);
			this.setPart(part);
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public File getPart() {
			return part;
		}

		public void setPart(File part) {
			this.part = part;
		}
	}
	
	public static void main(String[] args) {
		String value = "";
		JsonArray al = new JsonArray();
		JsonObject json = new JsonObject();
//		json.addProperty("1", al);
		
	}

}
