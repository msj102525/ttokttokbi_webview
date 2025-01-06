package com.jyes.www.service;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jyes.www.mapper.TemplateMapper;
import com.jyes.www.service.impl.ITemplateService;
import com.jyes.www.vo.TemplateVo;

@Service(value="templateService")
public class TemplateService implements ITemplateService {
	
	static final Logger logger = LoggerFactory.getLogger(TemplateService.class);

	@Resource(name="templateMapper")
	private TemplateMapper templateMapper;

	@Override
	public TemplateVo selectContents(TemplateVo vo) throws SQLException {
		return templateMapper.selectContents(vo);
	}

	@Override
	public void updateContents(TemplateVo vo) {
		templateMapper.updateContents(vo);
	}

	@Override
	public void insertContents(TemplateVo vo) {
		templateMapper.insertContents(vo);
	}
}
