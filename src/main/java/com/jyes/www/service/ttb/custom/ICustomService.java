package com.jyes.www.service.ttb.custom;

import java.sql.SQLException;

import org.springframework.transaction.annotation.Transactional;

public interface ICustomService {
	@Transactional(rollbackFor = { SQLException.class })
	public int sendMailForm() throws SQLException;
}
