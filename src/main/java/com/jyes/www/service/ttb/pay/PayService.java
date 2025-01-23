package com.jyes.www.service.ttb.pay;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.jyes.www.vo.ttb.PayInfoRequestVo;
import com.jyes.www.vo.ttb.CheckMemberVo;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jyes.www.network.Api;
import com.jyes.www.network.Http;
import com.jyes.www.network.NetworkTask;
import com.jyes.www.network.OneStore;
import com.jyes.www.network.Request;
import com.jyes.www.network.Response;
import com.jyes.www.util.LogUtils;
import com.jyes.www.util.StringUtil;
import com.jyes.www.vo.ttb.AccessVo;
import com.jyes.www.vo.ttb.CreateOidVo;
import com.jyes.www.vo.ttb.PayCancelExeLogVo;
import com.jyes.www.vo.ttb.PayCancelUserInfoVo;
import com.jyes.www.vo.ttb.PayCustomVo;
import com.jyes.www.vo.ttb.PayExeLogVo;
import com.jyes.www.vo.ttb.PayNameCompanyVo;
import com.jyes.www.vo.ttb.PayTypeVo;
import com.jyes.www.vo.ttb.SmartroPurchaseInfoVo;
import com.jyes.www.vo.ttb.StorePurchaseInfoVo;
import com.jyes.www.vo.ttb.TbUserRefund;
import com.jyes.www.vo.ttb.TbUserTicketsDate;
import com.jyes.www.vo.ttb.TbUserTicketsDateCheck;
import com.jyes.www.mapper.ttb.PayMapper;

@Service(value = "ttbPayService")
public class PayService implements IPayService {
	static final Logger logger = LoggerFactory.getLogger(PayService.class);

	@Resource(name = "ttbPayMapper")
	private PayMapper payMapper;

	@Override
	public Object selectUserInfo(AccessVo accessVo) throws SQLException {
		return payMapper.selectUserInfo(accessVo);
	}

	@Override
	public long selectOid(CreateOidVo createOidVo) throws SQLException {
		long returnValue = 0;
		returnValue = payMapper.insertCreateOid(createOidVo);
		if (returnValue < 1) {
			throw new SQLException("insertCreateOid 결과 없음");
		}
		returnValue = payMapper.selectOid(createOidVo);
		if (returnValue < 1) {
			throw new SQLException("selectOid 결과 없음");
		}
		return returnValue;
	}

