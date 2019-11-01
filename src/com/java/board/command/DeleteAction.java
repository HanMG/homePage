package com.java.board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.CommandAction;
import com.java.guest.dao.GuestDao;

public class DeleteAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
		
		int check = GuestDao.getInstance().delBoard(boardNumber);
		logger.info(logMsg+check);
		request.setAttribute("check", check);
		request.setAttribute("pageNumber", pageNumber);	
	
		return "/WEB-INF/views/board/delete.jsp";
	}

}
