package com.jyes.www.mapper.inspection;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository(value = "tbInspectionMapper")
public interface TbInspectionMapper {
	public Object selectCheckServer() throws SQLException;
}