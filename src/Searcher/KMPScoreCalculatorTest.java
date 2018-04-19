package Searcher;

import static org.junit.Assert.*;

import org.junit.Test;

import Searcher.*;

public class KMPScoreCalculatorTest {

	@Test
	public void calculateScoreTest() {
		String str1 = "blue";
		String query1 = "blue";
		assertTrue(KMPScoreCalculator.calculateScore(str1, query1) == 4.0);
		
		String str2 = "blrue";
		String query2 = query1;
		assertTrue(KMPScoreCalculator.calculateScore(str2, query2) == 3.5);

		String str3 = "blrrrue";
		String query3 = query1;
		assertTrue(KMPScoreCalculator.calculateScore(str3, query3) == 2);
	}

}
