package com.jyes.www.vo;

public class BoardFaqVo extends AbstractVo{
	private String seq;
	private String subject;
	private String text;
	private String is_open;
	private String view_count;
	private String date;
	private String faq_board_category_code;
	private String admin_user_id;
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIs_open() {
		return is_open;
	}
	public void setIs_open(String is_open) {
		this.is_open = is_open;
	}
	public String getView_count() {
		return view_count;
	}
	public void setView_count(String view_count) {
		this.view_count = view_count;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFaq_board_category_code() {
		return faq_board_category_code;
	}
	public void setFaq_board_category_code(String faq_board_category_code) {
		this.faq_board_category_code = faq_board_category_code;
	}
	public String getAdmin_user_id() {
		return admin_user_id;
	}
	public void setAdmin_user_id(String admin_user_id) {
		this.admin_user_id = admin_user_id;
	}
}
