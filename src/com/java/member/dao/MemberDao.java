package com.java.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import com.java.db.ConnectionProvider;
import com.java.member.dto.MemberDto;

/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 10. 25.
 * @Detail : DAO (Data Access Object) 
 */

public class MemberDao {
	public int insert(MemberDto memberDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int check = 0;
		try {
			String sql = "insert into member values(member_num_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,memberDto.getId());
			pstmt.setString(2,memberDto.getPassword());
			pstmt.setString(3,memberDto.getName());
			pstmt.setString(4,memberDto.getJumin1());
			pstmt.setString(5,memberDto.getJumin2());
			pstmt.setString(6,memberDto.getEmail());
			pstmt.setString(7,memberDto.getZipcode());
			pstmt.setString(8,memberDto.getAddress());
			pstmt.setString(9,memberDto.getJob());
			pstmt.setString(10,memberDto.getMailing());
			pstmt.setString(11,memberDto.getInterest());
			pstmt.setString(12,memberDto.getMemberLevel());
			pstmt.setTimestamp(13,new Timestamp(memberDto.getRegisterDate().getTime()));
			
			check = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return check;
	}
}
