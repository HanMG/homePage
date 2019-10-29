package com.java.guest.dao;

/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 10. 29.
 * @Detail : 방명록에 관련된 DAO  
 */

public class GuestDao {
	private static GuestDao instance = new GuestDao();
	
	public static GuestDao getInstance() {
		return instance;
	}
	
	private GuestDao() {
		
	}
			
}
