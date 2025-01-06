package com.jyes.www.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

import com.jyes.www.vo.AccessVo;
import com.jyes.www.vo.CreateOidVo;
import com.jyes.www.vo.PayCancelExeLogVo;
import com.jyes.www.vo.PayCancelUserInfoVo;
import com.jyes.www.vo.PayCustomVo;
import com.jyes.www.vo.PayExeLogVo;
import com.jyes.www.vo.PayNameCompanyVo;
import com.jyes.www.vo.PayTypeVo;

/*
 * Copyright jyes.co.kr.
 * All rights reserved
 * This software is the confidential and proprietary information
 * of jyes.co.kr. ("Confidential Information")
 */
public interface IPayService {
	public Object selectUserInfo(AccessVo accessVo) throws SQLException;
	@Transactional(rollbackFor={SQLException.class})
	public int insertPayInfo(PayCustomVo payCustomVo, StringBuffer logData) throws SQLException;
	public PayTypeVo selectPayTypeWhereCode(PayTypeVo payTypeVo) throws SQLException;
	public PayNameCompanyVo selectPayNameCompany(PayNameCompanyVo payNameCompanyVo) throws SQLException;
	public ArrayList<PayCustomVo> selectPayBillBatchList() throws SQLException;
	@Transactional(rollbackFor={SQLException.class})
	public int insertPayBillBatch(PayCustomVo payCustomVo, StringBuffer logData) throws SQLException;
	public Object selectUserUsePayCode(PayCustomVo payCustomVo) throws SQLException;
	@Transactional(rollbackFor={SQLException.class})
	public int insertPayBillFail(PayCustomVo payCustomVo) throws SQLException;
	public long selectOid(CreateOidVo createOidVo) throws SQLException;
	public int insertPayExeLog(PayExeLogVo payExeLogVo) throws SQLException;
	public int insertPayCancelExeLog(PayCancelExeLogVo payCancelExeLogVo) throws SQLException;
	public Object selectPayCancelUserInfo(PayCancelUserInfoVo payCancelUserInfoVo) throws SQLException;

	public int selectAccessTestIdCount(Object object) throws SQLException;
	@Transactional(rollbackFor={SQLException.class})
	public int insertUserRefund(Object object) throws SQLException;
	public ArrayList<PayCustomVo> selectGoogleBillPayCheckList() throws SQLException;
	public int updateGoogleBillPayBillBatch(PayCustomVo payCustomVo) throws SQLException;
}