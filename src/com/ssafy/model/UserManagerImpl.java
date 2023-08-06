package com.ssafy.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ssafy.exception.DuplicatedException;
import com.ssafy.exception.InvalidDataException;
import com.ssafy.exception.RecordNotFoundException;
import com.ssafy.util.Utility;
import com.ssafy.view.UserTest;

/**
 * 1. Singleton pattern 2. implements 3. Collection 4. Exception
 */
public class UserManagerImpl implements UserManager {
	private List<User> list = new ArrayList<>();
//	private List<User> list;	//파일로부터 읽어올 정보가 존재하지 않으면

	private static UserManagerImpl instance = new UserManagerImpl();

	private UserManagerImpl() {
	}

	public static UserManagerImpl getInstance() {
		return instance;
	}

	// 아이디가 존재하는지 여부와, 존재한다면 저장된 위치(인덱스) 반환
	// 등록 : 존재유무(boolean), 존재하지 않으면 -1
	// 조회, 변경, 삭제 : int
	private int isExist(String id) {
		for (int index = 0; index < list.size(); index++) {
			if (list.get(index).getId().equals(id)) {
				return index;
			}
		}
		return -1;
	}

	@Override
	public void addUser(User user) throws DuplicatedException {
		// list.add(user); // List: 순서있음, 중복허용
		int index = isExist(user.getId());
//		if (index == -1) {
//			list.add(user);
//		} else {
//			throw new DuplicatedException(user.getId());
//		}

		if (index == -1) {
			user.setEntryDate(Utility.getCurrentDate());
			list.add(user);
			return;
		}
		
		list.add(user);
		
		throw new DuplicatedException(user.getId());
	}

	@Override
	public void addUser(Map<String, String> map) throws DuplicatedException {
		// int index = isExist(map.get("id"));
		if (isExist(map.get("id")) >= 0) {
			throw new DuplicatedException(map.get("id"));
		}

		User user = new User();
		user.setId(map.get("id"));
		user.setPw(map.get("pw"));
		user.setName(map.get("name"));
		user.setMobile(map.get("mobile"));
		user.setEntryDate(map.get("entryDate"));

		list.add(user);
	}

	@Override
	public List<User> getUserList() {
		return list;
	}

	@Override
	public User getUser(String id) throws RecordNotFoundException {
		int index = isExist(id);
		if (index >= 0) {
			return list.get(index);
		}

		throw new RecordNotFoundException(id + " 아이디를 가진 사용자");
		// return null;
	}

	@Override
	public String findUserId(String name, String mobile) throws RecordNotFoundException {
		for (int index = 0; index < list.size(); index++) {
			if (list.get(index).getName().equals(name) && list.get(index).getMobile().equals(mobile)) {
				return list.get(index).getId();
			}
		}

		throw new RecordNotFoundException(name + " 이름을 가진 사용자");
		// return null;
	}
	
	@Override
	public String findUserPw(String id, String name) throws RecordNotFoundException {
		for (int index = 0; index < list.size(); index++) {
			if (list.get(index).getId().equals(id) && list.get(index).getName().equals(name)) {
				return list.get(index).getPw();
			}
		}

		throw new RecordNotFoundException(name + " 이름을 가진 사용자");
		// return null;
	}

	@Override
	public void removeUser(String id, String pw) throws RecordNotFoundException, InvalidDataException {
		int index = isExist(id);
		if (index >= 0) {
			if (list.get(index).getPw().equals(pw)) {
				list.remove(index);
			} else {
				throw new InvalidDataException("비밀번호가 올바르지 않습니다."); // 보안이슈 문제
//				throw new InvalidDataException();
			}
		} else {
//			throw new RecordNotFoundException();
			throw new RecordNotFoundException(id + "아이디를 가진 사용자"); // 보안이슈 문제
		}
	}

	@Override
	public void updateUser(User user) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		int index = isExist(user.getId());
		
		if (index == -1) {
			throw new RecordNotFoundException(user.getId());
		}
		
