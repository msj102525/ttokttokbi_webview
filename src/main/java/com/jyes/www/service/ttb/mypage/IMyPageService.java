package com.jyes.www.service.ttb.mypage;

import java.sql.SQLException;

import com.jyes.www.vo.ttb.UserInfoInputVo;
import com.jyes.www.vo.ttb.UserInfoOutputVo;

/*
 * Copyright jyes.co.kr.
 * All rights reserved
 * This software is the confidential and proprietary information
 * of jyes.co.kr. ("Confidential Information")
 */
public interface IMyPageService {
	public UserInfoOutputVo getMyPageInfo(UserInfoInputVo boardCustomQnaVo) throws SQLException;

}
