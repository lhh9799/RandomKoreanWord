package com.ssafy.model;

import java.util.List;
import java.util.Map;

import com.ssafy.exception.DuplicatedException;
import com.ssafy.exception.InvalidDataException;
import com.ssafy.exception.RecordNotFoundException;

public interface UserManager {
	public void addUser(User user) throws DuplicatedException;
	public void addUser(Map<String, String> map) throws DuplicatedException;
	public User getUser(String id) throws RecordNotFoundException;
	public String findUserId(String name, String mobile) throws RecordNotFoundException;
	public void removeUser(String id, String pw) throws RecordNotFoundException, InvalidDataException;
	List<User> getUserList();
	
	void updateUser(User user) throws RecordNotFoundException;
	void updateUser(String id, String pw, String name) throws RecordNotFoundException;
	void updateUser(String id, String pw, String name, String mobile) throws RecordNotFoundException;
	void updateUser(Map<String, String> map) throws RecordNotFoundException;
	
	void saveData();
	void loadData();
	void saveDataCsv();
	
	//사용자 가입 입력 데이터 등록: 가입일은 서비스에서 현재날짜로 설정
	void addUser(String id, String pw, String name, String mobile) throws DuplicatedException;
	User login(String id, String pw) throws RecordNotFoundException, InvalidDataException;
}
