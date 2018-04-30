package Searcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import Parser.Documents.*;
import Searcher.KMPScore.KMPScoreCalculator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

public class Searcher {

	private String query;
	private String document;
	private double inarowWeight;
	private double scoreWeight;
	private int numOfResults;
	private boolean isPhrase;
	private ObservableList<String> searchResults;

	public Searcher(String query, String document, double inarowWeight, double scoreWeight, int numOfResults) {
		this.query = query;
		this.document = document;
		this.inarowWeight = inarowWeight;
		this.scoreWeight = scoreWeight;
		this.numOfResults = numOfResults;
		this.isPhrase = isPhrase();
		this.searchResults = calculateSearchResults();
	}

	public Searcher(String query, String document, int numOfResults) {
		this(query, document, 1.0, 1.0, numOfResults);
	}

	public Searcher(String query, ObservableList<String> titles, int numOfResults) {
		this(query, titlesToDocument(titles), 1.0, 1.0, numOfResults);
	}

	private static String titlesToDocument(ObservableList<String> titles) {
		String result = "";
		for (int x = 0; x < titles.size(); x++) {
			result += titles.get(x) + '|';
			// add error handling for this.
		}
		return result;
	}


	public ObservableList<String> getSearchResults() {return searchResults;};

	private ObservableList<String> calculateSearchResults() {
		ObservableList<String> results = FXCollections.observableArrayList();
		if (isPhrase) {
			PhraseSearcher phraseSearcher = new PhraseSearcher(query, document, inarowWeight, scoreWeight, numOfResults);
			results = phraseSearcher.getResults();
		} else {
			results = getTopResults(numOfResults);
		}
		return results;
	}

	private ObservableList<String> getTopResults(int numOfResults) {
		TreeMap<Double, String> searchResults = runSearch();
		ObservableList<String> result = FXCollections.observableArrayList();
		if (numOfResults > searchResults.size()) {
			numOfResults = searchResults.size();
		}

		for (int x = 0; x < numOfResults; x++) {
			result.add(searchResults.lastEntry().getValue());
			searchResults.remove(searchResults.lastEntry().getKey(), searchResults.lastEntry().getValue());
		}

		return result;
	}

	private TreeMap<Double, String> runSearch() {
		KMPScoreCalculator search = new KMPScoreCalculator(query, document, inarowWeight, scoreWeight);
		return search.getResult();
	}

	public ObservableList<String> getRelatedWords(int numOfResultsConsidered) {
		ObservableList<String> searchResults = getTopResults(numOfResultsConsidered);
		String topSearches = changeToString(searchResults);
		DocumentContainer searches = new DocumentContainer(topSearches);
		ArrayList<Pair<String, Double>> searchesWithScores = searches.getTermFrequency();
		return arrayToObservableList(searchesWithScores);
	}

	private String changeToString(ObservableList<String> searchResults) {
		String result = "";
		for (int x = 0; x < searchResults.size(); x++) {
			result += " " + searchResults.get(x);
		}
		result = result.substring(1);
		return result;
	}

	private ObservableList<String> arrayToObservableList(ArrayList<Pair<String, Double>> array) {
		ObservableList<String> result = FXCollections.observableArrayList();
		for (int x = 0; x < array.size(); x++) {
			result.add(array.get(x).getKey());
		}
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
	private boolean isPhrase() {
		if (query.indexOf(' ') != -1 && (query.indexOf(' ') != 0) && (query.indexOf(' ') != query.length() - 1)) {
			return true;
		}
		return false;
	}

}
