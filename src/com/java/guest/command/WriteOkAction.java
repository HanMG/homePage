package com.java.guest.command;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.CommandAction;
import com.java.guest.dao.GuestDao;
import com.java.guest.dto.GuestDto;

/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 10. 30.
 * @Detail :  방명록 CRUD중 CREATE부분 - write.jsp로 부터 이름, 비밀번호, 메세지를 받고, 
 * 			  Date객체를 생성하여 현재 시간을 만들고, Dto 객체에 set한후 Dao를 통해 DB에 insert한다.
 * 			  check를 통해 정상적으로 등록됐는지를 보여주기위해, writeOk.jsp로 check 값을 전달한다. 
 */

public class WriteOkAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		//request.setCharacterEncoding("utf-8");
		GuestDto guestDto = new GuestDto();
		guestDto.setName(request.getParameter("irum"));
		guestDto.setPassword(request.getParameter("pwd"));
		guestDto.setMessage(request.getParameter("message"));
		guestDto.setWriteDate(new Date());
		logger.info(logMsg+guestDto.toString());
		int check = GuestDao.getInstance().insert(guestDto);
		
		request.setAttribute("check", check);
		return "/WEB-INF/views/guest/writeOk.jsp";
	}

}
