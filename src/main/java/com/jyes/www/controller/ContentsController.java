package com.jyes.www.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jyes.www.service.impl.ITemplateService;
import com.jyes.www.util.LogUtils;
import com.jyes.www.util.StringUtil;
import com.jyes.www.vo.TemplateVo;

/**
 * Handles requests for the application home page.
 */
@Controller(value = "contentsController")
public class ContentsController {

	private static final Logger log = LoggerFactory.getLogger(ContentsController.class);
	
	@Resource(name = "templateService")
	private ITemplateService templateService;

	@RequestMapping(value = "/contents/images/url", method = {RequestMethod.GET, RequestMethod.POST})
	public String saveContents(@RequestParam("s_id") String s_id,
			@RequestParam("c_id") String c_id, HttpServletRequest request, HttpServletResponse response, Model model) {
		StringBuffer logData = new StringBuffer();
		HashMap requestMap = LogUtils.GetPrameterMap(request, logData);
		String currentUrl = request.getRequestURL().toString();
		String StartUrl = "/"+currentUrl.substring(currentUrl.indexOf(currentUrl.split("/")[3]));
		if(request.getQueryString() != null) {
			currentUrl = currentUrl+"?"+request.getQueryString();
		}
		String tag = StartUrl;
		long strartTime = System.currentTimeMillis();
		String logKey = LogUtils.getUserLogKey(request);
		logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"logKey:"+logKey+":"+StartUrl+"\n");
		logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"StartUrl : "+StartUrl+"\n");
		logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"CurrentUrl : "+currentUrl+"\n");
		logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"CallUrl : "+StringUtil.nvl((String)request.getHeader("REFERER"))+"\n");
		logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"requestMap : "+requestMap+"\n");
		
		String id = StringUtil.nvl(request.getParameter("id"), request.getParameter("salt_id"));
		String affiliates_code = StringUtil.nvl(request.getParameter("affiliates_code"));
		
		if (!"".equals(id) && !"".equals(s_id) && !"".equals(c_id)) {
			TemplateVo templateVo = new TemplateVo();
			templateVo.setTemplate_seq(c_id);
			templateVo.setTemplate_type_code(s_id);
			templateVo.setId(id);
			templateVo.setAffiliates_code(affiliates_code);
			try {
				// 컨텐츠 조회
				logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"query start selectContents TemplateVo templateVo"+templateVo+"\n");
				templateVo = templateService.selectContents(templateVo);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"query error selectContents : "+e.toString()+"\n");
			}
			logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"query end selectContents TemplateVo templateVo : "+templateVo+"\n");
			if (templateVo != null) {
				model.addAttribute("templateVo", templateVo);
			}
		}
		HashMap hashMap = (HashMap)model.asMap();
		Iterator<String> iterator = hashMap.keySet().iterator();
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 시작 ----\n");
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "key = " + key+"\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 종료 ----\n");
		long endTime = System.currentTimeMillis()-strartTime; 
		logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"tag:"+tag+":전체:endTime:"+endTime+"\n");
		if(endTime > 15000) {
			logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"tag:"+tag+":전체:endTime:"+endTime+",connection time out[15second over]"+"\n");
		}
		log.info(logData.toString());
		return "contents/images/url";
	}

}