package Searcher.NetSearcher.KMPScore;

import java.util.Map;
import java.util.TreeMap;

public class KMPScoreCalculator {

	private String query;
	private String document;
	private TreeMap<Double, String> result = new TreeMap<Double, String>();

	// is I search stead homestead will have the same score
	
	public KMPScoreCalculator(String query, String document) {
		this.query = query.toLowerCase();
		this.document = document;
		this.result = calculateResult();
	}
	
	public TreeMap<Double, String> getResult () {return result;};
	
	private TreeMap<Double, String> calculateResult() {
		char firstLetter = query.charAt(0);
		char thisChar = document.charAt(0);
		String currentSentence = "";
		double currentScore = 0.0;
		
		for (int x = 0; x < document.length(); x++) {
			thisChar = document.charAt(x);
			currentSentence += thisChar;
			if (thisChar == '.' || thisChar == '!' || thisChar == '?') { // add to make this work for the end of sentences.
				result.put(currentScore, currentSentence);
				currentSentence = "";
				currentScore = 0.0;
			} else if (Character.toLowerCase(thisChar) == firstLetter) {
				currentScore += ifFirstLettterGetScore(x, firstLetter);
			}
			
		}
		return result;
	}

	private double ifFirstLettterGetScore(int x, char firstLetter) {
		int nextLetterIndex = 0;
		
		// get a generated list of prefixes and postfixes
		
		boolean loop = true;
		int skippedLetters = 0;
		double score = 0.0;
		
		while (loop) {
			if (x == document.length() || (skippedLetters == 3) || (document.charAt(x) == ' ')) {
				loop = false;
			} else if ((nextLetterIndex < query.length()) && document.charAt(x) == query.charAt(nextLetterIndex)) {
				score ++;
				skippedLetters = 0;
				nextLetterIndex ++;
			} else if ((nextLetterIndex + skippedLetters < query.length()) && (document.charAt(x) == query.charAt(nextLetterIndex + skippedLetters))) {
				score ++;
				skippedLetters = 0;
				nextLetterIndex += skippedLetters + 1;
			} else if ((nextLetterIndex + 1 < query.length()) && (document.charAt(x) == query.charAt(nextLetterIndex + 1))) {
				score ++;
				skippedLetters = 0;
				nextLetterIndex += 2;
			} else {
				skippedLetters ++;
			}
			x ++;
		}
		return score;		
	}
	
}