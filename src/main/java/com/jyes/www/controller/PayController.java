package com.jyes.www.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.services.androidpublisher.model.SubscriptionPurchase;
import com.inicis.inipay.INIpay50;
import com.inicis.inipay4.INIpay;
import com.inicis.inipay4.util.INIdata;
import com.jyes.www.common.Config;
import com.jyes.www.google.billing.GoogleInApp;
import com.jyes.www.service.impl.IPayService;
import com.jyes.www.util.LogUtils;
import com.jyes.www.util.StringUtil;
import com.jyes.www.vo.AccessVo;
import com.jyes.www.vo.CreateOidVo;
import com.jyes.www.vo.PayCancelExeLogVo;
import com.jyes.www.vo.PayCancelUserInfoVo;
import com.jyes.www.vo.PayCustomVo;
import com.jyes.www.vo.PayExeLogVo;
import com.jyes.www.vo.PayNameCompanyVo;
import com.jyes.www.vo.PayTypeVo;
import com.jyes.www.vo.UserUsePayVo;

/**
 * Handles requests for the application home page.
 */
@Controller(value = "payController")
public class PayController {

	private static final Logger log = LoggerFactory.getLogger(PayController.class);

	@Resource(name = "payService")
	private IPayService payService;

	/**
	 * 이용권 UI(일반결제)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ini/mobile/tikets/tiketsRequest", method = { RequestMethod.GET, RequestMethod.POST })
	public String tiketsRequest(HttpServletRequest request, HttpServletResponse response, Model model) {
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
		String id = StringUtil.nvl(request.getParameter("id"));
		String affiliates_code = StringUtil.nvl(request.getParameter("affiliates_code"));
		String email = StringUtil.nvl(request.getParameter("email"));
		String code_move = StringUtil.nvl(request.getParameter("code"));
		String sim = StringUtil.nvl(request.getParameter("sim"));
		String googleadid = StringUtil.nvl(request.getParameter("googleadid"));
		String pkgInstaller = StringUtil.nvl(request.getParameter("pkgInstaller"));
		String app_store_type = StringUtil.nvl(request.getParameter("store_type"));
		String useragent = StringUtil.nvl(request.getParameter("useragent"));
		String p_mid = Config.INI_P_MID;
		String host = Config.HOST_CONTENTS_IMAGE_MAKE;

		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "useragent:" + useragent + "\n");
		String app_version = "";
		if (!"".equals(useragent)) {
			try {
				// 앱 버전
				app_version = StringUtil.getAppVersion(useragent);
				app_version = app_version.replaceAll("[.]", "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		AccessVo accessVo = new AccessVo();
		accessVo.setId(id);
		accessVo.setAffiliates_code(affiliates_code);
		try {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query start selectUserInfo AccessVo accessVo:"
					+ accessVo + "\n");
			accessVo = (AccessVo) payService.selectUserInfo(accessVo);
		} catch (Exception e) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectUserInfo : " + e.toString()
					+ "\n");
			e.printStackTrace();
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query end selectUserInfo AccessVo accessVo:"
				+ accessVo + "\n");
		if (accessVo != null) {
			String store_type = StringUtil.nvl(accessVo.getStore_type());
			model.addAttribute("store_type", store_type);
		}

		PayNameCompanyVo pncv = new PayNameCompanyVo();
		pncv.setId(id);
		pncv.setAffiliates_code(affiliates_code);
		try {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayNameCompany PayNameCompanyVo payNameCompanyVo" + pncv + "\n");
			pncv = payService.selectPayNameCompany(pncv);
		} catch (Exception e) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayNameCompany : "
					+ e.toString() + "\n");
			e.printStackTrace();
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayNameCompany PayNameCompanyVo payNameCompanyVo" + pncv + "\n");

		String code = "P0009";
		PayTypeVo payTypeVo_P0009 = new PayTypeVo();
		try {
			payTypeVo_P0009.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo_P0009:" + payTypeVo_P0009 + "\n");
			payTypeVo_P0009 = payService.selectPayTypeWhereCode(payTypeVo_P0009);
			if (payTypeVo_P0009 != null) {
				model.addAttribute("payTypeVo_P0009", payTypeVo_P0009);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_P0009:" + payTypeVo_P0009 + "\n");

		code = "P0017";
		PayTypeVo payTypeVo_P0017 = new PayTypeVo();
		try {
			payTypeVo_P0017.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo_P0017:" + payTypeVo_P0017 + "\n");
			payTypeVo_P0017 = payService.selectPayTypeWhereCode(payTypeVo_P0017);
			if (payTypeVo_P0017 != null) {
				model.addAttribute("payTypeVo_P0017", payTypeVo_P0017);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_P0017:" + payTypeVo_P0017 + "\n");

		code = "P0018";
		PayTypeVo payTypeVo_P0018 = new PayTypeVo();
		try {
			payTypeVo_P0018.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo_P0018:" + payTypeVo_P0018 + "\n");
			payTypeVo_P0018 = payService.selectPayTypeWhereCode(payTypeVo_P0018);
			if (payTypeVo_P0018 != null) {
				model.addAttribute("payTypeVo_P0018", payTypeVo_P0018);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_P0018:" + payTypeVo_P0018 + "\n");

		code = "P0019";
		PayTypeVo payTypeVo_P0019 = new PayTypeVo();
		try {
			payTypeVo_P0019.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo_P0019:" + payTypeVo_P0019 + "\n");
			payTypeVo_P0019 = payService.selectPayTypeWhereCode(payTypeVo_P0019);
			if (payTypeVo_P0019 != null) {
				model.addAttribute("payTypeVo_P0019", payTypeVo_P0019);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_P0019:" + payTypeVo_P0019 + "\n");

		code = "P0016";
		PayTypeVo payTypeVo_P0016 = new PayTypeVo();
		try {
			payTypeVo_P0016.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo_P0016:" + payTypeVo_P0016 + "\n");
			payTypeVo_P0016 = payService.selectPayTypeWhereCode(payTypeVo_P0016);
			if (payTypeVo_P0016 != null) {
				model.addAttribute("payTypeVo_P0016", payTypeVo_P0016);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_P0016:" + payTypeVo_P0016 + "\n");

		code = "P0012";
		PayTypeVo payTypeVo_P0012 = new PayTypeVo();
		try {
			payTypeVo_P0012.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo_P0012:" + payTypeVo_P0012 + "\n");
			payTypeVo_P0012 = payService.selectPayTypeWhereCode(payTypeVo_P0012);
			if (payTypeVo_P0012 != null) {
				model.addAttribute("payTypeVo_P0012", payTypeVo_P0012);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_P0012:" + payTypeVo_P0012 + "\n");

		code = "P0028";
		PayTypeVo payTypeVo_P0028 = new PayTypeVo();
		try {
			payTypeVo_P0028.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo_P0028:" + payTypeVo_P0028 + "\n");
			payTypeVo_P0028 = payService.selectPayTypeWhereCode(payTypeVo_P0028);
			if (payTypeVo_P0028 != null) {
				model.addAttribute("payTypeVo_P0028", payTypeVo_P0028);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_P0028:" + payTypeVo_P0028 + "\n");

		code = "P0029";
		PayTypeVo payTypeVo_P0029 = new PayTypeVo();
		try {
			payTypeVo_P0029.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo_P0029:" + payTypeVo_P0029 + "\n");
			payTypeVo_P0029 = payService.selectPayTypeWhereCode(payTypeVo_P0029);
			if (payTypeVo_P0029 != null) {
				model.addAttribute("payTypeVo_P0029", payTypeVo_P0029);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_P0029:" + payTypeVo_P0029 + "\n");

		code = "P0030";
		PayTypeVo payTypeVo_P0030 = new PayTypeVo();
		try {
			payTypeVo_P0030.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo_P0030:" + payTypeVo_P0030 + "\n");
			payTypeVo_P0030 = payService.selectPayTypeWhereCode(payTypeVo_P0030);
			if (payTypeVo_P0030 != null) {
				model.addAttribute("payTypeVo_P0030", payTypeVo_P0030);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_P0030:" + payTypeVo_P0030 + "\n");

		code = "P0031";
		PayTypeVo payTypeVo_P0031 = new PayTypeVo();
		try {
			payTypeVo_P0031.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo_P0031:" + payTypeVo_P0031 + "\n");
			payTypeVo_P0031 = payService.selectPayTypeWhereCode(payTypeVo_P0031);
			if (payTypeVo_P0031 != null) {
				model.addAttribute("payTypeVo_P0031", payTypeVo_P0031);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_P0031:" + payTypeVo_P0031 + "\n");

		code = "P0032";
		PayTypeVo payTypeVo_P0032 = new PayTypeVo();
		try {
			payTypeVo_P0032.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo_P0032:" + payTypeVo_P0032 + "\n");
			payTypeVo_P0032 = payService.selectPayTypeWhereCode(payTypeVo_P0032);
			if (payTypeVo_P0032 != null) {
				model.addAttribute("payTypeVo_P0032", payTypeVo_P0032);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_P0032:" + payTypeVo_P0032 + "\n");

		code = "P0033";
		PayTypeVo payTypeVo_P0033 = new PayTypeVo();
		try {
			payTypeVo_P0033.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo_P0033:" + payTypeVo_P0033 + "\n");
			payTypeVo_P0033 = payService.selectPayTypeWhereCode(payTypeVo_P0033);
			if (payTypeVo_P0033 != null) {
				model.addAttribute("payTypeVo_P0033", payTypeVo_P0033);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_P0033:" + payTypeVo_P0033 + "\n");

		code = "P0045";
		PayTypeVo payTypeVo_P0045 = new PayTypeVo();
		try {
			payTypeVo_P0045.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo_P0045:" + payTypeVo_P0045 + "\n");
			payTypeVo_P0045 = payService.selectPayTypeWhereCode(payTypeVo_P0045);
			if (payTypeVo_P0045 != null) {
				model.addAttribute("payTypeVo_P0045", payTypeVo_P0045);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_P0045:" + payTypeVo_P0045 + "\n");

		code = "P0046";
		PayTypeVo payTypeVo_P0046 = new PayTypeVo();
		try {
			payTypeVo_P0046.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo_P0046:" + payTypeVo_P0046 + "\n");
			payTypeVo_P0046 = payService.selectPayTypeWhereCode(payTypeVo_P0046);
			if (payTypeVo_P0046 != null) {
				model.addAttribute("payTypeVo_P0046", payTypeVo_P0046);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_P0046:" + payTypeVo_P0046 + "\n");

		UserUsePayVo userUsePayVo = null;
		try {
			PayCustomVo payCustomVo = new PayCustomVo();
			payCustomVo.setId(id);
			payCustomVo.setAffiliates_code(affiliates_code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectUserUsePayCode PayCustomVo payCustomVo:" + payCustomVo + "\n");
			userUsePayVo = (UserUsePayVo) payService.selectUserUsePayCode(payCustomVo);
			if (userUsePayVo != null) {
				model.addAttribute("userUsePayVo", userUsePayVo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectUserUsePayCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectUserUsePayCode UserUsePayVo userUsePayVo:" + userUsePayVo + "\n");

		model.addAttribute("id", id);
		model.addAttribute("affiliates_code", affiliates_code);
		model.addAttribute("email", email);
		model.addAttribute("name", StringUtil.nvl(pncv.getName()));
		model.addAttribute("company", StringUtil.nvl(pncv.getCompany()));
		model.addAttribute("p_mid", p_mid);
		model.addAttribute("host", host);
		model.addAttribute("code", code_move);
		model.addAttribute("sim", sim);
		model.addAttribute("googleadid", googleadid);
		model.addAttribute("pkgInstaller", pkgInstaller);
		model.addAttribute("app_store_type", app_store_type);
		model.addAttribute("app_version", app_version);
		HashMap hashMap = (HashMap) model.asMap();
		Iterator<String> iterator = hashMap.keySet().iterator();
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 시작 ----\n");
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "key = " + key + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 종료 ----\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return "ini/mobile/tikets/tikets_request";
	}

	/**
	 * 이용권 UI(무통장)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay/tiketsRequestDepositInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public String tiketsRequestDepositInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
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
		String code = StringUtil.nvl(request.getParameter("code"));
		String id = StringUtil.nvl(request.getParameter("id"));
		String affiliates_code = StringUtil.nvl(request.getParameter("affiliates_code"));
		String email = StringUtil.nvl(request.getParameter("email"));
		PayNameCompanyVo pncv = new PayNameCompanyVo();
		pncv.setId(id);
		pncv.setAffiliates_code(affiliates_code);
		try {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayNameCompany PayNameCompanyVo payNameCompanyVo" + pncv + "\n");
			pncv = payService.selectPayNameCompany(pncv);
		} catch (Exception e) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayNameCompany : "
					+ e.toString() + "\n");
			e.printStackTrace();
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayNameCompany PayNameCompanyVo payNameCompanyVo" + pncv + "\n");
		PayTypeVo payTypeVo = new PayTypeVo();
		try {
			payTypeVo.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo:" + payTypeVo + "\n");
			payTypeVo = payService.selectPayTypeWhereCode(payTypeVo);
			if (payTypeVo != null) {
				model.addAttribute("payTypeVo", payTypeVo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo:" + payTypeVo + "\n");

		UserUsePayVo userUsePayVo = null;
		try {
			PayCustomVo payCustomVo = new PayCustomVo();
			payCustomVo.setId(id);
			payCustomVo.setAffiliates_code(affiliates_code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectUserUsePayCode PayCustomVo payCustomVo:" + payCustomVo + "\n");
			userUsePayVo = (UserUsePayVo) payService.selectUserUsePayCode(payCustomVo);
			if (userUsePayVo != null) {
				model.addAttribute("userUsePayVo", userUsePayVo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectUserUsePayCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectUserUsePayCode UserUsePayVo userUsePayVo:" + userUsePayVo + "\n");

		model.addAttribute("id", id);
		model.addAttribute("affiliates_code", affiliates_code);
		model.addAttribute("email", email);
		model.addAttribute("name", StringUtil.nvl(pncv.getName()));
		HashMap hashMap = (HashMap) model.asMap();
		Iterator<String> iterator = hashMap.keySet().iterator();
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 시작 ----\n");
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "key = " + key + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 종료 ----\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return "pay/tikets_request_deposit_info";
	}

	/**
	 * 이용권 UI(정기결제)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ini/mobile/tikets/tiketsRequestMonthlyPaymentInfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String tiketsRequestMonthlyPaymentInfo(HttpServletRequest request, HttpServletResponse response,
			Model model) {
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
		String id = StringUtil.nvl(request.getParameter("id"));
		String affiliates_code = StringUtil.nvl(request.getParameter("affiliates_code"));
		String email = StringUtil.nvl(request.getParameter("email"));
		String code = StringUtil.nvl(request.getParameter("code"), "P0032");// P0009

		// 박준태 2023-06-20 / 박준태 2023-09-12 가격 8,800 -> 9,900원으로 복구
		// String code =
		// StringUtil.nvl(request.getParameter("code"),"P0045");//P0009,P0032

		String p_mid = Config.INI_B_P_MID;
		String host = Config.HOST_CONTENTS_IMAGE_MAKE;
		String sim = StringUtil.nvl(request.getParameter("sim"));
		String googleadid = StringUtil.nvl(request.getParameter("googleadid"));
		String pkgInstaller = StringUtil.nvl(request.getParameter("pkgInstaller"));
		String app_store_type = StringUtil.nvl(request.getParameter("store_type"));
		PayNameCompanyVo pncv = new PayNameCompanyVo();
		pncv.setId(id);
		pncv.setAffiliates_code(affiliates_code);
		try {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayNameCompany PayNameCompanyVo payNameCompanyVo" + pncv + "\n");
			pncv = payService.selectPayNameCompany(pncv);
		} catch (Exception e) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayNameCompany : "
					+ e.toString() + "\n");
			e.printStackTrace();
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayNameCompany PayNameCompanyVo payNameCompanyVo" + pncv + "\n");

		UserUsePayVo userUsePayVo = null;
		try {
			PayCustomVo payCustomVo = new PayCustomVo();
			payCustomVo.setId(id);
			payCustomVo.setAffiliates_code(affiliates_code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectUserUsePayCode PayCustomVo payCustomVo:" + payCustomVo + "\n");
			userUsePayVo = (UserUsePayVo) payService.selectUserUsePayCode(payCustomVo);
			if (userUsePayVo != null) {
				model.addAttribute("userUsePayVo", userUsePayVo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectUserUsePayCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectUserUsePayCode UserUsePayVo userUsePayVo:" + userUsePayVo + "\n");

		model.addAttribute("id", id);
		model.addAttribute("affiliates_code", affiliates_code);
		model.addAttribute("email", email);
		model.addAttribute("name", StringUtil.nvl(pncv.getName()));
		model.addAttribute("company", StringUtil.nvl(pncv.getCompany()));
		model.addAttribute("p_mid", p_mid);
		model.addAttribute("host", host);
		model.addAttribute("sim", sim);
		model.addAttribute("googleadid", googleadid);
		model.addAttribute("pkgInstaller", pkgInstaller);
		model.addAttribute("app_store_type", app_store_type);
		if ("".equals(code)) {
			// 8,800원에서 9,900원으로 가격 복구
			code = "P0032";// App에서 다이렉트로 해당 URL 호출시 코드 없음 P0009
			// code = "P0045";//App에서 다이렉트로 해당 URL 호출시 코드 없음 P0009, P0032
		}
		PayTypeVo payTypeVo = new PayTypeVo();
		try {
			payTypeVo.setCode(code);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo:" + payTypeVo + "\n");
			payTypeVo = payService.selectPayTypeWhereCode(payTypeVo);
			if (payTypeVo != null) {
				model.addAttribute("payTypeVo", payTypeVo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo:" + payTypeVo + "\n");
		HashMap hashMap = (HashMap) model.asMap();
		Iterator<String> iterator = hashMap.keySet().iterator();
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 시작 ----\n");
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "key = " + key + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 종료 ----\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return "ini/mobile/tikets/tikets_request_monthly_payment_info";
	}

	/**
	 * 제작의뢰 UI(일반결제)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ini/mobile/make/makeRequest", method = { RequestMethod.GET, RequestMethod.POST })
	public String makeRequest(HttpServletRequest request, HttpServletResponse response, Model model) {
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
		String id = StringUtil.nvl(request.getParameter("id"));
		String affiliates_code = StringUtil.nvl(request.getParameter("affiliates_code"));
		String p_mid = Config.INI_P_MID;
		String host = Config.HOST_CONTENTS_IMAGE_MAKE;
		String sim = StringUtil.nvl(request.getParameter("sim"));
		String googleadid = StringUtil.nvl(request.getParameter("googleadid"));
		String pkgInstaller = StringUtil.nvl(request.getParameter("pkgInstaller"));
		String app_store_type = StringUtil.nvl(request.getParameter("store_type"));
		String useragent = StringUtil.nvl(request.getParameter("useragent"));

		String app_version = "";
		if (!"".equals(useragent)) {
			try {
				// 앱 버전
				app_version = StringUtil.getAppVersion(useragent);
				app_version = app_version.replaceAll("[.]", "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		PayNameCompanyVo pncv = new PayNameCompanyVo();
		pncv.setId(id);
		pncv.setAffiliates_code(affiliates_code);
		try {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayNameCompany PayNameCompanyVo payNameCompanyVo" + pncv + "\n");
			pncv = payService.selectPayNameCompany(pncv);
		} catch (Exception e) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayNameCompany : "
					+ e.toString() + "\n");
			e.printStackTrace();
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayNameCompany PayNameCompanyVo payNameCompanyVo" + pncv + "\n");
		model.addAttribute("id", id);
		model.addAttribute("affiliates_code", affiliates_code);
		model.addAttribute("name", StringUtil.nvl(pncv.getName()));
		model.addAttribute("company", StringUtil.nvl(pncv.getCompany()));
		model.addAttribute("p_mid", p_mid);
		model.addAttribute("host", host);
		model.addAttribute("sim", sim);
		model.addAttribute("googleadid", googleadid);
		model.addAttribute("pkgInstaller", pkgInstaller);
		model.addAttribute("app_store_type", app_store_type);
		model.addAttribute("app_version", app_version);
		HashMap hashMap = (HashMap) model.asMap();
		Iterator<String> iterator = hashMap.keySet().iterator();
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 시작 ----\n");
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "key = " + key + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 종료 ----\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return "ini/mobile/make/make_request";
	}

	/**
	 * 재제작의뢰 UI(일반결제)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ini/mobile/make/makeHalfRequest", method = { RequestMethod.GET, RequestMethod.POST })
	public String makeHalfRequest(HttpServletRequest request, HttpServletResponse response, Model model) {
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
		String id = StringUtil.nvl(request.getParameter("id"));
		String affiliates_code = StringUtil.nvl(request.getParameter("affiliates_code"));
		String p_mid = Config.INI_P_MID;
		String host = Config.HOST_CONTENTS_IMAGE_MAKE;
		String sim = StringUtil.nvl(request.getParameter("sim"));
		String googleadid = StringUtil.nvl(request.getParameter("googleadid"));
		String pkgInstaller = StringUtil.nvl(request.getParameter("pkgInstaller"));
		String app_store_type = StringUtil.nvl(request.getParameter("store_type"));
		String useragent = StringUtil.nvl(request.getParameter("useragent"));

		String app_version = "";
		if (!"".equals(useragent)) {
			try {
				// 앱 버전
				app_version = StringUtil.getAppVersion(useragent);
				app_version = app_version.replaceAll("[.]", "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		PayNameCompanyVo pncv = new PayNameCompanyVo();
		pncv.setId(id);
		pncv.setAffiliates_code(affiliates_code);
		try {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayNameCompany PayNameCompanyVo payNameCompanyVo" + pncv + "\n");
			pncv = payService.selectPayNameCompany(pncv);
		} catch (Exception e) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayNameCompany : "
					+ e.toString() + "\n");
			e.printStackTrace();
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayNameCompany PayNameCompanyVo payNameCompanyVo" + pncv + "\n");
		model.addAttribute("id", id);
		model.addAttribute("affiliates_code", affiliates_code);
		model.addAttribute("name", StringUtil.nvl(pncv.getName()));
		model.addAttribute("company", StringUtil.nvl(pncv.getCompany()));
		model.addAttribute("p_mid", p_mid);
		model.addAttribute("host", host);
		model.addAttribute("sim", sim);
		model.addAttribute("googleadid", googleadid);
		model.addAttribute("pkgInstaller", pkgInstaller);
		model.addAttribute("app_store_type", app_store_type);
		model.addAttribute("app_version", app_version);
		HashMap hashMap = (HashMap) model.asMap();
		Iterator<String> iterator = hashMap.keySet().iterator();
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 시작 ----\n");
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "key = " + key + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 종료 ----\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return "ini/mobile/make/make_half_request";
	}

	/**
	 * 구글결제 승인
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ini/mobile/playStorePayReturn", method = { RequestMethod.GET, RequestMethod.POST })
	public String playStorePayReturn(HttpServletRequest request, HttpServletResponse response, Model model) {
		int returnValue = -1;
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

		String orderId = StringUtil.nvl(request.getParameter("orderId"));
		String packageName = StringUtil.nvl(request.getParameter("packageName"));
		String app_store_type = StringUtil.nvl(request.getParameter("store_type"));
		String productId = StringUtil.nvl(request.getParameter("productId"));
		String purchaseTime = StringUtil.nvl(request.getParameter("purchaseTime"));
		String purchaseState = StringUtil.nvl(request.getParameter("purchaseState"));
		String developerPayload = StringUtil.nvl(request.getParameter("developerPayload"));
		String purchaseToken = StringUtil.nvl(request.getParameter("purchaseToken"));

		String pay_type_code = StringUtil.nvl(request.getParameter("pay_type_code"));// 상품코드

		String id = StringUtil.nvl(request.getParameter("id"));
		String affiliates_code = StringUtil.nvl(request.getParameter("affiliates_code"));
		String company = StringUtil.nvl(request.getParameter("company"), request.getParameter("name"));
		String email = StringUtil.nvl(request.getParameter("email"));

		String P_TYPE = "G-PAY";

		String log_url = "";
		String log_company = "";
		String log_payment = "";
		String log_price = "";
		String log_currency = "";
		String log_goodname = "";
		String log_buyername = "";
		String log_buyertel = "";
		String log_buyeremail = "";
		String log_cardquota = "";
		String log_billkey = "";
		String log_tid = "";
		String log_auth_dt = "";
		String log_oid = "";
		String log_msg = "";
		String log_is_success = "";
		String log_access_seq = "";
		String log_pay_type_code = "";
		String log_pay_success_seq = "";
		String log_pay_bill_seq = "";
		String log_id = "";
		String log_affiliates_code = "";

		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "DB 등록" + "\n");

		int checkPurchaseType = GoogleInApp.checkSubscription(productId);
		if (checkPurchaseType != 1) {
			checkPurchaseType = GoogleInApp.checkInAppProducts(productId);
		}
		/**
		 * 일반 결제:0
		 */
		if (checkPurchaseType == 0 && !"".equals(orderId) && !"".equals(productId)) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "일반 결제" + "\n");
			PayTypeVo payTypeVo = new PayTypeVo();
			try {
				payTypeVo.setCode(pay_type_code);
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
						+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo:" + payTypeVo + "\n");
				payTypeVo = payService.selectPayTypeWhereCode(payTypeVo);
				if (payTypeVo != null) {
					logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "StringUtil.nvl(payTypeVo.getPrice()):"
							+ StringUtil.nvl(payTypeVo.getPrice()) + "\n");
					PayCustomVo payCustomVo = new PayCustomVo();
					payCustomVo.setId(id);
					payCustomVo.setAffiliates_code(affiliates_code);
					payCustomVo.setPay_type_code(pay_type_code);
					payCustomVo.setCompany(company);
					payCustomVo.setP_type(P_TYPE);
					payCustomVo.setP_tid(purchaseToken);
					payCustomVo.setO_id(orderId);
					payCustomVo.setPrice(StringUtil.nvl(payTypeVo.getPrice()));
					payCustomVo.setGoodname(StringUtil.nvl(payTypeVo.getName()));
					payCustomVo.setP_auth_dt(new Date(Long.parseLong(purchaseTime)));
					payCustomVo.setCheckPurchaseType(checkPurchaseType + "");
					int success = 0;
					try {
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "insertPayInfo" + "\n");
						success = payService.insertPayInfo(payCustomVo, logData);
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "success:" + success + "\n");
						if (success > 0) {
							returnValue = 1;
							log_msg += "DB 등록 성공";
						} else {
							returnValue = 0;
							log_msg += "DB 등록 실패";
							model.addAttribute("S_P_RMESG1", "DB 등록 실패");
							logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "데이터 등록 에러" + "\n");
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue = 0;
						log_msg += "DB 등록 실패1 Exception";
						model.addAttribute("S_P_RMESG1", "DB 등록 실패1");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "insertPayInfo error e:"
								+ e.toString() + "\n");
					}
					log_url = "";// 홈페이지 URL
					log_company = "";
					log_payment = P_TYPE;
					log_price = StringUtil.nvl(payTypeVo.getPrice());
					log_currency = "";// 단위
					log_goodname = StringUtil.nvl(payTypeVo.getName() + "");
					log_buyername = company;
					log_buyertel = id;
					log_buyeremail = email;
					log_cardquota = "";// 할부기간
					log_billkey = "";// 빌링키
					log_tid = purchaseToken;// 상품 아이디
					SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
					log_auth_dt = dt.format(new Date(Long.parseLong(purchaseTime)));
					log_oid = StringUtil.nvl(payCustomVo.getO_id() + "");
					log_is_success = (returnValue == 1 ? "Y" : "N");
					log_access_seq = "";// id,affiliates_code 로대체
					log_pay_type_code = pay_type_code;
					log_pay_success_seq = StringUtil.nvl(payCustomVo.getPay_success_seq() + "");
					log_pay_bill_seq = StringUtil.nvl(payCustomVo.getPay_bill_seq() + "");
					log_id = id;
					log_affiliates_code = affiliates_code;
				}
			} catch (Exception e) {
				e.printStackTrace();
				returnValue = 0;
				log_msg += "상품 조회 SQLException";
				model.addAttribute("S_P_RMESG1", "상품 조회 SQLException");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
						+ e.toString() + "\n");
			}
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo:" + payTypeVo + "\n");
			/**
			 * 정기 결제:1
			 */
		} else if (checkPurchaseType == 1 && !"".equals(orderId) && !"".equals(productId)) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "정기 결제" + "\n");
			try {
				SubscriptionPurchase subscriptionPurchase = GoogleInApp.checkSubscriptionPurchase(productId,
						purchaseToken);
				long expiryTimeMillis = subscriptionPurchase.getExpiryTimeMillis();
				PayTypeVo payTypeVo = new PayTypeVo();
				try {
					payTypeVo.setCode(pay_type_code);
					logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
							+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo:" + payTypeVo + "\n");
					payTypeVo = payService.selectPayTypeWhereCode(payTypeVo);
					/**
					 * DB 저장
					 */
					String S_P_AUTH_DT = "";// 승인일자
					String S_P_OID = orderId;// 상점 주문번호
					String S_P_TID = purchaseToken;// 거래번호
					String S_P_TYPE = P_TYPE;// 지불수단
					String S_P_FN_NM = "";// 결제카드한글명 BC카드등...

					String S_P_URL = "";
					String S_P_PRICE = payTypeVo.getPrice();
					String S_P_CURRENCY = "WON";
					String S_P_GOODNAME = payTypeVo.getName();
					String S_P_BUYERNAME = company;
					String S_P_BUYERTEL = id;
					String S_P_BUYEREMAIL = email;
					String S_P_CARDQUOTA = "00";
					String S_P_BILLKEY = "";// data.getData("billkey");

					SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");
					Date timeInDate = new Date(Long.parseLong(purchaseTime));
					S_P_AUTH_DT = transFormat.format(timeInDate);

					logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "DB 등록" + "\n");
					PayCustomVo payCustomVo = new PayCustomVo();
					payCustomVo.setId(id);
					payCustomVo.setAffiliates_code(affiliates_code);
					payCustomVo.setPay_type_code(payTypeVo.getCode());
					payCustomVo.setCompany(company);
					payCustomVo.setP_type(S_P_TYPE);
					payCustomVo.setP_tid(S_P_TID);

					/**
					 * 빌링 정보 추가
					 */
					payCustomVo.setUrl(S_P_URL);
					payCustomVo.setPrice(S_P_PRICE);
					payCustomVo.setCurrency(S_P_CURRENCY);
					payCustomVo.setGoodname(S_P_GOODNAME);
					payCustomVo.setBuyername(S_P_BUYERNAME);
					payCustomVo.setBuyertel(S_P_BUYERTEL);
					payCustomVo.setBuyeremail(S_P_BUYEREMAIL);
					payCustomVo.setCardquota(S_P_CARDQUOTA);
					payCustomVo.setBillkey(S_P_BILLKEY);
					payCustomVo.setO_id(S_P_OID);
					payCustomVo.setSim("");
					payCustomVo.setGoogleadid("");
					payCustomVo.setPkgInstaller("");
					payCustomVo.setCheckPurchaseType(checkPurchaseType + "");
					payCustomVo.setP_auth_dt(timeInDate);
					payCustomVo.setG_auth_dt(timeInDate);
					timeInDate = new Date(expiryTimeMillis);
					Calendar cal = Calendar.getInstance();
					cal.setTime(timeInDate);
					cal.add(Calendar.DATE, -1);
					System.out.println(cal.getTimeInMillis());
					timeInDate = new Date(cal.getTimeInMillis());
					payCustomVo.setBatch_date(timeInDate);
					int success = 0;
					try {
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "insertPayInfo payCustomVo : "
								+ payCustomVo + "\n");
						success = payService.insertPayInfo(payCustomVo, logData);
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "success:" + success + "\n");
						if (success > 0) {
							returnValue = 1;
							log_msg += "DB 등록 성공";
						} else {
							returnValue = 0;
							log_msg += "DB 등록 실패";
							model.addAttribute("S_P_RMESG1", "DB 등록 실패");
							logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "데이터 등록 에러" + "\n");
						}
					} catch (Exception e) {
						log_msg += "DB 등록 실패1 Exception";
						e.printStackTrace();
						returnValue = 0;
						model.addAttribute("S_P_RMESG1", "DB 등록 실패1");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "insertPayInfo error e:"
								+ e.toString() + "\n");
					}
					log_url = "";// 홈페이지 URL
					log_company = "";
					log_payment = P_TYPE;
					log_price = StringUtil.nvl(payTypeVo.getPrice());
					log_currency = "";// 단위
					log_goodname = StringUtil.nvl(payTypeVo.getName() + "");
					log_buyername = company;
					log_buyertel = id;
					log_buyeremail = email;
					log_cardquota = "";// 할부기간
					log_billkey = "";// 빌링키
					log_tid = purchaseToken;// 상품 아이디
					SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
					log_auth_dt = dt.format(new Date(Long.parseLong(purchaseTime)));
					log_oid = StringUtil.nvl(payCustomVo.getO_id() + "");
					log_is_success = (returnValue == 1 ? "Y" : "N");
					log_access_seq = "";// id,affiliates_code 로대체
					log_pay_type_code = pay_type_code;
					log_pay_success_seq = StringUtil.nvl(payCustomVo.getPay_success_seq() + "");
					log_pay_bill_seq = StringUtil.nvl(payCustomVo.getPay_bill_seq() + "");
					log_id = id;
					log_affiliates_code = affiliates_code;
				} catch (Exception e) {
					e.printStackTrace();
					returnValue = 0;
					log_msg += "상품 조회 SQLException";
					model.addAttribute("S_P_RMESG1", "상품 조회 SQLException");
					logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
							+ e.toString() + "\n");
				}
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
						+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo:" + payTypeVo + "\n");
			} catch (Exception e) {
				e.printStackTrace();
				returnValue = 0;
				log_msg += "구글 상품 조회 Exception";
				model.addAttribute("S_P_RMESG1", "구글 상품 조회 Exception");
			}
			/**
			 * 오류:-1
			 */
		} else if (checkPurchaseType == -1) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "오류" + "\n");
			log_msg += "결제 오류";
			model.addAttribute("S_P_RMESG1", "결제 오류");
		}
		try {
			int success = 0;

			PayExeLogVo payExeLogVo = new PayExeLogVo();
			if (!"".equals(StringUtil.nvl(log_url))) {
				payExeLogVo.setUrl(log_url);
			}
			if (!"".equals(StringUtil.nvl(log_company))) {
				payExeLogVo.setCompany(log_company);
			}
			if (!"".equals(StringUtil.nvl(log_payment))) {
				payExeLogVo.setPayment(log_payment);
			}
			if (!"".equals(log_price)) {
				payExeLogVo.setPrice(log_price);
			}
			if (!"".equals(log_currency)) {
				payExeLogVo.setCurrency(log_currency);
			}
			if (!"".equals(log_goodname)) {
				payExeLogVo.setGoodname(log_goodname);
			}
			if (!"".equals(log_buyername)) {
				payExeLogVo.setBuyername(log_buyername);
			}
			if (!"".equals(log_buyertel)) {
				payExeLogVo.setBuyertel(log_buyertel);
			}
			if (!"".equals(log_buyeremail)) {
				payExeLogVo.setBuyeremail(log_buyeremail);
			}
			if (!"".equals(log_cardquota)) {
				payExeLogVo.setCardquota(log_cardquota);
			}
			if (!"".equals(log_billkey)) {
				payExeLogVo.setBillkey(log_billkey);
			}
			if (!"".equals(log_tid)) {
				payExeLogVo.setTid(log_tid);
			}
			if (!"".equals(log_auth_dt)) {
				payExeLogVo.setAuth_dt(log_auth_dt);
			}
			if (!"".equals(log_oid)) {
				payExeLogVo.setOid(log_oid);
			}
			if (!"".equals(log_msg)) {
				payExeLogVo.setMsg(log_msg);
			}
			if (!"".equals(log_is_success)) {
				payExeLogVo.setSuccess(log_is_success);
			} else {
				payExeLogVo.setSuccess("N");
			}
			if (!"".equals(log_access_seq)) {
				// payExeLogVo.setAccess_seq(log_access_seq);
			}
			if (!"".equals(log_pay_type_code)) {
				payExeLogVo.setPay_type_code(log_pay_type_code);
			}
			if (!"".equals(log_pay_success_seq)) {
				payExeLogVo.setPay_success_seq(log_pay_success_seq);
			}
			if (!"".equals(log_pay_bill_seq)) {
				payExeLogVo.setPay_bill_seq(log_pay_bill_seq);
			}
			if (!"".equals(log_id)) {
				payExeLogVo.setId(log_id);
			}
			if (!"".equals(log_affiliates_code)) {
				payExeLogVo.setAffiliates_code(log_affiliates_code);
			}
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "inserttPayExeLog PayExeLogVo payExeLogVo :"
					+ payExeLogVo + "\n");
			success = payService.insertPayExeLog(payExeLogVo);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "inserttPayExeLog success:" + success + "\n");
		} catch (Exception e) {
			e.printStackTrace();
			logData.append(
					"[" + LogUtils.getCurrentTime() + "]" + " " + "inserttPayExeLog error e:" + e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "returnValue = " + returnValue + "\n");
		model.addAttribute("returnValue", returnValue);
		HashMap hashMap = (HashMap) model.asMap();
		Iterator<String> iterator = hashMap.keySet().iterator();
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 시작 ----\n");
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "key = " + key + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 종료 ----\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return "/ini/mobile/PlayStorePayReturn";
	}

	/**
	 * 일반결제 승인
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ini/mobile/INIStdPayReturn", method = { RequestMethod.GET, RequestMethod.POST })
	public String iniMobileINIStdPayReturn(HttpServletRequest request, HttpServletResponse response, Model model) {
		int returnValue = -1;
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

		String S_P_STATUS = "";
		String S_P_RMESG1 = "";

		String log_url = "";
		String log_company = "";
		String log_payment = "";
		String log_price = "";
		String log_currency = "";
		String log_goodname = "";
		String log_buyername = "";
		String log_buyertel = "";
		String log_buyeremail = "";
		String log_cardquota = "";
		String log_billkey = "";
		String log_tid = "";
		String log_auth_dt = "";
		String log_oid = "";
		String log_msg = "";
		String log_is_success = "";
		String log_access_seq = "";
		String log_pay_type_code = "";
		String log_pay_success_seq = "";
		String log_pay_bill_seq = "";
		String log_id = "";
		String log_affiliates_code = "";

		/*******************************************************************************
		 * ' FILE NAME : mx_rnoti.asp
		 * ' FILE DESCRIPTION :
		 * ' 이니시스 smart phone 결제 결과 수신 페이지 샘플
		 * ' 기술문의 : ts@inicis.com
		 * ' HISTORY
		 * ' 2010. 02. 25 최초작성
		 * ' 2010 06. 23 WEB 방식의 가상계좌 사용시 가상계좌 채번 결과 무시 처리 추가(APP 방식은 해당 없음!!)
		 * ' WEB 방식일 경우 이미 P_NEXT_URL 에서 채번 결과를 전달 하였으므로,
		 * ' 이니시스에서 전달하는 가상계좌 채번 결과 내용을 무시 하시기 바랍니다.
		 * '
		 *******************************************************************************/

		// 이니시스 NOTI 서버에서 받은 Value
		String P_TID; // 거래번호
		String P_MID; // 상점아이디
		String P_AUTH_DT; // 승인일자
		String P_STATUS; // 거래상태 (00:성공, 01:실패)
		String P_TYPE; // 지불수단
		String P_OID; // 상점주문번호
		String P_FN_CD1; // 금융사코드1
		String P_FN_CD2; // 금융사코드2
		String P_FN_NM; // 금융사명 (은행명, 카드사명, 이통사명)
		String P_AMT; // 거래금액
		String P_UNAME; // 결제고객성명
		String P_RMESG1; // 결과코드
		String P_RMESG2; // 결과메시지
		String P_NOTI; // 노티메시지(상점에서 올린 메시지)
		String P_AUTH_NO; // 승인번호

		// **********************************************************************************
		// 이부분에 로그파일 경로를 수정해주세요.
		// String file_path = "/home/woong"; //로그를 기록할 디렉터리
		// **********************************************************************************
		String addr = request.getRemoteAddr().toString();
		addr = "118.129.210.25";// 로직 오류 사용자 IP(무조건 통과하도록 수정)
		// PG에서 보냈는지 IP로 체크
		if ("118.129.210.25".equals(addr) || "211.219.96.165".equals(addr)
				|| "183.109.71.153".equals(addr)) {
			// 이니시스에서 받은 value
			P_TID = request.getParameter("P_TID") + "";
			P_MID = request.getParameter("P_MID") + "";
			P_AUTH_DT = request.getParameter("P_AUTH_DT") + "";
			P_STATUS = request.getParameter("P_STATUS") + "";
			P_TYPE = request.getParameter("P_TYPE") + "";
			P_OID = request.getParameter("P_OID") + "";
			P_FN_CD1 = request.getParameter("P_FN_CD1") + "";
			P_FN_CD2 = request.getParameter("P_FN_CD2") + "";
			P_FN_NM = request.getParameter("P_FN_NM") + "";
			P_UNAME = request.getParameter("P_UNAME") + "";
			P_AMT = request.getParameter("P_AMT") + "";
			P_RMESG1 = request.getParameter("P_RMESG1") + "";
			P_RMESG2 = request.getParameter("P_RMESG2") + "";
			P_NOTI = request.getParameter("P_NOTI") + "";
			P_AUTH_NO = request.getParameter("P_AUTH_NO") + "";

			PayTypeVo payTypeVo_log = new PayTypeVo();
			try {
				HashMap noti_params_r = StringUtil.getUrlParamsHashMap(URLDecoder.decode(P_NOTI, "utf-8"));
				payTypeVo_log.setCode(StringUtil.nvl(noti_params_r.get("pay_type_code") + ""));
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
						+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo:" + payTypeVo_log + "\n");
				payTypeVo_log = payService.selectPayTypeWhereCode(payTypeVo_log);
				if (payTypeVo_log != null) {
					log_url = "";// 홈페이지 URL
					log_company = "";// 결제사명
					log_payment = "";// 결제종류
					log_price = StringUtil.nvl(payTypeVo_log.getPrice() + "");
					log_currency = "";// 단위
					log_goodname = StringUtil.nvl(payTypeVo_log.getName() + "");
					log_buyername = StringUtil.nvl(noti_params_r.get("name") + "");
					log_buyertel = StringUtil.nvl(noti_params_r.get("id") + "");
					log_buyeremail = StringUtil.nvl(noti_params_r.get("email") + "");
					log_cardquota = "";// 할부기간
					log_billkey = "";// 빌링키
					log_tid = "";// 결제코드
					log_auth_dt = "";// 결제일
					log_oid = StringUtil.nvl(noti_params_r.get("o_id") + "");
					log_is_success = (returnValue == 1 ? "Y" : "N");
					log_access_seq = "";// id,affiliates_code 로대체
					log_pay_type_code = StringUtil.nvl(noti_params_r.get("pay_type_code") + "");
					log_pay_success_seq = "";// 결제번호
					log_pay_bill_seq = "";// 정기결제번호
					log_id = StringUtil.nvl(noti_params_r.get("id") + "");
					log_affiliates_code = StringUtil.nvl(noti_params_r.get("affiliates_code") + "");
				} else {
					log_msg += "상품 조회 null";
				}
			} catch (Exception e) {
				e.printStackTrace();
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
						+ e.toString() + "\n");
				log_msg += "상품 조회 Exception";
			}
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_log:" + payTypeVo_log + "\n");

			/***********************************************************************************
			 * 결제처리에 관한 로그 기록 위에서 상점 데이터베이스에 등록 성공유무에 따라서 성공시에는 "OK"를 이니시스로 실패시는
			 * "FAIL" 을 리턴하셔야합니다. 아래 조건에 데이터베이스 성공시 받는 FLAG 변수를 넣으세요 (주의) OK를
			 * 리턴하지 않으시면 이니시스 지불 서버는 "OK"를 수신할때까지 계속 재전송을 시도합니다 기타 다른 형태의
			 * out.println(response.write)는 하지 않으시기 바랍니다
			 ***********************************************************************************/
			try {
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
						+ "************************************************" + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_TID : " + P_TID + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_MID : " + P_MID + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_AUTH_DT : " + P_AUTH_DT + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_STATUS : " + P_STATUS + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_TYPE : " + P_TYPE + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_OID : " + P_OID + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_FN_CD1 : " + P_FN_CD1 + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_FN_CD2 : " + P_FN_CD2 + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_FN_NM : " + P_FN_NM + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_AMT : " + P_AMT + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_UNAME : " + P_UNAME + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_RMESG1 : " + P_RMESG1 + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_RMESG2 : " + P_RMESG2 + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_NOTI : " + P_NOTI + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_AUTH_NO : " + P_AUTH_NO + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
						+ "************************************************" + "\n");

				model.addAttribute("P_STATUS", P_STATUS);
				model.addAttribute("P_RMESG1", P_RMESG1);
				model.addAttribute("P_RMESG2", P_RMESG2);

				// 인증 성공
				if ("00".equals(P_STATUS)) {
					log_msg += "인증 성공";
					logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "P_STATUS:" + P_STATUS + "\n");
					String send_response = "";
					try {
						// #####################
						// 4.API 통신 시작
						// #####################

						Map<String, String> authMap = new Hashtable<String, String>();
						authMap.put("P_TID", StringUtil.nvl(requestMap.get("P_TID") + ""));// 필수
						authMap.put("P_MID", Config.INI_P_MID);// 필수
						String authUrl = StringUtil.nvl(requestMap.get("P_REQ_URL") + "");

						// 승인 요청
						send_response = sendPost(authUrl, authMap);
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "send_response : " + send_response
								+ "\n");

						HashMap responseMap = StringUtil.getUrlParamsHashMap(send_response);

						logData.append(
								"[" + LogUtils.getCurrentTime() + "]" + " " + "responseMap : " + responseMap + "\n");

						S_P_STATUS = StringUtil.nvl(responseMap.get("P_STATUS") + "");// 거래상태
						S_P_RMESG1 = StringUtil.nvl(responseMap.get("P_RMESG1") + "");// 메시지 1 지불 결과 메시지
						String S_P_TID = StringUtil.nvl(responseMap.get("P_TID") + "");// 거래번호
						String S_P_TYPE = StringUtil.nvl(responseMap.get("P_TYPE") + "");// 지불수단
						String S_P_AUTH_DT = StringUtil.nvl(responseMap.get("P_AUTH_DT") + "");// 승인일자
						String S_P_MID = StringUtil.nvl(responseMap.get("P_MID") + "");// 상점아이디
						String S_P_AMT = StringUtil.nvl(responseMap.get("P_AMT") + "");// 거래금액
						String S_P_UNAME = StringUtil.nvl(responseMap.get("P_UNAME") + "");// 주문자명
						String S_P_MNAME = StringUtil.nvl(responseMap.get("P_MNAME") + "");// 가맹점 이름 주문정보에 입력한 값 반환
						String S_P_NOTI = StringUtil.nvl(responseMap.get("P_NOTI") + "");// 주문정보 주문정보에 입력한 값 반환
						String S_P_NOTEURL = StringUtil.nvl(responseMap.get("P_NOTEURL") + "");// 가맹점 전달 NOTI URL 거래요청 시
																								// 입력한 값을 그대로 반환합니다.
						String S_P_NEXT_URL = StringUtil.nvl(responseMap.get("P_NEXT_URL") + "");// 가맹점 전달 NEXT URL 거래요청
																									// 시 입력한 값을 그대로
																									// 반환합니다
						String S_P_CARD_ISSUER_CODE = StringUtil.nvl(responseMap.get("P_CARD_ISSUER_CODE") + "");// 발급사
																													// 코드
						String S_P_CARD_MEMBER_NUM = StringUtil.nvl(responseMap.get("P_CARD_MEMBER_NUM") + "");// 가맹점번호
																												// 자체
																												// 가맹점 일
																												// 경우만
																												// 해당
						String S_P_CARD_PURCHASE_CODE = StringUtil.nvl(responseMap.get("P_CARD_PURCHASE_CODE") + "");// 매입사
																														// 코드
																														// 자체
																														// 가맹점
																														// 일
																														// 경우만
																														// 해당
						String S_P_CARD_PRTC_CODE = StringUtil.nvl(responseMap.get("P_CARD_PRTC_CODE") + "");// 부분취소
																												// 가능여부
																												// 부분취소가능
																												// : 1 ,
																												// 부분취소불가능
																												// : 0
						String S_P_CARD_INTEREST = StringUtil.nvl(responseMap.get("P_CARD_INTEREST") + "");// 무이자 할부여부 0
																											// : 일반, 1 :
																											// 무이자
						String S_P_CARD_CHECKFLAG = StringUtil.nvl(responseMap.get("P_CARD_CHECKFLAG") + "");// 체크카드 여부
																												// 0 :
																												// 신용카드,
																												// 1 :
																												// 체크카드,
																												// 2 :
																												// 기프트카드
						String S_P_RMESG2 = StringUtil.nvl(responseMap.get("P_RMESG2") + "");// 메시지 2 신용카드 할부 개월 수
						String S_P_FN_CD1 = StringUtil.nvl(responseMap.get("P_FN_CD1") + "");// 카드코드
						String S_P_AUTH_NO = StringUtil.nvl(responseMap.get("P_AUTH_NO") + "");// 승인번호 신용카드거래에서만 사용합니다
						String S_P_ISP_CARDCODE = StringUtil.nvl(responseMap.get("P_ISP_CARDCODE") + "");// VP 카드코드
						String S_P_FN_NM = StringUtil.nvl(responseMap.get("P_FN_NM") + "");// 결제카드한글명 BC카드,
						String S_P_EVENT_CODE = StringUtil.nvl(responseMap.get("P_EVENT_CODE") + "");// 이벤트코드 A1,A2 등등

						logData.append(
								"[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_STATUS : " + S_P_STATUS + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_TID : " + S_P_TID + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_TYPE : " + S_P_TYPE + "\n");
						logData.append(
								"[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_AUTH_DT : " + S_P_AUTH_DT + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_MID : " + S_P_MID + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_AMT : " + S_P_AMT + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_UNAME : " + S_P_UNAME + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_MNAME : " + S_P_MNAME + "\n");
						logData.append(
								"[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_RMESG1 : " + S_P_RMESG1 + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_NOTI : " + S_P_NOTI + "\n");
						logData.append(
								"[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_NOTEURL : " + S_P_NOTEURL + "\n");
						logData.append(
								"[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_NEXT_URL : " + S_P_NEXT_URL + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_CARD_ISSUER_CODE : "
								+ S_P_CARD_ISSUER_CODE + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_CARD_MEMBER_NUM : "
								+ S_P_CARD_MEMBER_NUM + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_CARD_PURCHASE_CODE : "
								+ S_P_CARD_PURCHASE_CODE + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_CARD_PRTC_CODE : "
								+ S_P_CARD_PRTC_CODE + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_CARD_INTEREST : "
								+ S_P_CARD_INTEREST + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_CARD_CHECKFLAG : "
								+ S_P_CARD_CHECKFLAG + "\n");
						logData.append(
								"[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_RMESG2 : " + S_P_RMESG2 + "\n");
						logData.append(
								"[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_FN_CD1 : " + S_P_FN_CD1 + "\n");
						logData.append(
								"[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_AUTH_NO : " + S_P_AUTH_NO + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_ISP_CARDCODE : "
								+ S_P_ISP_CARDCODE + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_FN_NM : " + S_P_FN_NM + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_EVENT_CODE : "
								+ S_P_EVENT_CODE + "\n");

						model.addAttribute("S_P_STATUS", S_P_STATUS);
						model.addAttribute("S_P_RMESG1", S_P_RMESG1);

						if ("00".equals(S_P_STATUS)) {
							log_msg += "결제 성공";
							logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "결제 성공" + "\n");

							HashMap noti_params = StringUtil.getUrlParamsHashMap(S_P_NOTI);

							logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "noti_params : " + noti_params
									+ "\n");

							if (!"".equals(S_P_FN_NM) && !"".equals(S_P_TYPE) && !"".equals(S_P_TID)
									&& !"".equals(S_P_AUTH_DT)) {
								logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "DB 등록" + "\n");

								PayTypeVo payTypeVo = new PayTypeVo();
								try {
									payTypeVo.setCode(StringUtil.nvl(noti_params.get("pay_type_code") + ""));
									logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
											+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo:" + payTypeVo
											+ "\n");
									payTypeVo = payService.selectPayTypeWhereCode(payTypeVo);
									if (payTypeVo != null) {
										logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_P_AMT:"
												+ S_P_AMT + "\n");
										logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
												+ "StringUtil.nvl(payTypeVo.getPrice()):"
												+ StringUtil.nvl(payTypeVo.getPrice()) + "\n");
										if (S_P_AMT.equals(StringUtil.nvl(payTypeVo.getPrice()))) {
											PayCustomVo payCustomVo = new PayCustomVo();
											payCustomVo.setId(StringUtil.nvl(noti_params.get("id") + ""));
											payCustomVo.setAffiliates_code(
													StringUtil.nvl(noti_params.get("affiliates_code") + ""));
											payCustomVo.setPay_type_code(
													StringUtil.nvl(noti_params.get("pay_type_code") + ""));
											payCustomVo.setCompany(S_P_FN_NM);
											payCustomVo.setP_type(S_P_TYPE);
											payCustomVo.setP_tid(S_P_TID);
											payCustomVo.setO_id(StringUtil.nvl(noti_params.get("o_id") + ""));
											payCustomVo.setPrice(payTypeVo.getPrice());
											payCustomVo.setGoodname(payTypeVo.getName());
											payCustomVo.setSim(StringUtil.nvl(noti_params.get("sim") + ""));
											payCustomVo
													.setGoogleadid(StringUtil.nvl(noti_params.get("googleadid") + ""));
											payCustomVo.setPkgInstaller(
													StringUtil.nvl(noti_params.get("pkgInstaller") + ""));
											SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
											payCustomVo.setP_auth_dt(dt.parse(S_P_AUTH_DT));
											int success = 0;
											try {
												logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
														+ "insertPayInfo" + "\n");
												success = payService.insertPayInfo(payCustomVo, logData);
												logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "success:"
														+ success + "\n");
												if (success > 0) {
													returnValue = 1;
													log_msg += "DB 등록 성공";
												} else {
													returnValue = 0;
													log_msg += "DB 등록 실패";
													model.addAttribute("S_P_RMESG1", "결제 실패");
													logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
															+ "데이터 등록 에러" + "\n");
												}
											} catch (Exception e) {
												e.printStackTrace();
												returnValue = 0;
												log_msg += "DB 등록 Exception";
												model.addAttribute("S_P_RMESG1", "결제 실패");
												logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
														+ "insertPayInfo error e:" + e.toString() + "\n");
											}

											log_url = "";// 홈페이지 URL
											log_company = S_P_FN_NM;
											log_payment = S_P_TYPE;
											log_price = StringUtil.nvl(payTypeVo.getPrice() + "");
											log_currency = "";// 단위
											log_goodname = StringUtil.nvl(payTypeVo.getName() + "");
											log_buyername = StringUtil.nvl(noti_params.get("name") + "");
											log_buyertel = StringUtil.nvl(noti_params.get("id") + "");
											log_buyeremail = StringUtil.nvl(noti_params.get("email") + "");
											log_cardquota = "";// 할부기간
											log_billkey = "";// 빌링키
											log_tid = S_P_TID;//
											log_auth_dt = S_P_AUTH_DT;
											log_oid = StringUtil.nvl(payCustomVo.getO_id() + "");
											log_is_success = (returnValue == 1 ? "Y" : "N");
											log_access_seq = "";// id,affiliates_code 로대체
											log_pay_type_code = StringUtil.nvl(noti_params.get("pay_type_code") + "");
											log_pay_success_seq = StringUtil.nvl(payCustomVo.getPay_success_seq() + "");
											log_pay_bill_seq = StringUtil.nvl(payCustomVo.getPay_bill_seq() + "");
											log_id = StringUtil.nvl(noti_params.get("id") + "");
											log_affiliates_code = StringUtil
													.nvl(noti_params.get("affiliates_code") + "");

										} else {
											returnValue = 0;
											log_msg += "가격 에러";
											logData.append(
													"[" + LogUtils.getCurrentTime() + "]" + " " + "가격 에러" + "\n");
										}
									}
								} catch (SQLException e) {
									e.printStackTrace();
									logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
											+ "query error selectPayTypeWhereCode : " + e.toString() + "\n");
									returnValue = 0;
									log_msg += "상품 조회 SQLException";
								}
								logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
										+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo:" + payTypeVo + "\n");
							} else {
								logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "승인 파라미터 오류" + "\n");
								log_msg += "승인 파라미터 오류";
							}
						} else {
							logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "승인 실패" + "\n");
							returnValue = 0;
							log_msg += "승인 실패";
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + ex.toString() + "\n");
						log_msg += "인증 성공 후 Exception";
					}
					// 결제 데이터가 DB에 정상적으로 등록되지 않은 경우 결제 취소 처리
					if (1 != returnValue) {
						INIpay50 inipay = null;
						try {
							String S_P_STATUS_ = StringUtil.getUrlParamsKeyValue(send_response, "P_STATUS");// 거래상태
							String S_P_TID = StringUtil.getUrlParamsKeyValue(send_response, "P_TID");// 거래번호
							String S_P_MID = StringUtil.getUrlParamsKeyValue(send_response, "P_MID");// 상점아이디
							// 결제 성공, 결제 번호, 상점 아이디 가 존재 할경우 취소 처리
							if ("00".equals(S_P_STATUS_) && !"".equals(S_P_TID) && !"".equals(S_P_MID)) {
								String mid = S_P_MID;// 상점아이디
								String tid = S_P_TID;// 취소할 거래의 거래아이디
								String msg = "데이터 등록 실패";// 취소사유
								String cancelreason = "1";// 현금영수증 1:거래취소,2:오류,3:기타
								inipay = iniCancle(mid, tid, msg, cancelreason, logData);
								if (inipay != null) {
									log_msg += inipay.GetResult("ResultCode") + " " + inipay.GetResult("ResultMsg");
								} else {
									log_msg += "결제 취소 데이터 null";
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + e.toString() + "\n");
							log_msg += "결제 취소 Exception";
						}

						/**
						 * 결제 취소 로그 20190610
						 */
						int success = 0;
						try {
							String log_cancel_code = "";
							String log_cancel_msg = "";
							String log_cancel_date = "";
							String log_cancel_time = "";
							if (inipay != null) {
								log_cancel_code = StringUtil.nvl(inipay.GetResult("ResultCode"));
								log_cancel_msg = StringUtil.nvl(inipay.GetResult("ResultMsg"));
								log_cancel_date = StringUtil.nvl(inipay.GetResult("CancelDate"));
								log_cancel_time = StringUtil.nvl(inipay.GetResult("CancelTime"));
							} else {
								log_cancel_msg = "결제 취소 오류";
							}
							String msg_ = log_cancel_code + " " + log_cancel_msg + " " + log_cancel_date + " "
									+ log_cancel_time;
							String log_cancel_url = log_url;
							String log_cancel_company = log_company;
							String log_cancel_payment = log_payment;
							String log_cancel_price = log_price;
							String log_cancel_currency = log_currency;
							String log_cancel_goodname = log_goodname;
							String log_cancel_buyername = log_buyername;
							String log_cancel_buyertel = log_buyertel;
							String log_cancel_buyeremail = log_buyeremail;
							String log_cancel_cardquota = log_cardquota;
							String log_cancel_billkey = log_billkey;
							String log_cancel_tid = log_tid;
							String log_cancel_auth_dt = log_auth_dt;
							String log_cancel_oid = log_oid;
							String log_cancel_access_seq = log_access_seq;
							String log_cancel_pay_type_code = log_pay_type_code;
							String log_cancel_pay_success_seq = log_pay_success_seq;
							String log_cancel_pay_bill_seq = log_pay_bill_seq;
							String log_cancel_id = log_id;
							String log_cancel_affiliates_code = log_affiliates_code;
							PayCancelExeLogVo payCancelExeLogVo = new PayCancelExeLogVo();
							if (!"".equals(log_cancel_url)) {
								payCancelExeLogVo.setUrl(log_cancel_url);
							}
							if (!"".equals(log_cancel_company)) {
								payCancelExeLogVo.setCompany(log_cancel_company);
							}
							if (!"".equals(log_cancel_payment)) {
								payCancelExeLogVo.setPayment(log_cancel_payment);
							}
							if (!"".equals(log_cancel_price)) {
								payCancelExeLogVo.setPrice(log_cancel_price);
							}
							if (!"".equals(log_cancel_currency)) {
								payCancelExeLogVo.setCurrency(log_cancel_currency);
							}
							if (!"".equals(log_cancel_goodname)) {
								payCancelExeLogVo.setGoodname(log_cancel_goodname);
							}
							if (!"".equals(log_cancel_buyername)) {
								payCancelExeLogVo.setBuyername(log_cancel_buyername);
							}
							if (!"".equals(log_cancel_buyertel)) {
								payCancelExeLogVo.setBuyertel(log_cancel_buyertel);
							}
							if (!"".equals(log_cancel_buyeremail)) {
								payCancelExeLogVo.setBuyeremail(log_cancel_buyeremail);
							}
							if (!"".equals(log_cancel_cardquota)) {
								payCancelExeLogVo.setCardquota(log_cancel_cardquota);
							}
							if (!"".equals(log_cancel_billkey)) {
								payCancelExeLogVo.setBillkey(log_cancel_billkey);
							}
							if (!"".equals(log_cancel_tid)) {
								payCancelExeLogVo.setTid(log_cancel_tid);
							}
							if (!"".equals(log_cancel_auth_dt)) {
								payCancelExeLogVo.setAuth_dt(log_cancel_auth_dt);
							}
							if (!"".equals(log_cancel_oid)) {
								payCancelExeLogVo.setOid(log_cancel_oid);
							}
							if (!"".equals(log_cancel_access_seq)) {
								payCancelExeLogVo.setAccess_seq(log_cancel_access_seq);
							}
							if (!"".equals(log_cancel_pay_type_code)) {
								payCancelExeLogVo.setPay_type_code(log_cancel_pay_type_code);
							}
							if (!"".equals(log_cancel_pay_success_seq)) {
								payCancelExeLogVo.setPay_success_seq(log_cancel_pay_success_seq);
							}
							if (!"".equals(log_cancel_pay_bill_seq)) {
								payCancelExeLogVo.setPay_bill_seq(log_cancel_pay_bill_seq);
							}
							if (!"".equals(log_cancel_id)) {
								payCancelExeLogVo.setId(log_cancel_id);
							}
							if (!"".equals(log_cancel_affiliates_code)) {
								payCancelExeLogVo.setAffiliates_code(log_cancel_affiliates_code);
							}
							if (!"".equals(msg_)) {
								payCancelExeLogVo.setMsg(msg_);
							}
							if ("00".equals(log_cancel_code)) {
								payCancelExeLogVo.setSuccess("Y");
							} else {
								payCancelExeLogVo.setSuccess("N");
							}
							logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
									+ "query start insertPayCancelExeLog PayCancelExeLogVo payCancelExeLogVo:"
									+ payCancelExeLogVo + "\n");
							success = payService.insertPayCancelExeLog(payCancelExeLogVo);
						} catch (Exception e) {
							e.printStackTrace();
							logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
									+ "query error insertPayCancelExeLog : " + e.toString() + "\n");
						}
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
								+ "query end insertPayCancelExeLog int success:" + success + "\n");

					} else {
						/**
						 * 테스트를 위한 임시 환불처리 상용시 제거 필요 2017.12.27
						 */
						/*
						 * if(Config.isDevMode) {
						 * try {
						 * String S_P_STATUS = StringUtil.getUrlParamsKeyValue(send_response,
						 * "P_STATUS");//거래상태
						 * String S_P_TID = StringUtil.getUrlParamsKeyValue(send_response,
						 * "P_TID");//거래번호
						 * String S_P_MID = StringUtil.getUrlParamsKeyValue(send_response,
						 * "P_MID");//상점아이디
						 * //결제 성공, 결제 번호, 상점 아이디 가 존재 할경우 취소 처리
						 * if("00".equals(S_P_STATUS)&&!"".equals(S_P_TID)&&!"".equals(S_P_MID)) {
						 * String mid = S_P_MID;// 상점아이디
						 * String tid = S_P_TID;// 취소할 거래의 거래아이디
						 * String msg = "테스트";// 취소사유
						 * String cancelreason = "1";// 현금영수증 1:거래취소,2:오류,3:기타
						 * iniCancle(mid, tid, msg, cancelreason, logData);
						 * }
						 * } catch(Exception e) {
						 * e.printStackTrace();
						 * logData.append("["+LogUtils.getCurrentTime()+"]"+" "+e.toString()+"\n");
						 * }
						 * }
						 */
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + e.toString() + "\n");
				returnValue = 0;
				log_msg += "결제 Exception";
			}

			/**
			 * DB 결제 시도 데이터 추가 20190604
			 */
			if (!"".equals(log_msg)) {
				log_msg += " ";
			}

			log_msg += P_STATUS + " " + P_RMESG1 + " " + P_RMESG2 + " " + S_P_STATUS + " " + S_P_RMESG1;

			try {
				int success = 0;

				PayExeLogVo payExeLogVo = new PayExeLogVo();
				if (!"".equals(StringUtil.nvl(log_url))) {
					payExeLogVo.setUrl(log_url);
				}
				if (!"".equals(StringUtil.nvl(log_company))) {
					payExeLogVo.setCompany(log_company);
				}
				if (!"".equals(StringUtil.nvl(log_payment))) {
					payExeLogVo.setPayment(log_payment);
				}
				if (!"".equals(log_price)) {
					payExeLogVo.setPrice(log_price);
				}
				if (!"".equals(log_currency)) {
					payExeLogVo.setCurrency(log_currency);
				}
				if (!"".equals(log_goodname)) {
					payExeLogVo.setGoodname(log_goodname);
				}
				if (!"".equals(log_buyername)) {
					payExeLogVo.setBuyername(log_buyername);
				}
				if (!"".equals(log_buyertel)) {
					payExeLogVo.setBuyertel(log_buyertel);
				}
				if (!"".equals(log_buyeremail)) {
					payExeLogVo.setBuyeremail(log_buyeremail);
				}
				if (!"".equals(log_cardquota)) {
					payExeLogVo.setCardquota(log_cardquota);
				}
				if (!"".equals(log_billkey)) {
					payExeLogVo.setBillkey(log_billkey);
				}
				if (!"".equals(log_tid)) {
					payExeLogVo.setTid(log_tid);
				}
				if (!"".equals(log_auth_dt)) {
					payExeLogVo.setAuth_dt(log_auth_dt);
				}
				if (!"".equals(log_oid)) {
					payExeLogVo.setOid(log_oid);
				}
				if (!"".equals(log_msg)) {
					payExeLogVo.setMsg(log_msg);
				}
				if (!"".equals(log_is_success)) {
					payExeLogVo.setSuccess(log_is_success);
				} else {
					payExeLogVo.setSuccess("N");
				}
				if (!"".equals(log_access_seq)) {
					// payExeLogVo.setAccess_seq(log_access_seq);
				}
				if (!"".equals(log_pay_type_code)) {
					payExeLogVo.setPay_type_code(log_pay_type_code);
				}
				if (!"".equals(log_pay_success_seq)) {
					payExeLogVo.setPay_success_seq(log_pay_success_seq);
				}
				if (!"".equals(log_pay_bill_seq)) {
					payExeLogVo.setPay_bill_seq(log_pay_bill_seq);
				}
				if (!"".equals(log_id)) {
					payExeLogVo.setId(log_id);
				}
				if (!"".equals(log_affiliates_code)) {
					payExeLogVo.setAffiliates_code(log_affiliates_code);
				}
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
						+ "inserttPayExeLog PayExeLogVo payExeLogVo :" + payExeLogVo + "\n");
				success = payService.insertPayExeLog(payExeLogVo);
				logData.append(
						"[" + LogUtils.getCurrentTime() + "]" + " " + "inserttPayExeLog success:" + success + "\n");
			} catch (Exception e) {
				e.printStackTrace();
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "inserttPayExeLog error e:" + e.toString()
						+ "\n");
			}

		} else {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "IP 에러 addr:" + addr + "\n");
		}
		model.addAttribute("returnValue", returnValue);
		HashMap hashMap = (HashMap) model.asMap();
		Iterator<String> iterator = hashMap.keySet().iterator();
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 시작 ----\n");
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "key = " + key + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 종료 ----\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return "/ini/mobile/INIStdPayReturn";
	}

	/**
	 * 일반결제 승인 http post 통신
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private String sendPost(String url, Map params) throws Exception {

		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		con.setRequestMethod("POST");

		String urlParameters = "";

		Iterator it = params.keySet().iterator();
		Object key = null;
		String value = null;
		while (it.hasNext()) {
			key = it.next();
			value = (String) params.get(key);
			if (!"".equals(urlParameters)) {
				urlParameters += "&";
			}
			urlParameters += key + "=" + value;
		}

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
			System.out.println(inputLine);
		}
		in.close();

		return response.toString();

	}

	/**
	 * 정기결제 승인
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ini/mobile/INIBillPayReturn", method = { RequestMethod.GET, RequestMethod.POST })
	public String iniMobileINIBillPayReturn(HttpServletRequest request, HttpServletResponse response, Model model) {
		int returnValue = -1;
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

		String S_RESULTCODE = ""; // 결과 코드
		String S_RESULTMSG = ""; // 결과 메시지

		String log_url = "";
		String log_company = "";
		String log_payment = "";
		String log_price = "";
		String log_currency = "";
		String log_goodname = "";
		String log_buyername = "";
		String log_buyertel = "";
		String log_buyeremail = "";
		String log_cardquota = "";
		String log_billkey = "";
		String log_tid = "";
		String log_auth_dt = "";
		String log_oid = "";
		String log_msg = "";
		String log_is_success = "";
		String log_access_seq = "";
		String log_pay_type_code = "";
		String log_pay_success_seq = "";
		String log_pay_bill_seq = "";
		String log_id = "";
		String log_affiliates_code = "";

		String resultcode; // 결과 코드
		String resultmsg; // 결과 메시지
		String mid; // 상점아이디
		String tid; // 거래번호
		String orderid; // 상점 주문번호
		String billkey; // 발급된 빌링키
		String authkey; // 빌링인증 트랜잭션 ID
		String cardcd; // 카드코드
		String cardno; // 카드번호
		String merchantreserved; // 상점 예약필드
		String p_noti; // 가맹점 설정데이터
		String data1; // 카드비밀번호 앞두자리
		String cardkind; // 카드종류 0:개인, 1:법인
		String pgauthdate; // 처리 날짜 YYYYMMDD 형식
		String pgauthtime; // 처리 시간 MMHHSS 형식

		resultcode = StringUtil.nvl(requestMap.get("resultcode") + "");
		resultmsg = StringUtil.nvl(requestMap.get("resultmsg") + "");

		p_noti = StringUtil.nvl(requestMap.get("p_noti") + "");
		PayTypeVo payTypeVo_log = new PayTypeVo();
		try {
			HashMap noti_params_r = StringUtil.getUrlParamsHashMap(URLDecoder.decode(p_noti, "utf-8"));
			payTypeVo_log.setCode(StringUtil.nvl(noti_params_r.get("pay_type_code") + ""));
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo:" + payTypeVo_log + "\n");
			payTypeVo_log = payService.selectPayTypeWhereCode(payTypeVo_log);
			if (payTypeVo_log != null) {
				log_url = "";// 홈페이지 URL
				log_company = "";// 결제사명
				log_payment = "";// 결제종류
				log_price = StringUtil.nvl(payTypeVo_log.getPrice() + "");
				log_currency = "";// 단위
				log_goodname = StringUtil.nvl(payTypeVo_log.getName() + "");
				log_buyername = StringUtil.nvl(noti_params_r.get("name") + "");
				log_buyertel = StringUtil.nvl(noti_params_r.get("id") + "");
				log_buyeremail = StringUtil.nvl(noti_params_r.get("email") + "");
				log_cardquota = "";// 할부기간
				log_billkey = "";// 빌링키
				log_tid = "";// 결제코드
				log_auth_dt = "";// 결제일
				log_oid = StringUtil.nvl(noti_params_r.get("o_id") + "");
				log_is_success = (returnValue == 1 ? "Y" : "N");
				log_access_seq = "";// id,affiliates_code 로대체
				log_pay_type_code = StringUtil.nvl(noti_params_r.get("pay_type_code") + "");
				log_pay_success_seq = "";// 결제번호
				log_pay_bill_seq = "";// 정기결제번호
				log_id = StringUtil.nvl(noti_params_r.get("id") + "");
				log_affiliates_code = StringUtil.nvl(noti_params_r.get("affiliates_code") + "");
			} else {
				log_msg += "상품 조회 null";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
					+ e.toString() + "\n");
			log_msg += "상품 조회 Exception";
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end selectPayTypeWhereCode PayTypeVo payTypeVo_log:" + payTypeVo_log + "\n");

		if ("00".equals(resultcode)) {
			mid = StringUtil.nvl(requestMap.get("mid") + "");
			tid = StringUtil.nvl(requestMap.get("tid") + "");
			orderid = StringUtil.nvl(requestMap.get("orderid") + "");
			billkey = StringUtil.nvl(requestMap.get("billkey") + "");
			authkey = StringUtil.nvl(requestMap.get("authkey") + "");
			cardcd = StringUtil.nvl(requestMap.get("cardcd") + "");
			cardno = StringUtil.nvl(requestMap.get("cardno") + "");
			merchantreserved = StringUtil.nvl(requestMap.get("merchantreserved") + "");
			p_noti = StringUtil.nvl(requestMap.get("p_noti") + "");
			data1 = StringUtil.nvl(requestMap.get("data1") + "");
			cardkind = StringUtil.nvl(requestMap.get("cardkind") + "");
			pgauthdate = StringUtil.nvl(requestMap.get("pgauthdate") + "");
			pgauthtime = StringUtil.nvl(requestMap.get("pgauthtime") + "");

			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "resultcode:" + resultcode + "\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "resultmsg:" + resultmsg + "\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "mid : " + mid + "\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tid : " + tid + "\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "orderid : " + orderid + "\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "billkey : " + billkey + "\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "authkey : " + authkey + "\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "cardcd : " + cardcd + "\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "cardno : " + cardno + "\n");
			logData.append(
					"[" + LogUtils.getCurrentTime() + "]" + " " + "merchantreserved : " + merchantreserved + "\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "p_noti : " + p_noti + "\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "data1 : " + data1 + "\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "cardkind : " + cardkind + "\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "pgauthdate : " + pgauthdate + "\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "pgauthtime : " + pgauthtime + "\n");

			String price = "";
			String goodname = "";
			String buyername = "";
			String buyertel = "";
			String buyeremail = "";
			PayTypeVo payTypeVo = new PayTypeVo();
			try {
				p_noti = URLDecoder.decode(p_noti, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
						+ e.toString() + "\n");
			}
			HashMap noti_params = StringUtil.getUrlParamsHashMap(p_noti);
			buyername = noti_params.get("name") + "";
			buyertel = noti_params.get("id") + "";
			buyeremail = noti_params.get("email") + "";
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "noti_params : " + noti_params + "\n");
			try {
				payTypeVo.setCode(noti_params.get("pay_type_code") + "");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
						+ "query start selectPayTypeWhereCode PayTypeVo payTypeVo:" + payTypeVo + "\n");
				payTypeVo = payService.selectPayTypeWhereCode(payTypeVo);
				if (payTypeVo != null) {
					price = payTypeVo.getPrice();
					goodname = payTypeVo.getName();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayTypeWhereCode : "
						+ e.toString() + "\n");
			}

			/**
			 * 빌링 승인
			 */
			INIdata data = iniCardBill(price, goodname, buyername, buyertel, buyeremail, billkey, logData);

			String S_TID; // 거래 번호
			S_RESULTCODE = ""; // 결과 코드
			S_RESULTMSG = ""; // 결과 메시지
			String S_AUTHCODE; // 승인번호
			String S_PGAUTHDATE; // 승인날짜 YYYYMMDD 형식
			String S_PGAUTHTIME; // 승인시각 MMHHSS 형식

			S_RESULTCODE = StringUtil.nvl(data.getData("ResultCode"));
			S_RESULTMSG = StringUtil.nvl(data.getData("ResultMsg"));

			if ("00".equals(S_RESULTCODE)) {
				log_msg += "결제 성공";
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "결제 성공" + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "data:" + data + "\n");

				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_RESULTCODE:" + S_RESULTCODE + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_RESULTMSG : " + S_RESULTMSG + "\n");

				S_TID = StringUtil.nvl(data.getData("tid"));
				S_AUTHCODE = StringUtil.nvl(data.getData("CardAuthCode"));
				S_PGAUTHDATE = StringUtil.nvl(data.getData("PGauthdate"));
				S_PGAUTHTIME = StringUtil.nvl(data.getData("PGauthtime"));

				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_TID:" + S_TID + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_AUTHCODE : " + S_AUTHCODE + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_PGAUTHDATE : " + S_PGAUTHDATE + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_PGAUTHTIME : " + S_PGAUTHTIME + "\n");

				try {

					/**
					 * DB 저장
					 */
					String S_P_AUTH_DT = S_PGAUTHDATE + S_PGAUTHTIME;// 승인일자
					String S_P_OID = orderid;// 상점 주문번호
					String S_P_TID = S_TID;// 거래번호
					String S_P_TYPE = "CARD";// 지불수단
					String S_P_FN_NM = "";// 결제카드한글명 BC카드등...

					String S_P_URL = data.getData("url");
					String S_P_PRICE = data.getData("price");
					String S_P_CURRENCY = data.getData("currency");
					String S_P_GOODNAME = data.getData("goodname");
					String S_P_BUYERNAME = data.getData("buyername");
					String S_P_BUYERTEL = data.getData("buyertel");
					String S_P_BUYEREMAIL = data.getData("buyeremail");
					String S_P_CARDQUOTA = data.getData("cardquota");
					String S_P_BILLKEY = data.getData("billkey");

					if ("01".equals(cardcd)) {
						S_P_FN_NM = "외한카드";
					} else if ("03".equals(cardcd)) {
						S_P_FN_NM = "롯데카드";
					} else if ("04".equals(cardcd)) {
						S_P_FN_NM = "현대카드";
					} else if ("06".equals(cardcd)) {
						S_P_FN_NM = "국민카드";
					} else if ("11".equals(cardcd)) {
						S_P_FN_NM = "BC카드";
					} else if ("12".equals(cardcd)) {
						S_P_FN_NM = "삼성카드";
					} else if ("14".equals(cardcd)) {
						S_P_FN_NM = "신한카드";
					} else if ("15".equals(cardcd)) {
						S_P_FN_NM = "한미카드";
					} else if ("16".equals(cardcd)) {
						S_P_FN_NM = "NH카드";
					} else if ("17".equals(cardcd)) {
						S_P_FN_NM = "하나 SK카드";
					} else if ("21".equals(cardcd)) {
						S_P_FN_NM = "해외비자카드";
					} else if ("22".equals(cardcd)) {
						S_P_FN_NM = "해외마스터카드";
					} else if ("23".equals(cardcd)) {
						S_P_FN_NM = "JCB카드";
					} else if ("24".equals(cardcd)) {
						S_P_FN_NM = "해외아멕스카드";
					} else if ("25".equals(cardcd)) {
						S_P_FN_NM = "해외다이너스카드";
					}

					if (!"".equals(S_P_FN_NM) && !"".equals(S_P_TYPE) && !"".equals(S_P_TID)
							&& !"".equals(S_P_AUTH_DT)) {
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "DB 등록" + "\n");
						PayCustomVo payCustomVo = new PayCustomVo();
						payCustomVo.setId(StringUtil.nvl(noti_params.get("id") + ""));
						payCustomVo.setAffiliates_code(StringUtil.nvl(noti_params.get("affiliates_code") + ""));
						payCustomVo.setPay_type_code(StringUtil.nvl(noti_params.get("pay_type_code") + ""));
						payCustomVo.setCompany(S_P_FN_NM);
						payCustomVo.setP_type(S_P_TYPE);
						payCustomVo.setP_tid(S_P_TID);

						/**
						 * 빌링 정보 추가
						 */
						payCustomVo.setUrl(S_P_URL);
						payCustomVo.setPrice(S_P_PRICE);
						payCustomVo.setCurrency(S_P_CURRENCY);
						payCustomVo.setGoodname(S_P_GOODNAME);
						payCustomVo.setBuyername(S_P_BUYERNAME);
						payCustomVo.setBuyertel(S_P_BUYERTEL);
						payCustomVo.setBuyeremail(S_P_BUYEREMAIL);
						payCustomVo.setCardquota(S_P_CARDQUOTA);
						payCustomVo.setBillkey(S_P_BILLKEY);
						payCustomVo.setO_id(StringUtil.nvl(orderid));
						payCustomVo.setSim(StringUtil.nvl(noti_params.get("sim") + ""));
						payCustomVo.setGoogleadid(StringUtil.nvl(noti_params.get("googleadid") + ""));
						payCustomVo.setPkgInstaller(StringUtil.nvl(noti_params.get("pkgInstaller") + ""));

						SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
						payCustomVo.setP_auth_dt(dt.parse(S_P_AUTH_DT));
						int success = 0;
						try {
							logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "insertPayInfo payCustomVo : "
									+ payCustomVo + "\n");
							success = payService.insertPayInfo(payCustomVo, logData);
							logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "success:" + success + "\n");
							if (success > 0) {
								log_msg += "DB 등록 성공";
								returnValue = 1;
							} else {
								log_msg += "DB 등록 실패";
								returnValue = 0;
								model.addAttribute("S_P_RMESG1", "결제 실패");
								logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "데이터 등록 에러" + "\n");
							}
						} catch (Exception e) {
							log_msg += "DB 등록 Exception";
							e.printStackTrace();
							returnValue = 0;
							model.addAttribute("S_P_RMESG1", "결제 실패");
							logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "insertPayInfo error e:"
									+ e.toString() + "\n");
						}

						log_url = "";// 홈페이지 URL
						log_company = S_P_FN_NM;
						log_payment = S_P_TYPE;
						log_price = StringUtil.nvl(payTypeVo.getPrice() + "");
						log_currency = "";// 단위
						log_goodname = StringUtil.nvl(payTypeVo.getName() + "");
						log_buyername = StringUtil.nvl(noti_params.get("name") + "");
						log_buyertel = StringUtil.nvl(noti_params.get("id") + "");
						log_buyeremail = StringUtil.nvl(noti_params.get("email") + "");
						log_cardquota = "";// 할부기간
						log_billkey = "";// 빌링키
						log_tid = S_P_TID;//
						log_auth_dt = S_P_AUTH_DT;
						log_oid = StringUtil.nvl(payCustomVo.getO_id() + "");
						log_is_success = (returnValue == 1 ? "Y" : "N");
						log_access_seq = "";// id,affiliates_code 로대체
						log_pay_type_code = StringUtil.nvl(noti_params.get("pay_type_code") + "");
						log_pay_success_seq = StringUtil.nvl(payCustomVo.getPay_success_seq() + "");
						log_pay_bill_seq = StringUtil.nvl(payCustomVo.getPay_bill_seq() + "");
						log_id = StringUtil.nvl(noti_params.get("id") + "");
						log_affiliates_code = StringUtil.nvl(noti_params.get("affiliates_code") + "");

					} else {
						log_msg += "승인 파라미터 오류";
					}

				} catch (Exception e) {
					e.printStackTrace();
					logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + e.toString() + "\n");
					log_msg += "DB 저장 Exception";
				}
				// 결제 데이터가 DB에 정상적으로 등록되지 않은 경우 결제 취소 처리
				if (1 != returnValue) {
					INIpay50 inipay = null;
					try {
						String msg = "데이터 등록 실패";// 취소사유
						String cancelreason = "1";// 현금영수증 1:거래취소,2:오류,3:기타
						inipay = iniCancle(mid, S_TID, msg, cancelreason, logData);
						if (inipay != null) {
							log_msg += inipay.GetResult("ResultCode") + " " + inipay.GetResult("ResultMsg");
						} else {
							log_msg += "결제 취소 데이터 null";
						}
					} catch (Exception e) {
						e.printStackTrace();
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + e.toString() + "\n");
						log_msg += "결제 취소 Exception";
					}

					/**
					 * 결제 취소 로그 20190610
					 */
					int success = 0;
					try {
						String log_cancel_code = "";
						String log_cancel_msg = "";
						String log_cancel_date = "";
						String log_cancel_time = "";
						if (inipay != null) {
							log_cancel_code = StringUtil.nvl(inipay.GetResult("ResultCode"));
							log_cancel_msg = StringUtil.nvl(inipay.GetResult("ResultMsg"));
							log_cancel_date = StringUtil.nvl(inipay.GetResult("CancelDate"));
							log_cancel_time = StringUtil.nvl(inipay.GetResult("CancelTime"));
						} else {
							log_cancel_msg = "결제 취소 오류";
						}
						String msg_ = log_cancel_code + " " + log_cancel_msg + " " + log_cancel_date + " "
								+ log_cancel_time;
						String log_cancel_url = log_url;
						String log_cancel_company = log_company;
						String log_cancel_payment = log_payment;
						String log_cancel_price = log_price;
						String log_cancel_currency = log_currency;
						String log_cancel_goodname = log_goodname;
						String log_cancel_buyername = log_buyername;
						String log_cancel_buyertel = log_buyertel;
						String log_cancel_buyeremail = log_buyeremail;
						String log_cancel_cardquota = log_cardquota;
						String log_cancel_billkey = log_billkey;
						String log_cancel_tid = log_tid;
						String log_cancel_auth_dt = log_auth_dt;
						String log_cancel_oid = log_oid;
						String log_cancel_access_seq = log_access_seq;
						String log_cancel_pay_type_code = log_pay_type_code;
						String log_cancel_pay_success_seq = log_pay_success_seq;
						String log_cancel_pay_bill_seq = log_pay_bill_seq;
						String log_cancel_id = log_id;
						String log_cancel_affiliates_code = log_affiliates_code;
						PayCancelExeLogVo payCancelExeLogVo = new PayCancelExeLogVo();
						if (!"".equals(log_cancel_url)) {
							payCancelExeLogVo.setUrl(log_cancel_url);
						}
						if (!"".equals(log_cancel_company)) {
							payCancelExeLogVo.setCompany(log_cancel_company);
						}
						if (!"".equals(log_cancel_payment)) {
							payCancelExeLogVo.setPayment(log_cancel_payment);
						}
						if (!"".equals(log_cancel_price)) {
							payCancelExeLogVo.setPrice(log_cancel_price);
						}
						if (!"".equals(log_cancel_currency)) {
							payCancelExeLogVo.setCurrency(log_cancel_currency);
						}
						if (!"".equals(log_cancel_goodname)) {
							payCancelExeLogVo.setGoodname(log_cancel_goodname);
						}
						if (!"".equals(log_cancel_buyername)) {
							payCancelExeLogVo.setBuyername(log_cancel_buyername);
						}
						if (!"".equals(log_cancel_buyertel)) {
							payCancelExeLogVo.setBuyertel(log_cancel_buyertel);
						}
						if (!"".equals(log_cancel_buyeremail)) {
							payCancelExeLogVo.setBuyeremail(log_cancel_buyeremail);
						}
						if (!"".equals(log_cancel_cardquota)) {
							payCancelExeLogVo.setCardquota(log_cancel_cardquota);
						}
						if (!"".equals(log_cancel_billkey)) {
							payCancelExeLogVo.setBillkey(log_cancel_billkey);
						}
						if (!"".equals(log_cancel_tid)) {
							payCancelExeLogVo.setTid(log_cancel_tid);
						}
						if (!"".equals(log_cancel_auth_dt)) {
							payCancelExeLogVo.setAuth_dt(log_cancel_auth_dt);
						}
						if (!"".equals(log_cancel_oid)) {
							payCancelExeLogVo.setOid(log_cancel_oid);
						}
						if (!"".equals(log_cancel_access_seq)) {
							payCancelExeLogVo.setAccess_seq(log_cancel_access_seq);
						}
						if (!"".equals(log_cancel_pay_type_code)) {
							payCancelExeLogVo.setPay_type_code(log_cancel_pay_type_code);
						}
						if (!"".equals(log_cancel_pay_success_seq)) {
							payCancelExeLogVo.setPay_success_seq(log_cancel_pay_success_seq);
						}
						if (!"".equals(log_cancel_pay_bill_seq)) {
							payCancelExeLogVo.setPay_bill_seq(log_cancel_pay_bill_seq);
						}
						if (!"".equals(log_cancel_id)) {
							payCancelExeLogVo.setId(log_cancel_id);
						}
						if (!"".equals(log_cancel_affiliates_code)) {
							payCancelExeLogVo.setAffiliates_code(log_cancel_affiliates_code);
						}
						if (!"".equals(msg_)) {
							payCancelExeLogVo.setMsg(msg_);
						}
						if ("00".equals(log_cancel_code)) {
							payCancelExeLogVo.setSuccess("Y");
						} else {
							payCancelExeLogVo.setSuccess("N");
						}
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
								+ "query start insertPayCancelExeLog PayCancelExeLogVo payCancelExeLogVo:"
								+ payCancelExeLogVo + "\n");
						success = payService.insertPayCancelExeLog(payCancelExeLogVo);
					} catch (Exception e) {
						e.printStackTrace();
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
								+ "query error insertPayCancelExeLog : " + e.toString() + "\n");
					}
					logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
							+ "query end insertPayCancelExeLog int success:" + success + "\n");

				} else {
					/**
					 * 테스트를 위한 임시 환불처리 상용시 제거 필요 2017.01.02
					 */
					/*
					 * if(Config.isDevMode) {
					 * try {
					 * String msg = "테스트";// 취소사유
					 * String cancelreason = "1";// 현금영수증 1:거래취소,2:오류,3:기타
					 * iniCancle(mid, S_TID, msg, cancelreason, logData);
					 * } catch(Exception e) {
					 * e.printStackTrace();
					 * logData.append("["+LogUtils.getCurrentTime()+"]"+" "+e.toString()+"\n");
					 * }
					 * }
					 */
				}
			} else {
				returnValue = 0;
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "결제 실패" + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_RESULTCODE:" + S_RESULTCODE + "\n");
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "S_RESULTMSG : " + S_RESULTMSG + "\n");
				model.addAttribute("code", S_RESULTCODE);
				model.addAttribute("msg", S_RESULTMSG);
				log_msg += "결제 실패";
			}
		} else {
			returnValue = 0;
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "결제 실패" + "\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "resultcode:" + resultcode + "\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "resultmsg:" + resultmsg + "\n");
			model.addAttribute("code", resultcode);
			model.addAttribute("msg", resultmsg);
			log_msg += "승인 실패";
		}

		/**
		 * DB 결제 시도 데이터 추가 20190604
		 */
		if (!"".equals(log_msg)) {
			log_msg += " ";
		}

		log_msg += resultcode + " " + resultmsg + " " + S_RESULTCODE + " " + S_RESULTMSG;

		try {
			int success = 0;

			PayExeLogVo payExeLogVo = new PayExeLogVo();
			if (!"".equals(StringUtil.nvl(log_url))) {
				payExeLogVo.setUrl(log_url);
			}
			if (!"".equals(StringUtil.nvl(log_company))) {
				payExeLogVo.setCompany(log_company);
			}
			if (!"".equals(StringUtil.nvl(log_payment))) {
				payExeLogVo.setPayment(log_payment);
			}
			if (!"".equals(log_price)) {
				payExeLogVo.setPrice(log_price);
			}
			if (!"".equals(log_currency)) {
				payExeLogVo.setCurrency(log_currency);
			}
			if (!"".equals(log_goodname)) {
				payExeLogVo.setGoodname(log_goodname);
			}
			if (!"".equals(log_buyername)) {
				payExeLogVo.setBuyername(log_buyername);
			}
			if (!"".equals(log_buyertel)) {
				payExeLogVo.setBuyertel(log_buyertel);
			}
			if (!"".equals(log_buyeremail)) {
				payExeLogVo.setBuyeremail(log_buyeremail);
			}
			if (!"".equals(log_cardquota)) {
				payExeLogVo.setCardquota(log_cardquota);
			}
			if (!"".equals(log_billkey)) {
				payExeLogVo.setBillkey(log_billkey);
			}
			if (!"".equals(log_tid)) {
				payExeLogVo.setTid(log_tid);
			}
			if (!"".equals(log_auth_dt)) {
				payExeLogVo.setAuth_dt(log_auth_dt);
			}
			if (!"".equals(log_oid)) {
				payExeLogVo.setOid(log_oid);
			}
			if (!"".equals(log_msg)) {
				payExeLogVo.setMsg(log_msg);
			}
			if (!"".equals(log_is_success)) {
				payExeLogVo.setSuccess(log_is_success);
			} else {
				payExeLogVo.setSuccess("N");
			}
			if (!"".equals(log_access_seq)) {
				// payExeLogVo.setAccess_seq(log_access_seq);
			}
			if (!"".equals(log_pay_type_code)) {
				payExeLogVo.setPay_type_code(log_pay_type_code);
			}
			if (!"".equals(log_pay_success_seq)) {
				payExeLogVo.setPay_success_seq(log_pay_success_seq);
			}
			if (!"".equals(log_pay_bill_seq)) {
				payExeLogVo.setPay_bill_seq(log_pay_bill_seq);
			}
			if (!"".equals(log_id)) {
				payExeLogVo.setId(log_id);
			}
			if (!"".equals(log_affiliates_code)) {
				payExeLogVo.setAffiliates_code(log_affiliates_code);
			}
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "inserttPayExeLog PayExeLogVo payExeLogVo :"
					+ payExeLogVo + "\n");
			success = payService.insertPayExeLog(payExeLogVo);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "inserttPayExeLog success:" + success + "\n");
		} catch (Exception e) {
			e.printStackTrace();
			logData.append(
					"[" + LogUtils.getCurrentTime() + "]" + " " + "inserttPayExeLog error e:" + e.toString() + "\n");
		}

		model.addAttribute("returnValue", returnValue);
		HashMap hashMap = (HashMap) model.asMap();
		Iterator<String> iterator = hashMap.keySet().iterator();
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 시작 ----\n");
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "key = " + key + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 종료 ----\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return "ini/mobile/INIBillPayReturn";
	}

	/**
	 * 이니시스 Card Bill 승인 처리 모듈
	 * 
	 * @param price
	 * @param goodname
	 * @param buyername
	 * @param buyertel
	 * @param buyeremail
	 * @param billkey
	 * @param logData
	 * @return
	 */
	public INIdata iniCardBill(String price, String goodname, String buyername, String buyertel, String buyeremail,
			String billkey, StringBuffer logData) {
		/**
		 * 빌링 승인
		 */
		INIpay inipay = new INIpay();
		INIdata data = new INIdata();

		/**
		 * 필수 고정 및 이니시스 인증후 전달 받은 값
		 */
		data.setData("type", "reqrealbill"); // 결제 type, 고정
		data.setData("inipayHome", Config.HOST_PATH_INIPAYHOME); // 이니페이가 설치된 절대경로
		data.setData("logMode", "true"); // logMode
		data.setData("mid", Config.INI_B_P_MID); // 상점아이디
		data.setData("keyPW", "1111"); // 키패스워드
		data.setData("currency", "WON"); // 화폐단위
		data.setData("paymethod", "Card"); // 지불방법:카드빌링
		data.setData("billkey", billkey); // 빌링등록 키(빌키)
		data.setData("url", Config.HOST_CONTENTS_IMAGE_MAKE); // 홈페이지 주소(URL)
		data.setData("cardquota", "00"); // 할부:일시불
		data.setData("authentification", "00");// 본인인증여부:완료
		data.setData("crypto", "execure"); // Extrus 암호화 모듈 적용(고정)

		/**
		 * DB 상품 조회
		 */
		data.setData("price", price); // 가격
		data.setData("goodname", goodname); // 상품명 (최대 40자)

		/**
		 * 고객 입력값
		 */
		data.setData("buyername", buyername); // 구매자 (최대 15자)
		data.setData("buyertel", buyertel); // 구매자이동전화
		data.setData("buyeremail", buyeremail); // 구매자이메일

		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "data:" + data + "\n");

		data = inipay.payRequest(data);

		return data;
	}

	/**
	 * 주문(O_ID) 번호 생성
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ini/mobile/INIBillPay/get_o_id", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getOId(HttpServletRequest request, HttpServletResponse response) {
		String returnValue = "";
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
		long o_id = 0;
		try {
			CreateOidVo createOidVo = new CreateOidVo();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start selectOid CreateOidVo createOidVo : " + createOidVo + "\n");
			o_id = payService.selectOid(createOidVo);
		} catch (Exception e) {
			logData.append(
					"[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectOid : " + e.toString() + "\n");
			e.printStackTrace();
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query end selectOid int o_id : " + o_id + "\n");
		returnValue = String.valueOf(o_id);
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "returnValue : " + returnValue + "\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return returnValue;
	}

	/**
	 * 정기결제 hashkey 생성
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ini/mobile/INIBillPay/hashdata", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getHashdata(HttpServletRequest request, HttpServletResponse response) {
		String returnValue = "";
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
		String orderid = StringUtil.nvl(request.getParameter("orderid"));
		String timestamp = StringUtil.nvl(request.getParameter("timestamp"));
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "orderid : " + orderid + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "timestamp : " + timestamp + "\n");
		String mid = Config.INI_B_P_MID;
		String merchantkey = Config.INI_B_P_MERCHANTKEY;
		String data = mid + orderid + timestamp + merchantkey;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "data : " + data + "\n");
		returnValue = StringUtil.getSHA256(data);
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "returnValue : " + returnValue + "\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return returnValue;
	}

	/**
	 * 결제취소 UI(일반,정기)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ini/cancel/INIStdCancelRequest", method = { RequestMethod.GET, RequestMethod.POST })
	public String iNIStdCancelRequest(HttpServletRequest request, HttpServletResponse response, Model model) {
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
		String p_mid = Config.INI_P_MID;
		String b_p_mid = Config.INI_B_P_MID;
		model.addAttribute("mid", p_mid);
		model.addAttribute("bmid", b_p_mid);
		HashMap hashMap = (HashMap) model.asMap();
		Iterator<String> iterator = hashMap.keySet().iterator();
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 시작 ----\n");
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "key = " + key + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 종료 ----\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return "ini/cancel/INIStdCancelRequest";
	}

	/**
	 * 결제취소(일반,정기)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ini/cancel/INIStdCancel", method = { RequestMethod.GET, RequestMethod.POST })
	public String iNIStdCancel(HttpServletRequest request, HttpServletResponse response, Model model) {
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
		INIpay50 inipay = null;
		String tid = "";
		try {
			String mid = request.getParameter("mid");// 상점아이디
			tid = request.getParameter("tid");// 취소할 거래의 거래아이디
			String msg = request.getParameter("msg");// 취소사유
			String cancelreason = request.getParameter("cancelreason");// 현금영수증
			inipay = iniCancle(mid, tid, msg, cancelreason, logData);
		} catch (Exception e) {
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + e.toString() + "\n");
		}

		/**
		 * 결제 취소 로그 20190610
		 */
		int success = 0;
		try {
			String log_cancel_code = "";
			String log_cancel_msg = "";
			String log_cancel_date = "";
			String log_cancel_time = "";
			if (inipay != null) {
				log_cancel_code = StringUtil.nvl(inipay.GetResult("ResultCode"));
				log_cancel_msg = StringUtil.nvl(inipay.GetResult("ResultMsg"));
				log_cancel_date = StringUtil.nvl(inipay.GetResult("CancelDate"));
				log_cancel_time = StringUtil.nvl(inipay.GetResult("CancelTime"));
			} else {
				log_cancel_msg = "결제 취소 오류";
			}
			String msg_ = log_cancel_code + " " + log_cancel_msg + " " + log_cancel_date + " " + log_cancel_time;
			PayCancelUserInfoVo payCancelUserInfoVo = new PayCancelUserInfoVo();
			String log_cancel_url = "";
			String log_cancel_company = "";
			String log_cancel_payment = "";
			String log_cancel_price = "";
			String log_cancel_currency = "";
			String log_cancel_goodname = "";
			String log_cancel_buyername = "";
			String log_cancel_buyertel = "";
			String log_cancel_buyeremail = "";
			String log_cancel_cardquota = "";
			String log_cancel_billkey = "";
			String log_cancel_tid = "";
			String log_cancel_auth_dt = "";
			String log_cancel_oid = "";
			String log_cancel_access_seq = "";
			String log_cancel_pay_type_code = "";
			String log_cancel_pay_success_seq = "";
			String log_cancel_pay_bill_seq = "";
			String log_cancel_id = "";
			String log_cancel_affiliates_code = "";
			try {
				payCancelUserInfoVo.setTid(tid);
				payCancelUserInfoVo.setOid(null);
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
						+ "query start selectPayCancelUserInfo PayCancelUserInfoVo payCancelUserInfoVo:"
						+ payCancelUserInfoVo + "\n");
				payCancelUserInfoVo = (PayCancelUserInfoVo) payService.selectPayCancelUserInfo(payCancelUserInfoVo);
				if (payCancelUserInfoVo != null) {
					log_cancel_url = StringUtil.nvl(payCancelUserInfoVo.getUrl());
					log_cancel_company = StringUtil.nvl(payCancelUserInfoVo.getCompany());
					log_cancel_payment = StringUtil.nvl(payCancelUserInfoVo.getPayment());
					log_cancel_price = StringUtil.nvl(payCancelUserInfoVo.getPrice());
					log_cancel_currency = StringUtil.nvl(payCancelUserInfoVo.getCurrency());
					log_cancel_goodname = StringUtil.nvl(payCancelUserInfoVo.getGoodname());
					log_cancel_buyername = StringUtil.nvl(payCancelUserInfoVo.getBuyername());
					log_cancel_buyertel = StringUtil.nvl(payCancelUserInfoVo.getBuyertel());
					log_cancel_buyeremail = StringUtil.nvl(payCancelUserInfoVo.getBuyeremail());
					log_cancel_cardquota = StringUtil.nvl(payCancelUserInfoVo.getCardquota());
					log_cancel_billkey = StringUtil.nvl(payCancelUserInfoVo.getBillkey());
					log_cancel_tid = StringUtil.nvl(payCancelUserInfoVo.getTid());
					log_cancel_auth_dt = StringUtil.nvl(payCancelUserInfoVo.getAuth_dt());
					log_cancel_oid = StringUtil.nvl(payCancelUserInfoVo.getOid());
					log_cancel_access_seq = StringUtil.nvl(payCancelUserInfoVo.getAccess_seq());
					log_cancel_pay_type_code = StringUtil.nvl(payCancelUserInfoVo.getPay_type_code());
					log_cancel_pay_success_seq = StringUtil.nvl(payCancelUserInfoVo.getPay_success_seq());
					log_cancel_pay_bill_seq = StringUtil.nvl(payCancelUserInfoVo.getPay_bill_seq());
					log_cancel_id = StringUtil.nvl(payCancelUserInfoVo.getId());
					log_cancel_affiliates_code = StringUtil.nvl(payCancelUserInfoVo.getAffiliates_code());
				}
			} catch (Exception e) {
				e.printStackTrace();
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayCancelUserInfo : "
						+ e.toString() + "\n");
			}
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query end selectPayCancelUserInfo PayCancelUserInfoVo payCancelUserInfoVo:" + payCancelUserInfoVo
					+ "\n");
			PayCancelExeLogVo payCancelExeLogVo = new PayCancelExeLogVo();
			if (!"".equals(log_cancel_url)) {
				payCancelExeLogVo.setUrl(log_cancel_url);
			}
			if (!"".equals(log_cancel_company)) {
				payCancelExeLogVo.setCompany(log_cancel_company);
			}
			if (!"".equals(log_cancel_payment)) {
				payCancelExeLogVo.setPayment(log_cancel_payment);
			}
			if (!"".equals(log_cancel_price)) {
				payCancelExeLogVo.setPrice(log_cancel_price);
			}
			if (!"".equals(log_cancel_currency)) {
				payCancelExeLogVo.setCurrency(log_cancel_currency);
			}
			if (!"".equals(log_cancel_goodname)) {
				payCancelExeLogVo.setGoodname(log_cancel_goodname);
			}
			if (!"".equals(log_cancel_buyername)) {
				payCancelExeLogVo.setBuyername(log_cancel_buyername);
			}
			if (!"".equals(log_cancel_buyertel)) {
				payCancelExeLogVo.setBuyertel(log_cancel_buyertel);
			}
			if (!"".equals(log_cancel_buyeremail)) {
				payCancelExeLogVo.setBuyeremail(log_cancel_buyeremail);
			}
			if (!"".equals(log_cancel_cardquota)) {
				payCancelExeLogVo.setCardquota(log_cancel_cardquota);
			}
			if (!"".equals(log_cancel_billkey)) {
				payCancelExeLogVo.setBillkey(log_cancel_billkey);
			}
			if (!"".equals(log_cancel_tid)) {
				payCancelExeLogVo.setTid(log_cancel_tid);
			}
			if (!"".equals(log_cancel_auth_dt)) {
				payCancelExeLogVo.setAuth_dt(log_cancel_auth_dt);
			}
			if (!"".equals(log_cancel_oid)) {
				payCancelExeLogVo.setOid(log_cancel_oid);
			}
			if (!"".equals(log_cancel_access_seq)) {
				payCancelExeLogVo.setAccess_seq(log_cancel_access_seq);
			}
			if (!"".equals(log_cancel_pay_type_code)) {
				payCancelExeLogVo.setPay_type_code(log_cancel_pay_type_code);
			}
			if (!"".equals(log_cancel_pay_success_seq)) {
				payCancelExeLogVo.setPay_success_seq(log_cancel_pay_success_seq);
			}
			if (!"".equals(log_cancel_pay_bill_seq)) {
				payCancelExeLogVo.setPay_bill_seq(log_cancel_pay_bill_seq);
			}
			if (!"".equals(log_cancel_id)) {
				payCancelExeLogVo.setId(log_cancel_id);
			}
			if (!"".equals(log_cancel_affiliates_code)) {
				payCancelExeLogVo.setAffiliates_code(log_cancel_affiliates_code);
			}
			if (!"".equals(msg_)) {
				payCancelExeLogVo.setMsg(msg_);
			}
			if ("00".equals(log_cancel_code)) {
				payCancelExeLogVo.setSuccess("Y");
			} else {
				payCancelExeLogVo.setSuccess("N");
			}
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start insertPayCancelExeLog PayCancelExeLogVo payCancelExeLogVo:" + payCancelExeLogVo
					+ "\n");
			success = payService.insertPayCancelExeLog(payCancelExeLogVo);
		} catch (Exception e) {
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error insertPayCancelExeLog : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query end insertPayCancelExeLog int success:"
				+ success + "\n");

		HashMap hashMap = (HashMap) model.asMap();
		Iterator<String> iterator = hashMap.keySet().iterator();
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 시작 ----\n");
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "key = " + key + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- model 종료 ----\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return "ini/cancel/INIStdCancel";
	}

	/**
	 * 결제취소(일반,정기) API
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ini/cancel/INIStdCancelApi", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody HashMap<Object, Object> iNIStdCancelApi(HttpServletRequest request,
			HttpServletResponse response) {
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
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		HashMap<Object, Object> meta = new HashMap<Object, Object>();
		INIpay50 inipay = null;
		String tid = "";
		try {
			String mid = StringUtil.nvl(request.getParameter("mid"));// 상점아이디 0:일반,1:정기
			tid = StringUtil.nvl(request.getParameter("tid"));// 취소할 거래의 거래아이디
			String msg = "사용자요청 구매취소";// 취소사유 1 거래취소 고정
			if ("0".equals(mid)) {
				mid = Config.INI_P_MID;
			} else if ("1".equals(mid)) {
				mid = Config.INI_B_P_MID;
			}
			String cancelreason = "1";// 현금영수증 1 거래취소 고정
			inipay = iniCancle(mid, tid, msg, cancelreason, logData);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "inipay.GetResult(\"ResultCode\") : "
					+ inipay.GetResult("ResultCode") + "\n");
			if (inipay != null) {
				if ("00".equals(inipay.GetResult("ResultCode"))) {
					meta.put("code", "200");
					map.put("meta", meta);
				} else if ("01".equals(inipay.GetResult("ResultCode"))) {
					try {
						String ResultMsg_code = inipay.GetResult("ResultMsg").replaceAll("\\[", "")
								.replaceAll("\\]", "").split("[|]")[0];
						String ResultMsg_msg = inipay.GetResult("ResultMsg").replaceAll("\\[", "").replaceAll("\\]", "")
								.split("[|]")[1];
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "ResultMsg_code : "
								+ ResultMsg_code + "\n");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "ResultMsg_msg : " + ResultMsg_msg
								+ "\n");
						if ("500626".equals(ResultMsg_code) && "기 취소 거래".equals(ResultMsg_msg)) {
							meta.put("code", "201");
							map.put("meta", meta);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + e.toString() + "\n");
		}

		/**
		 * 결제 취소 로그 20190610
		 */
		int success = 0;
		try {
			String log_cancel_code = "";
			String log_cancel_msg = "";
			String log_cancel_date = "";
			String log_cancel_time = "";
			if (inipay != null) {
				log_cancel_code = StringUtil.nvl(inipay.GetResult("ResultCode"));
				log_cancel_msg = StringUtil.nvl(inipay.GetResult("ResultMsg"));
				log_cancel_date = StringUtil.nvl(inipay.GetResult("CancelDate"));
				log_cancel_time = StringUtil.nvl(inipay.GetResult("CancelTime"));
			} else {
				log_cancel_msg = "결제 취소 오류";
			}
			String msg_ = log_cancel_code + " " + log_cancel_msg + " " + log_cancel_date + " " + log_cancel_time;
			PayCancelUserInfoVo payCancelUserInfoVo = new PayCancelUserInfoVo();
			String log_cancel_url = "";
			String log_cancel_company = "";
			String log_cancel_payment = "";
			String log_cancel_price = "";
			String log_cancel_currency = "";
			String log_cancel_goodname = "";
			String log_cancel_buyername = "";
			String log_cancel_buyertel = "";
			String log_cancel_buyeremail = "";
			String log_cancel_cardquota = "";
			String log_cancel_billkey = "";
			String log_cancel_tid = "";
			String log_cancel_auth_dt = "";
			String log_cancel_oid = "";
			String log_cancel_access_seq = "";
			String log_cancel_pay_type_code = "";
			String log_cancel_pay_success_seq = "";
			String log_cancel_pay_bill_seq = "";
			String log_cancel_id = "";
			String log_cancel_affiliates_code = "";
			try {
				payCancelUserInfoVo.setTid(tid);
				payCancelUserInfoVo.setOid(null);
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
						+ "query start selectPayCancelUserInfo PayCancelUserInfoVo payCancelUserInfoVo:"
						+ payCancelUserInfoVo + "\n");
				payCancelUserInfoVo = (PayCancelUserInfoVo) payService.selectPayCancelUserInfo(payCancelUserInfoVo);
				if (payCancelUserInfoVo != null) {
					log_cancel_url = StringUtil.nvl(payCancelUserInfoVo.getUrl());
					log_cancel_company = StringUtil.nvl(payCancelUserInfoVo.getCompany());
					log_cancel_payment = StringUtil.nvl(payCancelUserInfoVo.getPayment());
					log_cancel_price = StringUtil.nvl(payCancelUserInfoVo.getPrice());
					log_cancel_currency = StringUtil.nvl(payCancelUserInfoVo.getCurrency());
					log_cancel_goodname = StringUtil.nvl(payCancelUserInfoVo.getGoodname());
					log_cancel_buyername = StringUtil.nvl(payCancelUserInfoVo.getBuyername());
					log_cancel_buyertel = StringUtil.nvl(payCancelUserInfoVo.getBuyertel());
					log_cancel_buyeremail = StringUtil.nvl(payCancelUserInfoVo.getBuyeremail());
					log_cancel_cardquota = StringUtil.nvl(payCancelUserInfoVo.getCardquota());
					log_cancel_billkey = StringUtil.nvl(payCancelUserInfoVo.getBillkey());
					log_cancel_tid = StringUtil.nvl(payCancelUserInfoVo.getTid());
					log_cancel_auth_dt = StringUtil.nvl(payCancelUserInfoVo.getAuth_dt());
					log_cancel_oid = StringUtil.nvl(payCancelUserInfoVo.getOid());
					log_cancel_access_seq = StringUtil.nvl(payCancelUserInfoVo.getAccess_seq());
					log_cancel_pay_type_code = StringUtil.nvl(payCancelUserInfoVo.getPay_type_code());
					log_cancel_pay_success_seq = StringUtil.nvl(payCancelUserInfoVo.getPay_success_seq());
					log_cancel_pay_bill_seq = StringUtil.nvl(payCancelUserInfoVo.getPay_bill_seq());
					log_cancel_id = StringUtil.nvl(payCancelUserInfoVo.getId());
					log_cancel_affiliates_code = StringUtil.nvl(payCancelUserInfoVo.getAffiliates_code());
				}
			} catch (Exception e) {
				e.printStackTrace();
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error selectPayCancelUserInfo : "
						+ e.toString() + "\n");
			}
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query end selectPayCancelUserInfo PayCancelUserInfoVo payCancelUserInfoVo:" + payCancelUserInfoVo
					+ "\n");
			PayCancelExeLogVo payCancelExeLogVo = new PayCancelExeLogVo();
			if (!"".equals(log_cancel_url)) {
				payCancelExeLogVo.setUrl(log_cancel_url);
			}
			if (!"".equals(log_cancel_company)) {
				payCancelExeLogVo.setCompany(log_cancel_company);
			}
			if (!"".equals(log_cancel_payment)) {
				payCancelExeLogVo.setPayment(log_cancel_payment);
			}
			if (!"".equals(log_cancel_price)) {
				payCancelExeLogVo.setPrice(log_cancel_price);
			}
			if (!"".equals(log_cancel_currency)) {
				payCancelExeLogVo.setCurrency(log_cancel_currency);
			}
			if (!"".equals(log_cancel_goodname)) {
				payCancelExeLogVo.setGoodname(log_cancel_goodname);
			}
			if (!"".equals(log_cancel_buyername)) {
				payCancelExeLogVo.setBuyername(log_cancel_buyername);
			}
			if (!"".equals(log_cancel_buyertel)) {
				payCancelExeLogVo.setBuyertel(log_cancel_buyertel);
			}
			if (!"".equals(log_cancel_buyeremail)) {
				payCancelExeLogVo.setBuyeremail(log_cancel_buyeremail);
			}
			if (!"".equals(log_cancel_cardquota)) {
				payCancelExeLogVo.setCardquota(log_cancel_cardquota);
			}
			if (!"".equals(log_cancel_billkey)) {
				payCancelExeLogVo.setBillkey(log_cancel_billkey);
			}
			if (!"".equals(log_cancel_tid)) {
				payCancelExeLogVo.setTid(log_cancel_tid);
			}
			if (!"".equals(log_cancel_auth_dt)) {
				payCancelExeLogVo.setAuth_dt(log_cancel_auth_dt);
			}
			if (!"".equals(log_cancel_oid)) {
				payCancelExeLogVo.setOid(log_cancel_oid);
			}
			if (!"".equals(log_cancel_access_seq)) {
				payCancelExeLogVo.setAccess_seq(log_cancel_access_seq);
			}
			if (!"".equals(log_cancel_pay_type_code)) {
				payCancelExeLogVo.setPay_type_code(log_cancel_pay_type_code);
			}
			if (!"".equals(log_cancel_pay_success_seq)) {
				payCancelExeLogVo.setPay_success_seq(log_cancel_pay_success_seq);
			}
			if (!"".equals(log_cancel_pay_bill_seq)) {
				payCancelExeLogVo.setPay_bill_seq(log_cancel_pay_bill_seq);
			}
			if (!"".equals(log_cancel_id)) {
				payCancelExeLogVo.setId(log_cancel_id);
			}
			if (!"".equals(log_cancel_affiliates_code)) {
				payCancelExeLogVo.setAffiliates_code(log_cancel_affiliates_code);
			}
			if (!"".equals(msg_)) {
				payCancelExeLogVo.setMsg(msg_);
			}
			if ("00".equals(log_cancel_code)) {
				payCancelExeLogVo.setSuccess("Y");
			} else {
				payCancelExeLogVo.setSuccess("N");
			}
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start insertPayCancelExeLog PayCancelExeLogVo payCancelExeLogVo:" + payCancelExeLogVo
					+ "\n");
			success = payService.insertPayCancelExeLog(payCancelExeLogVo);
		} catch (Exception e) {
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error insertPayCancelExeLog : "
					+ e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query end insertPayCancelExeLog int success:"
				+ success + "\n");

		// OK 성공
		if (!"200".equals(StringUtil.nvl(meta.get("code") + ""))
				&& !"201".equals(StringUtil.nvl(meta.get("code") + ""))) {
			meta.put("code", "999");
			meta.put("error_type", "Unknown Erro");
			meta.put("error_message", "시스템 오류 (timeout, database, program error 등등 서버 내부의 오류 내용)");
		}
		map.put("meta", meta);
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());

		return map;
	}

	/**
	 * 결제취소(일반,정기)
	 * 
	 * @param mid
	 * @param tid
	 * @param msg
	 * @param cancelreason
	 * @param logData
	 */
	public INIpay50 iniCancle(String mid, String tid, String msg, String cancelreason, StringBuffer logData) {

		/*-----------------------------------------------------
		 * INIcancel.jsp
		 *
		 * Date : 2007/09
		 * Author : ts@inicis.com
		 * Project : INIpay V5.0 for JAVA(JSP)
		 * 
		 * http://www.inicis.com
		 * Copyright (C) 2007 Inicis, Co. All rights reserved.
		-------------------------------------------------------*/
		/***************************************
		 * 1. INIpay 라이브러리 *
		 * com.inicis.inipay.*
		 ***************************************/

		/***************************************
		 * 2. INIpay 클래스의 인스턴스 생성 *
		 ***************************************/
		INIpay50 inipay = new INIpay50();

		/*********************
		 * 3. 취소 정보 설정 *
		 *********************/
		inipay.SetField("inipayhome", Config.HOST_PATH_INIPAYHOME); // 이니페이 홈디렉터리(상점수정 필요)
		inipay.SetField("type", "cancel"); // 고정 (절대 수정 불가)
		inipay.SetField("debug", "true"); // 로그모드("true"로 설정하면 상세로그가 생성됨.)
		inipay.SetField("mid", mid); // 상점아이디
		inipay.SetField("admin", "1111"); // 상점 키패스워드 (비대칭키)
		inipay.SetField("cancelreason", cancelreason); // 현금영수증
														// 취소코드
		// ***********************************************************************************************************
		// * admin 은 키패스워드 변수명입니다. 수정하시면 안됩니다. 1111의 부분만 수정해서 사용하시기 바랍니다. *
		// * 키패스워드는 상점관리자 페이지(https://iniweb.inicis.com)의 비밀번호가 아닙니다. 주의해 주시기
		// 바랍니다.*
		// * 키패스워드는 숫자 4자리로만 구성됩니다. 이 값은 키파일 발급시 결정됩니다. *
		// * 키패스워드 값을 확인하시려면 상점측에 발급된 키파일 안의 readme.txt 파일을 참조해 주십시오. *
		// ***********************************************************************************************************
		inipay.SetField("tid", tid); // 취소할 거래의 거래아이디
		inipay.SetField("cancelmsg", msg); // 취소사유
		inipay.SetField("crypto", "execure"); // Extrus 암호화모듈 사용(고정)

		/****************
		 * 4. 취소 요청 *
		 ****************/
		inipay.startAction();

		/****************************************************************
		 * 5. 취소 결과
		 *
		 * 결과코드 : inipay.GetResult("ResultCode") ("00"이면 취소 성공) 결과내용 :
		 * inipay.GetResult("ResultMsg") (취소결과에 대한 설명) 취소날짜 :
		 * inipay.GetResult("CancelDate") (YYYYMMDD) 취소시각 :
		 * inipay.GetResult("CancelTime") (HHMMSS) 현금영수증 취소 승인번호 :
		 * inipay.GetResult("CSHR_CancelNum") (현금영수증 발급 취소시에만 리턴됨)
		 ****************************************************************/
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---취소결과---" + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "고객님께서 이니페이를 통해 취소하신 내용입니다." + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---취소내역---" + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "결 과 코 드" + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + inipay.GetResult("ResultCode") + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "결 과 내 용" + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + inipay.GetResult("ResultMsg") + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "거 래 번 호" + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + tid + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "취 소 날 짜" + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + inipay.GetResult("CancelDate") + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "취 소 시 각" + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + inipay.GetResult("CancelTime") + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---현금영수증---" + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "취소승인번호" + "\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + inipay.GetResult("CSHR_CancelNum") + "\n");

		return inipay;

	}

	/**
	 * 테스트 아이디 체크
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ini/mobile/checkTestId", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String checkTestId(HttpServletRequest request, HttpServletResponse response) {
		String returnValue = "";
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
		String id = StringUtil.nvl(request.getParameter("id"));
		int count = 0;
		try {
			count = payService.selectAccessTestIdCount(id);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query end selectAccessTestIdCount int count : "
				+ count + "\n");
		returnValue = String.valueOf(count);
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "returnValue : " + returnValue + "\n");
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return returnValue;
	}

	public static void main(String[] arg) {
		// /**
		// * 빌링 결제 테스트
		// */
		// INIpay inipay = new INIpay();
		// INIdata data = new INIdata();
		// /**
		// * YDH 2018.01.02
		// * localhost 테스트 환경
		// */
		// if("".equals(Config.HOST_PATH_INIPAYHOME)) {
		// Config.HOST_PATH_INIPAYHOME =
		// "D:/home/server/tomcat/apache-tomcat-7.0.77/jyes_bizmsg/dev/bizcontents_tomcat/inipayhome";
		// }
		// /**
		// * 필수
		// */
		// data.setData("type", "reqrealbill"); // 결제 type, 고정
		// data.setData("inipayHome", Config.HOST_PATH_INIPAYHOME); // 이니페이가 설치된 절대경로
		// data.setData("logMode", "true"); // logMode
		// data.setData("mid", "INIBillTst"); // 상점아이디
		// data.setData("keyPW", "1111"); // 키패스워드
		// data.setData("paymethod", "Card"); // 지불방법, 카드빌링
		// data.setData("billkey", "0338d8aa3ca7b5e2c2b47642bcc0777801871ee4"); // 빌링등록
		// 키(빌키)
		// data.setData("crypto", "execure"); // Extrus 암호화 모듈 적용(고정)
		// /**
		// * 필수??
		// */
		// data.setData("url", "http://localhost"); // 홈페이지 주소(URL)
		// data.setData("price", "1000"); // 가격
		// data.setData("currency", "WON"); // 화폐단위
		// data.setData("goodname", "TEST"); // 상품명 (최대 40자)
		// data.setData("buyername", "제이예스"); // 구매자 (최대 15자)
		// data.setData("buyertel", "01084092025"); // 구매자이동전화
		// data.setData("buyeremail", "huntux2@jyes.co.kr"); // 구매자이메일
		// data.setData("cardquota", "0"); // 구매자이동전화
		// data.setData("authentification", "00");//본인인증여부
		// /**
		// * 필요없음!!
		// */
		//// data.setData("subPgip", "203.238.37.3"); // Sub PG IP (고정)
		//
		// data = inipay.payRequest(data);
		//
		// System.out.println("data:"+data);

		// /**
		// * 결제 취소 테스트(일반,빌링)
		// */
		//
		// String mid = "INIBillTst";// 상점아이디
		// String tid = "INIpayCARDINIBillTst20180103110331424171";// 취소할 거래의 거래아이디
		// String msg = "테스트";// 취소사유
		// String cancelreason = "1";// 현금영수증 1:거래취소,2:오류,3:기타
		//
		// StringBuffer logData = new StringBuffer();
		//
		// PayController pc = new PayController();
		// pc.iniCancle(mid, tid, msg, cancelreason, logData);
		//
		// System.out.println(logData.toString());

		// String osName = System.getProperty("os.name");
		// System.out.println(osName);

		// System.out.println(StringUtil.getSHA256("INIBillTstORDERID123420130808171939b09LVzhuTGZVaEY1WmJoQnZzdXpRdz09"));

		// String url = "https://inilite.inicis.com/inibill/inibill_card.jsp";
		//
		// System.out.println(url.split("/")[2].substring(url.split("/")[2].length()-10));

		ArrayList<String> allUrl = new ArrayList<String>();
		allUrl.add("http://bizcontentsdev.sohomsg.kr/1");
		allUrl.add("http://bizcontentsdev.sohomsg.kr/2");
		allUrl.add("http://bizcontentsdev.sohomsg.kr/3");
		allUrl.add("https://mobile.inicis.com/1");
		allUrl.add("https://mobile.inicis.com/2");
		allUrl.add("http://bizcontentsdev.sohomsg.kr/4");

		if (allUrl.size() > 0) {
			int remove_index = -1;
			for (int i = 0; i < allUrl.size(); i++) {
				String url = allUrl.get(i);
				if (url.split("/")[2].substring(url.split("/")[2].length() - 10).equals("inicis.com")) {
					remove_index = i;
					break;
				}
			}
			if (remove_index == -1) {
				allUrl.remove(allUrl.size() - 1);
			} else {
				for (int i = (allUrl.size() - 1); 0 <= i; i--) {
					if (i >= remove_index) {
						allUrl.remove(i);
					}
				}
			}
			if (allUrl.size() > 0) {
				System.out.println("move:" + allUrl.get(allUrl.size() - 1));
			}
		}
	}

}