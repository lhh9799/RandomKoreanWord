package com.ssafy.view;

import java.util.*;

import com.ssafy.exception.DuplicatedException;
import com.ssafy.exception.InvalidDataException;
import com.ssafy.exception.RecordNotFoundException;
import com.ssafy.model.User;
import com.ssafy.model.UserManager;
import com.ssafy.model.UserManagerImpl;

public class StartView {
	// 입력 device: 표준 입력장치
	private Scanner in = new Scanner(System.in);

	//사용자 관리를 위한 객체 생성
	private UserManager mngr = UserManagerImpl.getInstance();
	private String memberId;
	private User currentUser;
	private User user;
	
	public void mainMenu() {
		//서비스 시작전에 파일 저장 사용자
		mngr.loadData();
		
		System.out.println("=============================");
		System.out.println("사용자관리시스템 (ver.1.0)");
		System.out.println("=============================");
		System.out.println("1. 로그인");
		System.out.println("2. 사용자가입");
		System.out.println("3. 아이디찾기");
		System.out.println("4. 비밀번호찾기");
		System.out.println("9. 프로그램종료");
		System.out.println("=============================");

//		printTitle("사용자관리시스템 (ver.1.0)");
//		System.out.println("1. 로그인");
//		System.out.println("2. 사용자가입");
//		System.out.println("3. 아이디찾기");
//		System.out.println("4. 비밀번호찾기");
//		System.out.println("9. 프로그램종료");
//		printLine();

		System.out.print("메뉴번호: " );
		int menuNo = in.nextInt();
		switch(menuNo) {
		case 1:
			loginMenu();
			break;
		case 2:
			joinMenu();
			break;
		case 3:
			findIdMenu();
			break;
		case 4:
			findPwMenu();
			break;
		case 9:
			exitMenu();
			break;
		default :
			printMessage("Error: 올바르지 않은 메뉴번호입니다. 다시 입력하세요");
		}
	}

	public void serviceMainMenu(String memberId) {
		printTitle("사용자관리시스템 (ver.1.0)");
		printMessage("[로그인] " + memberId + "님");
		System.out.println("1. 로그아웃");
		System.out.println("2. 내정보조회");
		System.out.println("3. 암호변경");
		System.out.println("4. 게시글조회");
		printLine();

		System.out.print("메뉴번호: " );
		int menuNo = in.nextInt();
		switch(menuNo) {
		case 1:
			logoutMenu();
			break;
		case 2:
			myInfoMenu();		serviceMainMenu(memberId);
			break;
		case 3:
			updatePwMenu();		serviceMainMenu(memberId);
			break;
		case 4:
			boardListMenu();	serviceMainMenu(memberId);
			break;
		default :
			printMessage("Error: 올바르지 않은 메뉴번호입니다. 다시 입력하세요");
		}
	}

	public void loginMenu() {
		printTitle("로그인");
		System.out.print("아이디 : ");
//		String memberId = in.next();
		memberId = in.next();

		System.out.print("비밀번호 : ");
		String memberPw = in.next();
		
		try {
			user = mngr.login(memberId, memberPw);
		} catch (RecordNotFoundException | InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		printMessage(memberId + "님 사용자전용 서비스를 이용하세요");
		serviceMainMenu(memberId);
		
	}	

	public void joinMenu() {
		printTitle("등록");
		System.out.print("아이디 : ");
		String id = in.next();
		
		System.out.print("비밀번호 : ");
		String pw = in.next();
		
		System.out.print("이름 : ");
		String name = in.next();
		
		System.out.print("휴대폰 : ");
		String mobile = in.next();
		
		User user = new User();
		user.setId(id);
		user.setPw(pw);
		user.setName(name);
		user.setMobile(mobile);
		
		try {
			mngr.addUser(user);
			//회원 가입 성공 처리
			//축하메세지 출력
			//로그인 화면으로 이동
			mngr.saveData();
			mngr.saveDataCsv();
			System.out.println("축하합니다. 가입되었습니다.");
			myInfoMenu();
			
		} catch (DuplicatedException e) {
			e.printStackTrace();
		}
		
		printTitle("사용자가입");
	}
	
	public void findIdMenu() {
		printTitle("아이디찾기");
	}	

	public void findPwMenu() {
		printTitle("비밀번호찾기");
	}	

	public void exitMenu() {
		printTitle("프로그램을 종료합니다.");
		System.exit(1);
	}	

	public void logoutMenu() {
		printTitle("로그아웃");
		mainMenu();
	}

	public void myInfoMenu() {
		printTitle("내정보조회");
		try {
			currentUser = mngr.getUser(memberId);
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(currentUser);
	}

	public void updatePwMenu() {
		String[] line = in.nextLine().split(" ");
		
		String ID = line[0];
		String PW = line[1];
		
//		Map<String, String> map
		
//		ID
		
//		updateUser(Map<String, String> map);
//		currentUser
//		printTitle("암호변경");
	}

	public void boardListMenu() {
		printTitle("게시글조회");
	}
	

	public void printTitle(String title) {
		System.out.println();
		System.out.println("=============================");
		System.out.println(title);
		System.out.println("=============================");
	}

	public void printLine() {
		System.out.println("=============================");
	}

	public void printMessage(String message) {
		System.out.println(message);
	}	
}