package com.ssafy.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class CSVParser {
	private List<String> words = new ArrayList<String>();;
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
                	words.add(tempStr[i]);
                	num++;
                }
            }
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public ArrayList<String> getWords() {
    	if(!words.isEmpty()) {
    		return (ArrayList<String>)words;
    	}
    	
    	return null;
    }
    
    public int getNum() {
    	return num;
    }
    
}
