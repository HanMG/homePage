package com.java.board.command;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.board.dao.BoardDao;
import com.java.board.dto.BoardDto;
import com.java.command.CommandAction;

public class UpdateOkAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
		int groupNumber = Integer.parseInt(request.getParameter("groupNumber"));
		int sequenceNumber = Integer.parseInt(request.getParameter("sequenceNumber"));
		int sequenceLevel = Integer.parseInt(request.getParameter("sequenceLevel"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		
		
		BoardDto boardDto = new BoardDto();		
		boardDto.setSubject(request.getParameter("subject"));
		boardDto.setEmail(request.getParameter("email"));
		boardDto.setContent(request.getParameter("content"));
		boardDto.setPassword(request.getParameter("password"));
		boardDto.setWriteDate(new Date());
		
		logger.info(logMsg+boardDto);
		
		int check = BoardDao.getInstance().boardUpdateOk(boardNumber, boardDto);
		
		logger.info(logMsg+check);
		
		if(check != 0) {
			request.setAttribute("check", check);
			request.setAttribute("boardNumber",boardNumber);
			request.setAttribute("groupNumber",groupNumber);
			request.setAttribute("sequenceNumber",sequenceNumber);
			request.setAttribute("sequenceLevel",sequenceLevel);
			request.setAttribute("pageNumber", pageNumber);
		}		
		
		return "/WEB-INF/views/board/updateOk.jsp";
	}

}
