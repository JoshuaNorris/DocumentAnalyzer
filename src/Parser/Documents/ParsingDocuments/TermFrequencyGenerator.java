package Parser.Documents.ParsingDocuments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import javafx.util.Pair;

public class TermFrequencyGenerator {

	private ArrayList<String> words;
	private ArrayList< Pair<String, Double>> result;
	
	public TermFrequencyGenerator(ArrayList<String> words) {
		this.words = words;
		this.result = calculateResult();
		
	}
	
	public ArrayList<Pair<String, Double>> getResult() {return result;};

<<<<<<< HEAD
	public TreeMap<String, Double> getResult() {return result;};
=======
	private ArrayList< Pair<String, Double>> calculateResult() {
		HashMap<String, Double> map = run();
		TreeMap<Double, String> result = new TreeMap<Double, String>();
		for (Entry<String, Double> entry : map.entrySet()) {
			result.put(entry.getValue(), entry.getKey());
		}
		return getOrderedList(result);
	}
	
>>>>>>> fb1832b4597561b982e5f37b35d795b28a4d10a6
	
	private ArrayList<Pair<String, Double>> getOrderedList(TreeMap<Double, String> mapResult) {
		ArrayList<Pair<String, Double>> result = new ArrayList<Pair<String, Double>>();
		while (!mapResult.isEmpty()) {
			String value = mapResult.lastEntry().getValue();
			Double key = mapResult.lastKey();
			result.add(new Pair<String, Double>(value, key));
			mapResult.remove(key, value);
		}
		return result;
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
<<<<<<< HEAD
	
	private TreeMap<String, Double> averageWordcounts(TreeMap<String, Double> tf, ArrayList<String> noDuplicates) {
=======

	private HashMap<String, Double> averageWordcounts(HashMap<String, Double> tf, ArrayList<String> noDuplicates) {
>>>>>>> fb1832b4597561b982e5f37b35d795b28a4d10a6
		for (int x = 0; x < noDuplicates.size(); x++) {
			tf.put(noDuplicates.get(x), tf.get(noDuplicates.get(x)) / words.size());
		}
		return tf;
	}

}