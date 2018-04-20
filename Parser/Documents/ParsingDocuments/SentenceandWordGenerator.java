package Parser.Documents.ParsingDocuments;

import java.util.ArrayList;

public class SentenceandWordGenerator {

	private ArrayList<String> sentences = new ArrayList<String>();

	public SentenceandWordGenerator(ArrayList<String> sentences) {
		this.sentences = sentences;
	}

	public ArrayList<ArrayList<String>> run() {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		
		for (int x = 0; x < sentences.size(); x++) {
			String str = sentences.get(x);
			WordGenerator words = new WordGenerator(str);
			ArrayList<String> splittedSentence = words.run();
			result.add(splittedSentence);
		}

		return result;
	}

}
