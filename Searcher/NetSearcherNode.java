package Searcher;

import java.util.ArrayList;

public class NetSearcherNode {

	private NetSearcherNode prev2;
	private NetSearcherNode prev1;
	private ArrayList<String> sentence;
	private NetSearcherNode next1;
	private NetSearcherNode next2;
	private double thisScore;
	private double matchValue;
	private String query;
	
	
	NetSearcherNode (NetSearcherNode prev2, NetSearcherNode prev1, ArrayList<String> sentence, NetSearcherNode next1, NetSearcherNode next2, String query) {
		this.prev2 = prev2;
		this.prev1 = prev1;
		this.sentence = sentence;
		this.next1 = next1;
		this.next2 = next2;
		this.thisScore = calculateScore();
		this.matchValue = calculateMatchValue();
		this.query = query;
		
	}
	
	public double getMatchValue() {return matchValue;};
	public double getScore() {return thisScore;};
	
	

	private double calculateMatchValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	private double calculateScore() {
		KMPScoreCalculator score = new KMPScoreCalculator(query, sentence);
		return score.run();
	}
	
	public NetSearcherNode (NetSearcherNode prev1, ArrayList<String> sentence, NetSearcherNode next1, NetSearcherNode next2, String query) {
		// Missing prev2
		this(new NetSearcherNode((prev1.getScore() + next1.getScore() + next2.getScore()) / 3), 
				prev1, sentence, next1, next2, query);
	}
	
	public NetSearcherNode (ArrayList<String> sentence, NetSearcherNode next1, NetSearcherNode next2, String query) {
		// Missing prev2, prev1
		this(new NetSearcherNode((next1.getScore() + next2.getScore()) / 2),
				new NetSearcherNode((next1.getScore() + next2.getScore()) / 2),
				sentence, next1, next2, query);
	}
	
	public NetSearcherNode (NetSearcherNode prev2, NetSearcherNode prev1, ArrayList<String> sentence, NetSearcherNode next1, String query) {
		// Missing next2
		this(prev2, prev1, sentence, next1,
				new NetSearcherNode((prev2.getScore() + prev1.getScore() + next1.getScore()) / 3),
				query);
	}

	public NetSearcherNode (NetSearcherNode prev2, NetSearcherNode prev1, ArrayList<String> sentence, String query) {
		// Missing next1 next2
		this(prev2, prev1, sentence,
				new NetSearcherNode((prev2.getScore() + prev1.getScore()) / 2),
				new NetSearcherNode((prev2.getScore() + prev1.getScore()) / 2),
				query);
	}
	
	public NetSearcherNode (NetSearcherNode prev1, ArrayList<String> sentence, NetSearcherNode next1, String query) {
		// Missing prev2 next2
		this(new NetSearcherNode((prev1.getScore() + next1.getScore()) / 2),
				prev1, sentence, next1,
				new NetSearcherNode((prev1.getScore() + next1.getScore()) / 2),
				query);
	}
	
	public NetSearcherNode (NetSearcherNode prev1, ArrayList<String> sentence, String query) {
		// Missing prev2 next1 next2
		this (new NetSearcherNode(prev1.getScore()),
				prev1, sentence,
				new NetSearcherNode(prev1.getScore()),
				new NetSearcherNode(prev1.getScore()),
				query);
	}
	
	public NetSearcherNode (ArrayList<String> sentence, NetSearcherNode next1, String query) {
		// Missing prev2 prev1 next2
		this (new NetSearcherNode(next1.getScore()),
				new NetSearcherNode(next1.getScore()),
				sentence, next1,
				new NetSearcherNode(next1.getScore()),
				query);
	}

	public NetSearcherNode (double score) {
		this.prev2 = null;
		this.prev1 = null;
		this.sentence = null;
		this.next1 = null;
		this.next2 = null;
		this.thisScore = score;
		this.matchValue = 0.0;
		this.query = "";
	}
}
