package com.ssafy.util;

import java.util.ArrayList;
import java.util.Random;

/**
 * 파싱한 데이터 가지고 랜덤한 보안문자 생성 사용자가 입력한 보안문자 체킹
 */
public class CheckSecurityWords {
	private ArrayList<String> prenounList;
	private ArrayList<String> nounList;

	private int prenounNum;
	private int nounNum;

	// 싱글톤
	private static CheckSecurityWords security = new CheckSecurityWords();

	private CheckSecurityWords() {
		CSVParser parser = new CSVParser("prenoun");
		prenounList = parser.getWords();
		prenounNum = parser.getNum();

		parser = new CSVParser("words");
		nounList = parser.getWords();
		nounNum = parser.getNum();
	}

	public static CheckSecurityWords getSecurity() {
		return security;
	}

	/** 보안문자와 입력한 문자열 동일 여부 확인한 카운트 */
	private int tryCount = 0;
	private String securityWord = "";

	/**
	 * 새롭게 보안문자 생성
	 * 
	 * @param 랜덤 위한 시드
	 */
	public String createSecurityWord(long seed) {
		Random random = new Random();
		random.setSeed(seed);
		int randomIndex = random.nextInt(prenounNum);

		securityWord = prenounList.get(randomIndex);
		randomIndex = random.nextInt(nounNum);
		securityWord += nounList.get(randomIndex);
		
		return securityWord;
	}

	public String getSecurityWord() {
		return securityWord;
	}

	public boolean isCorrect(String input) {
		if(tryCount++ > 5) {
			return false;
		}
		
		return (securityWord.equals(input) ? true : false);
	}
	
	public int getTryCount() {
		return tryCount;
	}
}
