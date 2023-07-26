package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Test {
	
	public static List<List<String>> readCSV() {
		List<List<String>> csvList = new ArrayList<List<String>>();
		File csv = new File("./src/data/han.csv");
		BufferedReader br = null;
		String line = "";
		
		try {
			br = new BufferedReader(new FileReader(csv));
			
			while((line = br.readLine()) != null) {
				List<String> aLine = new ArrayList<String>();
				String[] lineArr = line.split(",");
				aLine = Arrays.asList(lineArr);
				csvList.add(aLine);
			}
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) {
					br.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return csvList;
	}

	public static void main(String[] args) {
		List<List<String>> list = readCSV();
		Random random = new Random();
		
		int indexA = random.nextInt(10000);
		int indexB = random.nextInt(10000);
		
		String a = list.get(0).get(indexA);
		String b = list.get(0).get(indexB);
		
		System.out.println(a + " " + b);

	}

}
