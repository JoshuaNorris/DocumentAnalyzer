package Searcher.KMPScore;

import java.util.TreeMap;

public class KMPScoreCalculator {

	private String query;
	private String document;
	private TreeMap<Double, String> result = new TreeMap<Double, String>();

	// is I search stead homestead will have the same score
	
	public KMPScoreCalculator(String query, String document, double inarowWeight, double scoreWeight) {
		this.query = query.toLowerCase() + ' '; // make this a better error handle.
		this.document = document;
		this.result = calculateResult(inarowWeight, scoreWeight);
	}
	
	public KMPScoreCalculator(String query, String document) {
		this(query, document, 1.0, 1.0);
	}

	public TreeMap<Double, String> getResult () {return result;};
	
	private TreeMap<Double, String> calculateResult(double inarowWeight, double scoreWeight) {
		char firstLetter = query.charAt(0);
		char thisChar = document.charAt(0);
		String currentSentence = "";
		double currentScore = 0.0;
		
		for (int x = 0; x < document.length(); x++) {
			thisChar = document.charAt(x);
			currentSentence += thisChar;
			if (thisChar == '|') {
				result.put(currentScore, currentSentence.substring(0, currentSentence.length() - 1));
				currentSentence = "";
				currentScore = 0.0;
			} else if (thisChar == '.' || thisChar == '!' || thisChar == '?') { // add to make this work for the end of sentences.
				result.put(currentScore, currentSentence);
				currentSentence = "";
				currentScore = 0.0;
			} else if (Character.toLowerCase(thisChar) == firstLetter) {
				currentScore += ifFirstLettterGetScore(x, firstLetter, inarowWeight, scoreWeight);
			}
			
		}
		return result;
	}

	private double ifFirstLettterGetScore(int x, char firstLetter, double inarowWeight, double scoreWeight ) {
		int nextLetterIndex = 0;
		
		// get a generated list of prefixes and postfixes
		
		boolean loop = true;
		int skippedLetters = 0;
		double score = 0.0;
		double inarow = 0.0;
		
		while (loop) {
			if (x == document.length() || (skippedLetters == 3) || (document.charAt(x) == ' ')) {
				loop = false;
			} else if ((nextLetterIndex < query.length()) && document.charAt(x) == query.charAt(nextLetterIndex)) {
				inarow += inarowWeight;
				score += inarow + scoreWeight;
				skippedLetters = 0;
				nextLetterIndex ++;
			} else if ((nextLetterIndex + skippedLetters < query.length()) && (document.charAt(x) == query.charAt(nextLetterIndex + skippedLetters))) {
				inarow = 0;
				score += scoreWeight;
				skippedLetters = 0;
				nextLetterIndex += skippedLetters + 1;
			} else if ((nextLetterIndex + 1 < query.length()) && (document.charAt(x) == query.charAt(nextLetterIndex + 1))) {
				inarow = 0;
				score += scoreWeight;
				skippedLetters = 0;
				nextLetterIndex += 2;
			} else {
				inarow = 0;
				skippedLetters ++;
			}
			x ++;
		}
		return score;		
	}

}