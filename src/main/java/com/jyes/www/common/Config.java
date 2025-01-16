package com.jyes.www.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;

import com.jyes.www.network.OneStore;
import com.jyes.www.util.NetUtils;

public class Config {
	private static final Log log = LogFactory.getLog(Config.class);

	/**
	 * ###dev true:개발, false: 상용
	 */
	public final static boolean isDevMode = true;

	/**
	 * ###db 서버 true:긴급, false: 일반
	 */
	public final static boolean isEmergencyMode = false;

	/**
	 * 이니시스 모듈 상점키 true:테스트, false:상용
	 */
	public final static boolean isIniDevMode = false;

	/**
	 * 고객센터 번호
	 */
	public static String REPRESENTATION_NUMBER = "01057569604";
	public static String REPRESENTATION_NUMBER_SMATRO = "01057569604";

	// 카카오 플러스 친구 아이디
	public static String KAKAOPLUS_ID = "_kxncpj";

	// 구글 API 인증 키
	public static String APPLICATION_NAME = "smartro";
	public static String API_KEY = "AIzaSyDDZiaqavqo-GbpS14Ulx242hoXSSBkYNg";

	// 스마트로 API URL 도메인
	public static String HOST_DEV_SMARTRO_SERVER_URL = "https://tbizzle.smartro.co.kr";
	public static String HOST_DEP_SMARTRO_SERVER_URL = "https://bizzle.smartro.co.kr";
	public static String HOST_SMARTRO_SERVER_URL = "";

	// 컨텐츠 이미지 MOBILE WEB URL 도메인
	public static String HOST_DEV_CONTENTS_IMAGE_MAKE = "http://bizcontentsdev.sohomsg.kr";
	public static String HOST_DEP_CONTENTS_IMAGE_MAKE = "https://bizcontents.sohomsg.kr";
	public static String HOST_CONTENTS_IMAGE_MAKE = "";

	// 컨텐츠 이미지 URL 도메인
	public static String HOST_DEV_CONTENTS_IMAGE_URL = "http://imgdev.sohomsg.kr";
	public static String HOST_DEP_CONTENTS_IMAGE_URL = "https://img.sohomsg.kr";
	public static String HOST_CONTENTS_IMAGE_URL = "";

	// 컨텐츠 이미지 URL 도메인
	public static String HOST_DEV_WWW_URL = "http://wwwdev.sohomsg.kr";
	public static String HOST_DEP_WWW_URL = "https://www.sohomsg.kr";
	public static String HOST_WWW_URL = "";
	// 컨텐츠 이미지 확장자(PNG 통일)
	public static String EXTENSION_CONTENTS_IMAGE = "png";
	// log4j
	public static String LOG4J_XML_DEV = "log4j_dev.xml";
	public static String LOG4J_XML_DEP = "log4j_dep.xml";
	public static String LOG4J_XML = "";
	// 이니시스홈
	public static String HOST_DEV_PATH_INIPAYHOME = "/home/server/tomcat/apache-tomcat-7.0.77/jyes_bizmsg/dev/bizcontents_tomcat/inipayhome";
	public static String HOST_DEP_PATH_INIPAYHOME = "/home/server/tomcat/bizcontents_tomcat/inipayhome";
	public static String HOST_PATH_INIPAYHOME = "";
	// 이니시스 상점아이디(일반결제)
	public static String INI_P_MID_DEV = "INIpayTest";
	public static String INI_P_MID_DEP = "jyescompan";
	public static String INI_P_MID = "";
	// 이니시스 상점아이디(빌링결제)
	public static String INI_B_P_MID_DEV = "INIBillTst";
	public static String INI_B_P_MID_DEP = "jyesbillco";
	public static String INI_B_P_MID = "";
	// 이니시스 상점아이디(빌링결제:상점대칭키)
	public static String INI_B_P_MERCHANTKEY_DEV = "b09LVzhuTGZVaEY1WmJoQnZzdXpRdz09";
	public static String INI_B_P_MERCHANTKEY_DEP = "ZklYZkNZa3NyZDV6V1BOc1o2RUdkdz09";
	public static String INI_B_P_MERCHANTKEY = "";

	public static String INI_HOST_MOBILETX = "https://mobile.inicis.com";
	public static String INI_HOST_INILITE = "https://inilite.inicis.com";

	// Jsam, 똑똑비 api 주소
	public static String API_URL_DEV = "http://192.168.0.240:8080/wirelessbizmsg";
	// public static String API_URL_DEV = "http://apidev.sohomsg.kr";
	public static String API_URL_DEP = "https://api.sohomsg.kr";
	public static String API_URL = "";

	public static void init() {
		if (isDevMode) {
			OneStore.is_onestore_dev = true;
			HOST_WWW_URL = HOST_DEV_WWW_URL;
			HOST_CONTENTS_IMAGE_MAKE = HOST_DEV_CONTENTS_IMAGE_MAKE;
			HOST_CONTENTS_IMAGE_URL = HOST_DEV_CONTENTS_IMAGE_URL;
			LOG4J_XML = LOG4J_XML_DEV;
			HOST_PATH_INIPAYHOME = HOST_DEV_PATH_INIPAYHOME;
			HOST_SMARTRO_SERVER_URL = HOST_DEV_SMARTRO_SERVER_URL;
			API_URL = API_URL_DEV;
			/*
			 * String osName = System.getProperty("os.name");
			 * if("windows".contains(osName.toLowerCase())) {
			 * Config.HOST_PATH_INIPAYHOME =
			 * "D:/home/server/tomcat/apache-tomcat-7.0.77/jyes_bizmsg/dev/bizcontents_tomcat/inipayhome";
			 * }
			 */
		} else {
			OneStore.is_onestore_dev = false;
			HOST_WWW_URL = HOST_DEP_WWW_URL;
			HOST_CONTENTS_IMAGE_MAKE = HOST_DEP_CONTENTS_IMAGE_MAKE;
			HOST_CONTENTS_IMAGE_URL = HOST_DEP_CONTENTS_IMAGE_URL;
			LOG4J_XML = LOG4J_XML_DEP;
			HOST_PATH_INIPAYHOME = HOST_DEP_PATH_INIPAYHOME;
			HOST_SMARTRO_SERVER_URL = HOST_DEP_SMARTRO_SERVER_URL;
			API_URL = API_URL_DEP;
		}
		if (isIniDevMode) {
			INI_P_MID = INI_P_MID_DEV;
			INI_B_P_MID = INI_B_P_MID_DEV;
			INI_B_P_MERCHANTKEY = INI_B_P_MERCHANTKEY_DEV;
		} else {
			INI_P_MID = INI_P_MID_DEP;
			INI_B_P_MID = INI_B_P_MID_DEP;
			INI_B_P_MERCHANTKEY = INI_B_P_MERCHANTKEY_DEP;
		}
		/**
		 * 로컬 개발시 주석처리 필요(콘솔로그안나옴)
		 */
		// DOMConfigurator.configure(Config.class.getClassLoader().getResource(LOG4J_XML));
		String emergencyMode = "일반 ";
		if (isEmergencyMode) {
			emergencyMode = "긴급 ";
		}
		if (isDevMode) {
			log.info(emergencyMode + "개발 모드 IP:" + NetUtils.getServerIp());
		} else {
			log.info(emergencyMode + "상용 모드 IP:" + NetUtils.getServerIp());
		}
	}

}
