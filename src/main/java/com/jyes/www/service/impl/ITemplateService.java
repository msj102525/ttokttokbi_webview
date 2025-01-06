package com.jyes.www.service.impl;

import java.sql.SQLException;

import com.jyes.www.vo.TemplateVo;

/*
 * Copyright jyes.co.kr.
 * All rights reserved
 * This software is the confidential and proprietary information
 * of jyes.co.kr. ("Confidential Information")
 */
public interface ITemplateService {
	public TemplateVo selectContents(TemplateVo vo) throws SQLException;
	public void updateContents(TemplateVo vo);
	public void insertContents(TemplateVo vo);
}

//@Transactional(rollbackFor={SQLException.class}) transaction, 필요한 함수에 annotaion 처리