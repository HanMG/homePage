package com.java.guest.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.CommandAction;
import com.java.guest.dao.GuestDao;

public class DeleteAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		int num = Integer.parseInt(request.getParameter("num"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		logger.info(logMsg+num+"\t"+pageNumber);
		
		int check = GuestDao.getInstance().delete(num);
		logger.info(logMsg+check);
		
		request.setAttribute("check", check);
		request.setAttribute("pageNumber", pageNumber);		
		
		return "/WEB-INF/views/guest/delete.jsp";
	}

}
