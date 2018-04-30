package Summarizer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Database.Database;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.Token;

public class FreqSummarizer {

	private Set<String> words = new HashSet<String>();
	private HashMap<String, Double> wordFreqs = new HashMap<String, Double>();
	private Database articleDatabase;


	public FreqSummarizer(int numberOfSentences, Database articleDatabase) {
		this.articleDatabase = articleDatabase;
	}

	public void getAllWords(String text) {
		for (String word : text.split(" ")) {
			words.add(word);
		}
	}

	public int getLength(String text) {
		int length = text.split(" ").length;
		return length;
	}

	public void initializeFreqs() {
		for (String word : words) {
			wordFreqs.put(word, 0.0);
		}
	}

	public void setWordFreqs(String text) {
		int length = getLength(text);
		Document userText = new Document(text);
		List<Sentence> sentences = userText.sentences();
		for (Sentence line : sentences) {
			for (Token word : line.tokens()) {
				String freqWord = word.word();
				wordFreqs.put(freqWord, (wordFreqs.get(freqWord) + 1) / length);
			}

		}
	}

	public void scoreSentences(String text, String title) throws SQLException {
		Double score = 0.0;
		int count = 0;
		Document userText = new Document(text);
		List<Sentence> sentences = userText.sentences();
		for (Sentence line : sentences) {
			for (Token word : line.tokens()) {
				String scoreWord = word.word();
				score += wordFreqs.get(scoreWord);
			}
			articleDatabase.insertSummarySentence(title, line.toString(), count, score);
			score = 0.0;
			count += 1;
		}
	}
}
