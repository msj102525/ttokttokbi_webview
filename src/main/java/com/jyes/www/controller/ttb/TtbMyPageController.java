package com.jyes.www.controller.ttb;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/ttb/set_user_info", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> setUserInfo(HttpServletRequest request) throws Exception {
        // UTF-8 설정
        request.setCharacterEncoding("UTF-8");

        // 파라미터 추출
        String id = request.getParameter("id");
        String approachPath = request.getParameter("approach_path");
        String affiliatesCode = request.getParameter("affiliates_code");
        String userName = request.getParameter("name");
        String company = request.getParameter("company");

        System.out.println("Decoded userName: " + userName);
        System.out.println("Decoded company: " + company);

        // 데이터 출력
        System.out.println("id: " + id);
        System.out.println("approachPath: " + approachPath);
        System.out.println("affiliatesCode: " + affiliatesCode);
        System.out.println("userName: " + userName);
        System.out.println("company: " + company);

        // 반환 값
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "데이터가 성공적으로 처리되었습니다.");
        return response;
    }

    @RequestMapping(value = "/ttb/set_user_infoa", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody Map<String, Object> setUserInfoa(HttpServletRequest request) throws Exception {
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

        // 반환 값
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "데이터가 성공적으로 처리되었습니다.");

        log.info(logData.toString());

        return response;
    }

}
