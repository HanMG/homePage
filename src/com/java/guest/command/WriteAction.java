package com.java.guest.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.CommandAction;

/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 10. 29.
 * @Detail : 방명록 작성에 관련된 Action  
 */
public class WriteAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {

		logger.info(logMsg+"writeAction");
		
		return "/WEB-INF/views/guest/write.jsp";
	}

}
