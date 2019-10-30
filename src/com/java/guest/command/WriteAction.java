package com.java.guest.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.CommandAction;
import com.java.guest.dao.GuestDao;
import com.java.guest.dto.GuestDto;

/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 10. 29.
 * @Detail : 방명록 CRUD중 READ부분 - 페이징을 위해 pageNumber로 현재번호를 얻어, 
 * 			  시작과 끝, 페이지당 출력할 게시물 수를 정의하고, 
 * 			 DAO를 통해 총 데이터수, 출력할 게시물들 등을 반환하여 write.jsp로 전달한다.
 */
public class WriteAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {

		logger.info(logMsg + "writeAction");

		String pageNumber = request.getParameter("pageNumber"); // get방식으로 받을 페이지 넘버

		if (pageNumber == null) // 첫 번째 일시 
			pageNumber = "1";

		int currentPage = Integer.parseInt(pageNumber); // 1) 요청페이지 1
		
		logger.info(logMsg +" 요청페이지번호: "+currentPage);

		int boardSize = 2; // 2) 페이지당 출력할 게시물 수
		int starRow = (currentPage - 1) * boardSize + 1; // 시작번호
		int endRow = currentPage * boardSize; // 끝번호		

		logger.info(logMsg + starRow + "," + endRow);

		int count = GuestDao.getInstance().getCount(); // 총 데이터 수 
		
		ArrayList<GuestDto> guestList = null;
		if (count > 0) {
			guestList = GuestDao.getInstance().guestList(starRow, endRow); // 시작번호와 끝번호를 통해 보여질 데이터를 ArrayList로 가져옴
			logger.info(logMsg +" 데이터 몇개: "+guestList.size());
		}
		
		
		if (guestList != null) {
			request.setAttribute("count", count);
			request.setAttribute("guestList", guestList);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("boardSize", boardSize);			
		}
		return "/WEB-INF/views/guest/write.jsp";
	}

}
