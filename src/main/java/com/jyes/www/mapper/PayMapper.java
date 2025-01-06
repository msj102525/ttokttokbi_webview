package com.jyes.www.mapper;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.jyes.www.vo.AccessVo;
import com.jyes.www.vo.CreateOidVo;
import com.jyes.www.vo.PayCancelExeLogVo;
import com.jyes.www.vo.PayCancelUserInfoVo;
import com.jyes.www.vo.PayCustomVo;
import com.jyes.www.vo.PayExeLogVo;
import com.jyes.www.vo.PayNameCompanyVo;
import com.jyes.www.vo.PayTypeVo;
import com.jyes.www.vo.SmartroCancelInfoVo;
import com.jyes.www.vo.SmartroPurchaseInfoVo;
import com.jyes.www.vo.StoreCancelInfoVo;
import com.jyes.www.vo.StorePurchaseInfoVo;

@Repository(value = "payMapper")
public interface PayMapper {
	public Object selectUserInfo(AccessVo accessVo) throws SQLException;
	public String selectPay(PayCustomVo payCustomVo) throws SQLException;
	public int insertPay(PayCustomVo payCustomVo) throws SQLException;
	public int insertPaySuccess(PayCustomVo payCustomVo) throws SQLException;
	public Object selectPayTypeIsTicketUseDayAndIsMakeCount(PayCustomVo payCustomVo) throws SQLException;
	public int insertUserTicketsInfo(PayCustomVo payCustomVo) throws SQLException;
	public int insertMakeProductionRequest(PayCustomVo payCustomVo) throws SQLException;
	public PayTypeVo selectPayTypeWhereCode(PayTypeVo payTypeVo) throws SQLException;
	public int insertPayBill(PayCustomVo payCustomVo) throws SQLException;
	public PayNameCompanyVo selectPayNameCompany(PayNameCompanyVo payNameCompanyVo) throws SQLException;
	public ArrayList<PayCustomVo> selectPayBillBatchList() throws SQLException;
	public int insertPayBillBatch(PayCustomVo payCustomVo) throws SQLException;
	public int updatePayBillBatchYn(String seq) throws SQLException;
	public Object selectUserUsePayCode(PayCustomVo payCustomVo) throws SQLException;
	public int insertPayBillFail(PayCustomVo payCustomVo) throws SQLException;
	public int updatePayBillBatch(PayCustomVo payCustomVo) throws SQLException;
	public Object selectAccess(AccessVo accessVo) throws SQLException;
	public long selectOid(CreateOidVo createOidVo) throws SQLException;
	public int insertCreateOid(CreateOidVo createOidVo) throws SQLException;
	public int insertStorePurchaseInfo(StorePurchaseInfoVo storePurchaseInfoVo) throws SQLException;
	public int updateStorePurchaseInfo(StorePurchaseInfoVo storePurchaseInfoVo) throws SQLException;
	public int insertStoreCancelInfo(StoreCancelInfoVo storeCancelInfoVo) throws SQLException;
	public int updateStoreCancelInfo(StoreCancelInfoVo storeCancelInfoVo) throws SQLException;
	public int insertSmartroPurchaseInfo(SmartroPurchaseInfoVo smartroPurchaseInfoVo) throws SQLException;
	public int updateSmartroPurchaseInfo(SmartroPurchaseInfoVo smartroPurchaseInfoVo) throws SQLException;
	public int insertSmartroCancelInfo(SmartroCancelInfoVo smartroCancelInfoVo) throws SQLException;
	public int updateSmartroCancelInfo(SmartroCancelInfoVo smartroCancelInfoVo) throws SQLException;
	public int insertPayExeLog(PayExeLogVo payExeLogVo) throws SQLException;
	public int insertPayCancelExeLog(PayCancelExeLogVo payCancelExeLogVo) throws SQLException;
	public Object selectPayCancelUserInfo(PayCancelUserInfoVo payCancelUserInfoVo) throws SQLException;

	public int selectAccessTestIdCount(Object object) throws SQLException;
	public int insertUserRefund(Object object) throws SQLException;
	public Object getUserTicketsDateCheck(Object object) throws SQLException;
	public int updateUserTicketsDate(Object object) throws SQLException;
	public int updateUserTicketsEndDate(Object object) throws SQLException;
	public Object selectPayBillBatchDate(Object object) throws SQLException;
	public int updatePayBillBatchDate(Object object) throws SQLException;
	public ArrayList<PayCustomVo> selectGoogleBillPayCheckList() throws SQLException;
	public int updateGoogleBillPayBillBatch(PayCustomVo payCustomVo) throws SQLException;
	
	public Object selectStatsDayCuponUserCheck(Object object) throws SQLException;
	public int insertStatsDayCupon(PayCustomVo payCustomVo) throws SQLException;
}