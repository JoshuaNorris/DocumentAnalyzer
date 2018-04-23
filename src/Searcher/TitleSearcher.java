package Searcher;

import java.util.ArrayList;

import Searcher.NetSearcher.KMPScore.KMPScoreCalculator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

public class TitleSearcher {

	private String query;
	private ArrayList<String> titles = new ArrayList<String>();
	private ArrayList<String> searchResults = new ArrayList<String>();
	
	public TitleSearcher(String query, ObservableList<String> titles) {
		this.query = query;
		this.titles = observableToArrayList(titles);
		this.searchResults = calculateSearchResults();
	}
	
	public ObservableList<String> getSearchResults() {
		return arrayToObservableList(searchResults);
	}
	

	private ArrayList<String> calculateSearchResults() {
		ArrayList<Pair<String, Double>> scores = getScores(query, titles);
		ArrayList<String> result = orderScores(scores);
		return result;
	}



	private ArrayList<String> orderScores(ArrayList<Pair<String, Double>> scores) {
		ArrayList<String> result = new ArrayList<String>();
		while (!scores.isEmpty()) {
			Pair<String, Double> max = getMax(scores);
			scores.remove(max);
			result.add(max.getKey());
		}
		
		return result;
	}



	private Pair<String, Double> getMax(ArrayList<Pair<String, Double>> scores) {
		double max = 0;
		int index = 0;
		for (int x = 0; x < scores.size(); x++) {
			if (scores.get(x).getValue() > max) {
				max = scores.get(x).getValue();
				index = x;
			}
		}
		return scores.get(index);
	}



	private ArrayList<Pair<String, Double>> getScores(String query, ArrayList<String> titles) {
		ArrayList<Pair<String, Double>> result = new ArrayList<Pair<String, Double>>();
		for (int x = 0; x < titles.size(); x++) {
			KMPScoreCalculator getScore = new KMPScoreCalculator(query, titles.get(x));
			Pair<String, Double> pair = new Pair<String, Double> (titles.get(x), getScore.run());
			result.add(pair);
		}
		return result;
	}



	private ArrayList<String> observableToArrayList(ObservableList<String> observableList) {
		ArrayList<String> result = new ArrayList<String>();
		result.addAll(observableList);
		return result;
	}
	
	private ObservableList<String> arrayToObservableList(ArrayList<String> array) {
		ObservableList<String> result = FXCollections.observableArrayList();
		result.addAll(array);
		return result;
	}
	
}
