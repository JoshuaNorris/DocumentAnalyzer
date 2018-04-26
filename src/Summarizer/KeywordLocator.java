package Summarizer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import Parser.Documents.DocumentContainer;
import gui.GUIController;

public class KeywordLocator {
	
	private int numOfKeywords;
	private String document;
	private String title;
	
	public KeywordLocator(int numOfKeywords, String document, String title) {
		this.title = title;
		this.numOfKeywords = numOfKeywords;
		this.document = document; 
	}

	public void insertRelatedWordsInDatabase() throws SQLException {
		DocumentContainer documentContainer = new DocumentContainer(document);
		HashMap<String, Double> searchesWithScores = documentContainer.getTermFrequency();
		ArrayList<String> result = new ArrayList<String>();
		for (int x = 0; x < numOfKeywords; x++) {
			String vote = getHighestVote(searchesWithScores);
			GUIController.db.insertKeyword(title, vote);
			searchesWithScores.remove(vote);
		}
	}
		
	/*
	 * I got the getHighestVote function from: 
	 * https://bukkit.org/threads/get-string-with-the-highest-integer-from-
	 * hashmap.309098/
	 */

	public String getHighestVote(HashMap<String, Double> map) {
		String highestMap = null;
		double highestVote = 0;
		for (Entry<String, Double> entry : map.entrySet()) {
			if (entry.getValue() > highestVote) {
				highestMap = entry.getKey();
				highestVote = entry.getValue();
			}
		}
		return highestMap;
	}


}
