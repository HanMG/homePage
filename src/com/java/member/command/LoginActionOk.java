package com.java.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.CommandAction;
import com.java.member.dao.MemberDao;

public class LoginActionOk implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		logger.info(logMsg+id+" "+pwd);
		
		String memberLevel = MemberDao.getInstance().login(id,pwd);
		logger.info(logMsg+memberLevel);
		
		if(memberLevel != null) {
			request.setAttribute("id", id);
			request.setAttribute("memberLevel", memberLevel);
		}
		
		return "/WEB-INF/views/member/loginOk.jsp";
	}

}
