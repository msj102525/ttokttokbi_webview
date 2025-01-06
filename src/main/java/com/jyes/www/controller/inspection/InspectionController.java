package com.jyes.www.controller.inspection;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyes.www.service.inspection.impl.ITbInspectionService;
import com.jyes.www.util.LogUtils;
import com.jyes.www.util.StringUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public class InspectionController {

	private static final Log log = LogFactory.getLog(InspectionController.class);

	@Resource(name = "tbInspectionService")
	private ITbInspectionService tbInspectionService;
	
	/**
	 * 웹서버 및 DB 서버 정상 유무 확인
	 */
	@RequestMapping(value = "/check_server", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody HashMap<Object, Object> checkServer(HttpServletRequest request) throws Exception {
		StringBuffer logData = new StringBuffer();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		HashMap<Object, Object> meta = new HashMap<Object, Object>();
		HashMap<Object, Object> data = new HashMap<Object, Object>();
		String is_db_check = StringUtil.nvl(request.getParameter("is_db_check"),"N");
		is_db_check = is_db_check.toUpperCase();
		try {
			tbInspectionService.selectCheckServer();
		} catch(Exception e) {
			e.printStackTrace();
			meta.put("code", 999);
			meta.put("error_type", "Exception");
			meta.put("error_message", e.toString());
		}
		if(meta.get("code")==null) {
			meta.put("code", 200);
		}
		data.put("is_db_check", is_db_check);
		map.put("meta", meta);
		map.put("data", data);
		logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"Inspection map:"+map);
		log.info(logData.toString());
		return map;
	}
	
}