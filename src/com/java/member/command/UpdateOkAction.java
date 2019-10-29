package com.java.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.CommandAction;
import com.java.member.dao.MemberDao;
import com.java.member.dto.MemberDto;

public class UpdateOkAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		MemberDto memberDto = new MemberDto();
		memberDto.setNum(Integer.parseInt(request.getParameter("num")));
		memberDto.setId(request.getParameter("id"));
		memberDto.setPassword(request.getParameter("pwd"));
		memberDto.setEmail(request.getParameter("email"));
		memberDto.setZipcode(request.getParameter("zipcode"));
		memberDto.setAddress(request.getParameter("address"));
		memberDto.setJob(request.getParameter("job"));
		memberDto.setMailing(request.getParameter("mailing"));
		memberDto.setInterest(request.getParameter("interest"));
		
		logger.info(logMsg+memberDto.toString());
		
		int check = MemberDao.getInstance().updateOk(memberDto);
		logger.info(logMsg+check);
		
		request.setAttribute("check", check);
		
		return "/WEB-INF/views/member/updateOk.jsp";
	}

}
