package com.jyes.www.service.ttb.board;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jyes.www.vo.ttb.BoardCategoryVo;
import com.jyes.www.vo.ttb.BoardCustomQnaVo;
import com.jyes.www.vo.ttb.BoardFaqVo;

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
