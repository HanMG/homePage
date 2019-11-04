package com.java.fileboard.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.board.dao.BoardDao;
import com.java.command.CommandAction;

public class DeleteAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
		
		int check = BoardDao.getInstance().delBoard(boardNumber);
		logger.info(logMsg+check);
		request.setAttribute("check", check);
		request.setAttribute("pageNumber", pageNumber);	
	
		return "/WEB-INF/views/fileBoard/delete.jsp";
	}

}
