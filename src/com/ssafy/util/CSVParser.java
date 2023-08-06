package com.ssafy.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.TreeMap;


public class CSVParser {
	private TreeMap<Integer, String> words = new TreeMap<Integer, String>();
    private int num;
    
    public CSVParser(String category) {
    	loadData(category);
    }
 
    private void loadData(String category) {
    	String marketInfoFilePath = "res/" + category + ".csv";
    	
    	String line;
    	try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(marketInfoFilePath)))) {
            while((line = br.readLine()) != null) {
                String[] tempStr = line.split(",");
                for(int i = 0; i < tempStr.length; i++) {
                	words.put(i, tempStr[i]);
                	num++;
                }
            }
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public TreeMap<Integer, String> getWords() {
    	
    	return words;
    }
    
    public int getNum() {
    	return num;
    }
    
}
