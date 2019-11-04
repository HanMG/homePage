package com.java.fileboard.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.fileboard.dao.BoardDao;
import com.java.fileboard.dto.BoardDto;
import com.java.command.CommandAction;

public class UpdateAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
					
		int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
		int groupNumber = Integer.parseInt(request.getParameter("groupNumber"));
		int sequenceNumber = Integer.parseInt(request.getParameter("sequenceNumber"));
		int sequenceLevel = Integer.parseInt(request.getParameter("sequenceLevel"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		
		BoardDto boardDto = BoardDao.getInstance().boardUpdate(boardNumber);
		
		
		
		if(boardDto != null) {
			request.setAttribute("boardDto", boardDto);
			request.setAttribute("boardNumber",boardNumber);
			request.setAttribute("groupNumber",groupNumber);
			request.setAttribute("sequenceNumber",sequenceNumber);
			request.setAttribute("sequenceLevel",sequenceLevel);
			request.setAttribute("pageNumber", pageNumber);
		}
		
		
		return "/WEB-INF/views/fileBoard/update.jsp";
	}

}
