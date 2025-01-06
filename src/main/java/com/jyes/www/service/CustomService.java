package com.jyes.www.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.jyes.www.common.Config;
import com.jyes.www.mapper.CustomMapper;
import com.jyes.www.service.impl.ICustomService;
import com.jyes.www.util.StringUtil;
import com.jyes.www.vo.MailFormLogVo;
import com.jyes.www.vo.MailFormSendListVo;

@Service(value = "customService")
public class CustomService implements ICustomService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Resource(name = "customMapper")
	private CustomMapper customMapper;
	
	@Override
	public int sendMailForm() throws SQLException {
		int returnValue = 0;
		
		//통계 데이터 처리 여부 확인(두번 실행 방지)
		returnValue = customMapper.selectMailFormLogNowDataCount();
		if(returnValue > 0) {
			throw new SQLException("selectMailFormLogNowDataCount returnValue : "+returnValue +" 이미 처리 됨");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = new GregorianCalendar(Locale.KOREA);
	    cal.setTime(new Date());
	    cal.add(Calendar.DAY_OF_YEAR, -1); // 하루를 뺀다.
		Calendar cal2 = new GregorianCalendar(Locale.KOREA);
		cal2.setTime(new Date());
		
//		MailFormLogVo request_MailFormLogVo = new MailFormLogVo();
//		request_MailFormLogVo.setReg_date(sdf.format(cal.getTime()));
//		ArrayList<Object> response_mailFormLogVoList = (ArrayList<Object>)customMapper.selectMailFormLogDataWhereRegDate(request_MailFormLogVo);
//		int count = 29;
//		while (count>-1) {
//			System.out.println(count);
//			String request_date = (String)customMapper.selectDayDate(-count);//2019-12-13
//			ArrayList<Object> mailFormLogVoList = (ArrayList<Object>)customMapper.selectMailFromDataList(request_date);
//			if(mailFormLogVoList==null) {
//				throw new SQLException("selectMailFromDataList mailFormLogVoList null");
//			}
//			sdf = new SimpleDateFormat("M/d");
//			MailFormLogVo mailFormLogVo = null;
//			MailFormLogVo response_mailFormLogVo = null;
//			returnValue = 0;
//			for (int i = 0; i < mailFormLogVoList.size(); i++) {
//				mailFormLogVo = (MailFormLogVo)mailFormLogVoList.get(i);
//				mailFormLogVo.setReg_date(request_date+" "+"10:00:00");
//				mailFormLogVo.setIndex_num((i+1)+"");
//				int success = customMapper.insertMailFormLog(mailFormLogVo);
//				if(success < 1) {
//					throw new SQLException("insertMailFormLog 결과 없음");
//				}
//				returnValue += 1;
//			}
//			System.out.println(returnValue);
//			count--;
//		}
		
//		String category_1 = StringUtil.nvl(mailFormLogVo.getCategory_1());
//		String category_2 = StringUtil.nvl(mailFormLogVo.getCategory_2());
//		String category_3 = StringUtil.nvl(mailFormLogVo.getCategory_3());
//		String name = StringUtil.nvl(mailFormLogVo.getName());
//		String count = StringUtil.nvl(mailFormLogVo.getCount());
//		String count_01 = StringUtil.nvl(mailFormLogVo.getCount_01());
//		String count_02 = StringUtil.nvl(mailFormLogVo.getCount_02());
//		String count_03 = StringUtil.nvl(mailFormLogVo.getCount_03());
//		String count_04 = StringUtil.nvl(mailFormLogVo.getCount_04());
//		String count_05 = StringUtil.nvl(mailFormLogVo.getCount_05());
//		String count_06 = StringUtil.nvl(mailFormLogVo.getCount_06());
//		String count_07 = StringUtil.nvl(mailFormLogVo.getCount_07());
//		String count_08 = StringUtil.nvl(mailFormLogVo.getCount_08());
//		String count_09 = StringUtil.nvl(mailFormLogVo.getCount_09());
//		String count_10 = StringUtil.nvl(mailFormLogVo.getCount_10());
//		String count_11 = StringUtil.nvl(mailFormLogVo.getCount_11());
//		String count_12 = StringUtil.nvl(mailFormLogVo.getCount_12());
//		String count_13 = StringUtil.nvl(mailFormLogVo.getCount_13());
//		String count_14 = StringUtil.nvl(mailFormLogVo.getCount_14());
//		String count_15 = StringUtil.nvl(mailFormLogVo.getCount_15());
//		String count_16 = StringUtil.nvl(mailFormLogVo.getCount_16());
//		String count_17 = StringUtil.nvl(mailFormLogVo.getCount_17());
//		String count_18 = StringUtil.nvl(mailFormLogVo.getCount_18());
//		String count_19 = StringUtil.nvl(mailFormLogVo.getCount_19());
//		String count_20 = StringUtil.nvl(mailFormLogVo.getCount_20());
//		String count_21 = StringUtil.nvl(mailFormLogVo.getCount_21());
//		String count_22 = StringUtil.nvl(mailFormLogVo.getCount_22());
//		String count_23 = StringUtil.nvl(mailFormLogVo.getCount_23());
//		String count_24 = StringUtil.nvl(mailFormLogVo.getCount_24());
//		String count_25 = StringUtil.nvl(mailFormLogVo.getCount_25());
//		String count_26 = StringUtil.nvl(mailFormLogVo.getCount_26());
//		String count_27 = StringUtil.nvl(mailFormLogVo.getCount_27());
//		String count_28 = StringUtil.nvl(mailFormLogVo.getCount_28());
//		String count_29 = StringUtil.nvl(mailFormLogVo.getCount_29());
//		String count_30 = StringUtil.nvl(mailFormLogVo.getCount_30());
//		String date = StringUtil.nvl(mailFormLogVo.getDate());

//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_01()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_02()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_03()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_04()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_05()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_06()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_07()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_08()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_09()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_10()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_11()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_12()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_13()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_14()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_15()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_16()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_17()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_18()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_19()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_20()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_21()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_22()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_23()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_24()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_25()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_26()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_27()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_28()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_29()))));
//		System.out.println("================"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_30()))));

//		System.out.println("================"+mailFormLogVo.getCategory_1());
//		System.out.println("================"+mailFormLogVo.getCategory_2());
//		System.out.println("================"+mailFormLogVo.getCategory_3());
//		System.out.println("================"+mailFormLogVo.getName());
//		System.out.println("================"+mailFormLogVo.getCount_01());
//		System.out.println("================"+mailFormLogVo.getCount_02());
//		System.out.println("================"+mailFormLogVo.getCount_03());
//		System.out.println("================"+mailFormLogVo.getCount_04());
//		System.out.println("================"+mailFormLogVo.getCount_05());
//		System.out.println("================"+mailFormLogVo.getCount_06());
//		System.out.println("================"+mailFormLogVo.getCount_07());
//		System.out.println("================"+mailFormLogVo.getCount_08());
//		System.out.println("================"+mailFormLogVo.getCount_09());
//		System.out.println("================"+mailFormLogVo.getCount_10());
//		System.out.println("================"+mailFormLogVo.getCount_11());
//		System.out.println("================"+mailFormLogVo.getCount_12());
//		System.out.println("================"+mailFormLogVo.getCount_13());
//		System.out.println("================"+mailFormLogVo.getCount_14());
//		System.out.println("================"+mailFormLogVo.getCount_15());
//		System.out.println("================"+mailFormLogVo.getCount_16());
//		System.out.println("================"+mailFormLogVo.getCount_17());
//		System.out.println("================"+mailFormLogVo.getCount_18());
//		System.out.println("================"+mailFormLogVo.getCount_19());
//		System.out.println("================"+mailFormLogVo.getCount_20());
//		System.out.println("================"+mailFormLogVo.getCount_21());
//		System.out.println("================"+mailFormLogVo.getCount_22());
//		System.out.println("================"+mailFormLogVo.getCount_23());
//		System.out.println("================"+mailFormLogVo.getCount_24());
//		System.out.println("================"+mailFormLogVo.getCount_25());
//		System.out.println("================"+mailFormLogVo.getCount_26());
//		System.out.println("================"+mailFormLogVo.getCount_27());
//		System.out.println("================"+mailFormLogVo.getCount_28());
//		System.out.println("================"+mailFormLogVo.getCount_29());
//		System.out.println("================"+mailFormLogVo.getCount_30());
		
		//어제 통계 데이터 조회
		String request_date = (String)customMapper.selectDayDate(-0);//하루전 통계 데이터
		ArrayList<Object> mailFormLogVoList = (ArrayList<Object>)customMapper.selectMailFromDataList(request_date);
		MailFormLogVo mailFormLogVo = null;
		
		//어제 통게 데이터 넣기
		returnValue = 0;
		for (int i = 0; i < mailFormLogVoList.size(); i++) {
			mailFormLogVo = (MailFormLogVo)mailFormLogVoList.get(i);
			mailFormLogVo.setIndex_num((i+1)+"");
			int success = customMapper.insertMailFormLog(mailFormLogVo);
			if(success < 1) {
				throw new SQLException("insertMailFormLog 결과 없음");
			}
			returnValue += 1;
		}
		
		//어제부터 30일전 통계 데이터
		ArrayList<Object> response_mailFormLogVoList = (ArrayList<Object>)customMapper.selectMailFromToday30DaysBeforeData();
		
		sdf = new SimpleDateFormat("MM/dd");
		
		//통계 정보
		String html = "";
		html += "<table style=\"border-collapse:collapse;border-spacing:0; width: 1320px;\">";
		html += "<tbody>";
		html += "<tr>";
		html += "<th colspan=\"19\" style=\"font-family:Arial, sans-serif;font-size:18px;font-weight:normal;padding:2px 10px;border-left:2px solid black;border-top:2px solid black;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;height:45px;\"> &lt;JSAM "+sdf.format(cal2.getTime())+" 현황 보고&gt; </th>";
		html += "</tr>";
		html += "<tr>";
		html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;\">대분류</th>";
		html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;\">중분류</th>";
		html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;\" colspan=\"2\">소분류</th>";
		
		//날짜데이터
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(0);
		try {
			SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			SimpleDateFormat sdf_date_sub = new SimpleDateFormat("MM/dd");
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_01())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_02())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_03())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_04())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_05())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_06())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_07())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_08())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_09())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_10())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_11())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_12())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_13())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_14())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_15())))+"</th>";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int j = 0;
		
		//전체 가입 고객 무료가입자
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "</tr>";
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;\" rowspan=\"12\">"+mailFormLogVo.getCategory_1()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;\" rowspan=\"3\">"+mailFormLogVo.getCategory_2()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#ffecc1;\" colspan=\"2\">"+mailFormLogVo.getName()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//전체 가입 고객 유료가입자
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#ffecc1;\" colspan=\"2\">"+mailFormLogVo.getName()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//전체 가입 고객 총 가입자 합계
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2; font-weight: 600;\" colspan=\"2\">총 가입자 합계</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//전체 이용권 이용 현황 1개월 이용권 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;\" rowspan=\"9\">전체 이용권 이용 현황</td>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#ffecc1;\" colspan=\"2\">1개월 이용권 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//전체 이용권 이용 현황 3개월 이용권 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#ffecc1;\" colspan=\"2\">3개월 이용권 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//전체 이용권 이용 현황 6개월 이용권 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#ffecc1;\" colspan=\"2\">6개월 이용권 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//전체 이용권 이용 현황 12개월 이용권 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#ffecc1;\" colspan=\"2\">12개월 이용권 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//전체 이용권 이용 현황 24개월 이용권 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#ffecc1;\" colspan=\"2\">24개월 이용권 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//전체 이용권 이용 현황 12개월 유무선 이용권 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#ffecc1;\" colspan=\"2\">12개월 유무선 이용권 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//전체 이용권 이용 현황 무선 정기결제 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#ffecc1;\" colspan=\"2\">무선 정기 결제 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//전체 이용권 이용 현황 유무선 정기결제 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#ffecc1;\" colspan=\"2\">유무선 정기 결제 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//전체 이용권 이용 현황 총 이용권 이용 건수 합계
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2; font-weight: 600;\" colspan=\"2\">총 이용권 이용 건수 합계</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//일별 가입 고객 신규(무료)가입자
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;\" rowspan=\"11\">일일 현황</td>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;\" rowspan=\"2\">일별 가입고객</td>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#d6ffc1;\" colspan=\"2\">신규(무료)가입자</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//일별 가입 고객 유료 가입자
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#d6ffc1; border-bottom:2px solid black;\" colspan=\"2\">유료가입자</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; border-bottom:2px solid black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:0px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; border-bottom:2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; border-bottom:2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; border-bottom:2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; border-bottom:2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; border-bottom:2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; border-bottom:2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; border-bottom:2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; border-bottom:2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; border-bottom:2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; border-bottom:2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; border-bottom:2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; border-bottom:2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; border-bottom:2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; border-bottom:2px solid black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//일별 이용권 판매 현황 1개월 이용권 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;\" rowspan=\"9\">일별 이용권 판매 현황</td>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#d9e1f2;\" colspan=\"2\">1개월 이용권 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//일별 이용권 판매 현황 3개월 이용권 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#d9e1f2;\" colspan=\"2\">3개월 이용권 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//일별 이용권 판매 현황 6개월 이용권 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#d9e1f2;\" colspan=\"2\">6개월 이용권 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//일별 이용권 판매 현황 12개월 이용권 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#d9e1f2;\" colspan=\"2\">12개월 이용권 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//일별 이용권 판매 현황 24개월 이용권 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#d9e1f2;\" colspan=\"2\">24개월 이용권 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//일별 이용권 판매 현황 12개월 유무선 이용권 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#d9e1f2;\" colspan=\"2\">12개월 유무선 이용권 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//일별 이용권 판매 현황 무선 정기결제 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#d9e1f2;\" colspan=\"2\">무선 정기 결제 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//일별 이용권 판매 현황 유무선 정기결제 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#d9e1f2;\" colspan=\"2\">유무선 정기 결제 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//일별 이용권 판매 현황 이용권 결제 건수 합계
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2; font-weight: 600;\" colspan=\"2\">이용권 결재 건수 합계</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d9e1f2;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//기타 이미지 판매 일일 판매 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;\" rowspan=\"5\">기타 현황</td>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;\" rowspan=\"3\">이미지 판매</td>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#ffc107;\" colspan=\"2\">일일 판매 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//기타 이미지 판매 일일 제작(사용) 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#ffc107;\" colspan=\"2\">일일 제작(사용) 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//기타 이미지 판매 일일 누적 판매 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;background-color:#ffc107;\" colspan=\"2\">누적 판매 건수</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//기타 무료쿠폰 전체 무료쿠폰이용자
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;\" rowspan=\"2\">무료 쿠폰</td>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#ffecc1;\" colspan=\"2\">전체 무료쿠폰이용자</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		
		//기타 무료쿠폰 일일 신규 무료쿠폰이용자
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;background-color:#ffecc1;\" colspan=\"2\">일일 신규 무료쿠폰이용자</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_02()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_03()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_04()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_05()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_06()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_07()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_08()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_09()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_10()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_11()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_12()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_13()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;border-right: 1px solid black; border-left: 1px solid black;\">"+mailFormLogVo.getCount_14()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-bottom: 2px solid black;\">"+mailFormLogVo.getCount_15()+"</td>";
		html += "</tr>";
		html += "</tbody>";
		html += "</table>";
		
		//모니터링 정보
		html += "<br/>";
		
		html += "<table style=\"border-collapse:collapse;border-spacing:0; width: 670px;\">";
		html += "<tbody>";
		html += "<tr>";
		html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;width:80px;\">대분류</th>";
		html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;width:160px;\">중분류</th>";
		html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;width:200px;\" colspan=\"4\">소분류</th>";
		html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;width:60px;\">건수</th>";
		html += "</tr>";
		
		//결제 모니터링 익일 정기 자동 결제 예정 무선 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 5px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;\" rowspan=\"11\">"+mailFormLogVo.getCategory_1()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;\" rowspan=\"3\">"+mailFormLogVo.getCategory_2()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#fce4d6;\" colspan=\"4\">"+mailFormLogVo.getName()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "</tr>";
		
		//결제 모니터링 익일 정기 자동 결제 예정 유무선 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#fce4d6;\" colspan=\"4\">"+mailFormLogVo.getName()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "</tr>";
		
		//결제 모니터링 익일 정기 자동 결제 예정 총 합계
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d6dce4; font-weight: 600;\" colspan=\"4\">"+mailFormLogVo.getName()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-top: 2px solid black;border-bottom: 2px solid black;background-color:#d6dce4;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "</tr>";
		
		//결제 모니터링 금일 정기 자동 결제 내역 무선 총 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;\" rowspan=\"8\">"+mailFormLogVo.getCategory_2()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#fff;\" colspan=\"2\" rowspan=\"4\">"+mailFormLogVo.getCategory_3()+"</td>";
		html += "</tr>";
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#f4b084;\" colspan=\"2\">"+mailFormLogVo.getName()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "</tr>";
		
		//결제 모니터링 금일 정기 자동 결제 내역 무선 완료 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#f4b084;\" colspan=\"2\">"+mailFormLogVo.getName()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "</tr>";
		
		//결제 모니터링 금일 정기 자동 결제 내역 무선 미결제 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#f4b084;\" colspan=\"2\">"+mailFormLogVo.getName()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; color:red;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "</tr>";
		
		//결제 모니터링 금일 정기 자동 결제 내역 유무선 총 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#fff; border-bottom: 2px solid black\" colspan=\"2\" rowspan=\"4\">"+mailFormLogVo.getCategory_3()+"</td>";
		html += "</tr>";
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#f4b084;\" colspan=\"2\">"+mailFormLogVo.getName()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "</tr>";
		
		//결제 모니터링 금일 정기 자동 결제 내역 유무선 완료 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#f4b084;\" colspan=\"2\">"+mailFormLogVo.getName()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "</tr>";
		
		//결제 모니터링 금일 정기 자동 결제 내역 유무선 미결제 건수
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#f4b084; border-bottom: 2px solid black\" colspan=\"2\">"+mailFormLogVo.getName()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black; color:red; border-bottom: 2px solid black\">"+mailFormLogVo.getCount_01()+"</td>";
		html += "</tr>";
		html += "</tbody>";
		html += "</table>";
		
		// 이벤트쿠폰 통계
		html += "<br/>";
		
		html += "<table style=\"border-collapse:collapse;border-spacing:0; width: 1320px;\">";
		html += "<tbody>";
		html += "<tr>";
		html += "<th colspan=\"19\" style=\"font-family:Arial, sans-serif;font-size:18px;font-weight:normal;padding:2px 10px;border-left:2px solid black;border-top:2px solid black;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;height:45px;\"> &lt;이벤트 이용권 "+sdf.format(cal2.getTime())+" 현황 보고&gt; </th>";
		html += "</tr>";
		html += "<tr>";
		html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;\">대분류</th>";
		html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;\">중분류</th>";
		html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;\" colspan=\"2\">소분류</th>";
		
		//날짜데이터
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(0);
		try {
			SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			SimpleDateFormat sdf_date_sub = new SimpleDateFormat("MM/dd");
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_01())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_02())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_03())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_04())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_05())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_06())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_07())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_08())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_09())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_10())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_11())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_12())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_13())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-right: 1px solid black; border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_14())))+"</th>";
			html += "<th style=\"font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;background-color:#adadad;color:white;border-left: 1px solid black;\">"+sdf_date_sub.format(sdf_date.parse(StringUtil.nvl(mailFormLogVo.getDate_15())))+"</th>";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 이벤트 쿠폰 사용자
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "</tr>";
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;\" rowspan=\"2\">"+mailFormLogVo.getCategory_1()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:black;\" rowspan=\"2\">"+mailFormLogVo.getCategory_2()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#ffecc1;\" colspan=\"2\">"+mailFormLogVo.getName()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_01())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_02())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_03())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_04())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_05())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_06())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_07())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_08())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_09())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_10())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_11())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_12())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_13())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_14())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_15())+"</td>";                                         
		html += "</tr>";
		
		// 이벤트 쿠폰 사용자 중 유료결제자
		mailFormLogVo = (MailFormLogVo)response_mailFormLogVoList.get(j++);
		html += "<tr>";
		html += "<td style=\"border-color:inherit;text-align:center;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;background-color:#ffecc1;\" colspan=\"2\">"+mailFormLogVo.getName()+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_01())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_02())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_03())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_04())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_05())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_06())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_07())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_08())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_09())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_10())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_11())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_12())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_13())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;border-right: 1px solid black; border-left: 1px solid black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_14())+"</td>";
		html += "<td style=\"border-color:inherit;text-align:right;font-family:Arial, sans-serif;font-size:14px;padding:2px 10px;border-style:solid;border-width:1px;border-right:2px solid black;overflow:hidden;word-break:normal;border-color:black;\">"+checkNullAndEmpty(mailFormLogVo.getCount_15())+"</td>";
		html += "</tr>";
		html += "</tbody>";
		html += "</table>";
		
		sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		String from = "jyesapps@gmail.com";
		/**
		오종환이사님: sunmaker@jyes.co.kr
		서기원소장님: james@jyes.co.kr
		이영재과장님: leejuk84@jyes.co.kr
		윤주현님: yoonzoo25@jyes.co.kr
		백지환: armqdk94@jyes.co.kr
		이해임센터장: sopilee12@jyes.co.kr
		정해준팀장님: haejunj@jyes.co.kr
		여대현: huntux2@jyes.co.kr
		 */
		ArrayList<Object> mail_list = new ArrayList<Object>();
		MailFormSendListVo mfslv = new MailFormSendListVo();
		if(Config.isDevMode) {
			mfslv.setIs_dev("Y");
			mail_list = customMapper.selectMailFormSendListWhereIsDevList(mfslv);
		} else {
			mfslv.setIs_dev("N");
			mail_list = customMapper.selectMailFormSendListWhereIsDevList(mfslv);
		}
		String subject = "<JSAM "+sdf.format(cal2.getTime())+" 현황 보고>";
		if(Config.isDevMode) {
			subject = "개발서버" + " " + subject;
		}
		try {
			for (int i = 0; i < mail_list.size(); i++) {
				MailFormSendListVo mailFormSendListVo = (MailFormSendListVo)mail_list.get(i);
				if(mailFormSendListVo!=null&&mailFormSendListVo.getEmail()!=null) {
					MimeMessage msg = mailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
					helper.setFrom(from);
					helper.setTo(mailFormSendListVo.getEmail());
					helper.setSubject(subject);
					helper.setText("", html);
					mailSender.send(msg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("MimeMessageHelper Exception");
		}
		
		return returnValue;
	}
	
	public String checkNullAndEmpty(String checkStr){
		String result = null;
		
		result = checkStr;
		
		if(null == result){
			result = "0";
		}else{
			if("".equals(result)){
				result = "0";
			}
		}
		
		return result;
	}

}