package com.ssafy.model.service;

import com.ssafy.exception.DuplicatedException;
import com.ssafy.exception.InvalidDataException;
import com.ssafy.exception.InvalidPasswordException;
import com.ssafy.exception.RecordNotFoundException;
import com.ssafy.model.User;

public interface MainService {
	public User login() throws RecordNotFoundException, InvalidDataException;
	public User join() throws DuplicatedException, InvalidPasswordException;
	public String findId(User loginUser) throws RecordNotFoundException;
	public String findPw() throws RecordNotFoundException;
	public void myInfo(User loginUser) throws RecordNotFoundException;
	public void updatePw(User loginUser) throws RecordNotFoundException;
	public void printMessage(String message);
}
