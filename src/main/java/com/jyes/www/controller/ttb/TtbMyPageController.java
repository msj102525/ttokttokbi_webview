package com.jyes.www.controller.ttb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyes.www.common.Config;
import com.jyes.www.service.ttb.board.IBoardService;
import com.jyes.www.service.ttb.mypage.IMyPageService;
import com.jyes.www.service.ttb.pay.IPayService;
import com.jyes.www.util.LogUtils;
import com.jyes.www.util.StringUtil;
import com.jyes.www.vo.ttb.BoardFaqVo;
import com.jyes.www.vo.ttb.PayCustomVo;
import com.jyes.www.vo.ttb.UserInfoInputVo;
import com.jyes.www.vo.ttb.UserInfoOutputVo;
import com.jyes.www.vo.ttb.UserUsePayVo;

@Controller(value = "ttbMyPageController")
public class TtbMyPageController {

    private static final Log log = LogFactory.getLog(TtbMyPageController.class);
    @Resource(name = "ttbMyPageService")
    private IMyPageService myPageService;

    @Resource(name = "ttbPayService")
    private IPayService payService;

    @Resource(name = "ttbBoardService")
    private IBoardService boardService;

    @RequestMapping(value = "/ttb/mypage", method = RequestMethod.GET)
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
        String email = StringUtil.nvl(request.getParameter("email"));
        String useragent = StringUtil.nvl(request.getParameter("useragent"));

        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "useragent : " + useragent + "\n");

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

            // 웹뷰 테스트
            // email = "msj102525@gmail.com";
            // useragent =
            // "SAM(compatible;ServiceType/SAM;DeviceType/AndroidPhone;DeviceModel/Pixel7a;OSType/Android;OSVersion/35;AppVersion/1.7.53;StoreType/PLAY)";

            userInfoOutputVo.setEmail(email);
            userInfoOutputVo.setUseragent(useragent);

            model.addAttribute("userInfoOutputVo", userInfoOutputVo);

        } catch (Exception e) {
            e.printStackTrace();
            logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error getMyPageInfo : " + e.toString()
                    + "\n");
        }
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
                + "query end getMyPageInfo UserInfoOutputVo : " + userInfoOutputVo + "\n");

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

        String useragent = StringUtil.nvl(request.getParameter("useragent"));
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
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "useragent : " + useragent + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "requestMap : " + requestMap + "\n");

        String approach_path = StringUtil.nvl(request.getParameter("approach_path"));
        String id = StringUtil.nvl(request.getParameter("id"));
        String affiliates_code = StringUtil.nvl(request.getParameter("affiliates_code"));

        String apiUrl = Config.API_URL + "/ttb/version/1_2/get_payment_list";

        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "apiUrl : " + apiUrl + "\n");

        HttpURLConnection con = null;
        StringBuilder apiResponse = new StringBuilder();

        try {
            // URL 연결 설정
            URL url = new URL(apiUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", useragent);

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

    @RequestMapping(value = "/ttb/privacy/policy", method = RequestMethod.GET)
    public String getPrivacyPolicy(HttpServletRequest request, Model model) {
        StringBuffer logData = new StringBuffer();

        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "call privacyPolicy :" + "\n");
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

        log.info(logData.toString());
        return "mypage/privacyPolicy";
    }

    @RequestMapping(value = "/ttb/terms", method = RequestMethod.GET)
    public String getTerms(HttpServletRequest request, Model model) {
        StringBuffer logData = new StringBuffer();
        HashMap requestMap = LogUtils.GetPrameterMap(request, logData);

        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "call terms :" + "\n");
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

        log.info(logData.toString());
        return "mypage/terms";
    }

    @RequestMapping(value = "/ttb/get_notice_list", method = { RequestMethod.GET, RequestMethod.POST })
    public String getNoticeList(HttpServletRequest request, Model model) throws Exception {
        StringBuffer logData = new StringBuffer();
        HashMap requestMap = LogUtils.GetPrameterMap(request, logData);

        String useragent = StringUtil.nvl(request.getParameter("useragent"));
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
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "useragent : " + useragent + "\n");
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "requestMap : " + requestMap + "\n");

        String approach_path = "05";
        String is_main_notice = "N";
        String count = "999";
        String page = "1";

        String apiUrl = Config.API_URL + "/ttb/version/1_2/get_notice_list";

        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "apiUrl : " + apiUrl + "\n");

        HttpURLConnection con = null;
        StringBuilder apiResponse = new StringBuilder();

        try {
            // URL 연결 설정
            URL url = new URL(apiUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", useragent);

            // form-data
            con.setDoOutput(true);

            // 보내는 데이터 준비
            StringBuilder postData = new StringBuilder();
            postData.append("approach_path=" + URLEncoder.encode(approach_path, "UTF-8"));
            postData.append("&is_main_notice=" + URLEncoder.encode(is_main_notice, "UTF-8"));
            postData.append("&count=" + URLEncoder.encode(count, "UTF-8"));
            postData.append("&page=" + URLEncoder.encode(page, "UTF-8"));

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

        // 날짜 형식 변환
        Map<String, Object> data = (Map<String, Object>) apiResponseMap.get("data");
        List<Map<String, Object>> noticesList = (List<Map<String, Object>>) data.get("notices_list");

        for (Map<String, Object> notice : noticesList) {
            String timestamp = (String) notice.get("date");
            long timeInMillis = Long.parseLong(timestamp);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String formattedDate = sdf.format(new Date(timeInMillis));

            notice.put("date", formattedDate);
        }

        data.put("notices_list", noticesList);
        apiResponseMap.put("data", data);

        model.addAttribute("apiResponse", apiResponseMap);

        log.info(logData.toString());

        return "mypage/notice";
    }

    // 똑똑비 FAQ 전체 리스트
    @RequestMapping(value = "/ttb/faq", method = { RequestMethod.GET, RequestMethod.POST })
    public String call(HttpServletRequest request, HttpServletResponse response, Model model) {
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

        ArrayList<BoardFaqVo> al_tbBoardFaqCategoryContentsList = null;
        try {
            logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
                    + "query start getBoardFaqCategoryContentsList :" + "\n");
            al_tbBoardFaqCategoryContentsList = (ArrayList<BoardFaqVo>) boardService
                    .getTTBFaqCategoryContentsList();
        } catch (Exception e) {
            e.printStackTrace();
            logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
                    + "query error getBoardFaqCategoryContentsList : " + e.toString() + "\n");
        }
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
                + "query end getBoardFaqCategoryContentsList ArrayList<BoardFaqVo> al_tbBoardFaqCategoryContentsList : "
                + al_tbBoardFaqCategoryContentsList + "\n");
        if (al_tbBoardFaqCategoryContentsList != null) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy.MM.dd");

            for (BoardFaqVo faq : al_tbBoardFaqCategoryContentsList) {
                try {
                    Date date = inputFormat.parse(faq.getDate());
                    faq.setDate(outputFormat.format(date));
                } catch (ParseException e) {
                    logData.append(
                            "[" + LogUtils.getCurrentTime() + "]" + " Date parsing error: " + e.getMessage() + "\n");
                }
            }

            model.addAttribute("al_tbBoardFaqCategoryContentsList", al_tbBoardFaqCategoryContentsList);
        }

        long endTime = System.currentTimeMillis() - strartTime;
        logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
        if (endTime > 15000) {
            logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
                    + ",connection time out[15second over]" + "\n");
        }
        log.info(logData.toString());
        return "mypage/faq";
    }
}
