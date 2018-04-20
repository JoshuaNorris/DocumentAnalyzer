package Parser.Documents.ParsingDocuments;

import java.util.ArrayList;
import java.util.HashMap;

public class TermFrequencyGenerator {

	private ArrayList<String> words;
	
	public TermFrequencyGenerator(ArrayList<String> words) {
		this.words = words;
	}

	public HashMap<String, Double> run() {
		HashMap<String, Double> tf = new HashMap<String, Double>();
		ArrayList<String> noDuplicates = new ArrayList<String>();
		for (int x = 0; x < words.size(); x++) {
			if (!tf.containsKey(words.get(x))) {
				tf.put(words.get(x), 1.0);
				noDuplicates.add(words.get(x));
			} else {
				tf.put(words.get(x), tf.get(words.get(x)) + 1.0);
			}
		}
	
		tf = averageWordcounts(tf, noDuplicates);
		
		return tf;
	}

	private HashMap<String, Double> averageWordcounts(HashMap<String, Double> tf, ArrayList<String> noDuplicates) {
		for (int x = 0; x < noDuplicates.size(); x++) {
			tf.put(noDuplicates.get(x), tf.get(noDuplicates.get(x)) / words.size());
		}
		return tf;
	}

}