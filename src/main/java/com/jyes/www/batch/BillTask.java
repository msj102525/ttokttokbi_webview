package com.jyes.www.batch;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.api.services.androidpublisher.model.SubscriptionPurchase;
import com.inicis.inipay.INIpay50;
import com.inicis.inipay4.util.INIdata;
import com.jyes.www.common.Config;
import com.jyes.www.controller.PayController;
import com.jyes.www.google.billing.GoogleInApp;
import com.jyes.www.service.impl.ICustomService;
import com.jyes.www.service.impl.IPayService;
import com.jyes.www.util.LogUtils;
import com.jyes.www.util.NetUtils;
import com.jyes.www.util.StringUtil;
import com.jyes.www.vo.PayCancelExeLogVo;
import com.jyes.www.vo.PayCustomVo;
import com.jyes.www.vo.PayExeLogVo;
import com.jyes.www.vo.TbUserRefund;

@Component
public class BillTask {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"/config/spring/context-root.xml",
				"file:D:\\eclipse-workspace\\bizcontents_dev_20200103\\src\\main\\webapp\\WEB-INF\\config\\spring\\dispatcher-servlet.xml"
		});
//		BillTask b = (BillTask)context.getBean(BillTask.class);
//		b.taskSchedulerCheckGoogle();
//		b.taskScheduler();
//		b.execute();
	}
	
	public void execute() {
		StringBuffer logData = new StringBuffer();
		logData.append("\n");
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- taskScheduler 시작 ----\n");
		String tag = "taskScheduler";
		long strartTime = System.currentTimeMillis();
		int success = 0;
		try {
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query start sendMailForm" + "\n");
			success = customService.sendMailForm();
		} catch(Exception e) {
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error sendMailForm : " + e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query end sendMailForm int success : " + success + "\n");
		long endTime = System.currentTimeMillis()-strartTime; 
		logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"tag:"+tag+":전체:endTime:"+endTime+"\n");
		if(endTime > 15000) {
			logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"tag:"+tag+":전체:endTime:"+endTime+",connection time out[15second over]"+"\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- taskScheduler 종료 ----\n");
		log.info(logData.toString());
	}
	
	private static final Log log = LogFactory.getLog(BillTask.class);

	@Resource(name = "payService")
	private IPayService payService;
	
	@Resource(name = "customService")
	private ICustomService customService;
	
	/**
	 * @desc : Task Scheduler
	 */
//	@Scheduled(cron="0/30 * * * * *") //30초마다 테스트용
	@Scheduled(cron="0 0 10 * * *") //매일10시마다 상용 세팅
	public void taskScheduler() {
		log.info("taskScheduler 개발서버:10.255.0.15, 상용서버:10.131.101.13 NetUtils.getServerIp() : "+NetUtils.getServerIp());
		if("10.255.0.15".equals(StringUtil.nvl(NetUtils.getServerIp()))
		 ||"10.131.101.13".equals(StringUtil.nvl(NetUtils.getServerIp()))) {
			StringBuffer logData = new StringBuffer();
			logData.append("\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- taskScheduler 시작 ----\n");
			String tag = "taskScheduler";
			long strartTime = System.currentTimeMillis();
			ArrayList<PayCustomVo> al_PayCustomVo = null;
			try {
				al_PayCustomVo = payService.selectPayBillBatchList();
				if(al_PayCustomVo != null) {
					if(al_PayCustomVo.size() > 0) {
						PayController payController = new PayController();
						INIdata data = null;
						SubscriptionPurchase subscriptionPurchase = null;
						String mid = Config.INI_B_P_MID;
						for (int i = 0; i < al_PayCustomVo.size(); i++) {
							PayCustomVo payCustomVo = al_PayCustomVo.get(i);
							String pay_bill_seq = payCustomVo.getSeq();
							String access_seq = payCustomVo.getAccess_seq();
							String pay_type_code = payCustomVo.getPay_type_code();
							String id = payCustomVo.getId();
							String affiliates_code = payCustomVo.getAffiliates_code();
							String url = payCustomVo.getUrl();
							String price = payCustomVo.getPrice();
							String currency = payCustomVo.getCurrency();
							String goodname = payCustomVo.getGoodname();
							String buyername = payCustomVo.getBuyername();
							String buyertel = payCustomVo.getBuyertel();
							String buyeremail = payCustomVo.getBuyeremail();
							String cardquota = payCustomVo.getCardquota();
							String billkey = payCustomVo.getBillkey();
							String pay_success_seq = payCustomVo.getPay_success_seq();
							String p_type = payCustomVo.getP_type();
	
							logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"pay_bill_seq : "+pay_bill_seq+"\n");
							logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"access_seq : "+access_seq+"\n");
							logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"pay_type_code : "+pay_type_code+"\n");
							logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"id : "+id+"\n");
							logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"affiliates_code : "+affiliates_code+"\n");
							logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"url : "+url+"\n");
							logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"price : "+price+"\n");
							logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"currency : "+currency+"\n");
							logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"goodname : "+goodname+"\n");
							logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"buyername : "+buyername+"\n");
							logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"buyertel : "+buyertel+"\n");
							logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"buyeremail : "+buyeremail+"\n");
							logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"cardquota : "+cardquota+"\n");
							logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"billkey : "+billkey+"\n");
							logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"pay_success_seq : "+pay_success_seq+"\n");
							
							String log_url = url;
							String log_company = "";
							String log_payment = p_type;
							String log_price = price;
							String log_currency = currency;
							String log_goodname = goodname;
							String log_buyername = buyername;
							String log_buyertel = buyertel;
							String log_buyeremail = buyeremail;
							String log_cardquota = cardquota;
							String log_billkey = billkey;
							String log_tid = "";
							String log_auth_dt = "";
							String log_oid = "";
							String log_msg = "";
							String log_is_success = "N";
							String log_access_seq = access_seq;
							String log_pay_type_code = pay_type_code;
							String log_pay_success_seq = "";
							String log_pay_bill_seq = "";
							String log_id = id;
							String log_affiliates_code = affiliates_code;
							
							String S_TID = null; 		//거래 번호
							String S_RESULTCODE = null;	//결과 코드
							String S_RESULTMSG = null;	//결과 메시지
							String S_AUTHCODE = null;	//승인번호
							String S_PGAUTHDATE = null;	//승인날짜 YYYYMMDD 형식
							String S_PGAUTHTIME = null;	//승인시각 MMHHSS 형식
							
							String S_P_AUTH_DT = null;	//승인일자 
