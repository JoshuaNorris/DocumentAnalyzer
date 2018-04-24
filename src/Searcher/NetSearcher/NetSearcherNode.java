package Searcher.NetSearcher;

import java.util.ArrayList;

import Searcher.NetSearcher.KMPScore.KMPScoreCalculator;

public class NetSearcherNode {

	private NetSearcherNode prev2;
	private NetSearcherNode prev1;
	private ArrayList<String> sentence;
	private NetSearcherNode next1;
	private NetSearcherNode next2;
	private double thisScore;
	private double avg;
	private String query;
	
	public NetSearcherNode (ArrayList<String> sentence, String query) {
		this.sentence = sentence;
		this.query = query;
		this.thisScore = calculateScore();
	}
	
	public void setPrev2 (NetSearcherNode prev2) {this.prev2 = prev2;};
	public void setPrev1 (NetSearcherNode prev1) {this.prev1 = prev1;};
	public void setNext1 (NetSearcherNode next1) {this.next1 = next1;};
	public void setNext2 (NetSearcherNode next2) {this.next2 = next2;};
	
	public void setAvg () {
		ArrayList<Double> listOfScores = new ArrayList<Double>();
		if (prev2 != null) {
			listOfScores.add(prev2.getScore());
		}
		if (prev1 != null) {
			listOfScores.add(prev2.getScore());
		}
		if (next1 != null) {
			listOfScores.add(prev2.getScore());
		}
		if (next2 != null) {
			listOfScores.add(prev2.getScore());
		}
		this.avg = getSum(listOfScores) / listOfScores.size();
	}
	
	private double getSum(ArrayList<Double> listOfScores) {
		int result = 0;
		for (int x = 0; x < listOfScores.size(); x++) {
			result += listOfScores.get(x);
		}
		return result;
	}

	public double getOtherScore (NetSearcherNode node) {
		if (node == null) {
			return this.avg;
		} else {
			return node.getScore();
		}
	}
	
	public double getMatchValue() {return calculateMatchValue();};
	public double getScore() {return thisScore;};
	
	private double calculateMatchValue() {
		return (getOtherScore(prev2) * .125) + (getOtherScore(prev1) * .125) + (getOtherScore(next1) * .25) + (getOtherScore(next2) * .25) + this.getScore();
	}

	private double calculateScore() {
		KMPScoreCalculator score = new KMPScoreCalculator(query, sentence);
		double x = score.run();
		return x;
	}
	
}
