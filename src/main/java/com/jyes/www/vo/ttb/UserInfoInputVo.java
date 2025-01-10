package com.jyes.www.vo.ttb;

public class UserInfoInputVo extends AbstractVo {
	private String id;
	private String approach_path;
	private String affiliates_code;
	private String name;
	private String company;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApproach_path() {
		return approach_path;
	}

	public void setApproach_path(String approach_path) {
		this.approach_path = approach_path;
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
