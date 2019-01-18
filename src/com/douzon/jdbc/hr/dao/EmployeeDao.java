package com.douzon.jdbc.hr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.douzon.jdbc.hr.vo.EmployeeVO;

public class EmployeeDao {

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public EmployeeDao() {
		try {
			conn = getConnection();
			pstmt = null;
			rs = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<EmployeeVO> searchName(String name) {
		List<EmployeeVO> list = new ArrayList<EmployeeVO>();
		
		try {
			
			String sql = "select emp_no, concat(last_name, ' ', first_name), hire_date "
					+ "  from employees "
					+ "  where concat(last_name, ' ', first_name) = " + name
					+ "  order by hire_date";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery(sql);
			
			while(rs.next()) {
				EmployeeVO vo = new EmployeeVO();
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2));
				vo.setHire_date(rs.getString(3));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			arrangeResource();
		}
		
		return list;
	}
	
	private Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://127.0.0.1:3306/employees";
			conn = DriverManager.getConnection(url, "hr", "hr");
			
			System.out.println("연결성공");
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
