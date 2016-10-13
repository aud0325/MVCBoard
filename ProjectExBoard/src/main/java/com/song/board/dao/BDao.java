package com.song.board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.song.board.dto.BDto;

public class BDao { 
	 
	public BDao(){
		
	}
	
	public ArrayList<BDto> list(){
		Connection conn=null;
		PreparedStatement pstmt=null; 
		 ResultSet rSet=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("드라이버 로딩 성공");
			conn =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "springmvc" , "0000");
            System.out.println("커넥션 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
            System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			 System.out.println("커넥션 실패");
		}
		
		ArrayList<BDto> dtos=new ArrayList<BDto>();
		try {
			String query = "select bId, bName, bTitle, bContent, bDate, bHit, bGroup, "
					+ "bStep, bIndent from mvc_board order by bGroup desc, bStep asc";
			pstmt= conn.prepareStatement(query);
			rSet = pstmt.executeQuery();
			
			while(rSet.next()){
				int bId = rSet.getInt("bId");
				String bName = rSet.getString("bName");
				String bTitle = rSet.getString("bTitle");
				String bContent = rSet.getString("bContent");
				Timestamp bDate = rSet.getTimestamp("bDate");
				int bHit = rSet.getInt("bHit");
				int bGroup = rSet.getInt("bGroup");
				int bStep = rSet.getInt("bStep");
				int bIndent = rSet.getInt("bIndent");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				dtos.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(rSet!=null) rSet.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
				
			} catch (Exception e2) {
			}
			
		}
		return dtos;
		
	}

	public void write(String bName, String bTitle, String bContent) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("드라이버 로딩 성공");
			conn =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "springmvc" , "0000");
            System.out.println("커넥션 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
            System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			 System.out.println("커넥션 실패");
		}
		
		try{
			
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent)"
					+ " values(mvc_board_seq.nextval,?,?,?, 0, mvc_board_seq.currval, 0, 0)";
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			
			pstmt.executeQuery();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
	}

}
