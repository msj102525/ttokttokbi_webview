package com.jyes.www.vo;

public class MailFormSendListVo extends AbstractVo {
	private String seq;
	private String name;
	private String email;
	private String is_dev;
	private String date;
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIs_dev() {
		return is_dev;
	}
	public void setIs_dev(String is_dev) {
		this.is_dev = is_dev;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
