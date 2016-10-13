package com.song.board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.DirStateFactory.Result;
import javax.sql.DataSource;

import com.song.board.dto.BDto;

public class BDao {
	DataSource dataSource;

	public BDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<BDto> list() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		// try {
		// Class.forName("oracle.jdbc.driver.OracleDriver");
		// System.out.println("드라이버 로딩 성공");
		// conn
		// =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
		// "springmvc" , "0000");
		// System.out.println("커넥션 성공");
		// } catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// System.out.println("드라이버 로딩 실패");
		// } catch (SQLException e) {
		// e.printStackTrace();
		// System.out.println("커넥션 실패");
		// }

		ArrayList<BDto> dtos = new ArrayList<BDto>();
		try {
			String query = "select bId, bName, bTitle, bContent, bDate, bHit, bGroup, "
					+ "bStep, bIndent from mvc_board order by bGroup desc, bStep asc";
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			rSet = pstmt.executeQuery();

			while (rSet.next()) {
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
		} finally {
			try {
				if (rSet != null)
					rSet.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		return dtos;

	}

	public void write(String bName, String bTitle, String bContent) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		// try {
		// Class.forName("oracle.jdbc.driver.OracleDriver");
		// System.out.println("드라이버 로딩 성공");
		// conn
		// =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
		// "springmvc" , "0000");
		// System.out.println("커넥션 성공");
		// } catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// System.out.println("드라이버 로딩 실패");
		// } catch (SQLException e) {
		// e.printStackTrace();
		// System.out.println("커넥션 실패");
		// }

		try {

			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent)"
					+ " values(mvc_board_seq.nextval,?,?,?, 0, mvc_board_seq.currval, 0, 0)";
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);

			pstmt.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

	}

	public void modify(String bId, String bName, String bTitle, String bContent) {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			String query = "update mvc_board set bName = ?, bTitle=?, bContent=? where bId=?";
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bId));

			int rn = pstmt.executeUpdate();
			//rn에는 1이 반환됨.
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

	}

	public BDto contentView(String SbId) {
		
		upHit(SbId);
		BDto dto = null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			String query="select * from mvc_board where bId=?";
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(SbId));
			rs =pstmt.executeQuery();
			
			if(rs.next()){
				int bId= rs.getInt("bId");
				String bTitle= rs.getString("bTitle");
				String bName= rs.getString("bName");
				String bContent= rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit= rs.getInt("bHit");
				int bGroup= rs.getInt("bGroup");
				int bStep= rs.getInt("bStep");
				int bIndent= rs.getInt("bIndent");
				
				dto= new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e2) {
			}
		}
		
		
		return dto;
		// TODO Auto-generated method stub
		
	}

	public void delete(String Sbid) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			String query="delete from mvc_board where bId=?";
			conn= dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(Sbid));
			pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

		
	}

	private void upHit(String bId) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			String query = "update mvc_board set bHit=bHit+1 where bId=?";
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
				try {
					if(pstmt!=null)pstmt.close();
					if(conn!=null) conn.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
		}
	}

	public BDto reply_view(String SbId) {
		BDto dto =null;
		Connection conn= null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		try {
			String query="select * from mvc_board where bid=?";
			conn=dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(SbId));
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				int bId = rs.getInt("bid");
				String bTitle= rs.getString("bTitle");
				String bName= rs.getString("bName");
				String bContent= rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit= rs.getInt("bHit");
				int bGroup= rs.getInt("bGroup");
				int bStep= rs.getInt("bStep");
				int bIndent= rs.getInt("bIndent");
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)rs.close();
				if(conn!=null)rs.close();
			} catch (Exception e2) {
			}
		}
		
		return dto;
	}

	public BDto reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep,
			String bIndent) {
		
//		replyShape(bGroup, bStep);
		BDto dto = null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		Result rs = null;
		
		try {
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent)"
					+ "values (mvc_board_seq.nextval, ?, ?, ?, ?, ?,?)";
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(query);
			int i=1;
			pstmt.setString(i++, bName);
			pstmt.setString(i++, bTitle);
			pstmt.setString(i++, bContent);
			pstmt.setInt(i++, Integer.parseInt(bGroup));
			pstmt.setInt(i++, Integer.parseInt(bStep));
			pstmt.setInt(i++, Integer.parseInt(bIndent));
			
			int rn = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e2) {
			}
		}
		
		
		return null;
	}

}
