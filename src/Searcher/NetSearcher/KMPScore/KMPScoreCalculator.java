package Searcher.NetSearcher.KMPScore;

import java.util.ArrayList;

public class KMPScoreCalculator {

	private String query;
	private ArrayList<String> sentence;

	public KMPScoreCalculator(String query, ArrayList<String> sentence) {
		this.query = query.toLowerCase();
		this.sentence = sentence;
	}
	
	public KMPScoreCalculator(String query, String word) {
		this.query = query;
		ArrayList<String> wordToSentence = new ArrayList<String>();
		word = word.toLowerCase();
		query = query.toLowerCase();
		wordToSentence.add(word);
		this.sentence = wordToSentence;
	}

	public double run() {

		ArrayList<String> tempSentence = sentence;
		double x = run(0.0, tempSentence);
		return x;
	}

	private double run(double num, ArrayList<String> sentence) {
		for (int x = 0; x < sentence.size(); x++) {
			num += getWordScore(sentence.get(x));
		}
		num = num / sentence.size();
		return num;
	}

	private double getWordScore(String str) {
		return checkForPartialMatch(str, query, 0);
	}

	private double checkForPartialMatch(String str, String query, double num) {
		char queryFirstLetter = query.charAt(0);

		if (str.equals("")) {
			return num;
		} else {
			char firstLetter = str.charAt(0);
			if (queryFirstLetter == firstLetter || (Character.toUpperCase(queryFirstLetter) == firstLetter)) {
				num += calculateScore(str, query);
			} // I could add a case to check for the second letter here.
			str = str.substring(1);
			return checkForPartialMatch(str, query, num);
		}
	}

	public static double calculateScore(String str, String query) {
		return calculateScoreHelper(str, query, str.charAt(0), str.charAt(0), 0, 0, 0, 0, 0.0);
	}

	private static double calculateScoreHelper(String str, String query, char expectedChar, char nextChar,
			int expectedCharIndex, int nextCharIndex, int skippedNum, int inarow, double num) {
		if (str.equals("") || skippedNum == 3) {
			return num;
		} else {
			return checkForMatchingLetters(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
					skippedNum, inarow, num);
		}
	}

	private static double checkForMatchingLetters(String str, String query, char expectedChar, char nextChar,
			int expectedCharIndex, int nextCharIndex, int skippedNum, int inarow, double num) {
		
		char firstLetter = str.charAt(0);
		str = str.substring(1);
		if (firstLetter == expectedChar) {
			inarow++;
			return checkQueryLengthThenIterate(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
					skippedNum, inarow, num, true);
		} else if (firstLetter == nextChar) {
			inarow++;
			return checkQueryLengthThenIterate(str, query, nextChar, expectedChar, nextCharIndex, expectedCharIndex,
					skippedNum, inarow, num, false);
		} else {
			inarow = 0;
			if (nextCharIndex == query.length() - 1) {
				return num;
				
			} else if (expectedCharIndex == query.length() - 1) {
				skippedNum++;
				return calculateScoreHelper(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
						skippedNum, inarow, num);
				
			} else {
				expectedCharIndex++;
				expectedChar = query.charAt(expectedCharIndex);
				skippedNum++;
				return calculateScoreHelper(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
						skippedNum, inarow, num);
			}
		}

	}

	private static double checkQueryLengthThenIterate(String str, String query, char expectedChar, char nextChar,
			int expectedCharIndex, int nextCharIndex, int skippedNum, int inarow, double num, boolean isExpectedChar) {
		if (expectedCharIndex == query.length()) {
			return num;
		} else if (expectedCharIndex == query.length() - 1) {
			return checkMatchedLetter(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
					skippedNum, inarow, num, isExpectedChar);
		} else {
			return iterateMatchedLetter(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
					skippedNum, inarow, num, isExpectedChar);
		}

	}

	private static double checkMatchedLetter(String str, String query, char expectedChar, char nextChar,
			int expectedCharIndex, int nextCharIndex, int skippedNum, int inarow, double num, boolean isExpectedChar) {
		nextCharIndex = expectedCharIndex;
		nextChar = expectedChar;
		skippedNum = 0;
		if (isExpectedChar) {
			num++;
			num += inarow;
			return calculateScoreHelper(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
					skippedNum, inarow, num);
		} else {
			num = num + .5;
			num += (inarow * .5);
			return calculateScoreHelper(str, query, nextChar, expectedChar, nextCharIndex, expectedCharIndex,
					skippedNum, inarow, num);
		}
	}

	private static double iterateMatchedLetter(String str, String query, char expectedChar, char nextChar,
			int expectedCharIndex, int nextCharIndex, int skippedNum, int inarow, double num, boolean isExpectedChar) {
		expectedCharIndex++;
		expectedChar = query.charAt(expectedCharIndex);
		nextCharIndex = expectedCharIndex;
		nextChar = expectedChar;
		skippedNum = 0;
		if (isExpectedChar) {
			num++;
			num += inarow;
			return calculateScoreHelper(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
					skippedNum, inarow, num);
		} else {
			num = num + .5;
			num += (inarow * .5);
			return calculateScoreHelper(str, query, nextChar, expectedChar, nextCharIndex, expectedCharIndex,
					skippedNum, inarow, num);
		}
	}

}