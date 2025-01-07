package com.jyes.www.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/*import com.jyes.www.vo.aligo.RemainVo;
import com.jyes.www.vo.aligo.SendVo;*/
import com.jyes.www.vo.CheckMemberVo;
import com.jyes.www.vo.PayInfoRequestVo;

public class JsonParser {

	public static Gson mGson = new GsonBuilder().create();

	/*
	 * public static SendVo parseSend(String jsonString) throws Exception {
	 * return mGson.fromJson(jsonString, SendVo.class);
	 * }
	 * 
	 * public static RemainVo parseRemain(String jsonString) throws Exception {
	 * return mGson.fromJson(jsonString, RemainVo.class);
	 * }
	 */

	public static CheckMemberVo parseCheckMemberVo(String jsonString) throws Exception {
		return mGson.fromJson(jsonString, CheckMemberVo.class);
	}

	public static PayInfoRequestVo parsePayInfoRequestVo(String jsonString) throws Exception {
		return mGson.fromJson(jsonString, PayInfoRequestVo.class);
	}

	// 똑똑비 추가

	public static com.jyes.www.vo.ttb.CheckMemberVo parseTtbCheckMemberVo(String jsonString) throws Exception {
		return mGson.fromJson(jsonString, com.jyes.www.vo.ttb.CheckMemberVo.class);
	}

	public static com.jyes.www.vo.ttb.PayInfoRequestVo parseTtbPayInfoRequestVo(String jsonString) throws Exception {
		return mGson.fromJson(jsonString, com.jyes.www.vo.ttb.PayInfoRequestVo.class);
	}

}