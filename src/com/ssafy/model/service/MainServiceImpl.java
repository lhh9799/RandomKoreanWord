package com.ssafy.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.ssafy.exception.DuplicatedException;
import com.ssafy.exception.InvalidDataException;
import com.ssafy.exception.InvalidPasswordException;
import com.ssafy.exception.RecordNotFoundException;
import com.ssafy.model.User;
import com.ssafy.model.UserManager;
import com.ssafy.model.UserManagerImpl;
import com.ssafy.util.CheckSecurityWords;

public class MainServiceImpl implements MainService {
	User loginUser = null;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	//사용자 관리를 위한 객체 생성
	private UserManager mngr = UserManagerImpl.getInstance();
	private static MainServiceImpl msi = new MainServiceImpl();
	
	//한글 보안문자 4글자를 만들기 위한 클래스 (싱글톤 디자인)
	CheckSecurityWords csw = CheckSecurityWords.getSecurity();
	
	public static MainServiceImpl getInstance() {
		return msi;
	}
	
	public MainServiceImpl() {}
	
	@Override
	public User login() throws RecordNotFoundException, InvalidDataException {
		String id = null;
		System.out.print("아이디 : ");
		try {
			id = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.print("비밀번호 : ");
		String memberPw = null;
		try {
			memberPw = br.readLine().trim();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		loginUser = mngr.login(id, memberPw);
		
		printMessage(loginUser.getId() + "님 사용자전용 서비스를 이용하세요");
		
		return loginUser;
	}
	
	@Override
	public User join() throws DuplicatedException, InvalidPasswordException, InvalidDataException {
		String[] pwBanList = {"password", "12345678", "qwerty"};
		String id = null;
		String pw = null;
		
		try {
			System.out.print("아이디 : ");
			id = br.readLine().trim();
			pw = null;
			
			System.out.println("사용할 수 없는 비밀번호 리스트: " + Arrays.toString(pwBanList));
			System.out.print("비밀번호 : ");
			pw = br.readLine().trim();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(String ban_string : pwBanList) {
			if(pw.contains(ban_string)) {
//				System.out.println("사용할 수 없는 비밀번호입니다.");
				
				throw new InvalidPasswordException();
			}
		}
		
		String name = null;
		String mobile = null;
		
		try {
			System.out.print("이름 : ");
			name = br.readLine().trim();
			
			System.out.print("휴대폰 : ");
			mobile = br.readLine().trim();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(name.equals("") || id.equals("") || pw.equals("") || name.equals("") || mobile.equals("")) {
			throw new InvalidDataException();
		}
		
		User user = new User();
		user.setId(id);
		user.setPw(String.valueOf(pw.hashCode()));
		user.setName(name);
		user.setMobile(mobile);
		
		mngr.addUser(user);
		//회원 가입 성공 처리
		//축하메세지 출력
		//로그인 화면으로 이동
		mngr.saveData();
		mngr.saveDataCsv();
		loginUser = user;
		System.out.println("축하합니다. 가입되었습니다.");
		
		return loginUser;
	}
	
	@Override
	public String findId(User loginUser) throws RecordNotFoundException, InvalidDataException {
		String name = null, mobile = null;
		
		try {
			System.out.print("이름을 입력하세요: ");
			name = br.readLine().trim();
			System.out.print("휴대폰 번호를 입력하세요: ");
			mobile = br.readLine().trim();
			
			if(name.equals("") || mobile.equals("")) throw new InvalidDataException();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return mngr.findUserId(name, mobile);
	}
	
	@Override
	public String findPw() throws RecordNotFoundException {
		String id = null, name = null;
		String pw = null;
		
		try {
			System.out.print("아이디를 입력하세요: ");
			id = br.readLine().trim();
			System.out.print("이름을 입력하세요: ");
			name = br.readLine().trim();
			
			pw = mngr.findUserPw(id, name);
			String OTP = csw.createSecurityWord();
			System.out.println("임시 비밀번호가 발급되었습니다. 비밀번호를 변경하시기 바랍니다.");
			System.out.println("임시 비밀번호: " + OTP);
			
			Map<String, String>map = new HashMap<String, String>();
			map.put(pw, OTP.trim());
			mngr.updateUser(id, map);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getLocalizedMessage());
		}
		
		return pw;
	}
	
	public void myInfo(User loginUser) throws RecordNotFoundException {
		loginUser.showMyInfo();
	}
	
	public boolean pwLengthCheck(String pw) {
		if(pw.length() < 8 || pw.length() > 15) {
			return false;
		}
		
		return true;
	}
	
	public boolean pwCapitalLetterCheck(String pw) {
		for(char c : pw.toCharArray()) {
			if(Character.isUpperCase(c)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean pwspecialCharacterCheck(String pw) {
		String[] specialCharacters = {"~", "․", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "-", "+", "=", "[", "]", "[", "]", "|", "\\", ";", ":", "‘", "“", "<", ">", ",", ".", "?", "/"};
		
		for(String s : specialCharacters) {
			if(pw.contains(s)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean pwCheck(String pw) {
		return pwLengthCheck(pw) && pwCapitalLetterCheck(pw) && pwspecialCharacterCheck(pw);
	}
	
	public void updatePw(User loginUser) throws RecordNotFoundException {
		String id = loginUser.getId();
		String pw = null;
		String new_pw = null;
		String confirm_new_pw = null;
		Map<String, String> map = null;
		
		try {
			System.out.print("현재 비밀번호를 입력하세요: ");
			pw = br.readLine().trim();
			
			if(Integer.parseInt(loginUser.getPw()) != pw.hashCode()) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				
				return;
			}
			
			while(true) {
				System.out.print("새 비밀번호 (비밀번호는 특수문자 1 자리와 대문자 1 자리를 포함하고 8자리 ~ 15자리이어야 합니다.): ");
				new_pw = br.readLine().trim();
				System.out.print("새 비밀번호 확인: ");
				confirm_new_pw = br.readLine().trim();
				
				if(!pwCheck(new_pw)) {
					char[] specialCharacters = {'~', '․', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '+', '=', '[', ']', '[', ']', '|', '\\', ';', ':', '‘', '“', '<', '>', ',', '.', '?', '/'};
					
					System.out.print("사용 가능한 특수문자 리스트: ");
					for(char c : specialCharacters) {
						System.out.print(c + " ");
					}
					System.out.println();
				}
				
				else if(new_pw.equals(confirm_new_pw)) {
					map = new HashMap<String, String>();
					map.put(pw, new_pw);
					
					break;
				}
				
			}
		} catch (IOException e) {
//			e.printStackTrace();
		}
		
		mngr.updateUser(id, map);
	}
	
	public void printMessage(String message) {
		System.out.println(message);
	}
}
