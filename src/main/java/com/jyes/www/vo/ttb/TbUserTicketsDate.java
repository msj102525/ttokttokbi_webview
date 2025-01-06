package com.jyes.www.vo.ttb;

public class TbUserTicketsDate extends AbstractVo {
	private String access_seq;
	private String start_date;
	private String time;
	private String use_day;
	private String is_pay_wl;

	public String getAccess_seq() {
		return access_seq;
	}

	public void setAccess_seq(String access_seq) {
		this.access_seq = access_seq;
	}

	public String getStart_date() {
		return start_date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getUse_day() {
		return use_day;
	}

	public void setUse_day(String use_day) {
		this.use_day = use_day;
	}

	public String getIs_pay_wl() {
		return is_pay_wl;
	}

	public void setIs_pay_wl(String is_pay_wl) {
		this.is_pay_wl = is_pay_wl;
	}
}