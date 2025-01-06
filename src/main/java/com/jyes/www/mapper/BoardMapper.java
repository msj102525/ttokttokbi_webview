package com.jyes.www.mapper;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.jyes.www.vo.BoardCategoryVo;
import com.jyes.www.vo.BoardCustomQnaVo;
import com.jyes.www.vo.BoardFaqVo;

@Repository(value = "boardMapper")
public interface BoardMapper {
	public ArrayList<BoardCategoryVo> getBoardFaqCategoryList() throws SQLException;
	public ArrayList<BoardFaqVo> getBoardFaqCategoryContentsList(String code) throws SQLException;
	public int insertBoardQna(BoardCustomQnaVo boardCustomQnaVo) throws SQLException;
}