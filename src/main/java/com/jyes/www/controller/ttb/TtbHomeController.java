package com.jyes.www.controller.ttb;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jyes.www.service.ttb.template.ITemplateService;
import com.jyes.www.util.LogUtils;

/**
 * Handles requests for the application home page.
 */
@Controller(value = "ttbHomeController")
public class TtbHomeController {

	private static final Log log = LogFactory.getLog(TtbHomeController.class);
	@Resource(name = "ttbTemplateService")
	private ITemplateService templateService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/ttb", method = RequestMethod.GET)
	public String home(@RequestParam("image_id") String image_id) {
		String imgId = "";
		imgId = image_id;

		StringBuffer logData = new StringBuffer();
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "call home :" + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "imageId : " + imgId + "\n");
		String tempUrl = "template";
		if (!"".equals(imgId)) {
			int imgIdVal = Integer.parseInt(imgId);
			switch (imgIdVal) {
				// 약도
				case 1:
					tempUrl = tempUrl + "/map/map_template";
					break;
				case 6:
					tempUrl = tempUrl + "/map/template_house";
					break;
				case 7:
					tempUrl = tempUrl + "/map/template_phone";
					break;
				// 전단지
				case 2:
					tempUrl = tempUrl + "/food/food_template";
					break;
				case 8:
					tempUrl = tempUrl + "/food/template_mart";
					break;
				case 9:
					tempUrl = tempUrl + "/food/template_yoga";
					break;
				// 명함
				case 3:
					tempUrl = tempUrl + "/namecard/namecard_template";
					break;
				case 10:
					tempUrl = tempUrl + "/namecard/template_card";
					break;
				case 11:
					tempUrl = tempUrl + "/namecard/template_card2";
					break;
				// 이벤트
				case 5:
					tempUrl = tempUrl + "/event/event_template";
					break;
				case 12:
					tempUrl = tempUrl + "/holiday/template_beauty";
					break;
				case 13:
					tempUrl = tempUrl + "/holiday/template_health";
					break;
				// 휴무
				case 4:
					tempUrl = tempUrl + "/holiday/holiday_template";
					break;
				case 14:
					tempUrl = tempUrl + "/event/template_company";
					break;
				case 15:
					tempUrl = tempUrl + "/event/template_kids";
					break;
				// JSAM 똑똑비 새로운 템플릿 추가
				case 16:
					tempUrl = tempUrl + "/food/template_pc";
					break;
				default:
					break;
			}
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tempUrl : " + tempUrl + "\n");
		log.info(logData.toString());
		return tempUrl;
	}
}
