package com.jyes.www.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.http.MediaType;

public class Http {
	
    private static final String DEFAULT_ENCODING = "UTF-8";
     
    private String url;
    private MultipartEntityBuilder params;
    
    private JSONObject json_params;
    
    private String content_type = null;

	/**
	 * @param url 접속할 url
	 */
	public Http(String url, String content_type) {
		this.content_type = content_type;
		this.url = url;
		params = MultipartEntityBuilder.create();
	}

	/**
	 * Map 으로 한꺼번에 파라메터 훅 추가하는 메소드
	 * 
	 * @param param 파라메터들이 담긴 맵, 파라메터들은 UTF-8로 인코딩 됨
	 * @return
	 */
	public Http addParam(Map<String, Object> param) {
		return addParam(param, DEFAULT_ENCODING);
	}

	/**
	 * Map 으로 한꺼번에 파라메터 훅 추가하는 메소드
	 * 
	 * @param param    파라메터들이 담긴 맵
	 * @param encoding 파라메터 encoding charset
	 * @return
	 */
	public Http addParam(Map<String, Object> param, String encoding) {
		for (Map.Entry<String, Object> e : param.entrySet()) {
			if (e.getValue() instanceof File) {
				addParam(e.getKey(), (File) e.getValue(), encoding);
			} else {
				addParam(e.getKey(), (String) e.getValue(), encoding);
			}
		}
		return this;
	}
     
	/**
	 * 문자열 파라메터를 추가한다.
	 * 
	 * @param name  추가할 파라메터 이름
	 * @param value 파라메터 값
	 * @return
	 */
	public Http addParam(String name, String value) {
		return addParam(name, value, DEFAULT_ENCODING);
	}

	public Http addParam(String name, String value, String encoding) {
		params.addPart(name, new StringBody(value, ContentType.create("text/plain", encoding)));
		return this;
	}
     
    /**
     * 업로드할 파일 파라메터를 추가한다.
     * @param name
     * @param file
     * @return
     */
    public Http addParam(String name, File file){
        return addParam(name, file, DEFAULT_ENCODING);
    }
     
	public Http addParam(String name, File file, String encoding) {
		if (file.exists()) {
			try {
				params.addPart(name, new FileBody(file, ContentType.create("application/octet-stream"), URLEncoder.encode(file.getName(), encoding)));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return this;
	}
    
	public void setJsonParam(JSONObject json_params) {
		this.json_params = json_params;
	}

	public JSONObject getJsonParam() {
		return this.json_params;
	}
 
    /**
     * 타겟 URL 로 POST 요청을 보낸다.
     * @return 요청결과
     * @throws Exception
     */
    public String submit() throws Exception{
        CloseableHttpClient http = HttpClients.createDefault();
        StringBuffer result = new StringBuffer();
        try{
            HttpPost httpPost = new HttpPost(url);
            if(content_type == null) {
            	httpPost.setEntity(params.build());
            } else {
                if(content_type.equals(MediaType.APPLICATION_FORM_URLENCODED.toString())) {
                	System.out.println("getJsonParam():"+getJsonParam());
                	httpPost.setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
                	List<NameValuePair> params = new ArrayList<NameValuePair>();
                	if(getJsonParam()!=null) {
                		Iterator<String> it = getJsonParam().keys();
    					// 각각 키 값 출력
    					while (it.hasNext()) {
    						String key = it.next().toString();
    						System.out.println("key:" + key);
    						System.out.println("value:" + getJsonParam().getString(key));
                    		params.add(new BasicNameValuePair(key, getJsonParam().getString(key)));
    					}
                	}
                    httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                }
                if(content_type.equals(MediaType.APPLICATION_JSON.toString())) {
                	System.out.println("getJsonParam():"+getJsonParam());
            		httpPost.addHeader("Content-Type", MediaType.APPLICATION_JSON.toString());
            		httpPost.addHeader("Authorization", "Bearer "+getJsonParam().get("access_token"));
                	HttpEntity httpEntity = new StringEntity(getJsonParam().toString(), "UTF-8" );
                	httpPost.setEntity(httpEntity);
                }
            }
            CloseableHttpResponse response = http.execute(httpPost);
			try {
				HttpEntity res = response.getEntity();
				BufferedReader br = new BufferedReader(new InputStreamReader(res.getContent(), Charset.forName("UTF-8")));
				String buffer = null;
				while ((buffer = br.readLine()) != null) {
					result.append(buffer).append("\r\n");
				}
			} finally {
				response.close();
			}
        }finally{
            http.close();
        }
        System.out.println("결과:"+result.toString());
        return result.toString();
    }
    
    /**
     * 테스트
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception {
		
	}
}