//							String S_P_OID = null;		//상점 주문번호 
							String S_P_TID = null;		//거래번호
							String S_P_TYPE = null;		//지불수단
							String S_P_FN_NM = null;	//결제카드한글명 BC카드등...
							
							String S_P_URL = null;
							String S_P_PRICE = null;
							String S_P_CURRENCY = null;
							String S_P_GOODNAME = null;
							String S_P_BUYERNAME = null;
							String S_P_BUYERTEL = null;
							String S_P_BUYEREMAIL = null;
							String S_P_CARDQUOTA = null;
							String S_P_BILLKEY = null;
							
							int returnValue = -1;
							
							if(!"G-PAY".equals(p_type)) {
								//이니시스 정기결제 승인
								data = payController.iniCardBill(price, goodname, buyername, buyertel, buyeremail, billkey, logData);
								
								S_RESULTCODE = StringUtil.nvl(data.getData("ResultCode"));
								S_RESULTMSG = StringUtil.nvl(data.getData("ResultMsg"));
								
								if("00".equals(S_RESULTCODE)) {
									log_msg += "결제 성공";
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"결제 성공"+"\n");
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"data:"+data+"\n");
									
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"S_RESULTCODE:"+S_RESULTCODE+"\n");
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"S_RESULTMSG : "+S_RESULTMSG+"\n");
									
									S_TID = StringUtil.nvl(data.getData("tid"));
									S_AUTHCODE = StringUtil.nvl(data.getData("CardAuthCode"));
									S_PGAUTHDATE = StringUtil.nvl(data.getData("PGauthdate"));
									S_PGAUTHTIME = StringUtil.nvl(data.getData("PGauthtime"));
									
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"S_TID:"+S_TID+"\n");
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"S_AUTHCODE : "+S_AUTHCODE+"\n");
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"S_PGAUTHDATE : "+S_PGAUTHDATE+"\n");
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"S_PGAUTHTIME : "+S_PGAUTHTIME+"\n");

									try {
										/**
										 * DB 저장
										 */
										S_P_AUTH_DT = S_PGAUTHDATE + S_PGAUTHTIME;//승인일자 
//										S_P_OID = orderid;//상점 주문번호 
										S_P_TID = S_TID;//거래번호
										S_P_TYPE = p_type;//지불수단
										S_P_FN_NM = "";//결제카드한글명 BC카드등...
										
										S_P_URL = data.getData("url");
										S_P_PRICE = data.getData("price");
										S_P_CURRENCY = data.getData("currency");
										S_P_GOODNAME = data.getData("goodname");
										S_P_BUYERNAME = data.getData("buyername");
										S_P_BUYERTEL = data.getData("buyertel");
										S_P_BUYEREMAIL = data.getData("buyeremail");
										S_P_CARDQUOTA = data.getData("cardquota");
										S_P_BILLKEY = data.getData("billkey");

									} catch(Exception e) {
										e.printStackTrace();
										logData.append("["+LogUtils.getCurrentTime()+"]"+" "+e.toString()+"\n");
										log_msg += "DB 등록 관련 정보 Exception";
									}
								}
							} else if("G-PAY".equals(payCustomVo.getP_type())) {
								String token = payCustomVo.getP_tid();
								String subscriptionId = "wireless_subscribe_ticket";
								if("P0012".equals(pay_type_code)||"P0033".equals(pay_type_code)) {
									subscriptionId = "wireless_fixed_subscribe_ticket";
								}else if("P0045".equals(pay_type_code)){
									subscriptionId = "wireless_subscribe_ticket_01";
								}else if("P0046".equals(pay_type_code)){
									subscriptionId = "wireless_fixed_subscribe_ticket_01";
								}
								//구글구독 상태 확인
								subscriptionPurchase = GoogleInApp.checkSubscriptionPurchase(subscriptionId, token);
								if(subscriptionPurchase!=null&&subscriptionPurchase.getAutoRenewing()) {
									log_msg += "결제 성공";
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"결제 성공(구독중)"+"\n");
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"subscriptionPurchase:"+subscriptionPurchase+"\n");

									S_RESULTCODE = "00";
									S_RESULTMSG = "구독중";
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"S_RESULTCODE:"+S_RESULTCODE+"\n");
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"S_RESULTMSG : "+S_RESULTMSG+"\n");
									S_TID = token;
									S_AUTHCODE = "";
