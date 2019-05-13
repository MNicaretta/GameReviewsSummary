package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameReviewsSummary {

	private static class GenreSummary {
		private String genre;
		private int reviews;
		private List<Map<String, Integer>> scorePhrases = new ArrayList<Map<String,Integer>>();
		private List<Integer> scores = new ArrayList<Integer>();
	}

	public static void main(String[] args) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("game-reviews.csv"))) {
		    String line = br.readLine();
		    String[] str = line.split(";");
		    
		    while((line = br.readLine()) != null){
	        str = line.split(";");
		        for(int i = 0; i < str.length; i++){
		            
		        }
		    }
		}
	}
}
