package Parser.Documents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import Parser.Documents.ParsingDocuments.SentenceGenerator;
import Parser.Documents.ParsingDocuments.SentenceandWordGenerator;
import Parser.Documents.ParsingDocuments.TermFrequencyGenerator;
import Parser.Documents.ParsingDocuments.WordGenerator;

public class DocumentContainer {

	private String doc;
	
	
	public DocumentContainer (String doc) {
		this.doc = doc;
	}
	
	public String getText() {return doc;}
	
	public ArrayList<String> getWords() {
		WordGenerator words = new WordGenerator(doc);
		return words.run();
	}
	
	public TreeMap<String, Double> getTermFrequency() {
		TermFrequencyGenerator tf = new TermFrequencyGenerator(getWords());
		return tf.getResult();
	}
	
	public ArrayList<String> getSentences() {
		SentenceGenerator sentences = new SentenceGenerator(doc);
		return sentences.run();
	}
	
	public ArrayList<ArrayList<String>> getSentencesWithWords() {
		SentenceandWordGenerator sentencesAndWords = new SentenceandWordGenerator(getSentences());
		return sentencesAndWords.run();
	}
	
	// result.lastEntry() gives the highest value of the TreeMap result
	// result.firstEntry() gives the lowest value of the TreeMap result
	
}
