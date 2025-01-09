package com.jyes.www.service.ttb.mypage;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jyes.www.mapper.ttb.MyPageMapper;
import com.jyes.www.vo.ttb.UserInfoInputVo;
import com.jyes.www.vo.ttb.UserInfoOutputVo;

@Service(value = "ttbMyPageService")
public class MyPageService implements IMyPageService {

	static final Logger logger = LoggerFactory.getLogger(MyPageService.class);

	@Resource(name = "ttbMyPageMapper")
	private MyPageMapper myPageMapper;

	@Override
	public UserInfoOutputVo getMyPageInfo(UserInfoInputVo userInfoInputVo) throws SQLException {
		return myPageMapper.getMyPageInfo(userInfoInputVo);
	}

}
