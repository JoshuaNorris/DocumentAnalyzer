package Searcher;

import java.util.ArrayList;

import javafx.collections.ObservableList;

public class PhraseSearcher {

	private String[] query;
	private String document;
	private double inarowWeight;
	private double scoreWeight;
	private int numOfResults;
	
	public PhraseSearcher(String strQuery, String document, double inarowWeight, double scoreWeight, int numOfResults) {
		strQuery = removeWhitespace(strQuery);
		this.query = strQuery.split(" ");
		this.document = document;
		this.inarowWeight = inarowWeight;
		this.scoreWeight = scoreWeight;
		this.numOfResults = numOfResults;
	}

	public ObservableList<String> getResults() {
		ArrayList< ObservableList <String>> wordByWordResults = populateWordByWordResults(); 
		ArrayList< SentenceCamparison> sentenceRanks = populateSentenceRanks(wordByWordResults);
	}
	
	
	
	private ArrayList<SentenceCamparison> populateSentenceRanks(ArrayList<ObservableList<String>> rankings) {
		ArrayList< SentenceCamparison> result = new ArrayList< SentenceCamparison>();
		
		ObservableList<String> comparisonList = rankings.get(0);
		rankings.remove(0);
		
		for (int x = 0; x < comparisonList.size(); x++) {
			String currentSentence = comparisonList.get(x);
			ArrayList<Integer> ranks = new ArrayList<Integer>();
			for (int y = 0; y < rankings.size(); y++) {
				ranks.add(rankings.get(y).indexOf(currentSentence));
			}
			result.add(new SentenceCamparison(currentSentence, ranks, rankings.size()));
		}
		return result;
	}

	private ArrayList<ObservableList<String>> populateWordByWordResults() {
		ArrayList< ObservableList<String>> wordByWordResults = new ArrayList< ObservableList<String>>();
		
		for (int x = 0; x < query.length; x++) {
			Searcher oneWordSearch = new Searcher (query[x], document, inarowWeight, scoreWeight, Integer.MAX_VALUE);
			wordByWordResults.add(oneWordSearch.getSearchResults());
		}
		return wordByWordResults;
	}

	public static String removeWhitespace(String str) {
		if (str.charAt(0) == ' ') {
			str = removeWhitespace(str.substring(1));
		} else if (str.charAt(str.length() - 1) == ' ') {
			str = removeWhitespace(str.substring(0, str.length() - 1));
		} else {
			return str;
		}
		return str;
	}

}
