package com.java.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java.command.CommandAction;
import com.java.member.dao.MemberDao;
import com.java.member.dto.MemberDto;

public class UpdateAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		logger.info(logMsg+id);
		
		MemberDto memberDto = MemberDao.getInstance().update(id);
		logger.info(logMsg+memberDto.toString());
		
		request.setAttribute("memberDto", memberDto);
		return "/WEB-INF/views/member/update.jsp";
	}

}
