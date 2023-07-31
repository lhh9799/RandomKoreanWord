package com.ssafy.exception;

/**
 * 식별키가 중복된 경우 예외 클래스
 * @author 임경혜
 */
public class DuplicatedException extends Exception {
	public DuplicatedException() {
		super("데이터가 중복 되었습니다.");
	}
	
	public DuplicatedException(String message) {
		super(message + " 데이터가 중복 되었습니다.");
	}
}