		list.get(index).setPw(user.getPw());
	}

	@Override
	public void updateUser(String id, String pw) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		int index = isExist(id);
		
		if (index == -1) {
			throw new RecordNotFoundException(id);
		}
		
		list.get(index).setPw(pw);
	}
	
	@Override
	public void updateUser(String id, String pw, String name) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		updateUser(id, pw);
		list.get(isExist(id)).setName(name);
	}

	@Override
	public void updateUser(String id, String pw, String name, String mobile) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		list.get(isExist(id)).setMobile(mobile);
	}

	@Override
	public void updateUser(String id, Map<String, String> map) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		int index = isExist(id);
		
		if (index == -1) {
			for(User u : list) {
				System.out.println(u);
			}
			throw new RecordNotFoundException(id);
		}
		
		User targetUser = list.get(index);
		
		for(Entry<String, String> i : map.entrySet()) {
			String old_PW = i.getKey();
			String new_PW = i.getValue();
			
			if(targetUser.getPw().equals(old_PW)) {
				targetUser.setPw(map.get(old_PW));
				System.out.println("비밀번호 변경 성공");
				saveData();
				saveDataCsv();
			}
			else {
				System.out.println("old_PW: " + old_PW);
				System.out.println("new_PW: " + new_PW);
				System.out.println("현재 비밀번호가 일치하지 않습니다.");
			}
		}
	}

	// 프로그램 종료시에 사용자의 정보들이 저장된 자료저장구조 객체 파일 저장
	@Override
	public void saveData() {
		// System.out.println("파일 저장작업을 시작합니다.");
		// 저장 폴더 확인 및 생성하기
		// 저장 파일의 위치: src/data => src\\data
		File foldername = new File("src/data");
		if (!foldername.exists()) { // 폴더가 존재하지 않으면
			foldername.mkdir();
//			System.out.println("[INFO] 파일 저장폴더를 생성합니다.:" + foldername.getAbsolutePath());
			try {
				System.out.println("[INFO] 파일 저장폴더를 생성합니다.:" + foldername.getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace(); // 개발중에 오류발생시 디버깅 목저그로 트래킹 정보 출력!!!
			}
		}

		// 저장폴더에 파일 생성하기
		File filename = new File("src/data/user.dat");
		// 객체 출력
		ObjectOutputStream out = null;

		try {
			out = new ObjectOutputStream(new FileOutputStream(filename, false)); // append true

			// 객체를 파일에출력하기위한 출력스트림을 이용해서 자료저장구조 객체를 출력
			out.writeObject(list);
			// 파일저장 성공 메세지 출력
			System.out.printf("[성공] 사용자 총 %d명의 정보를 해당폴더에 (%s)저장하였습니다.%n", list.size(), filename.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();	//!!!!!개발 중
			System.out.println(e.getMessage());
			System.out.println("[실패] 사용자 정보 파일 저장중에 문제가 발생했습니다.");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	// 프로그램이 실행이 될때 저장된 파일이 존재하면 해당 데이터를 가져와서 자료저장구조에 로드
	@Override
	public void loadData() {
		//System.out.println("파일 저장 정보를 로드 시작합니다.");
		File filename = new File("src/data/user.dat");
		if(filename.exists() && filename.isFile()) {
			//try 생성된 객체 자동으로 자원해제: finally 생략!!!
			try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
				System.out.println("[INFO] 프로그램 시작시에 사용자 데이터를 로드중입니다.");
				
				//해당 파일로부터 저장된 자료저장구조객체를 읽어오기
				list = (List<User>) in.readObject();
				System.out.printf("[INFO] 사용자 총 %d명 정보를 로드 완료하였습니다.%n", list.size());
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				//파일 로딩중에 오류가 발생, 따라서 Collection 생성 할당해야함
				//멤버변수에 선언만 되어있다면!!!
				//private List<User> list;
				list = new ArrayList<>();
			}
		}
		else {
			//저장된 파일이 존재하지 않으므로 읽어와서 로드할 객체 정보가 없음, 따라서 Collection 생성 할당해야함
			//멤버변수에 선언만 되어있다면!!!
			//private List<User> list;
			System.out.println("[INFO] 로드할 파일 정보가 없습니다. 테스트용 회원정보 파일을 생성합니다.");
//			list = new ArrayList<>();
			UserTest.makeUserDataFile();
		}
	}

	// 프로그램 종료시에 자료저장구조에(Collection) 저장된 객체의 크기만큼 반복하면서
	// 저장된 객체의 정보를 ,(컴마)를 구분자로 문자열로 출력
	@Override
	public void saveDataCsv() {
		//System.out.println("파일 CSV 형식으로 저장작업을 시작합니다.");
		//저장폴더 확인 및 생성하기
		File foldername = new File("src/data");
		if (!foldername.exists()) { // 폴더가 존재하지 않으면
			foldername.mkdir();
			System.out.println("[INFO] 파일 저장폴더를 생성합니다.:" + foldername.getAbsolutePath());
			try {
				System.out.println("[INFO] 파일 저장폴더를 생성합니다.:" + foldername.getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace(); // 개발중에 오류발생시 디버깅 목저그로 트래킹 정보 출력!!!
			}
		}
		
		// 저장폴더에 파일 생성하기
		File filename = new File("src/data/user.csv");
		try(PrintWriter out = new PrintWriter(new FileWriter(filename, false))) {
			for(User u : list) {
//				System.out.println(u);	//표준 출력창에 출력할때는 toString() 생략
				out.println(u.toString());	//출력 스트림으로 해당 객체의 중요정보를 문자열로 출력 명시적 지정
//				out.println(u);	//u 참조변수의 참조값이 출력됨!!!!
//				System.out.printf();
			}
			System.out.printf("[성공] 사용자 총 %d명의 정보를 해당폴더에 (%s)저장하였습니다.%n", list.size(), filename.getAbsolutePath());
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("[실패] 파일 CSV 형식으로 저장시에 문제 발생했습니다.");
		}
		
	}

	 @Override
    public void addUser(String id, String pw, String name, String mobile) throws DuplicatedException {
        addUser(new User(id, pw, name, mobile, Utility.getCurrentDate()));
        
    }

    @Override
    public User login(String id, String pw) throws RecordNotFoundException, InvalidDataException {
        int index = isExist(id);
        if(index==-1) {
//            throw new RecordNotFoundException();
            throw new RecordNotFoundException("아이디 또는 비밀번호 ");
        }
        if (list.get(index).getPw().equals(pw)) {
            return list.get(index);
        } else {
            throw new InvalidDataException("아이디 또는 비밀번호 ");
        }
    }
    
}
