package com.java.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.CommandAction;
import com.java.member.dao.MemberDao;
import com.java.member.dto.MemberDto;

public class DropoutOkAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		MemberDto memberDto = new MemberDto();
		memberDto.setNum(Integer.parseInt(request.getParameter("num")));
		memberDto.setId(request.getParameter("id"));
		memberDto.setPassword(request.getParameter("pwd"));
		
		int check = MemberDao.getInstance().dropoutOk(memberDto);
		
		request.setAttribute("check", check);
		return "/WEB-INF/views/member/dropoutOk.jsp";
	}

}
