package Parser.Documents;

import java.util.ArrayList;
import java.util.HashMap;

import Parser.Documents.ParsingDocuments.SentenceGenerator;
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

}
