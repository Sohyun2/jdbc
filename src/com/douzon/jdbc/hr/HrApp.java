package com.douzon.jdbc.hr;

import java.util.List;
import java.util.Scanner;

import com.douzon.jdbc.hr.dao.EmployeeDao;
import com.douzon.jdbc.hr.vo.EmployeeVO;

public class HrApp {
	public static void main(String[] args) {
		EmployeeDao dao = new EmployeeDao();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("검색하고 싶은 사원의 이름을 입력하세요 : ");
		String name = sc.nextLine();
		
		// 사원 이름 검색하기
		List<EmployeeVO> list = new EmployeeDao().searchName(name);
		for(EmployeeVO vo : list) {
			System.out.print("사번 : " + vo.getNo());
			System.out.print("이름 : " + vo.getName());
			System.out.print("입사일 : " + vo.getHire_date());
		}
	}
}
