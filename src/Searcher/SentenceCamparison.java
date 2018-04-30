package Searcher;

import java.util.ArrayList;

public class SentenceCamparison {
	
	private String sentence;
	private ArrayList<Integer> ranks;
	private int numOfSentences;
	private int score;
	
	public SentenceCamparison(String sentence, ArrayList<Integer> ranks, int numOfSentences) {
		this.sentence = sentence;
		this.ranks = ranks;
		this.numOfSentences = numOfSentences;
		this.score = calculateScore();
	}
	
	private int calculateScore() {
		int result = 0;
		for (int x = 0; x < ranks.size(); x++) {
			result += (ranks.get(x) - numOfSentences);
		}
		return result;
	}

	public String getSentence() {return sentence;};
	public int getScore() {return score;};

}
