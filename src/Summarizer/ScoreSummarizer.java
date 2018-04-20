package Summarizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import edu.stanford.nlp.simple.Document;
//import edu.stanford.nlp.simple.Sentence;
//import edu.stanford.nlp.simple.Token;

public class ScoreSummarizer {
/**
	private LinkedHashMap<Sentence, Double> pileOfSentences = new LinkedHashMap<Sentence, Double>();
	private ArrayList<String> ignoreWords;
	private ArrayList<Sentence> finalSentences;

	public void stopMaker() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("stop-words-list.txt"));
		while (reader.readLine() != null) {
			ignoreWords.add(reader.readLine());
		}
		reader.close();
	}

	public void ScoreSentences(String text) {
		Document userText = new Document(text);
		List<Sentence> sentences = userText.sentences();
		for (Sentence potential : sentences) {
			pileOfSentences.put(potential, 0.0);
			for (Token word : potential.tokens()) {
				String scoreWord = word.word();
				if (!ignoreWords.contains(scoreWord)) {
					pileOfSentences.put(potential, pileOfSentences.get(potential) + 1);
				}

			}

		}

	}

	//public ArrayList topReturner() {

	//} */

}
