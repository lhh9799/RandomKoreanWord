package com.ssafy.exception;

/**
 * 데이터가 존재하지 않는 경우 예외 클래스
 * @author 임경혜
 *
 */
public class RecordNotFoundException extends Exception {
	public RecordNotFoundException() {
		this("데이터가 존재하지 않습니다.");
	}
	
	public RecordNotFoundException(String message) {
		super(message + " 데이터가 존재하지 않습니다.");
	}
}
