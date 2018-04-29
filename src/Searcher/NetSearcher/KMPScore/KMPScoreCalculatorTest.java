package Searcher.NetSearcher.KMPScore;

import static org.junit.Assert.*;

import org.junit.Test;

public class KMPScoreCalculatorTest {

	@Test
	public void ScoreCalculatorTest() {
		String doc1 = "hello blue person blue how are you.";
		
		String query1 = "blue";
		KMPScoreCalculator test1 = new KMPScoreCalculator(query1, doc1);
		System.out.println(test1.getResult());
		
		String doc2 = "hello blrue person how are you.";
		String query2 = query1;
		KMPScoreCalculator test2 = new KMPScoreCalculator(query2, doc2);
		System.out.println(test2.getResult());
	}

}
