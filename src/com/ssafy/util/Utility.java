package com.ssafy.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
	<pre>
	## Utility 클래스 구현
	-- 공통으로 사용해야 하는 기능을 객체 생성없이 사용하기위한 메서드로 구현
	-- 현재날짜 문자열 반환 : getCurrent():String
		>> 기본형식: 년도4자리.월2자리.일2자리
		>> 지정한 형식의 문자열
		>> 지정한 형식, 지정한 로케일의 문자열 반환
		
	-- 정수형 숫자를 지정한 형식의 문자열 반환	
		>> 천단위 컴마 표기
		>> 기본화폐단위 + 천단위 컴마 표기 
		>> 지정한형식의화폐단위 + 천단위 컴마 표기
		
	-- 보안문자, 비밀번호찾기(임시발급번호) 랜덤 반환
		>> 기본: 4자리 숫자 형식의 문자열
		>> 지정한길이: 지정한 길의 숫자 형식의 문자열
		>> 지정한길이, 지정한 대소문자 여부: 지정한 길의 숫자 + 대소문자 조합 문자열 

	-- 보안문자 데이터 변환해서 반환: 비밀번호, 중요한 정보를 일부분을 "*" 문자로 대체
</pre>

 * @author 임경헤
 *
 */
public class Utility {
	public static final String DATE_PATTERN = "yyyy.MM.dd";
	public static String getCurrentDate() {
		return getCurrentDate(DATE_PATTERN, Locale.KOREA);
	}
	
	public static String getCurrentDate(String pattern) {
		return getCurrentDate(pattern, Locale.KOREA);
	}
	
	public static String getCurrentDate(String pattern, Locale locale) {
//		SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
//		Date currentDate = new Date();
//		String currentDateString = sdf.format(currentDate);
//		return currentDateString;
		
		return new SimpleDateFormat(pattern, locale).format(new Date());
	}
	
	public static void main(String[] args) {
		System.out.println(Utility.getCurrentDate());
		System.out.println(Utility.getCurrentDate("yyyy-MM-dd"));
		System.out.println(Utility.getCurrentDate("hh:mm:ss"));
		System.out.println(Utility.getCurrentDate("HH:mm:ss"));
		System.out.println(Utility.getCurrentDate("yyyy-MM-dd [a]hh:mm:ss"));
		System.out.println(Utility.getCurrentDate("yyyy-MM-dd [a]hh:mm:ss", Locale.US));
		System.out.println(Utility.getCurrentDate("yyyy-MM-dd [a]hh:mm:ss", Locale.JAPAN));
		System.out.println(Utility.getCurrentDate("yyyy-MM-dd [a]hh:mm:ss", Locale.CHINA));
	}
	
}
