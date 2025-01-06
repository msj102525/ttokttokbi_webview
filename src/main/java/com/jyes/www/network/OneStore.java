package com.jyes.www.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OneStore {
	
	public static boolean is_onestore_dev = false;
	
	public final static String PACKAGE_NAME = "kr.co.jyes.sam";
	public final static String PACKAGE_NAME_SMARTRO = "kr.co.jyes.smtr.soho";
    public final static String CLIENT_SECRET = "GpkY3wnEXzst09+w2hq0nQIBbOVpnE8F8E3Kpt7iDTE=";
    public final static String CLIENT_SECRET_SMARTRO = "YmBeooQHUqY7hi/yLefsgJw7d4ZdPjB4txyHFGfLs3Y=";
    public static String GRANT_TYPE = "client_credentials";
    
    private static final String HOST_DEP = "https://apis.onestore.co.kr";
    private static final String HOST_DEV = "https://sbpp.onestore.co.kr";
    private static String HOST = "";
    
    public static String getUrl(String url) {
    	if(is_onestore_dev) {
    		HOST = HOST_DEV;
    	} else {
    		HOST = HOST_DEP;
    	}
    	url = HOST + url;
    	return url;
    }
    
    public class API {
        public static final String TOKEN = "/v2/oauth/token";
        public static final String SEND = "/v2/purchase/developer/"+PACKAGE_NAME+"/send";
        public static final String CANCEL = "/v2/purchase/developer/"+PACKAGE_NAME+"/cancel";
        public static final String SEND_SMARTRO = "/v2/purchase/developer/"+PACKAGE_NAME_SMARTRO+"/send";
        public static final String CANCEL_SMARTRO = "/v2/purchase/developer/"+PACKAGE_NAME_SMARTRO+"/cancel";
    }
    
    public static void main(String[] args) {
    	
    	try {
    		
			long currenttime = System.currentTimeMillis();
			
			JsonParser jp = null;
			JsonObject json_response = null;
			String access_token = "";
			
			/**
			 * getAccessToken (AccessToken 발급)
			 */

			String url = OneStore.getUrl(OneStore.API.TOKEN);
			String content_type = MediaType.APPLICATION_FORM_URLENCODED.toString();
			
			JSONObject json_params = new JSONObject();
			json_params.put("client_id", OneStore.PACKAGE_NAME);
			json_params.put("client_secret", OneStore.CLIENT_SECRET);
			json_params.put("grant_type", OneStore.GRANT_TYPE);
			
			Http http = new Http(url, content_type);
			http.setJsonParam(json_params);
			String response = http.submit();
			
			if(response!=null) {
				jp = new JsonParser();
				json_response = jp.parse(response).getAsJsonObject();
				System.out.println("json_response:"+json_response);
				if(json_response.get("error")==null) {
					access_token = json_response.get("access_token").getAsString();
					System.out.println("access_token:"+access_token);
				} else {
					String code = json_response.get("error").getAsJsonObject().get("code").getAsString();
					System.out.println("code:"+code);
				}
			}
			
			if(!"".equals(access_token)) {
				
				/**
				 * send3rdPartyPurchase (외부결제 구매내역 전송)
				 */
				
				ArrayList<Object> al = new ArrayList<Object>();
				Map<String, String> map = new HashMap<String, String>();
				map.put("developerProductId", "P0001");//상품코드
				map.put("developerProductName", "테스트 상품");//상품명
				map.put("developerProductPrice", "1000");//가격
				map.put("developerProductQty", "1");//수량
				al.add(map);
				
				ArrayList<Object> al_a = new ArrayList<Object>();
				Map<String, String> map_a = new HashMap<String, String>();
				map_a.put("purchaseMethodCd", "TRD_CREDITCARD");//TRD_CREDITCARD 신용카드 TRD_BANKTRANSFER 무통장
				map_a.put("purchasePrice", "1000");//결제수단 별 결제금액
				al_a.add(map_a);
				
				json_params = new JSONObject();
				json_params.put("access_token", access_token);
				json_params.put("adId", "UNKNOWN_ADID");//단말에서 획득한 ADID 없을경우 UNKNOWN_ADID
				json_params.put("developerOrderId", "3");//구매 ID
				json_params.put("developerProductList", al);
				json_params.put("simOperator", "UNKNOWN_SIM_OPERATOR");//단말 simOperator 없을경우 UNKNOWN_SIM_OPERATOR
				json_params.put("installerPackageName", "UNKNOWN_INSTALLER");//패키지 없을경우 UNKNOWN_INSTALLER
				json_params.put("purchaseMethodList", al_a);
				json_params.put("totalPrice", "1000");//총가격
				json_params.put("purchaseTime", currenttime);
				
				url = OneStore.getUrl(OneStore.API.SEND);
				content_type = MediaType.APPLICATION_JSON.toString();
				
				http = new Http(url, content_type);
				http.setJsonParam(json_params);
				response = http.submit();
				
				if(response!=null) {
					jp = new JsonParser();
					json_response = jp.parse(response).getAsJsonObject();
					System.out.println("json_response:"+json_response);
					if(json_response.get("error")==null) {
						String responseCode = String.valueOf(json_response.get("responseCode").getAsInt());
						System.out.println("response:"+response);
						if("0".equals(responseCode)) {
							System.out.println("성공");
						}
					} else {
						String code = json_response.get("error").getAsJsonObject().get("code").getAsString();
						System.out.println("code:"+code);
					}
				}
				
			}
			
			if(!"".equals(access_token)) {
				
				/**
				 * cancel3rdPartyPurchase (외부결제 구매내역 취소)
				 */
				
				json_params = new JSONObject();
				json_params.put("access_token", access_token);
				json_params.put("developerOrderId", 3);
				json_params.put("cancelTime", currenttime);
				json_params.put("cancelCd", "TRD_CANCEL_TEST");
				
				url = OneStore.getUrl(OneStore.API.CANCEL);
				content_type = MediaType.APPLICATION_JSON.toString();
				
				http = new Http(url, content_type);
				http.setJsonParam(json_params);
				response = http.submit();
				
				if(response!=null) {
					jp = new JsonParser();
					json_response = jp.parse(response).getAsJsonObject();
					System.out.println("json_response:"+json_response);
					if(json_response.get("error")==null) {
						String responseCode = String.valueOf(json_response.get("responseCode").getAsInt());
						System.out.println("response:"+response);
						if("0".equals(responseCode)) {
							System.out.println("성공");
						}
					} else {
						String code = json_response.get("error").getAsJsonObject().get("code").getAsString();
						System.out.println("code:"+code);
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}
    
}
