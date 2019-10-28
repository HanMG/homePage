package com.java.member.command;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.CommandAction;
import com.java.member.dao.MemberDao;
import com.java.member.dto.MemberDto;

/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 10. 25.
 * @Detail : 회원에  쓰이는 DTO
 */
public class RegisterOkAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		//logger.info(logMsg+"RegisterOkAction");
		request.setCharacterEncoding("utf-8");	
		
		
		MemberDto memberDto = new MemberDto();
		memberDto.setId(request.getParameter("id"));
		memberDto.setPassword(request.getParameter("pwd"));
		memberDto.setName(request.getParameter("name"));
		
		memberDto.setJumin1(request.getParameter("jumin1"));
		memberDto.setJumin2(request.getParameter("jumin2"));
		memberDto.setEmail(request.getParameter("email"));
		
		memberDto.setZipcode(request.getParameter("zipcode"));
		memberDto.setAddress(request.getParameter("address"));
		memberDto.setJob(request.getParameter("job"));
		
		memberDto.setMailing(request.getParameter("mailing"));
		memberDto.setInterest(request.getParameter("interest"));
		memberDto.setMemberLevel("1");
		memberDto.setRegisterDate(new Date());
		
		
		logger.info(logMsg+memberDto.toString());
				
		int check = MemberDao.getInstance().insert(memberDto);
		logger.info(logMsg + check);
		request.setAttribute("check", check);
		return "/WEB-INF/views/member/registerOk.jsp";
	}
	
}
