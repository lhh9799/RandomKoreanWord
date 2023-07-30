package test;

import java.util.Scanner;

import com.ssafy.util.CheckSecurityWords;

public class Test {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		CheckSecurityWords security = CheckSecurityWords.getSecurity();

		
		while(security.getTryCount() < 5) {
			String securityWord = security.createSecurityWord(System.currentTimeMillis() * 10000);
			
			System.out.print("보안문자: ");
			System.out.println(securityWord);
			System.out.println();
			
			System.out.print("입력하세요: ");
			String input = sc.next();
			
			if (security.isCorrect(input)) {
				System.out.println("OK\n\n");
				break;
			} else {
				System.out.println("Fail\n\n");
			}
		}
		
		System.out.println("보안문자 관련 기능 종료");

		sc.close();
	}

}