package com.java.fileboard.command;


import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import com.java.command.CommandAction;
import com.java.fileboard.dao.BoardDao;
import com.java.fileboard.dto.BoardDto;

/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 11. 04.
 * @Detail : 파일게시판 글 작성 관련 
 */

public class WriteOkActionMap implements CommandAction {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// 보관할 클래스 선언
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 업로드할 클래스 선언
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		List<FileItem> list = upload.parseRequest(request);
		Iterator<FileItem> iter = list.iterator();
		BoardDto boardDto = new BoardDto();	
		HashMap<String, String> dataMap = new HashMap<String, String>();
		
		while(iter.hasNext()) {
			FileItem fileItem = iter.next();
			
			if(fileItem.isFormField()) { // text
				String name = fileItem.getFieldName();
				String value = fileItem.getString("utf-8");
				dataMap.put(name, value);
				
			}else { // binary
				if(fileItem.getFieldName().equals("file")) {					
					if(fileItem.getName() == null || fileItem.getName().equals("")) {
						continue;
					}
					// 파일명
					String fileName = Long.toString(System.currentTimeMillis())+"_"+fileItem.getName();
					logger.info(logMsg+fileName);
					// 사이즈
					upload.setFileSizeMax(1024*1024*10);
					long size = fileItem.getSize();
					
					// 경로
					String dir="C:\\MVC\\Workspace\\homePage\\WebContent\\ftp";
					
					File file = new File(dir,fileName);
					
					fileItem.write(file);					
					
					boardDto.setFileName(fileName);
					boardDto.setFileSize(size);
					boardDto.setPath(file.getAbsolutePath());					
				}				
			}			
		}
		
		
		logger.info(logMsg+boardDto.toString());
		boardDto.setDataMap(dataMap);
		boardDto.setWriteDate(new Date());
		boardDto.setReadCount(0);
		int check = BoardDao.getInstance().insert(boardDto);
		logger.info(logMsg+check);
		
		
		request.setAttribute("check", check);
		
		return "/WEB-INF/views/fileBoard/writeOk.jsp";		
	}

}
