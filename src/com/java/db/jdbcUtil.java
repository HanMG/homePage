package com.java.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 10. 25.
 * @Detail : 각각의 메모리 사용 close;
 */

public class jdbcUtil {
	
	
	public static void close(Connection conn){
		if(conn != null) {
			try {
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}		
		
	}
	
	public static void close(PreparedStatement ptsmt) {
		if(ptsmt != null) {
			try {
				ptsmt.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void rollBack(Connection conn) {
		if(conn!=null) {
			try {
				conn.rollback();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
