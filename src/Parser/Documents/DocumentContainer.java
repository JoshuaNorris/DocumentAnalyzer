package Parser.Documents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import Parser.Documents.ParsingDocuments.SentenceGenerator;
import Parser.Documents.ParsingDocuments.SentenceandWordGenerator;

import Parser.Documents.ParsingDocuments.TermFrequencyGenerator;
import Parser.Documents.ParsingDocuments.WordGenerator;
import javafx.util.Pair;

public class DocumentContainer {

	private String doc;
	private ArrayList<String> sentences;
	private ArrayList<ArrayList<String>> sentencesWithWords;
	private ArrayList<String> words;
	private ArrayList<Pair<String, Double>> termFreq;
	
	
	public DocumentContainer (String doc) {
		this.doc = doc;
		this.sentences = makeSentences();
		this.sentencesWithWords = makeSentencesWithWords();
		this.words = makeWords();
		this.termFreq = makeTermFrequency();
	}
	
	public String getText() {return doc;}
	public ArrayList<String> getWords() {return words;}
	public ArrayList<String> getSentences() {return sentences;}
	public ArrayList<ArrayList<String>> getSentencesWithWords() {return sentencesWithWords;}
	public ArrayList<Pair<String, Double>> getTermFrequency() {return termFreq;}
	
	private ArrayList<String> makeWords() {
		WordGenerator words = new WordGenerator(doc);
		return words.run();
	}
	
	private ArrayList<Pair<String, Double>> makeTermFrequency() {
		TermFrequencyGenerator tf = new TermFrequencyGenerator(words);
		return tf.getResult();
	}
	
	private ArrayList<String> makeSentences() {
		SentenceGenerator sentences = new SentenceGenerator(doc);
		return sentences.run();
	}
	
	private ArrayList<ArrayList<String>> makeSentencesWithWords() {
		SentenceandWordGenerator sentencesAndWords = new SentenceandWordGenerator(sentences);
		return sentencesAndWords.run();
	}
	
	// result.lastEntry() gives the highest value of the TreeMap result
	// result.firstEntry() gives the lowest value of the TreeMap result
	
}
