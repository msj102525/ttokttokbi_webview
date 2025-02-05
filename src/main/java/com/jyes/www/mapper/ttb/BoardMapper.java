package com.jyes.www.mapper.ttb;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.jyes.www.vo.ttb.BoardCategoryVo;
import com.jyes.www.vo.ttb.BoardCustomQnaVo;
import com.jyes.www.vo.ttb.BoardFaqVo;

@Repository(value = "ttbBoardMapper")
public interface BoardMapper {
	public ArrayList<BoardCategoryVo> getBoardFaqCategoryList() throws SQLException;

	public ArrayList<BoardFaqVo> getBoardFaqCategoryContentsList(String code) throws SQLException;

	public int insertBoardQna(BoardCustomQnaVo boardCustomQnaVo) throws SQLException;

	public ArrayList<BoardFaqVo> getTTBFaqCategoryContentsList() throws SQLException;
}