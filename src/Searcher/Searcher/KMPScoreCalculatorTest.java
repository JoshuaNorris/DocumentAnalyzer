package Searcher;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class KMPScoreCalculatorTest {

	@Test
	public void ScoreCalculatorTest() {
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("hello");
		list1.add("blue");
		list1.add("dog");
		list1.add("hi");
		String query1 = "blue";
		KMPScoreCalculator test1 = new KMPScoreCalculator(query1, list1);
		assertTrue(test1.run() == 4.0);
		
		ArrayList<String> list2 = new ArrayList<String>();
		list2.add("hello");
		list2.add("blue");
		list2.add("dog");
		list2.add("blrue");
		String query2 = "blue";
		KMPScoreCalculator test2 = new KMPScoreCalculator(query2, list2);
		assertTrue(test2.run() == 7.5);
	}
	
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
	
	@Test
	public void KMPScoreTest() {
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("hello");
		list1.add("I");
		list1.add("am");
		list1.add("Josh");
		
		String query1 = "Josh";
		
		KMPScoreCalculator test1 = new KMPScoreCalculator(query1, list1);
		
		System.out.println(test1.run() == 4.0);
	}

}
