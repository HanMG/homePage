package com.java.fileboard.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.CommandAction;

/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 10. 31.
 * @Detail : 게시판 글 작성 관련 
 */

public class WriteAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		// 부모글 , ROOT글 
		int boardNumber = 0;		// 글번호 (DB 결정)
		int groupNumber = 1;		// 그룹번호
		int sequenceNumber = 0;		// 글순서
		int sequenceLevel = 0;		// 글레벨
		
		if(request.getParameter("boardNumber")!=null) {
			// 답글			
			boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
			groupNumber = Integer.parseInt(request.getParameter("groupNumber"));
			sequenceNumber = Integer.parseInt(request.getParameter("sequenceNumber"));
			sequenceLevel = Integer.parseInt(request.getParameter("sequenceLevel"));
		}
		
		request.setAttribute("boardNumber",boardNumber);
		request.setAttribute("groupNumber",groupNumber);
		request.setAttribute("sequenceNumber",sequenceNumber);
		request.setAttribute("sequenceLevel",sequenceLevel);
		
		return "/WEB-INF/views/fileBoard/write.jsp";
	}

}
