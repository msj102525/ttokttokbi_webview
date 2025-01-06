package com.jyes.www.service;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jyes.www.mapper.BoardMapper;
import com.jyes.www.service.impl.IBoardService;
import com.jyes.www.vo.BoardCategoryVo;
import com.jyes.www.vo.BoardCustomQnaVo;
import com.jyes.www.vo.BoardFaqVo;

@Service(value="boardService")
public class BoardService implements IBoardService {
	
	static final Logger logger = LoggerFactory.getLogger(BoardService.class);

	@Resource(name="boardMapper")
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

}
