package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameReviewsSummary {

	private static class GenreSummary {
		private int reviews;
		private Map<String, Integer> scorePhrases = new HashMap<>();
		private List<Integer> scores = new ArrayList<Integer>();
	}

	public static void main(String[] args) throws IOException {
		Map<String,GenreSummary> summaries = new HashMap<>();
		Integer highestScore = null;
		Integer lowestScore = null;
		String bestGame = null;
		String worstGame = null;
		
		List<String> headers;
		
		try (BufferedReader br = new BufferedReader(new FileReader("game-reviews.csv"))) {
		    String line = br.readLine();
		    String[] str = line.split(";");
		    
		    headers = Arrays.asList(str);
		    
		    while((line = br.readLine()) != null) {
		    	try {
			    	str = line.split(";");
			    	String game = str[headers.indexOf("title")];
			        String genre = str[headers.indexOf("genre")];
			        String scorePhrase = str[headers.indexOf("score_phrase")];
			        Integer score = Integer.parseInt(str[headers.indexOf("score")]);
			        
			        // genre
			        GenreSummary summary = summaries.get(genre);
			        
			        if (summary == null) {
			        	summaries.put(genre, summary = new GenreSummary());
			        }
			        
			        summary.reviews++;
			        
			        // score_phrase
			        Integer phraseCount = summary.scorePhrases.get(scorePhrase);
			        
			        if (phraseCount == null) {
			        	phraseCount = 0;
			        }
			        
			        summary.scorePhrases.put(scorePhrase, ++phraseCount);
			        
			        // score
			        summary.scores.add(score);
			        
			        // best game
			        if (highestScore == null || highestScore < score) {
			        	highestScore = score;
			        	bestGame = game;
			        }
			        
			        // worst game
			        if (lowestScore == null || lowestScore > score) {
			        	lowestScore = score;
			        	worstGame = game;
			        }
		    	} catch (Exception e) {
		    		e.printStackTrace();
		    	}
		    }
		}
	}
}
