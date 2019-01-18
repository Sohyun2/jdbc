package com.douzon.jdbc.bookshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.douzon.jdbc.bookshop.vo.BookVo;

public class BookDao {

	Connection conn;
	PreparedStatement pstmt;
	Statement stmt;
	ResultSet rs;

	public boolean updateStatus(long no, String status) {

		boolean result = false;

		try {
			conn = getConnection();

			String sql = "update book " 
						+ "  set status = ? " 
						+ "  where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, status);
			pstmt.setLong(2, no);

			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			arrangeResource();
		}

		return result;

	}

	public boolean insert(BookVo bookVo) {
		boolean result = false;

		try {
			conn = getConnection();
			String sql = " insert " + "   into book " + " values (null, ?, '대여가능', ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, bookVo.getTitle());
			pstmt.setLong(2, bookVo.getAuthorNo());

			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			arrangeResource();
		}
		return result;
	}

	// 책의 상태 확인
	public boolean checkStatus(long no) {
		try {
			conn = getConnection();

			String sql = "select status from book where no = "+ no;
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery(sql);
			
			String status = "";
			if(rs.next()) {
				status = rs.getString(1);				
			}

			if (status.equals("대여가능"))
				return true;

		} catch (SQLException e) {
			System.out.println("error:" + e);
			e.printStackTrace();
		} finally {
			arrangeResource();
		}
		return false;
	}

	public List<BookVo> getList() {
		List<BookVo> list = new ArrayList<BookVo>();

		try {
			conn = getConnection();

			String sql = "   select a.no, a.title, a.status, b.name" + "     from book a, author b"
					+ "    where a.author_no = b.no" + " order by a.no asc";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery(sql);

			while (rs.next()) {
				long no = rs.getLong(1);
				String title = rs.getString(2);
				String status = rs.getString(3);
				String authorName = rs.getString(4);

				BookVo vo = new BookVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setStatus(status);
				vo.setAuthorName(authorName);

				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			arrangeResource();
		}

		return list;
	}

	private Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://127.0.0.1:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}

	private void arrangeResource() {

		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
