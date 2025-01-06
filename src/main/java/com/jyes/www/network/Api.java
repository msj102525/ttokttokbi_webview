package com.jyes.www.network;

public class Api {

	public static final String GET = "GET";
	public static final String POST = "POST";

	private String url;
	private String kind;
	private String method;

	public Api(String url, String kind, String method) {
		this.setUrl(url);
		this.setKind(kind);
		this.setMethod(method);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return new StringBuilder()
		.append("Api url=").append(url)
		.append(", kind=").append(kind)
		.append(", method=").append(method).toString();
	}
	
	/**
	 * Aligo Api
	 */
	public static class Aligo {
		//API 종류
		public static final String KIND = "aligo";
		/** 발송 */
		public static final Api SEND = new Api("/?userid={userid}&key={key}&sender={sender}&receiver={receiver}&destination={destination}&msg={msg}&rdate={rdate}&rtime={rtime}&image={image}&testmode_yn={testmode_yn}", KIND, POST);
		
		public static final Api SEND_MASS = new Api("/send_mass/?userid={userid}&key={key}&sender={sender}&rec_1={rec_1}&rec_2={rec_2}&destination={destination}&msg_1={msg_1}&msg_2={msg_2}&rdate={rdate}&rtime={rtime}&image={image}&testmode_yn={testmode_yn}", KIND, POST);
		/** 잔여건수 */
		public static final Api REMAIN = new Api("/remain/?user_id={user_id}&key={key}", KIND, POST);
		/** 최근발송내역 */
		public static final Api LIST = new Api("/list/?user_id={user_id}&key={key}&page={page}&page_size={page_size}", KIND, POST);
		/** 문자전송결과 */
		public static final Api SMS_LIST = new Api("/sms_list/?user_id={user_id}&key={key}&mid={mid}&page={page}&page_size={page_size}", KIND, POST);
	}
	
	/**
	 * 080 Api
	 */
	public static class Unsubscribe {
		/**
		 * API 종류
		 */
		public static final String KIND = "unsubscribe";
		/**
		 * JYES 080 수신 거부 목록
		 */
		public static final Api GET_UNSUBSCRIBE = new Api("/api/version/1/get_unsubscribe?unsubscribe_number={unsubscribe_number}", KIND, POST);
		/**
		 * JYES 080 수신 거부 목록(v1.2)
		 */
		public static final Api GET_UNSUBSCRIBE_1_2 = new Api("/api/version/1_2/get_unsubscribe?unsubscribe_number={unsubscribe_number}&number={number}", KIND, POST);
	}
	
	/**
	 * 스마트로 Api
	 */
	public static class Smartro {
		/**
		 * API 종류
		 */
		public static final String KIND = "smartro";
		/**
		 * 비즐 회원 여부 확인
		 */
		public static final Api CHECK_MEMBER = new Api("/svc/soho/checkMember.do?hpNo={hpNo}", KIND, POST);
		/**
		 * 결제 정보 확인
		 */
		public static final Api PAY_INFO_REQUEST = new Api("/svc/soho/payInfoRequest.do?hpNo={hpNo}&idSeq={idSeq}&orderId={orderId}&prodCode={prodCode}&prodName={prodName}&prodPrice={prodPrice}&prodQuantity={prodQuantity}&payMethod={payMethod}&payDate={payDate}", KIND, POST);
	}
	
}
