package com.java.board.command;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.board.dao.BoardDao;
import com.java.board.dto.BoardDto;
import com.java.command.CommandAction;

/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 10. 31.
 * @Detail : 게시판 글 작성 관련 
 */

public class WriteOkAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		BoardDto boardDto = new BoardDto();
		boardDto.setBoardNumber(Integer.parseInt(request.getParameter("boardNumber")));
		boardDto.setGroupNumber(Integer.parseInt(request.getParameter("groupNumber")));
		boardDto.setSequenceLevel(Integer.parseInt(request.getParameter("sequenceLevel")));
		boardDto.setSequenceNumber(Integer.parseInt(request.getParameter("sequenceNumber")));
		
		boardDto.setWriter(request.getParameter("writer"));
		boardDto.setSubject(request.getParameter("subject"));
		boardDto.setEmail(request.getParameter("email"));
		boardDto.setContent(request.getParameter("content"));
		boardDto.setPassword(request.getParameter("password"));
		
		boardDto.setWriteDate(new Date());
		boardDto.setReadCount(0);
		
		logger.info(logMsg+boardDto.toString());
		
		int check = BoardDao.getInstance().insert(boardDto);
		logger.info(logMsg+check);
		
		request.setAttribute("check", check);		
		
		return "/WEB-INF/views/board/writeOk.jsp";
	}

}
