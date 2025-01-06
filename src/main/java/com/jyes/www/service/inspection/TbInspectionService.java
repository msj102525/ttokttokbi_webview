package com.jyes.www.service.inspection;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jyes.www.mapper.inspection.TbInspectionMapper;
import com.jyes.www.service.inspection.impl.ITbInspectionService;

@Service(value="tbInspectionService")
public class TbInspectionService implements ITbInspectionService {
	
	static final Logger logger = LoggerFactory.getLogger(TbInspectionService.class);

	@Resource(name="tbInspectionMapper")
	private TbInspectionMapper tbInspectionMapper;

	@Override
	public Object selectCheckServer() throws SQLException {
		return tbInspectionMapper.selectCheckServer();
	}
	
}