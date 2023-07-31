package com.ssafy.exception;

/**
 * 데이터가 올바르지 않는 경우 예외 클래스
 * @author 임경혜
 *
 */
public class InvalidDataException extends Exception {
	public InvalidDataException() {
		super("데이터가 올바르지 않습니다.");
	}
	
	public InvalidDataException(String message) {
		super(message + " 데이터가 올바르지 않습니다.");
	}
}
