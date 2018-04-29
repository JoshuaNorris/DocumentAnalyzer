package Parser.Documents;

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.HashMap;
import java.util.TreeMap;
import Parser.Documents.ParsingDocuments.SentenceGenerator;
import Parser.Documents.ParsingDocuments.SentenceandWordGenerator;
=======

>>>>>>> fb1832b4597561b982e5f37b35d795b28a4d10a6
import Parser.Documents.ParsingDocuments.TermFrequencyGenerator;
import Parser.Documents.ParsingDocuments.WordGenerator;
import javafx.util.Pair;

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
	
<<<<<<< HEAD
	public TreeMap<String, Double> getTermFrequency() {
=======
	public ArrayList<Pair<String, Double>> getTermFrequency() {
>>>>>>> fb1832b4597561b982e5f37b35d795b28a4d10a6
		TermFrequencyGenerator tf = new TermFrequencyGenerator(getWords());
		return tf.getResult();
	}
	
<<<<<<< HEAD
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
	
=======
>>>>>>> fb1832b4597561b982e5f37b35d795b28a4d10a6
}
