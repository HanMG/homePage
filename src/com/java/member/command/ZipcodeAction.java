package com.java.member.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.CommandAction;
import com.java.member.dao.MemberDao;
import com.java.member.dto.ZipcodeDto;

public class ZipcodeAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		String checkDong= request.getParameter("dong");
		logger.info(logMsg+checkDong);
		
		if(checkDong != null) {
			ArrayList<ZipcodeDto> zipcodeList =	MemberDao.getInstance().zipcodeReader(checkDong);
			logger.info(logMsg + zipcodeList.size());
			request.setAttribute("zipcodeList", zipcodeList);
		}
		
		return "/WEB-INF/views/member/zipcode.jsp";
	}

}
