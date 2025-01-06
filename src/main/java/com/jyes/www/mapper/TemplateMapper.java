package com.jyes.www.mapper;
import org.springframework.stereotype.Repository;

import com.jyes.www.vo.TemplateVo;

@Repository(value = "templateMapper")
public interface TemplateMapper {
	public TemplateVo selectContents(TemplateVo vo);
	public void updateContents(TemplateVo vo);
	public void insertContents(TemplateVo vo);
}