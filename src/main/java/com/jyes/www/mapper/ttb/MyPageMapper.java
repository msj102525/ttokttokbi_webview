package com.jyes.www.mapper.ttb;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.jyes.www.vo.ttb.UserInfoInputVo;
import com.jyes.www.vo.ttb.UserInfoOutputVo;

@Repository(value = "ttbMyPageMapper")
public interface MyPageMapper {
	public UserInfoOutputVo getMyPageInfo(UserInfoInputVo boardCustomQnaVo) throws SQLException;
}