package com.jyes.www.controller.ttb;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jyes.www.util.LogUtils;
import com.jyes.www.util.StringUtil;

@Controller(value = "ttbTermsController")
public class TtbTermsController {

	private static final Logger log = LoggerFactory.getLogger(TtbTermsController.class);

	/**
	 * 이용약관
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ttb/info/terms", method = { RequestMethod.GET, RequestMethod.POST })
	public String agree(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer logData = new StringBuffer();
		HashMap requestMap = LogUtils.GetPrameterMap(request, logData);
		String currentUrl = request.getRequestURL().toString();
		String StartUrl = "/" + currentUrl.substring(currentUrl.indexOf(currentUrl.split("/")[3]));
		if (request.getQueryString() != null) {
			currentUrl = currentUrl + "?" + request.getQueryString();
		}
		String tag = StartUrl;
		long strartTime = System.currentTimeMillis();
		String logKey = LogUtils.getUserLogKey(request);
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "logKey:" + logKey + ":" + StartUrl + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "StartUrl : " + StartUrl + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "CurrentUrl : " + currentUrl + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "CallUrl : "
				+ StringUtil.nvl((String) request.getHeader("REFERER")) + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "requestMap : " + requestMap + "\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return "/info/terms";
	}

	/**
	 * 개인정보 수집 및 이용
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ttb/info/privacy", method = { RequestMethod.GET, RequestMethod.POST })
	public String privacy(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer logData = new StringBuffer();
		HashMap requestMap = LogUtils.GetPrameterMap(request, logData);
		String currentUrl = request.getRequestURL().toString();
		String StartUrl = "/" + currentUrl.substring(currentUrl.indexOf(currentUrl.split("/")[3]));
		if (request.getQueryString() != null) {
			currentUrl = currentUrl + "?" + request.getQueryString();
		}
		String tag = StartUrl;
		long strartTime = System.currentTimeMillis();
		String logKey = LogUtils.getUserLogKey(request);
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "logKey:" + logKey + ":" + StartUrl + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "StartUrl : " + StartUrl + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "CurrentUrl : " + currentUrl + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "CallUrl : "
				+ StringUtil.nvl((String) request.getHeader("REFERER")) + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "requestMap : " + requestMap + "\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return "/info/privacy";
	}

	/**
	 * 개인정보 제3자 제공에 대한 동의
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ttb/info/marketing", method = { RequestMethod.GET, RequestMethod.POST })
	public String marketing(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer logData = new StringBuffer();
		HashMap requestMap = LogUtils.GetPrameterMap(request, logData);
		String currentUrl = request.getRequestURL().toString();
		String StartUrl = "/" + currentUrl.substring(currentUrl.indexOf(currentUrl.split("/")[3]));
		if (request.getQueryString() != null) {
			currentUrl = currentUrl + "?" + request.getQueryString();
		}
		String tag = StartUrl;
		long strartTime = System.currentTimeMillis();
		String logKey = LogUtils.getUserLogKey(request);
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "logKey:" + logKey + ":" + StartUrl + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "StartUrl : " + StartUrl + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "CurrentUrl : " + currentUrl + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "CallUrl : "
				+ StringUtil.nvl((String) request.getHeader("REFERER")) + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "requestMap : " + requestMap + "\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return "/info/marketing";
	}

	/**
	 * 미정
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ttb/info/thirdParty", method = { RequestMethod.GET, RequestMethod.POST })
	public String thirdpParty(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer logData = new StringBuffer();
		HashMap requestMap = LogUtils.GetPrameterMap(request, logData);
		String currentUrl = request.getRequestURL().toString();
		String StartUrl = "/" + currentUrl.substring(currentUrl.indexOf(currentUrl.split("/")[3]));
		if (request.getQueryString() != null) {
			currentUrl = currentUrl + "?" + request.getQueryString();
		}
		String tag = StartUrl;
		long strartTime = System.currentTimeMillis();
		String logKey = LogUtils.getUserLogKey(request);
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "logKey:" + logKey + ":" + StartUrl + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "StartUrl : " + StartUrl + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "CurrentUrl : " + currentUrl + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "CallUrl : "
				+ StringUtil.nvl((String) request.getHeader("REFERER")) + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "requestMap : " + requestMap + "\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return "/info/marketing";
	}

}
