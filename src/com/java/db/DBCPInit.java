package com.java.db;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 10. 29.
 * @Detail : jdbc를 불러들임  
 */

public class DBCPInit extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriver");
			Class.forName(jdbcDriver);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
