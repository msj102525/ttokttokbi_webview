package com.jyes.www.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyes.www.service.impl.IBoardService;
import com.jyes.www.util.LogUtils;
import com.jyes.www.util.StringUtil;
import com.jyes.www.vo.BoardCategoryVo;
import com.jyes.www.vo.BoardCustomQnaVo;
import com.jyes.www.vo.BoardFaqVo;

@Controller(value = "boardController")
public class BoardController {

	private static final Logger log = LoggerFactory.getLogger(BoardController.class);

	@Resource(name = "boardService")
	private IBoardService boardService;

	@Autowired
	private JavaMailSender mailSender;

	@RequestMapping(value = "/board/call", method = { RequestMethod.GET, RequestMethod.POST })
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
		String id = StringUtil.nvl(request.getParameter("id"));
		String affiliates_code = StringUtil.nvl(request.getParameter("affiliates_code"));
		/**
		 * FAQ 카테고리 목록
		 */
		ArrayList<BoardCategoryVo> al_tbBoardFaqCategoryList = null;
		try {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query start getBoardFaqCategoryList " + "\n");
			al_tbBoardFaqCategoryList = (ArrayList<BoardCategoryVo>) boardService.getBoardFaqCategoryList();
		} catch (Exception e) {
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query error getContentsSuggestedCharactersList : " + e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end getContentsSuggestedCharactersList ArrayList<BoardCategoryVo> al_tbBoardFaqCategoryList : "
				+ al_tbBoardFaqCategoryList + "\n");
		if (al_tbBoardFaqCategoryList != null) {
			model.addAttribute("al_tbBoardFaqCategoryList", al_tbBoardFaqCategoryList);
			String code = "";
			for (int i = 0; i < al_tbBoardFaqCategoryList.size(); i++) {
				BoardCategoryVo boardCategoryVo = (BoardCategoryVo) al_tbBoardFaqCategoryList.get(i);
				code = boardCategoryVo.getCode();
				break;
			}
			/**
			 * 첫 번째 카테고리 내용 목록
			 */
			ArrayList<BoardFaqVo> al_tbBoardFaqCategoryContentsList = null;
			try {
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
						+ "query start getBoardFaqCategoryContentsList String code:" + code + "\n");
				al_tbBoardFaqCategoryContentsList = (ArrayList<BoardFaqVo>) boardService
						.getBoardFaqCategoryContentsList(code);
			} catch (Exception e) {
				e.printStackTrace();
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
						+ "query error getBoardFaqCategoryContentsList : " + e.toString() + "\n");
			}
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query end getBoardFaqCategoryContentsList ArrayList<BoardFaqVo> al_tbBoardFaqCategoryContentsList : "
					+ al_tbBoardFaqCategoryContentsList + "\n");
			if (al_tbBoardFaqCategoryContentsList != null) {
				model.addAttribute("al_tbBoardFaqCategoryContentsList", al_tbBoardFaqCategoryContentsList);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("affiliates_code", affiliates_code);
		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());
		return "/board/call";
	}

	/**
	 * QNA 등록
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/board/setBoardAjax", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody HashMap<String, Object> setBoardAjax(HttpServletRequest request, Model model) {

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
		String text = StringUtil.nvl(request.getParameter("text"));

		String msg = "-1";

		int success = 0;
		BoardCustomQnaVo boardCustomQnaVo = null;
		try {
			boardCustomQnaVo = new BoardCustomQnaVo();
			boardCustomQnaVo.setId(id);
			boardCustomQnaVo.setAffiliates_code(affiliates_code);
			boardCustomQnaVo.setEmail(email);
			boardCustomQnaVo.setText(text);
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start insertBoardQna BoardCustomQnaVo boardCustomQnaVo : " + boardCustomQnaVo + "\n");
			// success = boardService.insertBoardQna(boardCustomQnaVo);
			if (success == 1) {
				msg = "1";
				{// 메일링 추가
					sendMail(text);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error insertBoardQna : " + e.toString()
					+ "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query end insertBoardQna int success : " + success
				+ "\n");

		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("msg", msg);
		return result;
	}

	/**
	 * FAQ 카테고리별 리스트 조회
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/board/getFaqBoardCategoryContentsListAjax", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody HashMap<String, Object> getFaqBoardListAjax(HttpServletRequest request, Model model) {

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

		String code = StringUtil.nvl(request.getParameter("category_code"));

		String msg = "-1";

		/**
		 * Faq 카테고리 내용 목록 조회
		 */
		ArrayList<BoardFaqVo> al_tbBoardFaqCategoryContentsList = null;
		try {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query start getBoardFaqCategoryContentsList String code:" + code + "\n");
			al_tbBoardFaqCategoryContentsList = (ArrayList<BoardFaqVo>) boardService
					.getBoardFaqCategoryContentsList(code);
			if (al_tbBoardFaqCategoryContentsList != null) {
				msg = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
					+ "query error getBoardFaqCategoryContentsList : " + e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
				+ "query end getBoardFaqCategoryContentsList ArrayList<BoardFaqVo> al_tbBoardFaqCategoryContentsList : "
				+ al_tbBoardFaqCategoryContentsList + "\n");

		long endTime = System.currentTimeMillis() - strartTime;
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime + "\n");
		if (endTime > 15000) {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "tag:" + tag + ":전체:endTime:" + endTime
					+ ",connection time out[15second over]" + "\n");
		}
		log.info(logData.toString());

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("msg", msg);
		result.put("al_tbBoardFaqCategoryContentsList", al_tbBoardFaqCategoryContentsList);
		return result;
	}

	public void sendMail(String msg) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String text = msg;
					if (text == null) {
						text = "";
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd, HH:mm");
					Date now = new Date();
					String formatedNow = sdf.format(now);
					String from = "jyesapps@gmail.com";
					// String to = "diapala82@jyes.co.kr";
					// String[] to = {"huntux2@jyes.co.kr"};
					String[] to = new String[4];
					to[0] = "huntux2@jyes.co.kr";// 여대현[개발]
					to[1] = "chan7579@jyes.co.kr";// 전찬균[개발]
					to[2] = "gruwn23@jyes.co.kr";// 송혁주[기획]
					String subject = formatedNow + " 에 문의가 등록되었습니다.";
					MimeMessage m = mailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(m, true, "utf-8");
					helper.setFrom(from);
					helper.setTo(to);
					helper.setSubject(subject);
					helper.setText("", text.replace("\r\n", "<br>").replace("\n", "<br>"));
					mailSender.send(m);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).start();
	}

}