//									long timeInMillis = subscriptionPurchase.getExpiryTimeMillis();
//									Date timeInDate = new Date(timeInMillis);
									Date timeInDate = new Date();
									SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
									S_PGAUTHDATE = transFormat.format(timeInDate);
									transFormat = new SimpleDateFormat("HHmmss");
									S_PGAUTHTIME = transFormat.format(timeInDate);
									
//									timeInDate = new Date(timeInMillis);
									timeInDate = new Date();
							        Calendar cal = Calendar.getInstance();
							        cal.setTime(timeInDate);
							        cal.add(Calendar.DATE, 30);
									timeInDate = new Date(cal.getTimeInMillis());
									payCustomVo.setBatch_date(timeInDate);
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"S_TID:"+S_TID+"\n");
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"S_AUTHCODE : "+S_AUTHCODE+"\n");
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"S_PGAUTHDATE : "+S_PGAUTHDATE+"\n");
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"S_PGAUTHTIME : "+S_PGAUTHTIME+"\n");
									
									S_P_AUTH_DT = S_PGAUTHDATE + S_PGAUTHTIME;//승인일자 
//									S_P_OID = orderid;//상점 주문번호
									S_P_TID = S_TID;//거래번호
									S_P_TYPE = "G-PAY";//지불수단
									S_P_FN_NM = "";//결제카드한글명 BC카드등...
									
									S_P_URL = url;
									S_P_PRICE = price;
									S_P_CURRENCY = currency;
									S_P_GOODNAME = goodname;
									S_P_BUYERNAME = buyername;
									S_P_BUYERTEL = buyertel;
									S_P_BUYEREMAIL = buyeremail;
									S_P_CARDQUOTA = cardquota;
									S_P_BILLKEY = null;
								}
							}
							
							if("00".equals(S_RESULTCODE)&&!"".equals(StringUtil.nvl(S_P_TID))&&!"".equals(StringUtil.nvl(S_P_AUTH_DT))) {
								logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"DB 등록"+"\n");
								/**
								 * 빌링 정보 추가
								 */
								payCustomVo.setUrl(S_P_URL);
								payCustomVo.setPrice(S_P_PRICE);
								payCustomVo.setCurrency(S_P_CURRENCY);
								payCustomVo.setGoodname(S_P_GOODNAME);
								payCustomVo.setBuyername(S_P_BUYERNAME);
								payCustomVo.setBuyertel(S_P_BUYERTEL);
								payCustomVo.setBuyeremail(S_P_BUYEREMAIL);
								payCustomVo.setCardquota(S_P_CARDQUOTA);
								payCustomVo.setBillkey(S_P_BILLKEY);
								payCustomVo.setP_tid(S_P_TID);
								SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
								payCustomVo.setP_auth_dt(dt.parse(S_P_AUTH_DT));
								int success = 0;
								try {
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"insertPayBillBatch"+"\n");
									success = payService.insertPayBillBatch(payCustomVo, logData);
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"success:"+success+"\n");
									if(success > 0) {
										returnValue = 1;
										log_msg += "DB 등록 성공";
									} else {
										returnValue = 0;
										log_msg += "DB 등록 실패";
									}
								} catch(Exception e) {
									e.printStackTrace();
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"insertPayBillBatch error e:"+e.toString()+"\n");
									returnValue = 0;
									log_msg += "DB 등록 Exception";
								}
								log_url = url;
								log_company = "";
								log_payment = p_type;
								log_price = price;
								log_currency = currency;
								log_goodname = goodname;
								log_buyername = buyername;
								log_buyertel = buyertel;
								log_buyeremail = buyeremail;
								log_cardquota = cardquota;
								log_billkey = S_P_BILLKEY;
								log_tid = S_P_TID;
								log_auth_dt = S_P_AUTH_DT;
								log_oid = StringUtil.nvl(payCustomVo.getO_id());
								log_is_success = (returnValue==1?"Y":"N");
								log_access_seq = access_seq;
								log_pay_type_code = pay_type_code;
								log_pay_success_seq = StringUtil.nvl(payCustomVo.getPay_success_seq());
								log_pay_bill_seq = StringUtil.nvl(payCustomVo.getPay_bill_seq());
								log_id = id;
								log_affiliates_code = affiliates_code;
							}
								
							//결제 데이터가 DB에 정상적으로 등록되지 않은 경우  결제 취소 처리
							if(1!=returnValue) {
								String log_cancel_code = "";
								String log_cancel_msg = "";
								String log_cancel_date = "";
								String log_cancel_time = "";
								if(!"G-PAY".equals(payCustomVo.getP_type())) {
									INIpay50 inipay = null;
									try {
										String msg = "데이터 등록 실패";// 취소사유
										String cancelreason = "1";// 현금영수증 1:거래취소,2:오류,3:기타
										inipay = payController.iniCancle(mid, S_TID, msg, cancelreason, logData);
										if(inipay!=null) {
											log_msg += inipay.GetResult("ResultCode") + " " + inipay.GetResult("ResultMsg");
										} else {
											log_msg += "결제 취소 데이터 null";
										}
									} catch(Exception e) {
										e.printStackTrace();
										logData.append("["+LogUtils.getCurrentTime()+"]"+" "+e.toString()+"\n");
										log_msg += "결제 취소 Exception";
									}
									if(inipay != null) {
										log_cancel_code = StringUtil.nvl(inipay.GetResult("ResultCode"));
										log_cancel_msg = StringUtil.nvl(inipay.GetResult("ResultMsg"));
										log_cancel_date = StringUtil.nvl(inipay.GetResult("CancelDate"));
										log_cancel_time = StringUtil.nvl(inipay.GetResult("CancelTime"));
									} else {
										log_cancel_msg = "결제 취소 오류";
									}
								} else {
									//구독 취소 처리
									if(subscriptionPurchase!=null) {
										if(subscriptionPurchase.getCancelReason()!=null) {
											int cancelReason = subscriptionPurchase.getCancelReason();
											if(cancelReason==0) {
												log_cancel_msg = "사용자가 구독을 취소했습니다.";
											} else if(cancelReason==1) {
												log_cancel_msg = "청구 문제로 인해 시스템에서 구독을 취소했습니다.";
											} else if(cancelReason==2) {
												log_cancel_msg = "구독이 새로운 구독으로 대체되었습니다.";
											} else if(cancelReason==3) {
												log_cancel_msg = "개발자가 구독을 취소했습니다.";
											}
											log_cancel_code = cancelReason+"";
										} else {
											log_cancel_code = "999";
											log_cancel_msg = "구독 취소 상태 확인 필요";
										}
										S_RESULTCODE = "01";
										S_RESULTMSG = log_cancel_msg;
									} else {
										//구독 상태 확인 실패
										log_cancel_msg = "subscriptionPurchase is null(구독 상태 확인 실패)";
										S_RESULTCODE = "01";
										S_RESULTMSG = log_cancel_msg;
									}
								}
								
								if(!"G-PAY".equals(payCustomVo.getP_type())) {
									/**
									 * 결제 취소 로그 20190610
									 */
									int success = 0;
									try {
										String msg_ = log_cancel_code +" "+ log_cancel_msg +" "+ log_cancel_date +" "+ log_cancel_time;
										String log_cancel_url = log_url;
										String log_cancel_company = log_company;
										String log_cancel_payment = log_payment;
										String log_cancel_price = log_price;
										String log_cancel_currency = log_currency;
										String log_cancel_goodname = log_goodname;
										String log_cancel_buyername = log_buyername;
										String log_cancel_buyertel = log_buyertel;
										String log_cancel_buyeremail = log_buyeremail;
										String log_cancel_cardquota = log_cardquota;
										String log_cancel_billkey = log_billkey;
										String log_cancel_tid = log_tid;
										String log_cancel_auth_dt = log_auth_dt;
										String log_cancel_oid = log_oid;
										String log_cancel_access_seq = log_access_seq;
										String log_cancel_pay_type_code = log_pay_type_code;
										String log_cancel_pay_success_seq = log_pay_success_seq;
										String log_cancel_pay_bill_seq = log_pay_bill_seq;
										String log_cancel_id = log_id;
										String log_cancel_affiliates_code = log_affiliates_code;
										PayCancelExeLogVo payCancelExeLogVo = new PayCancelExeLogVo();
										if(!"".equals(log_cancel_url))			   {payCancelExeLogVo.setUrl(log_cancel_url);                        }            
										if(!"".equals(log_cancel_company))		   {payCancelExeLogVo.setCompany(log_cancel_company);                }        
										if(!"".equals(log_cancel_payment)) 		   {payCancelExeLogVo.setPayment(log_cancel_payment);                }        
										if(!"".equals(log_cancel_price)) 		   {payCancelExeLogVo.setPrice(log_cancel_price);                    }          
										if(!"".equals(log_cancel_currency)) 	   {payCancelExeLogVo.setCurrency(log_cancel_currency);              }       
										if(!"".equals(log_cancel_goodname)) 	   {payCancelExeLogVo.setGoodname(log_cancel_goodname);              }       
										if(!"".equals(log_cancel_buyername)) 	   {payCancelExeLogVo.setBuyername(log_cancel_buyername);            }      
										if(!"".equals(log_cancel_buyertel)) 	   {payCancelExeLogVo.setBuyertel(log_cancel_buyertel);              }       
										if(!"".equals(log_cancel_buyeremail)) 	   {payCancelExeLogVo.setBuyeremail(log_cancel_buyeremail);          }     
										if(!"".equals(log_cancel_cardquota)) 	   {payCancelExeLogVo.setCardquota(log_cancel_cardquota);            }      
										if(!"".equals(log_cancel_billkey)) 		   {payCancelExeLogVo.setBillkey(log_cancel_billkey);                }        
										if(!"".equals(log_cancel_tid)) 			   {payCancelExeLogVo.setTid(log_cancel_tid);                        }            
										if(!"".equals(log_cancel_auth_dt)) 		   {payCancelExeLogVo.setAuth_dt(log_cancel_auth_dt);                }        
										if(!"".equals(log_cancel_oid)) 			   {payCancelExeLogVo.setOid(log_cancel_oid);                        }            
										if(!"".equals(log_cancel_access_seq))      {payCancelExeLogVo.setAccess_seq(log_cancel_access_seq);          }     
										if(!"".equals(log_cancel_pay_type_code))   {payCancelExeLogVo.setPay_type_code(log_cancel_pay_type_code);    }  
										if(!"".equals(log_cancel_pay_success_seq)) {payCancelExeLogVo.setPay_success_seq(log_cancel_pay_success_seq);}
										if(!"".equals(log_cancel_pay_bill_seq))    {payCancelExeLogVo.setPay_bill_seq(log_cancel_pay_bill_seq);      }   
										if(!"".equals(log_cancel_id)) 			   {payCancelExeLogVo.setId(log_cancel_id);                          }             
										if(!"".equals(log_cancel_affiliates_code)) {payCancelExeLogVo.setAffiliates_code(log_cancel_affiliates_code);}
										if(!"".equals(msg_)) 					   {payCancelExeLogVo.setMsg(msg_);}
										if("00".equals(log_cancel_code)) {
											payCancelExeLogVo.setSuccess("Y");
										} else {
											payCancelExeLogVo.setSuccess("N");
										}
										logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"query start insertPayCancelExeLog PayCancelExeLogVo payCancelExeLogVo:"+payCancelExeLogVo+"\n");
										success = payService.insertPayCancelExeLog(payCancelExeLogVo);
									} catch(Exception e) {
										e.printStackTrace();
										logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"query error insertPayCancelExeLog : "+e.toString()+"\n");
									}
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"query end insertPayCancelExeLog int success:"+success+"\n");
								}
							}
							
							if(!"00".equals(S_RESULTCODE)) {
								/**
								 * 결제 오류 DB 정보 추가
								 */
								int success = 0;
								try {
									payCustomVo.setError_code(S_RESULTCODE);
									payCustomVo.setError_msg(S_RESULTMSG);
									payCustomVo.setPay_bill_seq(pay_bill_seq);
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"insertPayBillFail PayCustomVo payCustomVo : " + payCustomVo +"\n");
									success = payService.insertPayBillFail(payCustomVo);
									if(success > 0) {
										sendMail(S_RESULTMSG);
									}
								} catch(Exception e) {
									e.printStackTrace();
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"insertPayBillFail error e:"+e.toString()+"\n");
								}
								logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"insertPayBillFail success:"+success+"\n");
								returnValue = 0;
							}
							
							if(returnValue >= 1) {
								logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"결제 성공"+"\n");
								logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"payCustomVo : "+payCustomVo+"\n");
							} else {
								logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"결제 실패"+"\n");
								logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"S_RESULTCODE:"+S_RESULTCODE+"\n");
								logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"S_RESULTMSG : "+S_RESULTMSG+"\n");
								logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"payCustomVo : "+payCustomVo+"\n");
							}
							
							/**
							 * DB 결제 시도 데이터 추가 20190604
							 */
							if(!"".equals(log_msg)) {
								log_msg += " ";
							}
							
							log_msg += S_RESULTCODE + " " + S_RESULTMSG;
							
							try {
								int success = 0;
								
								PayExeLogVo payExeLogVo = new PayExeLogVo();
								if(!"".equals(StringUtil.nvl(log_url))) {
									payExeLogVo.setUrl(log_url);
								}
								if(!"".equals(StringUtil.nvl(log_company))) {
									payExeLogVo.setCompany(log_company);
								}
								if(!"".equals(StringUtil.nvl(log_payment))) {
									payExeLogVo.setPayment(log_payment);
								}
								if(!"".equals(log_price)) {
									payExeLogVo.setPrice(log_price);
								}
								if(!"".equals(log_currency)) {
									payExeLogVo.setCurrency(log_currency);
								}
								if(!"".equals(log_goodname)) {
									payExeLogVo.setGoodname(log_goodname);
								}
								if(!"".equals(log_buyername)) {
									payExeLogVo.setBuyername(log_buyername);
								}
								if(!"".equals(log_buyertel)) {
									payExeLogVo.setBuyertel(log_buyertel);
								}
								if(!"".equals(log_buyeremail)) {
									payExeLogVo.setBuyeremail(log_buyeremail);
								}
								if(!"".equals(log_cardquota)) {
									payExeLogVo.setCardquota(log_cardquota);
								}
								if(!"".equals(log_billkey)) {
									payExeLogVo.setBillkey(log_billkey);
								}
								if(!"".equals(log_tid)) {
									payExeLogVo.setTid(log_tid);
								}
								if(!"".equals(log_auth_dt)) {
									payExeLogVo.setAuth_dt(log_auth_dt);
								}
								if(!"".equals(log_oid)) {
									payExeLogVo.setOid(log_oid);
								}
								if(!"".equals(log_msg)) {
									payExeLogVo.setMsg(log_msg);
								}
								if(!"".equals(log_is_success)) {
									payExeLogVo.setSuccess(log_is_success);
								} else {
									payExeLogVo.setSuccess("N");
								}
								if(!"".equals(log_access_seq)) {
//									payExeLogVo.setAccess_seq(log_access_seq);
								}
								if(!"".equals(log_pay_type_code)) {
									payExeLogVo.setPay_type_code(log_pay_type_code);
								}
								if(!"".equals(log_pay_success_seq)) {
									payExeLogVo.setPay_success_seq(log_pay_success_seq);
								}
								if(!"".equals(log_pay_bill_seq)) {
									payExeLogVo.setPay_bill_seq(log_pay_bill_seq);
								}
								if(!"".equals(log_id)) {
									payExeLogVo.setId(log_id);
								}
								if(!"".equals(log_affiliates_code)) {
									payExeLogVo.setAffiliates_code(log_affiliates_code);
								}
								logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"inserttPayExeLog PayExeLogVo payExeLogVo :"+payExeLogVo+"\n");
								success = payService.insertPayExeLog(payExeLogVo);
								logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"inserttPayExeLog success:"+success+"\n");
							} catch(Exception e) {
								e.printStackTrace();
								logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"inserttPayExeLog error e:"+e.toString()+"\n");
							}
							
						}
					} else {
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "payCustomVo is size 0"+"\n");
					}
				} else {
					logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "payCustomVo is null"+"\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			/**
			 * 메일 발송 추가
			 */
			int success = 0;
			try {
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query start sendMailForm" + "\n");
				success = customService.sendMailForm();
			} catch(Exception e) {
				e.printStackTrace();
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error sendMailForm : " + e.toString() + "\n");
			}
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query end sendMailForm int success : " + success + "\n");
			
			long endTime = System.currentTimeMillis()-strartTime; 
			logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"tag:"+tag+":전체:endTime:"+endTime+"\n");
			if(endTime > 15000) {
				logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"tag:"+tag+":전체:endTime:"+endTime+",connection time out[15second over]"+"\n");
			}
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- taskScheduler 종료 ----\n");
			log.info(logData.toString());
		}
	}
	
	/**
	 * 구글 결제 성공 여부를 체크 한다
	 * @desc : Task Scheduler
	 */
