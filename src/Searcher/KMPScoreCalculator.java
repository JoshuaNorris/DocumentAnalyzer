package Searcher;

import java.util.ArrayList;

public class KMPScoreCalculator {

	private String query;
	private ArrayList<String> sentence;

	public KMPScoreCalculator(String query, ArrayList<String> sentence) {
		this.query = query;
		this.sentence = sentence;
	}

	public double run() {

		return run(0.0, sentence);
	}

	private double run(double num, ArrayList<String> sentence) {
		if (sentence.isEmpty()) {
			return num;
		} else {
			double wordScore = getWordScore(sentence.get(0));
			sentence.remove(0);
			return run((num + wordScore), sentence);
		}
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
			if (queryFirstLetter == firstLetter) {
				num += calculateScore(str, query);
			} // I could add a case to check for the second letter here.
			str = str.substring(1);
			return checkForPartialMatch(str, query, num);
		}
	}

	public static double calculateScore(String str, String query) {
		return calculateScoreHelper(str, query, str.charAt(0), str.charAt(0), 0, 0, 0, 0.0);
	}

	private static double calculateScoreHelper(String str, String query, char expectedChar, char nextChar,
			int expectedCharIndex, int nextCharIndex, int skippedNum, double num) {
		if (str.equals("") || skippedNum == 3) {
			return num;
		} else {
			return checkForMatchingLetters(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
					skippedNum, num);
		}
	}

	private static double checkForMatchingLetters(String str, String query, char expectedChar, char nextChar,
			int expectedCharIndex, int nextCharIndex, int skippedNum, double num) {
		
		char firstLetter = str.charAt(0);
		str = str.substring(1);
		if (firstLetter == expectedChar) {
			return checkQueryLengthThenIterate(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
					skippedNum, num, true);
		} else if (firstLetter == nextChar) {
			return checkQueryLengthThenIterate(str, query, nextChar, expectedChar, nextCharIndex, expectedCharIndex,
					skippedNum, num, false);
		} else {
			if (nextCharIndex == query.length() - 1) {
				return num;
				
			} else if (expectedCharIndex == query.length() - 1) {
				skippedNum++;
				return calculateScoreHelper(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
						skippedNum, num);
				
			} else {
				expectedCharIndex++;
				expectedChar = query.charAt(expectedCharIndex);
				skippedNum++;
				return calculateScoreHelper(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
						skippedNum, num);
				
			}
		}

	}

	private static double checkQueryLengthThenIterate(String str, String query, char expectedChar, char nextChar,
			int expectedCharIndex, int nextCharIndex, int skippedNum, double num, boolean isExpectedChar) {
	
		if (expectedCharIndex == query.length()) {
			return num;
		} else if (expectedCharIndex == query.length() - 1) {
			return checkMatchedLetter(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
					skippedNum, num, isExpectedChar);
		} else {
			return iterateMatchedLetter(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
					skippedNum, num, isExpectedChar);
		}

	}

	private static double checkMatchedLetter(String str, String query, char expectedChar, char nextChar,
			int expectedCharIndex, int nextCharIndex, int skippedNum, double num, boolean isExpectedChar) {
		nextCharIndex = expectedCharIndex;
		nextChar = expectedChar;
		skippedNum = 0;
		if (isExpectedChar) {
			num++;
			return calculateScoreHelper(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
					skippedNum, num);
		} else {
			num = num + .5;
			return calculateScoreHelper(str, query, nextChar, expectedChar, nextCharIndex, expectedCharIndex,
					skippedNum, num);
		}
	}

	private static double iterateMatchedLetter(String str, String query, char expectedChar, char nextChar,
			int expectedCharIndex, int nextCharIndex, int skippedNum, double num, boolean isExpectedChar) {
		expectedCharIndex++;
		expectedChar = query.charAt(expectedCharIndex);
		nextCharIndex = expectedCharIndex;
		nextChar = expectedChar;
		skippedNum = 0;
		if (isExpectedChar) {
			num++;
			return calculateScoreHelper(str, query, expectedChar, nextChar, expectedCharIndex, nextCharIndex,
					skippedNum, num);
		} else {
			num = num + .5;
			return calculateScoreHelper(str, query, nextChar, expectedChar, nextCharIndex, expectedCharIndex,
					skippedNum, num);
		}
	}

}