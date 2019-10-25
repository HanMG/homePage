package com.java.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.CommandAction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Servlet implementation class FrontController
 */

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(FrontController.class.getName());
	private final String logMsg = "*******logMsg*******";
	private HashMap<String, Object> commandMap = new HashMap<String, Object>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		String configFile = config.getInitParameter("configFile");
		logger.info(logMsg + configFile);

		// 서버 실제 경로
		String path = config.getServletContext().getRealPath(configFile);
		logger.info(logMsg + path);

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		Properties prop = new Properties();

		try {
			fis = new FileInputStream(path);
			bis = new BufferedInputStream(fis);
			prop.load(bis);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Iterator<Object> keyIter = prop.keySet().iterator();
		while(keyIter.hasNext()) {
			String key = (String)keyIter.next();
			String value = prop.getProperty(key);
			logger.info(logMsg+key+"\t"+value);
			
			try {
			Class<?> handlerClass = Class.forName(value);
			Object handlerInstance = handlerClass.getDeclaredConstructor().newInstance();
			commandMap.put(key,handlerInstance);
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getRequestURI();
		cmd = cmd.substring(request.getContextPath().length());
		logger.info(logMsg + cmd);
		String view = null; //
		
		try {
		CommandAction command = (CommandAction) commandMap.get(cmd);
		view = command.proRequest(request, response);
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
		
		if(view != null) {
			RequestDispatcher rd = request.getRequestDispatcher(view);
			rd.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
