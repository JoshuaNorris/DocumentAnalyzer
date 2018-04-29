package Parser.Documents;

import java.util.ArrayList;

import Parser.Documents.ParsingDocuments.TermFrequencyGenerator;
import Parser.Documents.ParsingDocuments.WordGenerator;
import javafx.util.Pair;

public class DocumentContainer {

	private String doc;
	
	
	public DocumentContainer (String doc) {
		this.doc = doc;
	}
	
	public ArrayList<String> getWords() {
		WordGenerator words = new WordGenerator(doc);
		return words.run();
	}
	
	public ArrayList<Pair<String, Double>> getTermFrequency() {
		TermFrequencyGenerator tf = new TermFrequencyGenerator(getWords());
		return tf.getResult();
	}
	
}
