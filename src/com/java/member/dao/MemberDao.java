package com.java.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.java.db.ConnectionProvider;
import com.java.db.jdbcUtil;
import com.java.member.dto.MemberDto;
import com.java.member.dto.ZipcodeDto;

/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 10. 25.
 * @Detail : DAO (Data Access Object)
 */

public class MemberDao {
	// Singleton Pattern : 단 한개의 객체만을 가지고 구현한다.
	private static MemberDao instance = new MemberDao();

	public static MemberDao getInstance() { // MemberDao.getInstance()
		return instance;
	}

	private MemberDao() {
	}

	public int insert(MemberDto memberDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int check = 0;
		try {
			String sql = "insert into member values(member_num_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, memberDto.getId());
			pstmt.setString(2, memberDto.getPassword());
			pstmt.setString(3, memberDto.getName());
			pstmt.setString(4, memberDto.getJumin1());
			pstmt.setString(5, memberDto.getJumin2());
			pstmt.setString(6, memberDto.getEmail());
			pstmt.setString(7, memberDto.getZipcode());
			pstmt.setString(8, memberDto.getAddress());
			pstmt.setString(9, memberDto.getJob());
			pstmt.setString(10, memberDto.getMailing());
			pstmt.setString(11, memberDto.getInterest());
			pstmt.setString(12, memberDto.getMemberLevel());
			pstmt.setTimestamp(13, new Timestamp(memberDto.getRegisterDate().getTime()));

			check = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}
		return check;
	}

	public int idCheck(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int check = 0;

		try {
			String sql = "select id from member where id=?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			if (rs.next())
				check = 1;

		} catch (Exception e) {

		} finally {
			jdbcUtil.close(rs);
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}
		return check;
	}

	public ArrayList<ZipcodeDto> zipcodeReader(String checkDong) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ZipcodeDto> valueList = null;

		try {
			String sql = "SELECT * FROM zipcode WHERE dong Like ?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + checkDong + "%");
			rs = pstmt.executeQuery();
			valueList = new ArrayList<ZipcodeDto>();

			while (rs.next()) {
				ZipcodeDto address = new ZipcodeDto();
				address.setZipcode(rs.getString("zipcode"));
				address.setSido(rs.getString("sido"));
				address.setGugun(rs.getString("gugun"));
				address.setDong(rs.getString("dong"));
				address.setRi(rs.getString("ri"));
				address.setBunji(rs.getString("bunji"));

				valueList.add(address);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close(rs);
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}

		return valueList;
	}

	public String login(String id, String pwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String value = null;
		
		try {
			String sql ="select member_level from member where id = ? AND password = ?";
			conn = ConnectionProvider.getConnection();
			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				value=rs.getString("member_level");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUtil.close(rs);
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}		
				
		return value;		
	}
	
	
	public MemberDto update(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDto memberDto = null;
		
		try {
			String sql = "select * from member WHERE id=?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				memberDto = new MemberDto();
				memberDto.setNum(rs.getInt("num"));
				memberDto.setId(rs.getString("id"));
				memberDto.setPassword(rs.getString("password"));
				memberDto.setName(rs.getString("name"));
				memberDto.setJumin1(rs.getString("jumin1"));
				memberDto.setJumin2(rs.getString("jumin2"));
				memberDto.setEmail(rs.getString("email"));
				
				memberDto.setZipcode(rs.getString("zipcode"));
				memberDto.setAddress(rs.getString("address"));
				memberDto.setJob(rs.getString("job"));
				memberDto.setMailing(rs.getString("mailing"));
				memberDto.setInterest(rs.getString("interest"));
				memberDto.setMemberLevel(rs.getString("member_level"));
				memberDto.setRegisterDate(new Date(rs.getTimestamp("register_date").getTime()));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUtil.close(rs);
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}
		
		return memberDto;
	}

	public int updateOk(MemberDto memberDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;		
		int check = 0;
		
		try {
			String sql = "UPDATE member SET password = ?, email=?, zipcode=?, address=?, job=?, mailing=?, interest=? WHERE num=?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberDto.getPassword());
			pstmt.setString(2, memberDto.getEmail());
			pstmt.setString(3, memberDto.getZipcode());
			pstmt.setString(4, memberDto.getAddress());
			pstmt.setString(5, memberDto.getJob());
			pstmt.setString(6, memberDto.getMailing());
			pstmt.setString(7, memberDto.getInterest());
			pstmt.setInt(8, memberDto.getNum());
			
			check = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}
		return check;
	}

	public MemberDto dropout(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDto memberDto = null;
		
		try {
			String sql = "select * from member WHERE id=?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				memberDto = new MemberDto();	
				memberDto.setNum(rs.getInt("num"));
				memberDto.setId(rs.getString("id"));
				memberDto.setPassword(rs.getString("password"));				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUtil.close(rs);
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}
		
		return memberDto;	
	}

	public int dropoutOk(MemberDto memberDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;		
		int check = 0;
		
		try {
			String sql = "DELETE FROM member WHERE num = ? AND id = ? AND password = ?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberDto.getNum());
			pstmt.setString(2, memberDto.getId());
			pstmt.setString(3, memberDto.getPassword());			
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
