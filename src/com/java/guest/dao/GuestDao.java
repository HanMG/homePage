package com.java.guest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.java.db.ConnectionProvider;
import com.java.db.jdbcUtil;
import com.java.guest.dto.GuestDto;

/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 10. 29.
 * @Detail : 방명록에 관련된 DAO  
 */

public class GuestDao {
	
	// 싱글톤, 대부분의 ActionClass에서 쓰이는 Dao의 객체생성을  private로 하여 제한 
	private static GuestDao instance = new GuestDao();
	
	public static GuestDao getInstance() {
		return instance;
	}
	
	private GuestDao() {
		
	}
	
	// 방명록 - 글쓰기 
	// ** 주의할부분 - 날짜를 DB에 입력시 날짜를 getTime으로 long형태로 변경, TimeStamp 객체를 생성하여 밀리시간의 형태로 입력한다.    
	public int insert(GuestDto guestDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;		
		int check = 0;
		
		try {
			String sql = "insert into guest values(guest_num_seq.nextval, ?,?,?,?)";
			conn = ConnectionProvider.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, guestDto.getName());
			pstmt.setString(2, guestDto.getPassword());
			pstmt.setString(3, guestDto.getMessage());
			pstmt.setTimestamp(4, new Timestamp(guestDto.getWriteDate().getTime()));
			check = pstmt.executeUpdate();
			
			/*
			 * Date d = guestDto.getWriteDate(); 
			 * long a = d.getTime(); 
			 * Timestamp t = new Timestamp(a); 
			 * pstmt.setTimestamp(4, t);
			 */
			
		}catch(Exception e){
			
		}finally {
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}
		
		// TODO Auto-generated method stub
		return check;
	}

	
	// 방명록 - 시작과 끝 값을 받아 범위에 맞는 데이터를 DB에서 가져와 리스트로 전달한다. 
	// ** 주의할부분 - ROWNUM으로 값중 해당 부분만 가져옴 
	public ArrayList<GuestDto> guestList(int starRow, int endRow) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GuestDto> arrayList = null;		
		
		try {
			String sql ="SELECT * FROM (SELECT ROWNUM AS rnum, A.* FROM"					
					+"(SELECT * FROM guest ORDER BY num desc) A) B "
					+"WHERE B.rnum >= ? and B.rnum <= ?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, starRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			arrayList = new ArrayList<GuestDto>();
			while(rs.next()) {
				GuestDto guestDto = new GuestDto();
				guestDto.setNum(rs.getInt("num"));
				guestDto.setName(rs.getString("name"));
				guestDto.setPassword(rs.getString("password"));
				guestDto.setMessage(rs.getString("message"));
				guestDto.setWriteDate(new Date(rs.getTimestamp("write_date").getTime()));
				arrayList.add(guestDto);
			}			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUtil.close(rs);
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}
		return arrayList;
	}
	
	// 방명록 - 총 데이터 갯수 추출
	public int getCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			String sql ="select count(*) from guest";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// count(*) 필드의 값을 가져옴
				count=rs.getInt(1);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUtil.close(rs);
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}
		
		return count;
	}

	public int delete(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int check = 0;
		try {
			String sql ="DELETE FROM guest WHERE num = ?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			check = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}
		
		
		return check;
	}

	public GuestDto update(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GuestDto guestDto = null;
		
		try {
			String sql ="SELECT * FROM guest WHERE num=?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				guestDto = new GuestDto();
				guestDto.setNum(rs.getInt("num"));
				guestDto.setName(rs.getString("name"));
				guestDto.setPassword(rs.getString("password"));
				guestDto.setMessage(rs.getString("message"));
				guestDto.setWriteDate(new Date(rs.getTimestamp("write_date").getTime()));
			}			
		}catch(Exception e) {
			e.printStackTrace();			
		}finally {
			jdbcUtil.close(rs);
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}		
		return guestDto;
	}

	public int updateOk(GuestDto guestDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int check = 0;
		
		try {
			String sql ="UPDATE guest SET password=?, message=? WHERE num=?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, guestDto.getPassword());
			pstmt.setString(2, guestDto.getMessage());
			pstmt.setInt(3, guestDto.getNum());
			
			check = pstmt.executeUpdate();			
				
		}catch(Exception e) {
			e.printStackTrace();			
		}finally {			
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}		
		return check;
	}

	
}
