package com.ssafy.model.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;

public class BulletinService {
	String boardPath = "res/board";
	File boardDir = null;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static BulletinService bs = new BulletinService();
	
	public static BulletinService getInstance() {
		return bs;
	}
	
	private BulletinService() {}
	
	public void load() {
		boardDir = new File(boardPath);
		
		FilenameFilter filter = new FilenameFilter() {
		    public boolean accept(File f, String name) {
		        return name.endsWith(".txt");
		    }
		};
		
		if(boardDir.exists()) {
			File[] files = boardDir.listFiles(filter);
			System.out.println("[게시판 목록]\n");
			
			if(files.length == 0) {
				System.out.println("게시글이 없습니다.");
				
				return;
			}
			
			for(int i=1; i<=files.length; i++) {
				//".txt" -> 뒤 4글자 절삭
				String fileName = files[i-1].getName();
				System.out.println(i + "번 | " + fileName.substring(0, fileName.length()-4));
			}
			
			while(true) {
				System.out.println("\n게시글을 선택하세요 (숫자 입력) | 뒤로가기: -1");
				
				int option = 0;
				
				try {
					option = Integer.parseInt(br.readLine().trim());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(option == -1 || option < 0 || option > files.length) {
					break;
				}
				else {
					try(BufferedReader fbr = new BufferedReader(new FileReader(files[option-1]))) {
						String fileName = files[option-1].getName();
						System.out.println("[제목] " + fileName.substring(0, fileName.length()-4) + "\n");
						
						System.out.print("[본문] ");
						String content;
						while((content = fbr.readLine()) != null) {
							System.out.println(content);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}
		else {
			System.out.println("게시판 \"" + boardDir.getAbsolutePath() + "\" 경로를 찾을 수 없습니다.");
		}
	}
	
	/*
	public static void main(String[] args) {
		load();
	}
	*/
}
