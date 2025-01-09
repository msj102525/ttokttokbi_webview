package com.jyes.www.controller.ttb;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    /**
     *
     */
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

}
