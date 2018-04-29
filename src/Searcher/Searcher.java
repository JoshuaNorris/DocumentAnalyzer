package Searcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import Parser.Documents.*;
import Searcher.NetSearcher.KMPScore.KMPScoreCalculator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Searcher {

	private String query;
	private String document;

	public Searcher(String query, String document) {
		this.query = query;
		this.document = document;
	}

	public ObservableList<String> getSearchResults(int numOfResults) {
		ObservableList<String> results = getTopResults(numOfResults);
		return results;
	}

	private ObservableList<String> getTopResults(int numOfResults) {
		TreeMap<Double, String> searchResults = runSearch();
		ObservableList<String> result = FXCollections.observableArrayList();
		
		for (int x = 0; x < numOfResults; x++) {
			System.out.println(searchResults);
			result.add(searchResults.lastEntry().getValue());
			searchResults.remove(searchResults.lastEntry().getKey(), searchResults.lastEntry().getValue());
		}
		
		return result;
	}

	private TreeMap<Double, String> runSearch() {
		KMPScoreCalculator search = new KMPScoreCalculator(query, document);
		return search.getResult();
	}

	public ObservableList<String> getRelatedWords(int numOfResultsConsidered) {
		ObservableList<String> searchResults = getTopResults(numOfResultsConsidered);
		
		String topSearches = changeToString(searchResults);
		DocumentContainer searches = new DocumentContainer(topSearches);
		HashMap<String, Double> searchesWithScores = searches.getTermFrequency();
		ArrayList<String> result = new ArrayList<String>();
		searchesWithScores.remove(this.query);
		for (int x = 0; x < searchResults.size(); x++) {
			String vote = getHighestVote(searchesWithScores);
			result.add(vote);
			searchesWithScores.remove(vote);
		}
		return arrayToObservableList(result);
	}
	
	private String changeToString(ObservableList<String> searchResults) {
		String result = "";
		for (int x = 0; x < searchResults.size(); x++) {
			result += " " + searchResults.get(x);
		}
		result = result.substring(1);
		return result;
	}

	private ObservableList<String> arrayToObservableList(ArrayList<String> array) {
		ObservableList<String> result = FXCollections.observableArrayList();
		result.addAll(array);
		return result;
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
