package com.jyes.www.network;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by JYES-YDH on 2016-10-18.
 */
public class NetworkTask extends Thread {
	
	private static final Log log = LogFactory.getLog(NetworkTask.class);

	private Request mRequest;
	private OnResponseListener mOnResponseListener;

	public static final int HTTP_CONNECT_TIMEOUT = 15 * 1000;
	public static final int HTTP_READ_TIMEOUT = 15 * 1000;

	public NetworkTask(Request request) {
		mRequest = request;
	}

	public NetworkTask(Request request, OnResponseListener onResponseListener) {
		mRequest = request;
		mOnResponseListener = onResponseListener;
	}

	@Override
	public void run() {
		super.run();
		final Response fResponse = doInBackground();
		onPostExecute(fResponse);
	}

	public Response doInBackground() {
//		log.info("request:"+mRequest);
		String url = mRequest.getUrl();
		Response response = new Response();
		response.setApi(mRequest.getApi());
		response.setRequestParams(mRequest.getParams());
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		// 헤더 세팅
		HttpHeaders requestHeaders = new HttpHeaders();
		HttpEntity<MultiValueMap<String, Object>> entity = null;
		if (mRequest.getApi().getMethod().equals(Api.POST)) {
			final MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
			Iterator<String> iterator = mRequest.getParams().keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				formData.add(key + "", mRequest.getParams().get(key));
			}
			if (mRequest.getParamType() == Request.STRING_TYPE) {
				entity = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);  
			} else if (mRequest.getParamType() == Request.JSON_TYPE) {
				
			} else {
				entity = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);  
			}
		}
		try {
			String logUrl = url.split("\\?")[0] + "?";
			Iterator<String> keys = mRequest.getParams().keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				String vaalue = mRequest.getParams().get(key)+"";
				logUrl += key + "=" + vaalue + "&";
			}
			logUrl = logUrl.substring(0, logUrl.length() - 1);
//			log.info("logUrl:"+logUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		log.info("entity:"+entity);
		ResponseEntity<String> responseEntyty = restTemplate.postForEntity(url.split("\\?")[0], entity, String.class);
		if (responseEntyty != null) {
			if (responseEntyty.getStatusCode() == HttpStatus.OK
					|| responseEntyty.getStatusCode() == HttpStatus.CREATED) {
				response.setResponse(responseEntyty.getBody().toString());
				response.setHeaders(responseEntyty.getHeaders());
			}
		}
//		log.info("response:"+response);
		return response;
	}
	
	public void test() {
		
	}
	
	@Override
	public synchronized void start() {
		onPreExecute();
		super.start();
	}

	public void onPreExecute() {
	}

	public void onPostExecute(Response response) {
		mOnResponseListener.onResponse(response);
	}

	public ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(HTTP_READ_TIMEOUT);
		factory.setConnectTimeout(HTTP_CONNECT_TIMEOUT);
		return factory;
	}

}