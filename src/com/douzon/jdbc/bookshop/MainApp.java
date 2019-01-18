package com.douzon.jdbc.bookshop;

import java.util.List;
import java.util.Scanner;

import com.douzon.jdbc.bookshop.dao.BookDao;
import com.douzon.jdbc.bookshop.vo.BookVo;

public class MainApp {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		displayBookInfo();

		System.out.println("대여하고 싶은 책의 번호를 입력하세요.");
		int no = sc.nextInt();
		sc.close();

		rent(no);
		displayBookInfo();
	}

	private static void rent(long no) {
		if (new BookDao().checkStatus(no)) {
			if (new BookDao().updateStatus(no, "대여중")) {
				System.out.println(no + "번 책이 대여되었습니다.");
			} else {
				System.out.println("대여 실패.");
			}
		}
	}

	private static void displayBookInfo() {
		System.out.println("*****도서 내용 출력하기*****");

		List<BookVo> bookList = new BookDao().getList();
		for (BookVo vo : bookList) {
			System.out.print("no : " + vo.getNo() + "\t");
			System.out.print("title : " + vo.getTitle() + "\t");
			System.out.print("status : " + vo.getStatus() + "\t");
			System.out.print("author name : " + vo.getAuthorName() + "\n");
		}
	}
}