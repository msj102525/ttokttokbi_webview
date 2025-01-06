package com.jyes.www.network;

import org.springframework.http.HttpHeaders;

import java.util.HashMap;

public class Response {

	private Api api;
	private String error;
	private String response;
	private HttpHeaders headers;
	private HashMap<String, Object> requestParams;

	public Api getApi() {
		return api;
	}

	public void setApi(Api api) {
		this.api = api;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	public HashMap<String, Object> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(HashMap<String, Object> requestParams) {
		this.requestParams = requestParams;
	}

	@Override
	public String toString() {
		return toString(false);
	}

	public String toString(boolean isPrettyJson) {
		String prettyJson = response;
		return new StringBuilder().append("\napi=[").append(api).append("]\nerror=").append(error).append("\nresponse=").append(prettyJson).toString();
	}

}
