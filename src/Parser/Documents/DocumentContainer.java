package Parser.Documents;

import java.util.ArrayList;
import java.util.HashMap;

import Parser.Documents.ParsingDocuments.TermFrequencyGenerator;
import Parser.Documents.ParsingDocuments.WordGenerator;

public class DocumentContainer {

	private String doc;
	
	
	public DocumentContainer (String doc) {
		this.doc = doc;
	}
	
	public ArrayList<String> getWords() {
		WordGenerator words = new WordGenerator(doc);
		return words.run();
	}
	
	public HashMap<String, Double> getTermFrequency() {
		TermFrequencyGenerator tf = new TermFrequencyGenerator(getWords());
		return tf.run();
	}
	
}
