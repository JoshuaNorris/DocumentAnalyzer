package Parser.Documents.ParsingDocuments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class TermFrequencyGenerator {

	private ArrayList<String> words;
	private TreeMap<String, Double> result;
	
	public TermFrequencyGenerator(ArrayList<String> words) {
		this.words = words;
		this.result = calculateResult();
	}

	public TreeMap<Double, String> getResult() {return result;};
	
	private TreeMap<String, Double> calculateResult() {
		TreeMap<String, Double> tf = new TreeMap<String, Double>();
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
	
	private TreeMap<Double, String> averageWordcounts(TreeMap<String, Double> tf, ArrayList<String> noDuplicates) {
		for (int x = 0; x < noDuplicates.size(); x++) {
			tf.put(noDuplicates.get(x), tf.get(noDuplicates.get(x)) / words.size());
		}
		return tf;
	}

}