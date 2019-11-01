package com.java.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.java.board.dto.BoardDto;
import com.java.db.ConnectionProvider;
import com.java.db.jdbcUtil;

/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 10. 31.
 * @Detail : 게시판 관련 DAO 
 */

public class BoardDao {
	
	private static BoardDao instance = new BoardDao();

	public static BoardDao getInstance() { // MemberDao.getInstance()
		return instance;
	}

	private BoardDao() {
	}
	
	public int insert(BoardDto boardDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;		
		int value = 0;
		
		writeNumber(conn, boardDto);
		
		try {
			String sql = "insert into board values(board_board_number_seq.nextval, ?,?,?,?,?,?,?,?,?,?)";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, boardDto.getWriter());
			pstmt.setString(2, boardDto.getSubject());
			pstmt.setString(3, boardDto.getEmail());
			pstmt.setString(4, boardDto.getContent().replaceAll("\r\n", "<br/>"));
			pstmt.setString(5, boardDto.getPassword());
			
			pstmt.setTimestamp(6, new Timestamp(boardDto.getWriteDate().getTime()));
			pstmt.setInt(7, boardDto.getReadCount());
			pstmt.setInt(8, boardDto.getGroupNumber());
			pstmt.setInt(9, boardDto.getSequenceNumber());
			pstmt.setInt(10, boardDto.getSequenceLevel());
			
			value = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}
		
		return value;	
	}
	
	public void writeNumber(Connection conn, BoardDto boardDto) {
		int boardNumber = boardDto.getBoardNumber();			// 0
		int groupNumber = boardDto.getGroupNumber();			// 1
		int sequenceNumber = boardDto.getSequenceNumber();		// 0
		int sequenceLevel = boardDto.getSequenceLevel();		// 0
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		
		try {			
			String sql = null;
			if(boardNumber == 0) {  // ROOT - 그룹번호
				sql = "select max(group_number) from board";
				conn = ConnectionProvider.getConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {					
					boardDto.setGroupNumber(rs.getInt(1)+1);
				}
				
			}else {  // 답글 - 글순서, 글레벨
				// 그룹번호가 같으면서 현재 본인 글 순서 보다 큰 번호는 +1 한다.				
				sql="UPDATE board SET sequence_number = sequence_number+1 WHERE group_number = ? AND sequence_number > ?";
				
				conn= ConnectionProvider.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, groupNumber);
				pstmt.setInt(2, sequenceNumber);
				pstmt.executeUpdate();
				
				boardDto.setSequenceNumber(sequenceNumber+1);				
				boardDto.setSequenceLevel(sequenceLevel+1);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			jdbcUtil.close(rs);
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);		
		}
	}

	public int getCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;		
		int count = 0;	
		
		try {			
			String sql = "SELECT count(*) FROM board";		
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			jdbcUtil.close(rs);
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);		
		}
		return count;
	}

	public ArrayList<BoardDto> getBoardList(int startRow, int endRow) {
		Connection conn = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;	
		ArrayList<BoardDto> boardList = null;
		try {			
			String sql = "SELECT * FROM"+
					"(SELECT rownum rnum, A.*FROM" + 
					"(SELECT * FROM board ORDER BY group_number DESC,"+
					"sequence_number ASC) A) WHERE rnum >= ? AND rnum <=?";		
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();		
			
			boardList = new ArrayList<BoardDto>();
			
			while(rs.next()) {
				BoardDto boardDto = new BoardDto();
				
				boardDto.setBoardNumber(rs.getInt("board_number"));
				boardDto.setWriter(rs.getString("writer"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setEmail(rs.getString("email"));
				boardDto.setContent(rs.getString("content"));
				
				boardDto.setPassword(rs.getString("password"));
				boardDto.setWriteDate(new Date(rs.getTimestamp("write_date").getTime()));
				boardDto.setReadCount(rs.getInt("read_count"));
				boardDto.setGroupNumber(rs.getInt("group_number"));
				boardDto.setSequenceNumber(rs.getInt("sequence_number"));
				boardDto.setSequenceLevel(rs.getInt("sequence_level"));
				
				boardList.add(boardDto);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			jdbcUtil.close(rs);
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);		
		}
		return boardList;
	}

	public BoardDto read(int boardNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDto boardDto = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			//update
			String sqlUp = "UPDATE board SET read_count = read_count+1 WHERE board_number=?";
			pstmt = conn.prepareStatement(sqlUp);
			pstmt.setInt(1, boardNumber);
			pstmt.executeUpdate();
			if(pstmt != null) {
				pstmt.close();
			}
			
			//select
			String sqlSelect = "SELECT * FROM board WHERE board_number=?";
			pstmt = conn.prepareStatement(sqlSelect);
			pstmt.setInt(1, boardNumber);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardDto = new BoardDto();
				boardDto.setBoardNumber(rs.getInt("board_number"));				
				boardDto.setWriter(rs.getString("writer"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setEmail(rs.getString("email"));
				boardDto.setContent(rs.getString("content"));
				
				boardDto.setPassword(rs.getString("password"));
				boardDto.setWriteDate(new Date(rs.getTimestamp("write_date").getTime()));
				boardDto.setReadCount(rs.getInt("read_count"));
				boardDto.setGroupNumber(rs.getInt("group_number"));
				boardDto.setSequenceNumber(rs.getInt("sequence_number"));
				boardDto.setSequenceLevel(rs.getInt("sequence_level"));
			}
			
			conn.commit();
		}catch(Exception e) {
			e.printStackTrace();
			jdbcUtil.rollBack(conn);
		}finally {
			jdbcUtil.close(rs);
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}
		
		return boardDto;
	}

	public BoardDto boardUpdate(int boardNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDto boardDto = null;
		
		try {
			conn = ConnectionProvider.getConnection();					
						
			//select
			String sqlSelect = "SELECT * FROM board WHERE board_number=?";
			pstmt = conn.prepareStatement(sqlSelect);
			pstmt.setInt(1, boardNumber);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardDto = new BoardDto();
				boardDto.setBoardNumber(rs.getInt("board_number"));				
				boardDto.setWriter(rs.getString("writer"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setEmail(rs.getString("email"));
				boardDto.setContent(rs.getString("content"));
				
				boardDto.setPassword(rs.getString("password"));
				boardDto.setWriteDate(new Date(rs.getTimestamp("write_date").getTime()));
				boardDto.setReadCount(rs.getInt("read_count"));
				boardDto.setGroupNumber(rs.getInt("group_number"));
				boardDto.setSequenceNumber(rs.getInt("sequence_number"));
				boardDto.setSequenceLevel(rs.getInt("sequence_level"));
			}			
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}finally {
			jdbcUtil.close(rs);
			jdbcUtil.close(pstmt);
			jdbcUtil.close(conn);
		}
		
		return boardDto;
	}

	public int boardUpdateOk(int boardNumber, BoardDto boardDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int check = 0;
		
		try {
			String sql = "UPDATE board SET subject=?, email=?, content=?, password=?, write_date=? WHERE board_number=?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
						
			pstmt.setString(1, boardDto.getSubject());
			pstmt.setString(2, boardDto.getEmail());
			pstmt.setString(3, boardDto.getContent().replaceAll("\r\n", "<br/>"));
			pstmt.setString(4, boardDto.getPassword());			
			pstmt.setTimestamp(5, new Timestamp(boardDto.getWriteDate().getTime()));
			pstmt.setInt(6, boardNumber);
			
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
