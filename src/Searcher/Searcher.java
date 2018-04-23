package Searcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import Parser.Documents.*;
import Searcher.NetSearcher.NetSearcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Searcher {

	private String query;
	private DocumentContainer documents;
	private ArrayList<String> searchResults;

	public Searcher(String query, String document) {
		this.query = query;
		this.documents = new DocumentContainer(document);
		this.searchResults = runSearch();
	}

	public ObservableList<String> getSearchResults() {return arrayToObservableList(searchResults);};

	private ArrayList<String> runSearch() {
		NetSearcher netSearch = new NetSearcher(query, documents);
		return netSearch.run();
	}

	public ObservableList<String> getRelatedWords() {
		String topSearches = changeToString(this.searchResults);
		DocumentContainer searches = new DocumentContainer(topSearches);
		HashMap<String, Double> searchesWithScores = searches.getTermFrequency();
		ArrayList<String> result = new ArrayList<String>();
		searchesWithScores.remove(this.query);
		for (int x = 0; x < 5; x++) {
			String vote = getHighestVote(searchesWithScores);
			result.add(vote);
			searchesWithScores.remove(vote);
		}
		return arrayToObservableList(result);
	}

	private String changeToString(ArrayList<String> searchResults) {
		String result = "";
		for (int x = 0; x < 5; x++) {
			result += searchResults.get(x);
		}
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
