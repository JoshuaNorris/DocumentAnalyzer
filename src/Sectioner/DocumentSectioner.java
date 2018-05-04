package Sectioner;

import java.util.ArrayList;
import java.util.Optional;

import Parser.Documents.DocumentContainer;
import Parser.Documents.ParsingDocuments.TermFrequencyGenerator;
import javafx.util.Pair;

public class DocumentSectioner {
	private DocumentContainer document;
	private ArrayList<String> sectionedText;
	private int highestWordWanted;
	private int arbitraryPercent;
	private ArrayList<String> sentences;
	private int numSections;

	public DocumentSectioner(String text, int percent) {
		this.document = new DocumentContainer(text);
		highestWordWanted = 0;
		/*
		 * on first iteration, functions want the highest possible scoring word it is
		 * possible that one word is the most important for the entire document So we
		 * might have to do this more than once with second most important words not yet
		 * implemented
		 */
		arbitraryPercent = percent;
		this.sentences = document.getSentences();
		sectionedText = new ArrayList<String>();
		sectionedText.add("");
		numSections = 0;
		section();
	}

	public String getLargerText() {
		return document.getText();
	}

	public DocumentContainer getDoc() {
		return document;
	}

	public ArrayList<String> getSectionedText() {
		return sectionedText;
	}

	private void section() {
		int lowerSentence = 0;
		int upperSentence = getSentenceIndexByPercent(lowerSentence, arbitraryPercent);
		Optional<Pair<String, Double>> highScorer = Optional.empty();
		sectionOut(lowerSentence, upperSentence, highScorer);
	}

	public int getSentenceIndexByPercent(int lowerBound, int percent) {
		int index = 0;
		int documentLength = sentences.size();
		double percentConverter = percent / 100.0;
		double temp = documentLength * percentConverter;
		index = (int) (lowerBound + Math.ceil(temp));
		index = checkTopIndex(index);
		return index;
	}

	private int checkTopIndex(int index) {
		int i = index;
		int docSize = sentences.size();
		if (i >= docSize) {
			i = docSize - 1;
		}
		return i;
	}

	private void sectionOut(int lower, int upper, Optional<Pair<String, Double>> prevHighScore) {
		System.out.println(lower);
		System.out.println(upper);

		ArrayList<String> wordsList = buildWordsListForTermFreq(lower, upper);
		Pair<String, Double> topWordStats = getDesiredScore(wordsList);
		boolean wordChanged = mostImportantWordIsDifferentWord(topWordStats, prevHighScore);
		int nextLowerBound = upper + 1;
		int nextUpperBound = getSentenceIndexByPercent(nextLowerBound, arbitraryPercent);
		Optional<Pair<String, Double>> newHighScore = Optional.of(topWordStats);

		if (upper == sentences.size() - 1 | lower == sentences.size() - 1) {
			System.out.println("HERE");
			addToThisSection(lower, upper);
			return;
		}
		
		if (!prevHighScore.isPresent()) {
			System.out.println("NUMBA 1");
			addToThisSection(lower, upper);
			sectionOut(nextLowerBound, nextUpperBound, newHighScore);
			return;
		} else if (wordChanged) {
			numSections++;
			sectionedText.add("");
		}
		
		addToThisSection(lower, upper);
		sectionOut(nextLowerBound, nextUpperBound, newHighScore);
	}

	private void addToThisSection(int lowerBound, int upperBound) {
		String section = "";
		for (int i = lowerBound; i <= upperBound; i++) {
			section += sentences.get(i);
		}
		String text = sectionedText.get(numSections);
		text += section;
		sectionedText.set(numSections, text);
	}

	private boolean mostImportantWordIsDifferentWord(Pair<String, Double> topWordStats,
			Optional<Pair<String, Double>> prevHighScore) {
		boolean bool = false;
		if (prevHighScore.isPresent()) {
			String firstHighWord = prevHighScore.get().getKey();
			String secondHighWord = topWordStats.getKey();
			System.out.println(firstHighWord);
			System.out.println(secondHighWord);

			if (!firstHighWord.equals(secondHighWord) & !firstHighWord.contains(secondHighWord) & !secondHighWord.contains(firstHighWord)) {
				bool = true;
			}
			
		}
		return bool;
	}

	private Pair<String, Double> getDesiredScore(ArrayList<String> wordsList) {
		TermFrequencyGenerator freq = new TermFrequencyGenerator(wordsList);
		ArrayList<Pair<String, Double>> results = freq.getResult();
		return results.get(highestWordWanted);
	}

	private ArrayList<String> buildWordsListForTermFreq(int lowerBound, int upperBound) {
		ArrayList<String> builder = new ArrayList<String>();

		for (int i = lowerBound; i <= upperBound; i++) {
			builder.addAll(document.getSentencesWithWords().get(i));
		}
		return builder;
	}

}
