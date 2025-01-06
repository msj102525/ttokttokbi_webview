package com.jyes.www.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jyes.www.vo.BoardCategoryVo;
import com.jyes.www.vo.BoardCustomQnaVo;
import com.jyes.www.vo.BoardFaqVo;

/*
 * Copyright jyes.co.kr.
 * All rights reserved
 * This software is the confidential and proprietary information
 * of jyes.co.kr. ("Confidential Information")
 */
public interface IBoardService {
	public ArrayList<BoardCategoryVo> getBoardFaqCategoryList() throws SQLException;
	public ArrayList<BoardFaqVo> getBoardFaqCategoryContentsList(String code) throws SQLException;
	public int insertBoardQna(BoardCustomQnaVo boardCustomQnaVo) throws SQLException;
}
