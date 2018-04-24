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
/**
public class ScoreSummarizer {

	private ArrayList<Sentence> allSentences = new ArrayList<Sentence>();
	private ArrayList<Integer> scores = new ArrayList<Integer>();
	private ArrayList<String> ignoreWords = new ArrayList<String>();
	private ArrayList<Sentence> finalSentences = new ArrayList<Sentence>();

	public ScoreSummarizer() {
	}

	private LinkedHashMap<Sentence, Double> pileOfSentences = new LinkedHashMap<Sentence, Double>();
	private ArrayList<String> ignoreWords;
	private ArrayList<Sentence> finalSentences;

	public void stopMaker() throws IOException {
		File file = new File("C:\\Users\\colby\\Documents\\CSCI 270 Computational Humanities\\stop-words-list.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		while (reader.readLine() != null) {
			ignoreWords.add(reader.readLine());
		}

	}

	public void ScoreSentences(String text) {
		int score = 0;
		Document userText = new Document(text);
		List<Sentence> sentences = userText.sentences();
		for (Sentence potential : sentences) {
			allSentences.add(potential);
			for (Token word : potential.tokens()) {
				String scoreWord = word.word();
				if (!ignoreWords.contains(scoreWord)) {
					score += 1;
				}

			}
			scores.add(score);
			score = 0;
		}

	}

	public void topReturner() {
		ArrayList<Integer> bestScoring = new ArrayList<Integer>();
		bestScoring.addAll(scores);
		bestScoring.sort(null);
		System.out.println(allSentences);
		System.out.println(scores);
		System.out.println(bestScoring);


	}
	//} 

}*/
