package com.ssafy.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.exception.DuplicatedException;
import com.ssafy.exception.InvalidDataException;
import com.ssafy.exception.RecordNotFoundException;
import com.ssafy.model.User;
import com.ssafy.model.UserManager;
import com.ssafy.model.UserManagerImpl;
import com.ssafy.util.Utility;

public class UserTest {

//	public static void notMain(String[] args) {
	public static void makeUserDataFile () {
		User u1 = new User("user01", "pass01", "홍길동", "01012341000", "2023.07.24.");
		User u2 = new User("user02", "pass02", "강감찬", "01012342000", "2023.07.25.");
		User u4 = new User("user03", "pass03", "김유신", "01012343000", "2023.07.21.");
		User u5 = new User("user04", "pass04", "이순신", "01012344000", "2023.07.19.");
		User u3 = new User("user05", "pass05", "유관순", "01012345000","2023.06.19.");
		
		//step2 test
		User u10 = new User("user10", "pass04", "이순신", "01012344000", "2023.07.19.");
		User u11 = new User("user11", "pass05", "유관순", "01012345000","2023.06.19.");
		
		// 사용자 관리를 위한 서비스 클래스 객체 생성
		UserManager mngr = UserManagerImpl.getInstance();
		
//		print("등록");
		try {
			// valid
			mngr.addUser(new User("a", "a", "Test", "010-1234-5678", Utility.getCurrentDate(Utility.DATE_PATTERN)));
			mngr.addUser(u1);
			mngr.addUser(u2);
			mngr.addUser(u3);
//			mngr.addUser(u4);
//			mngr.addUser(u5);
			
			//step2 test
			mngr.addUser(u10);
			mngr.addUser(u11);
			
			Map<String, String> userMap = new HashMap<>();
			userMap.put("id", "test01");	// key 존재하지 않으면 등록, 존재하면 변경
			userMap.putIfAbsent("id", "ssafy10"); // key 존재하지 않으면 등록, 존재하면 등록하지 않음
			userMap.putIfAbsent("pw", "happyssafy");
			userMap.putIfAbsent("name", "김싸피");
			userMap.putIfAbsent("mobile", "01033337777");
			userMap.putIfAbsent("entryDate", Utility.getCurrentDate());
			mngr.addUser(userMap);
			
			// invalid
			mngr.addUser(u5);
		} catch(DuplicatedException e) {
			System.out.println(e.getMessage());
		}
		/*
		print("전체조회");
		List<User> list = mngr.getUserList();
		for(User u: list) {
			System.out.println(u);
		}
		
		print("상세조회");
		try {
			User user = mngr.getUser("user01");
			System.out.println(user);
		} catch (RecordNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		print("아이디찾기");
		try {
			String id = mngr.findUserId("홍길동", "01012341000");
			System.out.println("아이디는 " + id);
		} catch (RecordNotFoundException e) {
			System.out.println(e.getMessage());		
		}
		
		print("탈퇴");
		try {
			mngr.removeUser("user01", "pass01");
			mngr.removeUser("xxx", "pass01");
		} catch (RecordNotFoundException | InvalidDataException e) {
			System.out.println(e.getMessage());
		}

		try {
			mngr.removeUser("user02", "xxxx");
		} catch (RecordNotFoundException | InvalidDataException e) {
			System.out.println(e.getMessage());
		}

		print("전체조회");
		list = mngr.getUserList();
		for(User u: list) {
			System.out.println(u);
		}
		 */
		
		//프로그램 종료전에 자료저장구조 객체 저장
		mngr.saveData();
		mngr.saveDataCsv();
	}

	public static void print(String data) {
		System.out.println("\n#### " + data + " ####");
	}
}
