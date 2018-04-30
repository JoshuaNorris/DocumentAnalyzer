package Summarizer;

import java.sql.SQLException;
import java.util.List;
import Database.Database;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.Token;

public class ScoreSummarizer {

	private Database articleDatabase;
	private StopWordMaker stopper;

	public ScoreSummarizer(StopWordMaker stopper, Database articleDatabase) {
		this.articleDatabase = articleDatabase;
		this.stopper = stopper;
	}

	public void scoreSentences(String text, String title) throws SQLException {
		Double score = 0.0;
		int count = 0;
		Document userText = new Document(text);
		List<Sentence> sentences = userText.sentences();
		for (Sentence potential : sentences) {
			for (Token word : potential.tokens()) {
				String scoreWord = word.word();
				if (!stopper.stopWords.contains(scoreWord)) {
					score += 1;
				}

			}
			articleDatabase.insertSummarySentence(title, potential.toString(), count, score);
			score = 0.0;
			count += 1;
		}

	}

	public String topReturner(String title) throws SQLException {
		int length = articleDatabase.getFullTextOf(title).split(" ").length;
		int numSentences = 0;
		if (length > 1000) {
			numSentences = 10;
		} else if (length > 500) {
			numSentences = 7;
		} else if (length > 300) {
			numSentences = 5;
		} else {
			numSentences = 3;
		}

		return articleDatabase.getTopSentencesOf(title, numSentences);

	}

}
