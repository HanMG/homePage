package com.java.board.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.board.dao.BoardDao;
import com.java.board.dto.BoardDto;
import com.java.command.CommandAction;

public class ListAction implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		String pageNumber = request.getParameter("pageNumber");
		
		if(pageNumber == null) {
			pageNumber = "1";
		}
		
		int currentPage = Integer.parseInt(pageNumber);  // 요청페이지 
		logger.info(logMsg+currentPage);
		
		int count = BoardDao.getInstance().getCount();	// 총 데이터수 
		logger.info(logMsg+count);
		
		int boardSize = 10;		// 출력할 데이터수
		int startRow = (currentPage-1)*boardSize+1; // 시작점
		int endRow = currentPage*boardSize;			// 끝점
		
		ArrayList<BoardDto> boardList = null;
		if(count > 0) {
			boardList = BoardDao.getInstance().getBoardList(startRow, endRow);
			logger.info(logMsg+boardList.size());
		}
		
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("boardSize", boardSize);
		request.setAttribute("count", count);
		request.setAttribute("boardList", boardList);
		return "/WEB-INF/views/board/list.jsp";
	}

}
