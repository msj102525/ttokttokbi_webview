package com.jyes.www.vo.ttb;

public class UserInfoOutputVo extends AbstractVo {
	private String id;
	private String user_name;
	private String company;
	private String start_date;
	private String expiration_date;
	private String remaining_days;
	private String pay_type_name;
	private String affiliates_code;
	private String email;
	private String useragent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(String expiration_date) {
		this.expiration_date = expiration_date;
	}

	public String getRemaining_days() {
		return remaining_days;
	}

	public void setRemaining_days(String remaining_days) {
		this.remaining_days = remaining_days;
	}

	public String getPay_type_name() {
		return pay_type_name;
	}

	public void setPay_type_name(String pay_type_name) {
		this.pay_type_name = pay_type_name;
	}

	public String getAffiliates_code() {
		return affiliates_code;
	}

	public void setAffiliates_code(String affiliates_code) {
		this.affiliates_code = affiliates_code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUseragent() {
		return useragent;
	}

	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}

}