//	@Scheduled(cron="0/30 * * * * *") //30초마다 테스트용
	@Scheduled(cron="0 0/10 * * * *") //10분마다 상용 세팅
	public void taskSchedulerCheckGoogle() {
		log.info("taskScheduler 개발서버:10.255.0.15, 상용서버:10.131.101.13 NetUtils.getServerIp() : "+NetUtils.getServerIp());
		if("10.255.0.15".equals(StringUtil.nvl(NetUtils.getServerIp()))
		 ||"10.131.101.13".equals(StringUtil.nvl(NetUtils.getServerIp()))
		 ||"192.168.0.28".equals(StringUtil.nvl(NetUtils.getServerIp()))) {
			StringBuffer logData = new StringBuffer();
			logData.append("\n");
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- taskSchedulerCheckGoogle 시작 ----\n");
			String tag = "taskSchedulerCheckGoogle";
			long strartTime = System.currentTimeMillis();
			ArrayList<PayCustomVo> al_PayCustomVo = null;
			try {
				al_PayCustomVo = payService.selectGoogleBillPayCheckList();
				if(al_PayCustomVo!=null) {
					if(al_PayCustomVo.size() > 0) {
						for (int i = 0; i < al_PayCustomVo.size(); i++) {
							PayCustomVo payCustomVo = al_PayCustomVo.get(i);
							logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "payCustomVo:"+payCustomVo+"\n");
							String access_seq = payCustomVo.getAccess_seq();
							String pay_bill_seq = payCustomVo.getSeq();
							String pay_success_seq = payCustomVo.getPay_success_seq();
							String pay_type_code = payCustomVo.getPay_type_code();
							String token = payCustomVo.getP_tid();
							Date p_auth_dt = payCustomVo.getP_auth_dt();
							String o_id = payCustomVo.getO_id();
							String expiration_date = payCustomVo.getExpiration_date();
							String subscriptionId = "wireless_subscribe_ticket";
							if("P0012".equals(pay_type_code)||"P0033".equals(pay_type_code)) {
								subscriptionId = "wireless_fixed_subscribe_ticket";
							}else if("P0045".equals(pay_type_code)){
								subscriptionId = "wireless_subscribe_ticket_01";
							}else if("P0046".equals(pay_type_code)){
								subscriptionId = "wireless_fixed_subscribe_ticket_01";
							}
							//구글구독 상태 확인
							SubscriptionPurchase subscriptionPurchase = GoogleInApp.checkSubscriptionPurchase(subscriptionId, token);
							logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "subscriptionPurchase:"+subscriptionPurchase+"\n");
							if(subscriptionPurchase!=null) {
								boolean auto_renewing = subscriptionPurchase.getAutoRenewing();
								if(auto_renewing) {
									int payment_satate = 0;
									try {
										payment_satate = subscriptionPurchase.getPaymentState();
									} catch(Exception e) {}
									if(payment_satate==1) {
										long expiryTimeMillis = subscriptionPurchase.getExpiryTimeMillis();
										SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
										Date expiryDate = new Date(expiryTimeMillis);
										expiryDate = dt.parse(dt.format(expiryDate));
										int compare = expiryDate.compareTo(p_auth_dt);
										if(compare>0) {
											//#결제 성공(만료일이 기존 만료일 보다 큰경 된 경우)
											//# - AUTH_DT(만료일) 변경, OID 추가, BATCH_DATE(만료일 하루전) 변경
											if(!o_id.equals(subscriptionPurchase.getOrderId())) {
												//새로운 오더아이디 일경우
												logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "결제 성공(만료일이 기존 만료일 보다 큰경 된 경우) 새로운 오더아이디 일경우"+"\n");
												int success = 0;
												try {
													String p_tid = subscriptionPurchase.getOrderId();
													p_auth_dt = expiryDate;
													Date batch_date = expiryDate;
													int cancelReason = (subscriptionPurchase.getCancelReason()==null)?0:subscriptionPurchase.getCancelReason();
													payCustomVo.setP_tid(p_tid);
													payCustomVo.setP_auth_dt(p_auth_dt);
													
													Calendar cal = Calendar.getInstance();
													
													SimpleDateFormat db_dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
													Date expir_date = db_dt.parse(expiration_date);
													SimpleDateFormat only_date = new SimpleDateFormat("yyyyMMdd");
													expir_date = only_date.parse(only_date.format(expir_date));
													cal.setTime(expir_date);
													cal.add(Calendar.DATE, -1);
													expir_date = new Date(cal.getTimeInMillis());
													
													expiryDate = only_date.parse(only_date.format(expiryDate));
													cal.setTime(expiryDate);
													cal.add(Calendar.DATE, -1);
													
													System.out.println(db_dt.format(cal.getTime()));
													
													only_date.format(expiryDate);
													

//											        cal.setTime(batch_date);
//											        cal.add(Calendar.DATE, -1);
//											        batch_date = new Date(cal.getTimeInMillis());
													
													Date tiket_expiryDate = dt.parse(expiration_date);
													compare = expiryDate.compareTo(tiket_expiryDate);
													if(compare>0) {
														//batch_date 구독 만료일이 티켓 만료일 보다 크다
														cal.setTime(db_dt.parse(expiration_date));
														cal.add(Calendar.DATE, -1);
														payCustomVo.setBatch_date(new Date(cal.getTimeInMillis()));//티켓 만료일 -1일
													} else {
														//batch_dat 구독 만료일이 티켓 만료일 보다 같거나  작다
														cal.setTime(new Date(expiryTimeMillis));
														cal.add(Calendar.DATE, -1);
														payCustomVo.setBatch_date(new Date(cal.getTimeInMillis()));//구독 만료일 -1일
													}
													payCustomVo.setPay_bill_seq(pay_bill_seq);
													logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"updateGoogleBillPayBillBatch PayCustomVo payCustomVo : " + payCustomVo +"\n");
													success = payService.updateGoogleBillPayBillBatch(payCustomVo);
												} catch(Exception e) {
													e.printStackTrace();
													logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"updateGoogleBillPayBillBatch error e:"+e.toString()+"\n");
												}
												logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"updateGoogleBillPayBillBatch success:"+success+"\n");
											} else {
												//기존 오더아이디 일경우
												logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "결제 보류(만료일이 기존 만료일 보다 큰 경우) 기존 오더아이디 일경우"+"\n");
											}
										} else {
											//#결제 보류(만료일이 기존 만료일 보다작은 같거나 경우)
											logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "결제 보류(만료일이 기존 만료일 보다작은 같거나 경우)"+"\n");
										}
									} else {
										//#결제 대기 상태
										logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "결제 대기 상태"+"\n");
									}
								} else {
									logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "결제 실패 auto_renewing:"+auto_renewing+"\n");
									String log_cancel_msg = "";
									//#결제 실패(만료일이 작은 같거나 경우)
									//# - 이용권 환불(중지) 처리
									//# - 실패 테이블 내용 추가
									logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "payCustomVo:"+payCustomVo+"\n");
									/**
									 * 이용권 환불(중지) 처리
									 */
									int success = insertUserRefund(access_seq, pay_success_seq, pay_bill_seq, logData);
									/**
									 * 결제 오류 DB 정보 추가
									 */
									try {
										if(subscriptionPurchase.getCancelReason()!=null) {
											int cancelReason = subscriptionPurchase.getCancelReason();
											if(cancelReason==0) {
												log_cancel_msg = "사용자가 구독을 취소했습니다.";
											} else if(cancelReason==1) {
												log_cancel_msg = "청구 문제로 인해 시스템에서 구독을 취소했습니다.";
											} else if(cancelReason==2) {
												log_cancel_msg = "구독이 새로운 구독으로 대체되었습니다.";
											} else if(cancelReason==3) {
												log_cancel_msg = "개발자가 구독을 취소했습니다.";
											}
										} else {
											log_cancel_msg = "구독 취소 상태 확인 필요";
										}
										payCustomVo.setError_code("01");
										payCustomVo.setError_msg(log_cancel_msg);
										payCustomVo.setPay_bill_seq(pay_bill_seq);
										logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"insertPayBillFail PayCustomVo payCustomVo : " + payCustomVo +"\n");
										success = payService.insertPayBillFail(payCustomVo);
										if(success > 0) {
											sendMail(log_cancel_msg);
										}
									} catch(Exception e) {
										e.printStackTrace();
										logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"insertPayBillFail error e:"+e.toString()+"\n");
									}
									logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"insertPayBillFail success:"+success+"\n");
								}
							} else {
								//구독 상태 확인 실패
								logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "subscriptionPurchase is null(구독 상태 확인 실패) payCustomVo:"+payCustomVo+"\n");
							}
						}
					} else {
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "payCustomVo is size 0"+"\n");
					}
				} else {
					logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "payCustomVo is null"+"\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			long endTime = System.currentTimeMillis()-strartTime; 
			logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"tag:"+tag+":전체:endTime:"+endTime+"\n");
			if(endTime > 15000) {
				logData.append("["+LogUtils.getCurrentTime()+"]"+" "+"tag:"+tag+":전체:endTime:"+endTime+",connection time out[15second over]"+"\n");
			}
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "---- taskScheduler 종료 ----\n");
			log.info(logData.toString());
		}
	}
	
	/**
	 * 구글 구독 미결제 취소 처리
	 * @param access_seq
	 * @param pay_success_seq
	 * @param pay_bill_seq
	 */
	public int insertUserRefund(String access_seq, String pay_success_seq, String pay_bill_seq, StringBuffer logData) {
		int success = 0;
		try {
			SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
			Date rs_date = new Date();
			TbUserRefund tbUserRefund = new TbUserRefund();
			tbUserRefund.setAccess_seq(access_seq);
			tbUserRefund.setPay_success_seq(pay_success_seq);
			tbUserRefund.setPay_bill_seq(pay_bill_seq);
			tbUserRefund.setRs_date(dt.format(rs_date));
			tbUserRefund.setComment("구글 구독 미결제 환불(중지) 처리");//고정
			tbUserRefund.setCode("A0001");//고정
			tbUserRefund.setBank_name(null);//고정
			tbUserRefund.setBank_num(null);//고정
			tbUserRefund.setUser_user_refund_type_code("A9999");//고정
			tbUserRefund.setUser_id("admin");//관리자 고정
			tbUserRefund.setIs_refund_success("Y");//고정
			tbUserRefund.setRs_price("0");//고정
			tbUserRefund.setRs_user_id("admin");//관리자 고정
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query start insertUserRefund TbUserRefund tbUserRefund:" + tbUserRefund + "\n");
			success = payService.insertUserRefund(tbUserRefund);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query error insertUserRefund : " + e.toString() + "\n");
		}
		logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "query end insertUserRefund int success : " + success + "\n");
		return success;
	}
	
	/**
	 * 결제 대기 처리 - 0(결제 대기 중) or AutoResumeTimeMillis is not null
	 * 정기결제 테이블에  결제 대기 필드 추가, 상품 조회 안되도록 처리 확인 필요!!
	 * @param subscriptionPurchase
	 * @return
	 */
	public boolean isPurchaseWait(SubscriptionPurchase subscriptionPurchase) {
		boolean returnValue = false;
		try {
			if(subscriptionPurchase!=null) {
				boolean isAutoRenewing = subscriptionPurchase.getAutoRenewing();//정기결제 활성화 여부
				boolean isExpiryTimeMillised = isExpiryTimeMillised(subscriptionPurchase.getExpiryTimeMillis());//만료일
				if(subscriptionPurchase.getPaymentState()!=null) {
					int paymentState = subscriptionPurchase.getPaymentState();//0(결제 대기 중), 1(결제 수신)
					if(paymentState==0) {
						returnValue = true;
					}
				}
				if(subscriptionPurchase.getAutoResumeTimeMillis()!=null) {
					returnValue = true;
				}
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
	/**
	 * 전달된 날짜에 만료여부 체크
	 * @param expiryTimeMillis
	 * @return
	 */
	public boolean isExpiryTimeMillised(Long expiryTimeMillis) {
		boolean returnValue = true;
		Date nowDate = new Date();
		Date expiryDate = new Date(expiryTimeMillis);
		SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			expiryDate = dt.parse(dt.format(expiryDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int compare = expiryDate.compareTo(nowDate);
		if(compare<0) {
			returnValue = true;
		} else {
			returnValue = false;
		}
		return returnValue;
	}
	
	public void sendMail(String msg) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String text = msg;
					if(text == null) {
						text = "";
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd, HH:mm");
					Date now = new Date();
			        String formatedNow = sdf.format(now);
					String from = "jyesapps@gmail.com";
//					String to = "diapala82@jyes.co.kr";
//					String[] to = {"huntux2@jyes.co.kr"};
					String[] to = new String[4];
					to[0] = "huntux2@jyes.co.kr";//여대현[개발]
					to[1] = "chan7579@jyes.co.kr";//전찬균[개발]
					to[2] = "gruwn23@jyes.co.kr";//송혁주[기획]
					String subject = formatedNow+" 에 정기결제 에러가 확인되었습니다.";
					MimeMessage m = mailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(m, true, "utf-8");
					helper.setFrom(from);
					helper.setTo(to);
					helper.setSubject(subject);
					helper.setText("", text.replace("\r\n","<br>").replace("\n","<br>"));
					mailSender.send(m);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
	}
	
}
