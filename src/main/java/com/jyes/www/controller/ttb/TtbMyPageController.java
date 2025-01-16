package com.jyes.www.controller.ttb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpHeaders;
import com.jyes.www.common.Config;
import com.jyes.www.service.ttb.mypage.IMyPageService;
import com.jyes.www.util.LogUtils;
import com.jyes.www.util.StringUtil;
import com.jyes.www.vo.ttb.UserInfoInputVo;
import com.jyes.www.vo.ttb.UserInfoOutputVo;

@Controller(value = "ttbMyPageController")
public class TtbMyPageController {

    private static final Log log = LogFactory.getLog(TtbMyPageController.class);
    @Resource(name = "ttbMyPageService")
    private IMyPageService myPageService;

    @RequestMapping(value = "/ttb/myPage", method = RequestMethod.GET)
    public String myPage(HttpServletRequest request, Model model) {
        StringBuffer logData = new StringBuffer();
        HashMap requestMap = LogUtils.GetPrameterMap(request, logData);

        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "call myPage :" + "\n");
        String currentUrl = request.getRequestURL().toString();
        String StartUrl = StringUtil.getStartUrlPath(currentUrl);
        if (request.getQueryString() != null) {
            currentUrl = currentUrl + "?" + request.getQueryString();
        }
        String logKey = LogUtils.getUserLogKey(request);

        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "logKey:" + logKey + ":" + StartUrl + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "StartUrl : " + StartUrl + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "CurrentUrl : " + currentUrl + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "CallUrl : "
                + StringUtil.nvl((String) request.getHeader("REFERER")) + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "requestMap : " + requestMap + "\n");

        String id = StringUtil.nvl(request.getParameter("id"));
        String affiliates_code = StringUtil.nvl(request.getParameter("affiliates_code"));

        UserInfoInputVo userInfoInputVo = new UserInfoInputVo();
        UserInfoOutputVo userInfoOutputVo = new UserInfoOutputVo();

        try {
            userInfoInputVo.setId(id);
            userInfoInputVo.setAffiliates_code(affiliates_code);
            logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query start getMyPageInfo UserInfoInputVo : "
                    + userInfoInputVo + "\n");

            userInfoOutputVo = myPageService.getMyPageInfo(userInfoInputVo);

            if (userInfoOutputVo == null) {
                logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "No data found for UserInfoOutputVo: "
                        + userInfoInputVo + "\n");
            }

            model.addAttribute("userInfoOutputVo", userInfoOutputVo);

        } catch (Exception e) {
            e.printStackTrace();
            logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error getMyPageInfo : " + e.toString()
                    + "\n");
        }
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
                + "query end getMyPageInfo UserInfoOutputVo : " + userInfoOutputVo + "\n");

        log.info(logData.toString());
        return "mypage/myPage";
    }

    @RequestMapping(value = "/ttb/set_user_info", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody Map<String, Object> setUserInfo(HttpServletRequest request) throws Exception {
        StringBuffer logData = new StringBuffer();
        HashMap requestMap = LogUtils.GetPrameterMap(request, logData);

        String userAgent = "SAM(compatible;ServiceType/SAM;DeviceType/AndroidPhone;DeviceModel/Pixel2;OSType/Android;OSVersion/30;AppVersion/1.7.53;StoreType/PLAY)";
        String currentUrl = request.getRequestURL().toString();
        String StartUrl = "/" + currentUrl.substring(currentUrl.indexOf(currentUrl.split("/")[3]));
        if (request.getQueryString() != null) {
            currentUrl = currentUrl + "?" + request.getQueryString();
        }
        String logKey = LogUtils.getUserLogKey(request);

        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "logKey:" + logKey + ":" + StartUrl + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "StartUrl : " + StartUrl + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "CurrentUrl : " + currentUrl + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "CallUrl : "
                + StringUtil.nvl((String) request.getHeader("REFERER")) + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "userAgent : " + userAgent + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "requestMap : " + requestMap + "\n");

        String approach_path = StringUtil.nvl(request.getParameter("approach_path"));
        String id = StringUtil.nvl(request.getParameter("id"));
        String affiliates_code = StringUtil.nvl(request.getParameter("affiliates_code"));
        String name = StringUtil.nvl(request.getParameter("name"));
        String company = StringUtil.nvl(request.getParameter("company"));

        String apiUrl = Config.API_URL + "/ttb/version/1_2/set_user_info";

        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "apiUrl : " + apiUrl + "\n");

        HttpURLConnection con = null;
        StringBuilder apiResponse = new StringBuilder();

        try {
            // URL 연결 설정
            URL url = new URL(apiUrl);

            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", userAgent);

            // // form-data
            con.setDoOutput(true);

            // 보내는 데이터 준비
            StringBuilder postData = new StringBuilder();
            postData.append("approach_path=" + URLEncoder.encode(approach_path,
                    "UTF-8"));
            postData.append("&id=" + URLEncoder.encode(id, "UTF-8"));
            postData.append("&affiliates_code=" + URLEncoder.encode(affiliates_code,
                    "UTF-8"));
            postData.append("&name=" + URLEncoder.encode(name, "UTF-8"));
            postData.append("&company=" + URLEncoder.encode(company, "UTF-8"));

            // 데이터 전송
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = postData.toString().getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            // 응답 받기
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    apiResponse.append(responseLine.trim());
                }
            }

            logData.append("[" + LogUtils.getCurrentTime() + "]" + " API Response : " +
                    apiResponse + "\n");

        } catch (Exception e) {
            logData.append("[" + LogUtils.getCurrentTime() + "]" + " Error : " +
                    e.getMessage() + "\n");
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        // JSON 응답 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> apiResponseMap = objectMapper.readValue(apiResponse.toString(), Map.class);

        Map<String, Object> response = new HashMap<>();
        response.put("apiResponse", apiResponseMap); // JSON 응답을 Map 형태로 반환

        log.info(logData.toString());

        return response;

    }

    @RequestMapping(value = "/ttb/get_payment_list", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody Map<String, Object> getPaymentList(HttpServletRequest request) throws Exception {
        StringBuffer logData = new StringBuffer();
        HashMap requestMap = LogUtils.GetPrameterMap(request, logData);

        String userAgent = "SAM(compatible;ServiceType/SAM;DeviceType/AndroidPhone;DeviceModel/Pixel2;OSType/Android;OSVersion/30;AppVersion/1.7.53;StoreType/PLAY)";
        String currentUrl = request.getRequestURL().toString();
        String StartUrl = "/" + currentUrl.substring(currentUrl.indexOf(currentUrl.split("/")[3]));
        if (request.getQueryString() != null) {
            currentUrl = currentUrl + "?" + request.getQueryString();
        }
        String logKey = LogUtils.getUserLogKey(request);

        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "logKey:" + logKey + ":" + StartUrl + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "StartUrl : " + StartUrl + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "CurrentUrl : " + currentUrl + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "CallUrl : "
                + StringUtil.nvl((String) request.getHeader("REFERER")) + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "userAgent : " + userAgent + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "requestMap : " + requestMap + "\n");

        String approach_path = StringUtil.nvl(request.getParameter("approach_path"));
        String id = StringUtil.nvl(request.getParameter("id"));
        String affiliates_code = StringUtil.nvl(request.getParameter("affiliates_code"));

        String apiUrl = Config.API_URL + "/api/version/1_2/get_payment_list";
        // String apiUrl = Config.API_URL + "/ttb/version/1_2/get_payment_list";

        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "apiUrl : " + apiUrl + "\n");

        HttpURLConnection con = null;
        StringBuilder apiResponse = new StringBuilder();

        try {
            // URL 연결 설정
            URL url = new URL(apiUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", userAgent);
            // // form-data
            con.setDoOutput(true);

            // 보내는 데이터 준비
            StringBuilder postData = new StringBuilder();
            postData.append("approach_path=" + URLEncoder.encode(approach_path, "UTF-8"));
            postData.append("&id=" + URLEncoder.encode(id, "UTF-8"));
            postData.append("&affiliates_code=" + URLEncoder.encode(affiliates_code, "UTF-8"));

            // 데이터 전송
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = postData.toString().getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            // 응답 받기
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    apiResponse.append(responseLine.trim());
                }
            }

            logData.append("[" + LogUtils.getCurrentTime() + "]" + " API Response : " + apiResponse + "\n");

        } catch (Exception e) {
            logData.append("[" + LogUtils.getCurrentTime() + "]" + " Error : " + e.getMessage() + "\n");
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        // JSON 응답 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> apiResponseMap = objectMapper.readValue(apiResponse.toString(), Map.class);

        Map<String, Object> response = new HashMap<>();
        response.put("apiResponse", apiResponseMap); // JSON 응답을 Map 형태로 반환

        log.info(logData.toString());

        return response;
    }

}
