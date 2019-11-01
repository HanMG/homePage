package com.java.guest.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.CommandAction;
import com.java.guest.dao.GuestDao;
import com.java.guest.dto.GuestDto;

public class UpdateAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		int num = Integer.parseInt(request.getParameter("num"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		logger.info(logMsg+num+"\t"+pageNumber);
		GuestDto guestDto = GuestDao.getInstance().update(num);
		logger.info(logMsg+guestDto.toString());
		
		request.setAttribute("guestDto", guestDto);
		request.setAttribute("pageNumber", pageNumber);
		
		return "/WEB-INF/views/guest/update.jsp";
	}

}
