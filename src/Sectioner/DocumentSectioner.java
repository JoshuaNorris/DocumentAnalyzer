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
	
	public DocumentSectioner(String text) {
		this.document = new DocumentContainer(text);
		section();
		highestWordWanted = 0; //on first iteration, functions want the highest possible scoring word
							  //it is possible that one word is the most important for the entire document
							 //So we might have to do this more than once with second most important words
	}
	
	public String getLargerText() {return document.getText();}
	public DocumentContainer getDoc() {return document;}
	public ArrayList<String> getSectionedText() {return sectionedText;}

	private void section() {
		ArrayList<String> finalSections = new ArrayList<String>();
		int lowerSentence = 0;
		int upperSentence = getSentenceIndexByPercent(lowerSentence, 1); //arbitrary 1% chosen to start the sectioning process
		Optional<Pair<String, Double>> highScorer = null;
		sectionOut(lowerSentence, upperSentence, highScorer);
	}
	
	public int getSentenceIndexByPercent(int lowerBound, int percent) {
		int index = 0;
		int documentLength = document.getSentences().size();
		double percentConverter = percent/100.0;
		double temp = documentLength * percentConverter;
		index = (int) (lowerBound + Math.floor(temp));
		return index;
	}
	
	private void sectionOut(int lower, int upper, Optional<Pair<String, Double>> prevHighScore) {
		ArrayList<String> wordsList = buildWordsListForTermFreq(lower, upper);
		Pair<String, Double> topWordStats = getDesiredScore(wordsList); 
		Pair<String, Double> compareToPrevStats = prevHighScore.get();
		boolean wordChanged = mostImportantWordIsDifferentWord(topWordStats, compareToPrevStats);
		if (!prevHighScore.isPresent()) {
			int nextPercent = 10;
			Optional<Pair<String,Double>> newHighScore = Optional.of(topWordStats);
			sectionOut(upper, getSentenceIndexByPercent(upper, nextPercent), newHighScore);
		} else if(wordChanged) {
			
		}
	}

	private boolean mostImportantWordIsDifferentWord(Pair<String, Double> topWordStats, Pair<String, Double> compareToPrevStats) {
		boolean bool = false;
		if(!compareToPrevStats.getKey().equals(topWordStats.getKey())) {
			bool = true;
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
