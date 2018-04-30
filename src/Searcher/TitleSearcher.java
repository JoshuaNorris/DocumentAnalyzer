package Searcher;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TitleSearcher {

	private String query;
	private ObservableList<String> titles = FXCollections.observableArrayList();
	private ObservableList<String> searchResults = FXCollections.observableArrayList();

	public TitleSearcher(String query, ObservableList<String> titles) {
		System.out.println(titles);
		this.query = query;
		this.titles = titles;
		this.searchResults = calculateSearchResults();
	}

	public ObservableList<String> getSearchResults() {return searchResults;};

	private ObservableList<String> calculateSearchResults() {
		Searcher searcher = new Searcher (query, titles, 10);
		ObservableList<String> result = searcher.getSearchResults();
		return result;
	}

}