	@Override
	public int insertPayInfo(PayCustomVo payCustomVo, StringBuffer logData) throws SQLException {
		int returnValue = 0;
		String seq = payMapper.selectPay(payCustomVo);
		if ("".equals(StringUtil.nvl(seq))) {
			returnValue = 0;
			returnValue = payMapper.insertPay(payCustomVo);
			if (returnValue < 1) {
				throw new SQLException("insertPay 결과 없음");
			}
			payCustomVo.setPay_seq(payCustomVo.getSeq());
		} else {
			payCustomVo.setPay_seq(seq);
		}
		returnValue = 0;
		returnValue = payMapper.insertPaySuccess(payCustomVo);
		if (returnValue < 1) {
			throw new SQLException("insertPaySuccess 결과 없음");
		}
		payCustomVo.setPay_success_seq(payCustomVo.getSeq());
		PayTypeVo pv = new PayTypeVo();
		pv.setCode(payCustomVo.getPay_type_code());
		PayTypeVo payTypeVo = payMapper.selectPayTypeWhereCode(pv);
		payCustomVo.setIs_pay_wl(StringUtil.nvl(payTypeVo.getIs_pay_wl(), "N"));
		String pay_bill_seq = "";
		if (!"".equals(StringUtil.nvl(payCustomVo.getBillkey())) ||
				("G-PAY".equals(StringUtil.nvl(payCustomVo.getP_type()))
						&& "1".equals(StringUtil.nvl(payCustomVo.getCheckPurchaseType())))) {
			returnValue = 0;
			returnValue = payMapper.insertPayBill(payCustomVo);
			if (returnValue < 1) {
				throw new SQLException("insertPayBill 결과 없음");
			}
			pay_bill_seq = payCustomVo.getSeq();
		}

		payCustomVo.setPay_type_code("P0031");

		PayCustomVo payCustomVo_sub = (PayCustomVo) payMapper.selectPayTypeIsTicketUseDayAndIsMakeCount(payCustomVo);

		logData.append("111111111111111111111" + "\n");
		logData.append("payCustomVo_sub " + payCustomVo_sub + "\n");

		try {
			if (payCustomVo_sub != null) {
				if (0 < Integer.parseInt(payCustomVo_sub.getUse_day())) {
					if (true) {
						if (!"".equals(pay_bill_seq)) {
							payCustomVo.setPay_bill_seq(pay_bill_seq);
						}
						returnValue = 0;
						returnValue = payMapper.insertUserTicketsInfo(payCustomVo);
						logData.append("returnValue" + returnValue + "\n");

						if (returnValue < 1) {
							throw new SQLException("insertUserTicketsInfo 결과 없음");
						}
					}
				} else {
					throw new SQLException("selectPayTypeIsTicketUseDayAndIsMakeCount 결과 없음");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logData.append(
					"[" + LogUtils.getCurrentTime() + "]" + " " + "insertPayInfo error e:" + e.toString() + "\n");
			throw new SQLException("Exception insertPayInfo 에러");
		}
		if (returnValue < 1) {
			throw new SQLException("insertPayInfo 에러");
		}

		return returnValue;
	}

	@Override
	public PayTypeVo selectPayTypeWhereCode(PayTypeVo payTypeVo) throws SQLException {
		return payMapper.selectPayTypeWhereCode(payTypeVo);
	}

	@Override
	public PayNameCompanyVo selectPayNameCompany(PayNameCompanyVo payNameCompanyVo) throws SQLException {
		return payMapper.selectPayNameCompany(payNameCompanyVo);
	}

	@Override
	public ArrayList<PayCustomVo> selectPayBillBatchList() throws SQLException {
		return payMapper.selectPayBillBatchList();
	}

	@Override
	public int insertPayBillBatch(PayCustomVo payCustomVo, StringBuffer logData) throws SQLException {
		int returnValue = 0;
		long o_id = 0;
		CreateOidVo createOidVo = new CreateOidVo();
		returnValue = payMapper.insertCreateOid(createOidVo);
		if (returnValue < 1) {
			throw new SQLException("insertCreateOid 결과 없음");
		}
		if (!"G-PAY".equals(payCustomVo.getP_type())) {
			o_id = payMapper.selectOid(createOidVo);
			if (o_id < 1) {
				throw new SQLException("selectOid 결과 없음");
			}
			payCustomVo.setO_id(o_id + "");
		}
		String seq = payCustomVo.getSeq();
		PayTypeVo pv = new PayTypeVo();
		pv.setCode(payCustomVo.getPay_type_code());
		PayTypeVo payTypeVo = payMapper.selectPayTypeWhereCode(pv);
		payCustomVo.setIs_pay_wl(StringUtil.nvl(payTypeVo.getIs_pay_wl(), "N"));
		returnValue = payMapper.insertPayBillBatch(payCustomVo);
		if (returnValue < 1) {
			throw new SQLException("insertPayBillBatch 결과 없음");
		}
		String pay_bill_seq = payCustomVo.getSeq();
		payCustomVo.setPay_bill_seq(pay_bill_seq);
		returnValue = payMapper.insertUserTicketsInfo(payCustomVo);
		if (returnValue < 1) {
			throw new SQLException("insertUserTicketsInfo 결과 없음");
		}
		returnValue = payMapper.updatePayBillBatchYn(seq);
		if (returnValue < 1) {
			throw new SQLException("updatePayBillBatchYn 결과 없음");
		}
		AccessVo accessVo = new AccessVo();
		accessVo.setId(payCustomVo.getId());
		accessVo.setAffiliates_code(payCustomVo.getAffiliates_code());
		accessVo = (AccessVo) payMapper.selectAccess(accessVo);

		StorePurchaseInfoVo storePurchaseInfoVo = new StorePurchaseInfoVo();
		/**
		 * 원스토어 정보 저장
		 */
		if ("0".equals(accessVo.getStore_type()) && !"G-PAY".equals(payCustomVo.getP_type())) {
			/**
			 * 구매 정보 스토어 전송 추가
			 */
			storePurchaseInfoVo.setId(accessVo.getId());
			storePurchaseInfoVo.setAffiliates_code(accessVo.getAffiliates_code());
			storePurchaseInfoVo.setO_id(payCustomVo.getO_id());
			storePurchaseInfoVo.setP_code(payCustomVo.getPay_type_code());
			storePurchaseInfoVo.setP_name(payCustomVo.getGoodname());
			storePurchaseInfoVo.setP_price(payCustomVo.getPrice());
			storePurchaseInfoVo.setP_count("1");
			storePurchaseInfoVo.setPayment("TRD_CREDITCARD");
			storePurchaseInfoVo.setSim(payCustomVo.getSim());
			storePurchaseInfoVo.setGoogleadid(payCustomVo.getGoogleadid());
			storePurchaseInfoVo.setStore_type(payCustomVo.getPkgInstaller());
			returnValue = 0;
			returnValue = payMapper.insertStorePurchaseInfo(storePurchaseInfoVo);
			if (returnValue < 1) {
				throw new SQLException("insertStorePurchaseInfo 결과 없음");
			}
		}

		SmartroPurchaseInfoVo smartroPurchaseInfoVo = new SmartroPurchaseInfoVo();
		String hp_no = StringUtil.nvl(accessVo.getId());
		String hp_no1 = "";
		String hp_no2 = "";
		String hp_no3 = "";
		if (hp_no.length() == 10 || hp_no.length() == 11) {
			hp_no1 = hp_no.substring(0, 3);
			hp_no2 = hp_no.substring(3, hp_no.length() - 4);
			hp_no3 = hp_no.substring(hp_no.length() - 4, hp_no.length());
		}
		// hp_no = hp_no1+"-"+hp_no2+"-"+hp_no3;
		String id_seq = "";
		/**
		 * 스마트로 이용권 정보 DB 저장
		 */
		if ("A0001".equals(accessVo.getAffiliates_code())) {
			/**
			 * 비즐 아이디 고유 번호 조회
			 */
			try {
				Request req = new Request(Api.Smartro.CHECK_MEMBER);
				req.addParameter("hpNo", hp_no);
				req.setParamType(Request.STRING_TYPE);
				NetworkTask networkTask = new NetworkTask(req);
				Response response = networkTask.doInBackground();
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "response:" + response + "\n");
				if (response != null && response.getResponse() != null) {
					CheckMemberVo checkMemberVo = com.jyes.www.network.JsonParser
							.parseTtbCheckMemberVo(response.getResponse().toString());
					logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "CheckMemberVo checkMemberVo : "
							+ checkMemberVo + "\n");
					String result = checkMemberVo.getResult();
					String message = checkMemberVo.getMessage();
					String bizzleFlag = checkMemberVo.getBizzleFlag();
					String idSeq = checkMemberVo.getIdSeq();
					if ("SUCCESS".equals(result)) {
						id_seq = idSeq;
					}
					logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "String id_seq : " + id_seq + "\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			/**
			 * 스마트로 이용권 정보 DB 저장
			 */
			smartroPurchaseInfoVo.setId(accessVo.getId());
			smartroPurchaseInfoVo.setAffiliates_code(accessVo.getAffiliates_code());
			smartroPurchaseInfoVo.setHp_no(hp_no);
			if (!"".equals(StringUtil.nvl(id_seq))) {
				smartroPurchaseInfoVo.setId_seq(id_seq);
			}
			smartroPurchaseInfoVo.setO_id(payCustomVo.getO_id());
			smartroPurchaseInfoVo.setP_code(payCustomVo.getPay_type_code());
			smartroPurchaseInfoVo.setP_name(payCustomVo.getGoodname());
			smartroPurchaseInfoVo.setP_price(payCustomVo.getPrice());
			smartroPurchaseInfoVo.setP_count("1");
			smartroPurchaseInfoVo.setPayment("C");// C: 신용카드 (Credit Card), P: 휴대폰 결제 (Phone), A: 계좌이체 (Account), V:
													// 이용권(Voucher), O: 기타(Other)
			smartroPurchaseInfoVo.setAprv_gbn("A");// A:승인, C:승인취소
			returnValue = 0;
			returnValue = payMapper.insertSmartroPurchaseInfo(smartroPurchaseInfoVo);
			if (returnValue < 1) {
				throw new SQLException("insertSmartroPurchaseInfo 결과 없음");
			}
		}

		/**
		 * 원스토어 정보 저장
		 */
		if ("0".equals(accessVo.getStore_type()) && !"G-PAY".equals(payCustomVo.getP_type())) {
			/**
			 * 원스토어 구매 정보 보내기
			 */
			try {
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
				// json_params.put("grant_type", OneStore.GRANT_TYPE);변수 초기화가 암됨 ㅠㅠ
				json_params.put("grant_type", "client_credentials");

				if ("A0001".equals(accessVo.getAffiliates_code())) {
					json_params.put("client_id", OneStore.PACKAGE_NAME_SMARTRO);
					json_params.put("client_secret", OneStore.CLIENT_SECRET_SMARTRO);
				}

				Http http = new Http(url, content_type);
				http.setJsonParam(json_params);
				String response = http.submit();

				if (response != null) {
					jp = new JsonParser();
					json_response = jp.parse(response).getAsJsonObject();
					logData.append(
							"[" + LogUtils.getCurrentTime() + "]" + " " + "json_response:" + json_response + "\n");
					if (json_response.get("error") == null) {
						access_token = json_response.get("access_token").getAsString();
						logData.append(
								"[" + LogUtils.getCurrentTime() + "]" + " " + "access_token:" + access_token + "\n");
					} else {
						String code = json_response.get("error").getAsJsonObject().get("code").getAsString();
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "code:" + code + "\n");
					}
				}

				if (!"".equals(access_token)) {

					long currenttime = System.currentTimeMillis();

					/**
					 * send3rdPartyPurchase (외부결제 구매내역 전송)
					 */

					ArrayList<Object> al = new ArrayList<Object>();
					Map<String, String> map = new HashMap<String, String>();
					map.put("developerProductId", storePurchaseInfoVo.getP_code());// 상품코드
					map.put("developerProductName", storePurchaseInfoVo.getP_name());// 상품명
					map.put("developerProductPrice", storePurchaseInfoVo.getP_price());// 가격
					map.put("developerProductQty", "1");// 수량
					al.add(map);

					ArrayList<Object> al_a = new ArrayList<Object>();
					Map<String, String> map_a = new HashMap<String, String>();
					map_a.put("purchaseMethodCd", "TRD_CREDITCARD");// TRD_CREDITCARD 신용카드 TRD_BANKTRANSFER 무통장
					map_a.put("purchasePrice", storePurchaseInfoVo.getP_price());// 결제수단 별 결제금액
					al_a.add(map_a);

					json_params = new JSONObject();
					json_params.put("access_token", access_token);
					json_params.put("adId", "UNKNOWN_ADID");// 단말에서 획득한 ADID 없을경우 UNKNOWN_ADID
					json_params.put("developerOrderId", storePurchaseInfoVo.getO_id());// 구매 ID
					json_params.put("developerProductList", al);
					json_params.put("simOperator", "UNKNOWN_SIM_OPERATOR");// 단말 simOperator 없을경우 UNKNOWN_SIM_OPERATOR
					json_params.put("installerPackageName", "UNKNOWN_INSTALLER");// 패키지 없을경우 UNKNOWN_INSTALLER
					json_params.put("purchaseMethodList", al_a);
					json_params.put("totalPrice", storePurchaseInfoVo.getP_price());// 총가격
					json_params.put("purchaseTime", currenttime);

					url = OneStore.getUrl(OneStore.API.SEND);
					content_type = MediaType.APPLICATION_JSON.toString();

					if ("A0001".equals(accessVo.getAffiliates_code())) {
						url = OneStore.getUrl(OneStore.API.SEND_SMARTRO);
					}

					http = new Http(url, content_type);
					http.setJsonParam(json_params);
					response = http.submit();

					if (response != null) {
						jp = new JsonParser();
						json_response = jp.parse(response).getAsJsonObject();
						logData.append(
								"[" + LogUtils.getCurrentTime() + "]" + " " + "json_response:" + json_response + "\n");
						if (json_response.get("error") == null) {
							String responseCode = String.valueOf(json_response.get("responseCode").getAsInt());
							logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "responseCode:" + responseCode
									+ "\n");
							if ("0".equals(responseCode)) {
								logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "성공" + "\n");
								int resultCnt = 0;
								storePurchaseInfoVo = new StorePurchaseInfoVo();
								storePurchaseInfoVo.setO_id(payCustomVo.getO_id());
								storePurchaseInfoVo.setIs_send("Y");
								logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
										+ "query start updateStorePurchaseInfo StorePurchaseInfoVo storePurchaseInfoVo : "
										+ storePurchaseInfoVo + "\n");
								resultCnt = payMapper.updateStorePurchaseInfo(storePurchaseInfoVo);
								logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
										+ "query end updateStorePurchaseInfo int resultCnt : " + resultCnt + "\n");
							}
						} else {
							String code = json_response.get("error").getAsJsonObject().get("code").getAsString();
							logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "code:" + code + "\n");
						}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * 스마트로 이용권 정보 API 연동
		 */
		if ("A0001".equals(accessVo.getAffiliates_code())) {
			/**
			 * 결제 정보 확인 API
			 */
			try {
				long timeInMillis = System.currentTimeMillis();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date timeInDate = new Date(timeInMillis);
				String payDateTime = sdf.format(timeInDate);
				Request req = new Request(Api.Smartro.PAY_INFO_REQUEST);
				req.addParameter("hpNo", hp_no);
				req.addParameter("idSeq", id_seq);
				req.addParameter("orderId", smartroPurchaseInfoVo.getO_id());
				// req.addParameter("cancelId", smartroPurchaseInfoVo.getO_id());//취소시
				req.addParameter("prodCode", smartroPurchaseInfoVo.getP_code());
				req.addParameter("prodName", smartroPurchaseInfoVo.getP_name());
				req.addParameter("prodPrice", smartroPurchaseInfoVo.getP_price());
				req.addParameter("prodQuantity", "1");
				req.addParameter("payMethod", "C");// C: 신용카드 (Credit Card), P: 휴대폰 결제 (Phone), A: 계좌이체 (Account), V:
													// 이용권(Voucher), O: 기타(Other)
				req.addParameter("aprvGbn", "A");// A:승인, C:승인취소
				req.addParameter("payDateTime", payDateTime);
				req.setParamType(Request.STRING_TYPE);
				NetworkTask networkTask = new NetworkTask(req);
				Response response = networkTask.doInBackground();
				logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "response:" + response + "\n");
				if (response != null && response.getResponse() != null) {
					PayInfoRequestVo payInfoRequestVo = com.jyes.www.network.JsonParser
							.parseTtbPayInfoRequestVo(response.getResponse().toString());
					String result = payInfoRequestVo.getResult();
					String message = payInfoRequestVo.getMessage();
					System.out.println("result:" + result);
					System.out.println("message:" + message);
					if ("SUCCESS".equals(result)) {
						int resultCnt = 0;
						smartroPurchaseInfoVo = new SmartroPurchaseInfoVo();
						smartroPurchaseInfoVo.setO_id(payCustomVo.getO_id());
						smartroPurchaseInfoVo.setIs_send("Y");
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
								+ "query start updateSmartroPurchaseInfo StorePurchaseInfoVo storePurchaseInfoVo : "
								+ storePurchaseInfoVo + "\n");
						resultCnt = payMapper.updateSmartroPurchaseInfo(smartroPurchaseInfoVo);
						logData.append("[" + LogUtils.getCurrentTime() + "]" + " "
								+ "query end updateSmartroPurchaseInfo int resultCnt : " + resultCnt + "\n");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	@Override
	public Object selectUserUsePayCode(PayCustomVo payCustomVo) throws SQLException {
		return payMapper.selectUserUsePayCode(payCustomVo);
	}

	@Override
	public int insertPayBillFail(PayCustomVo payCustomVo) throws SQLException {
		int returnValue = 0;
		returnValue = payMapper.insertPayBillFail(payCustomVo);
		if (returnValue < 1) {
			throw new SQLException("insertPayBillFail 결과 없음");
		}
		payCustomVo.setBatch_yn("N");
		returnValue = payMapper.updatePayBillBatch(payCustomVo);
		if (returnValue < 1) {
			throw new SQLException("updatePayBillBatch 결과 없음");
		}
		return returnValue;
	}

	@Override
	public int insertPayExeLog(PayExeLogVo payExeLogVo) throws SQLException {
		return payMapper.insertPayExeLog(payExeLogVo);
	}

	@Override
	public int insertPayCancelExeLog(PayCancelExeLogVo payCancelExeLogVo) throws SQLException {
		return payMapper.insertPayCancelExeLog(payCancelExeLogVo);
	}

	@Override
	public Object selectPayCancelUserInfo(PayCancelUserInfoVo payCancelUserInfoVo) throws SQLException {
		return payMapper.selectPayCancelUserInfo(payCancelUserInfoVo);
	}

	@Override
	public int selectAccessTestIdCount(Object object) throws SQLException {
		return payMapper.selectAccessTestIdCount(object);
	}

	@Override
	public int insertUserRefund(Object object) throws SQLException {
		int returnValue = 0;
		TbUserRefund tbUserRefund = (TbUserRefund) object;
		TbUserTicketsDateCheck tbUserTicketsDateCheck = null;
		// if("".equals(StringUtil.nvl(tbUserRefund.getSeq()))) {
		returnValue = payMapper.insertUserRefund(object);
		if (returnValue < 1) {
			throw new SQLException("insertUserRefund 결과 없음");
		}
		TbUserTicketsDateCheck tbUserTicketsDateCheck_request = new TbUserTicketsDateCheck();
		tbUserTicketsDateCheck_request.setPay_success_seq(tbUserRefund.getPay_success_seq());
		tbUserTicketsDateCheck_request.setPay_bill_seq(tbUserRefund.getPay_bill_seq());
		tbUserTicketsDateCheck = (TbUserTicketsDateCheck) payMapper
				.getUserTicketsDateCheck(tbUserTicketsDateCheck_request);
		if (tbUserTicketsDateCheck != null) {
			// 이용권
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 현재시간
			try {
				Date date = format.parse(tbUserTicketsDateCheck.getDate());
				Date start_date = format.parse(tbUserTicketsDateCheck.getStart_date());
				Date expiration_date = format.parse(tbUserTicketsDateCheck.getExpiration_date());
				int compare_start = date.compareTo(start_date);
				int compare_expiration = date.compareTo(expiration_date);
				/**
				 * 이용 시작 시간 체크
				 */
				if (compare_start < 0) {
					// 현재 시간이 작다
					if (Integer.parseInt(StringUtil.nvl(tbUserTicketsDateCheck.getUse_day(), "0")) > 0) {
						TbUserTicketsDate tbUserTicketsDate = new TbUserTicketsDate();
						tbUserTicketsDate.setAccess_seq(tbUserRefund.getAccess_seq());
						tbUserTicketsDate.setStart_date(tbUserTicketsDateCheck.getStart_date());
						tbUserTicketsDate.setUse_day(tbUserTicketsDateCheck.getUse_day());
						/**
						 * 이용권 시간 업데이트
						 */
						returnValue = payMapper.updateUserTicketsDate(tbUserTicketsDate);
						if (returnValue < 1) {
							// throw new SQLException("updateUserTicketsDate 결과 없음");//제일 마지막 이용권일 경우 없을 수
							// 있음
							returnValue = 1;
						}
					} else {
						// 현재 시간이 크거나 같다
					}
				} else {
					// 현재 시간이 크거나 같다
					/**
					 * 이용 만료 시간 체크
					 */
					if (compare_expiration < 0) {
						// 현재 시간이 작다
						Calendar c_date = Calendar.getInstance();
						c_date.setTime(date);
						Calendar c_expiration_date = Calendar.getInstance();
						c_expiration_date.setTime(expiration_date);
						long diffSec = (c_expiration_date.getTimeInMillis() - c_date.getTimeInMillis()) / 1000;

						TbUserTicketsDate tbUserTicketsDate = new TbUserTicketsDate();
						tbUserTicketsDate.setAccess_seq(tbUserRefund.getAccess_seq());
						tbUserTicketsDate.setStart_date(tbUserTicketsDateCheck.getStart_date());
						tbUserTicketsDate.setTime(diffSec + "");
						/**
						 * 이용권 시간 업데이트
						 */
						returnValue = payMapper.updateUserTicketsDate(tbUserTicketsDate);
						if (returnValue < 1) {
							// throw new SQLException("updateUserTicketsDate 결과 없음");//제일 마지막 이용권일 경우 없을 수
							// 있음
							returnValue = 1;
						}

						tbUserRefund.setTime(diffSec + "");
						/**
						 * 이용권 종료 시간 추가
						 */
						returnValue = payMapper.updateUserTicketsEndDate(object);
						if (returnValue < 1) {
							throw new SQLException("updateUserTicketsEndDate 결과 없음");
						}
					} else {
						// 현재 시간이 크거나 같다
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new SQLException("이용권 업데이트 에러");
			}
			/**
			 * 유선추가
			 */
			if (tbUserTicketsDateCheck.getStart_date_wl() != null) {
				try {
					Date date = format.parse(tbUserTicketsDateCheck.getDate());
					Date start_date_wl = format.parse(tbUserTicketsDateCheck.getStart_date_wl());
					Date expiration_date_wl = format.parse(tbUserTicketsDateCheck.getExpiration_date_wl());
					int compare_start = date.compareTo(start_date_wl);
					int compare_expiration = date.compareTo(expiration_date_wl);
					/**
					 * 이용 시작 시간 체크
					 */
					if (compare_start < 0) {
						// 현재 시간이 작다
						if (Integer.parseInt(StringUtil.nvl(tbUserTicketsDateCheck.getUse_day(), "0")) > 0) {
							TbUserTicketsDate tbUserTicketsDate = new TbUserTicketsDate();
							tbUserTicketsDate.setAccess_seq(tbUserRefund.getAccess_seq());
							tbUserTicketsDate.setStart_date(tbUserTicketsDateCheck.getStart_date_wl());
							tbUserTicketsDate.setUse_day(tbUserTicketsDateCheck.getUse_day());
							tbUserTicketsDate.setIs_pay_wl("Y");
							/**
							 * 이용권 시간 업데이트
							 */
							returnValue = payMapper.updateUserTicketsDate(tbUserTicketsDate);
							if (returnValue < 1) {
								// throw new SQLException("updateUserTicketsDate 결과 없음");//제일 마지막 이용권일 경우 없을 수
								// 있음
								returnValue = 1;
							}
						} else {
							// 현재 시간이 크거나 같다
						}
					} else {
						// 현재 시간이 크거나 같다
						/**
						 * 이용 만료 시간 체크
						 */
						if (compare_expiration < 0) {
							// 현재 시간이 작다
							Calendar c_date = Calendar.getInstance();
							c_date.setTime(date);
							Calendar c_expiration_date = Calendar.getInstance();
							c_expiration_date.setTime(expiration_date_wl);
							long diffSec = (c_expiration_date.getTimeInMillis() - c_date.getTimeInMillis()) / 1000;

							TbUserTicketsDate tbUserTicketsDate = new TbUserTicketsDate();
							tbUserTicketsDate.setAccess_seq(tbUserRefund.getAccess_seq());
							tbUserTicketsDate.setStart_date(tbUserTicketsDateCheck.getStart_date_wl());
							tbUserTicketsDate.setTime(diffSec + "");
							tbUserTicketsDate.setIs_pay_wl("Y");
							/**
							 * 이용권 시간 업데이트
							 */
							returnValue = payMapper.updateUserTicketsDate(tbUserTicketsDate);
							if (returnValue < 1) {
								// throw new SQLException("updateUserTicketsDate 결과 없음");//제일 마지막 이용권일 경우 없을 수
								// 있음
								returnValue = 1;
							}

							tbUserRefund.setTime(diffSec + "");
							tbUserRefund.setIs_pay_wl("Y");
							/**
							 * 이용권 종료 시간 추가
							 */
							returnValue = payMapper.updateUserTicketsEndDate(object);
							if (returnValue < 1) {
								throw new SQLException("updateUserTicketsEndDate 결과 없음");
							}
						} else {
							// 현재 시간이 크거나 같다
						}
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new SQLException("이용권 업데이트 에러");
				}
			}
		}
		// TbPayBillVo tbPayBillVo = null;
		// try {
		// tbPayBillVo = (TbPayBillVo)payMapper.selectPayBillBatchDate(object);
		// } catch(Exception e) {
		//// e.printStackTrace();
		//// throw new SQLException("selectPayBillBatchDate 결과 없음");
		// }
		// if(tbPayBillVo!=null) {
		// returnValue = payMapper.updatePayBillBatchDate(tbPayBillVo);
		// if(returnValue < 1) {
		// throw new SQLException("updatePayBillBatchDate 결과 없음");
		// }
		// }
		// } else {
		// returnValue = v1_2userMapper.updateUserRefund(object);
		// if(returnValue < 1) {
		// throw new SQLException("updateUserRefund 결과 없음");
		// }
		// }
		if (returnValue < 1) {
			throw new SQLException("insertUserRefund end 결과 없음");
		}
		return returnValue;
	}

	@Override
	public ArrayList<PayCustomVo> selectGoogleBillPayCheckList() throws SQLException {
		// TODO Auto-generated method stub
		return payMapper.selectGoogleBillPayCheckList();
	}

	@Override
	public int updateGoogleBillPayBillBatch(PayCustomVo payCustomVo) throws SQLException {
		// TODO Auto-generated method stub
		return payMapper.updateGoogleBillPayBillBatch(payCustomVo);
	}

}