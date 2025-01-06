package com.jyes.www.mapper;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository(value = "customMapper")
public interface CustomMapper {
	public Object selectDayDate(Object object) throws SQLException;
	public ArrayList<Object> selectMailFromDataList(Object object) throws SQLException;
	public int selectMailFormLogNowDataCount() throws SQLException;
	public int insertMailFormLog(Object object) throws SQLException;
	public ArrayList<Object> selectMailFormLogDataWhereRegDate(Object object) throws SQLException;
	public ArrayList<Object> selectMailFormSendListWhereIsDevList(Object object) throws SQLException;
	public ArrayList<Object> selectMailFromToday30DaysBeforeData() throws SQLException;
}
