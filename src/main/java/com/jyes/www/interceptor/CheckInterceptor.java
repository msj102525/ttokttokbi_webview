package com.jyes.www.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CheckInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(CheckInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String userAgent = (String)request.getHeader("User-Agent").toLowerCase();
		String[] chkAgent = {"daumoa", "naverbot", "Cowbot", "Googlebot", "googlebot-image", "googlebot-mobile", "MSNBot", "psbot", "Yahoo-MMCrawler", "Slurp", "yahoo-blogs/v3.9", "ia_archiver", "baiduspider", "WebZIP", "Teleport", "GetRight", "WebCopier", "NetZip", "Teleport"};
		for(int i=0; i<chkAgent.length; i++){
			int state = userAgent.indexOf(chkAgent[i].toLowerCase());
			if(state > 0){
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
			}
		}
//		System.out.println("User Agent Check");
		return true;
	}
	
}
