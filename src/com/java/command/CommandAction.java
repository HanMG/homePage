package com.java.command;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandAction {
	public static final Logger logger = Logger.getLogger(CommandAction.class.getName());
	public static final String logMsg = "*******logMsg*******";
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable;
}
