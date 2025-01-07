package com.jyes.www.mapper.ttb;

import org.springframework.stereotype.Repository;

import com.jyes.www.vo.ttb.TemplateVo;

@Repository(value = "ttbTemplateMapper")
public interface TemplateMapper {
	public TemplateVo selectContents(TemplateVo vo);

	public void updateContents(TemplateVo vo);

	public void insertContents(TemplateVo vo);
}