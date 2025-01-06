package com.jyes.www.util;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.urlshortener.Urlshortener;
import com.google.api.services.urlshortener.UrlshortenerRequestInitializer;
import com.google.api.services.urlshortener.model.Url;
import com.jyes.www.common.Config;

public class ShortenUrlGoogle {
	
	/**
	 * -------------------------------------
	 * https://console.developers.google.com
	 * jyesdev@gmail.com
	 * -------------------------------------
	 * 	<!-- https://mvnrepository.com/artifact/com.google.apis/google-api-services-urlshortener -->
	 * 	<dependency>
	 * 		<groupId>com.google.apis</groupId>
	 * 		<artifactId>google-api-services-urlshortener</artifactId>
	 * 		<version>v1-rev48-1.22.0</version>
	 * 	</dependency>
	 */

	public static String makeShortUrl(String longUrl) {
		String url = "";
		try {
			ShortenUrlGoogle sample = new ShortenUrlGoogle();
			UrlshortenerRequestInitializer initializer = sample.authorize();
			Urlshortener service = sample.getService(initializer);
			Url shortUrl = sample.getShortUrl(service, longUrl);
			url = shortUrl.getId();
			System.out.println(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	private Url getShortUrl(Urlshortener service, String longUrl) throws Exception {
		Url url = new Url().setLongUrl(longUrl);
		Url result = service.url().insert(url).execute();
		return result;
	}

	private UrlshortenerRequestInitializer authorize() throws Exception {
		return new UrlshortenerRequestInitializer(Config.API_KEY);
	}

	private Urlshortener getService(UrlshortenerRequestInitializer initializer) throws Exception {
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		Urlshortener service = new Urlshortener.Builder(httpTransport, jsonFactory, null)
				.setUrlshortenerRequestInitializer(initializer).setApplicationName(Config.APPLICATION_NAME).build();
		return service;
	}

}
