package com.ssafy.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.ssafy.exception.DuplicatedException;
import com.ssafy.exception.InvalidDataException;
import com.ssafy.exception.InvalidPasswordException;
import com.ssafy.exception.RecordNotFoundException;
import com.ssafy.model.User;
import com.ssafy.model.UserManager;
import com.ssafy.model.UserManagerImpl;
import com.ssafy.model.service.BulletinService;
import com.ssafy.model.service.MainServiceImpl;

public class StartView {
	// 입력 device: 표준 입력장치
	private Scanner in = new Scanner(System.in);
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	//사용자 관리를 위한 객체 생성
	private UserManager mngr = UserManagerImpl.getInstance();
	private User loginUser;
	
	//사용자 서비스를 위한 객체 생성
	MainServiceImpl msi = MainServiceImpl.getInstance();
	
	//게시판 조회를 위한 객체 생성
	BulletinService bs = BulletinService.getInstance();
	
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
//		int menuNo = in.nextInt();
		int menuNo = 0;
		try {
			menuNo = Integer.parseInt(br.readLine());
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		try {
			loginUser = msi.login();
			serviceMainMenu(loginUser.getId());
		} catch (RecordNotFoundException | InvalidDataException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}	

	public void joinMenu() {
		printTitle("사용자가입");
		try {
			loginUser = msi.join();
			myInfoMenu();
		} catch(DuplicatedException e) {
			System.out.println(e.getLocalizedMessage());
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public void findIdMenu() {
		printTitle("아이디찾기");
		String id;
		
		try {
			id = msi.findId(loginUser);
			System.out.printf("아이디는 %s 입니다.\n\n", id);
		} catch (RecordNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public void findPwMenu() {
		printTitle("비밀번호찾기");
		String pw = null;
		
		try {
			pw = msi.findPw();
//			System.out.printf("비밀번호는 %s 입니다.\n", pw);
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getLocalizedMessage());
		}
	}	

	public void exitMenu() {
		printTitle("프로그램을 종료합니다.");
		mngr.saveData();
		mngr.saveDataCsv();
		System.exit(1);
	}	

	public void logoutMenu() {
		printTitle("로그아웃");
		mainMenu();
	}

	public void myInfoMenu() {
		printTitle("내정보조회");
		try {
			msi.myInfo(loginUser);
		} catch (RecordNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public void updatePwMenu() {
		printTitle("암호변경");
		
		try {
			msi.updatePw(loginUser);
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getLocalizedMessage());
		}
	}

	public void boardListMenu() {
		printTitle("게시글조회");
		
		bs.load();
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