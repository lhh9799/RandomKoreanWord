package com.ssafy.exception;

public class InvalidPasswordException extends Exception {
	public InvalidPasswordException() {
		super("사용할 수 없는 비밀번호입니다.");
	}
	
	public InvalidPasswordException(String message) {
		super(message + " 는 사용할 수 없는 비밀번호입니다.");
	}
}