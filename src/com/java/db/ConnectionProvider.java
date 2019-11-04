package com.java.db;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 10. 29.
 * @Detail : DB 연결을 위한 정보  
 */

public class ConnectionProvider {
	/*
	 * public static Connection getConnection() { Connection conn = null; try {
	 * String url = "jdbc:oracle:thin:@localhost:1521:xe"; String id = "mvc"; String
	 * pass = "1234";
	 * 
	 * conn = DriverManager.getConnection(url, id, pass);
	 * 
	 * } catch (SQLException e) { System.out.println("Connection Error: " + e); }
	 * 
	 * return conn; }
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
		// 서버 컨텍스트 객체 
		Context initContext = new InitialContext();		
		// 리소스(태그)를 가져온다. /리소스를 찾는 접두어
		Context envContext = (Context)initContext.lookup("java:/comp/env");
		// 리소스안에 있는 java/mvcDB
		DataSource ds = (DataSource) envContext.lookup("jdbc/mvcDB");
		
		conn = ds.getConnection();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
