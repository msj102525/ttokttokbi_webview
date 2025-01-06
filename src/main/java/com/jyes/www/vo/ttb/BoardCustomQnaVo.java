package com.jyes.www.vo.ttb;

public class BoardCustomQnaVo extends AbstractVo {
	private String email;
	private String text;
	private String id;
	private String affiliates_code;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAffiliates_code() {
		return affiliates_code;
	}

	public void setAffiliates_code(String affiliates_code) {
		this.affiliates_code = affiliates_code;
	}
}
