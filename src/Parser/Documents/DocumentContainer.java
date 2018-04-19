package Parser.Documents;

import java.util.ArrayList;
import java.util.HashMap;

import Parser.Documents.ParsingDocuments.SentenceGenerator;
import Parser.Documents.ParsingDocuments.SentenceandWordGenerator;
import Parser.Documents.ParsingDocuments.TermFrequencyGenerator;
import Parser.Documents.ParsingDocuments.WordGenerator;

public class DocumentContainer {

	private String doc;
	
	
	public DocumentContainer (String doc) {
		this.doc = doc;
	}
	
	public ArrayList<String> getSentences() {
		SentenceGenerator sentences = new SentenceGenerator(doc);
		return sentences.run();
	}
	
	public ArrayList<String> getWords() {
		WordGenerator words = new WordGenerator(doc);
		return words.run();
	}
	
	public HashMap<String, Double> getTermFrequency() {
		TermFrequencyGenerator tf = new TermFrequencyGenerator(getWords());
		return tf.run();
	}
	
	public ArrayList<ArrayList<String>> getSentencesWithWords() {
		SentenceandWordGenerator sentencesAndWords = new SentenceandWordGenerator(getSentences());
		return sentencesAndWords.run();
	}

}
