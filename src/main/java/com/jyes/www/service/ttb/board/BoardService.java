package com.jyes.www.service.ttb.board;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jyes.www.mapper.ttb.BoardMapper;
import com.jyes.www.vo.ttb.BoardCategoryVo;
import com.jyes.www.vo.ttb.BoardCustomQnaVo;
import com.jyes.www.vo.ttb.BoardFaqVo;

@Service(value = "ttbBoardService")
public class BoardService implements IBoardService {

	static final Logger logger = LoggerFactory.getLogger(BoardService.class);

	@Resource(name = "ttbBoardMapper")
	private BoardMapper boardMapper;

	@Override
	public ArrayList<BoardCategoryVo> getBoardFaqCategoryList() throws SQLException {
		return boardMapper.getBoardFaqCategoryList();
	}

	@Override
	public ArrayList<BoardFaqVo> getBoardFaqCategoryContentsList(String code) throws SQLException {
		return boardMapper.getBoardFaqCategoryContentsList(code);
	}

	@Override
	public int insertBoardQna(BoardCustomQnaVo boardCustomQnaVo) throws SQLException {
		return boardMapper.insertBoardQna(boardCustomQnaVo);
	}

	@Override
	public ArrayList<BoardFaqVo> getTTBFaqCategoryContentsList() throws SQLException {
		return boardMapper.getTTBFaqCategoryContentsList();
	}

}
