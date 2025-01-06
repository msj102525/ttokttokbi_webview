package com.jyes.www.vo;

import java.util.Date;

public class PayTypeVo extends AbstractVo {
	private String seq;
	private String code;
	private String name;
	private String price;
	private Date date;
	private String use_day;
	private String is_pay_wl;
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
