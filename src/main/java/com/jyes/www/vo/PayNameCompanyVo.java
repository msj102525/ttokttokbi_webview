package com.jyes.www.vo;

public class PayNameCompanyVo extends AbstractVo {
	private String id;
	private String affiliates_code;
	private String name;
	private String company;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
}
