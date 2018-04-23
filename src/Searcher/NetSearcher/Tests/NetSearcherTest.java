package Searcher.NetSearcher.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Searcher.Searcher;
import Searcher.NetSearcher.NetSearcher;
import Parser.Documents.DocumentContainer;

public class NetSearcherTest {

	@Test
	public void test() {
		String doc1 = "Hello my name is Josh how are you doing today? I am writing this program in an"
				+ "imperative language called java. I wonder where they got the name java from, it must"
				+ "be some kind of pun off of coffee. Anyways java is a super cool language java. Java also"
				+ "can be a very not cool language. This was seen today when I was trying to code in java."
				+ "Then I realized I wanted a second constructor. I made this constructor and then continued"
				+ "to try to call the first constructor in java form the second. This did not work because java"
				+ "requires that one use the word 'this' in replacment for the constructor's actual name. It also"
				+ "has to be the first line of code in the constructor which seems like a super dumb thing in java.";
		
		DocumentContainer document1 = new DocumentContainer(doc1); 
		
		String query1 = "java";
		
		
		NetSearcher search1 = new NetSearcher(query1, document1);
		assertTrue(search1.run().get(0).equals(" Anyways java is a super cool language java."));
				
	}
	
	@Test
	public void otherTest() {
		String doc1 = "Hello my name is Josh how are you doing today? I am writing this program in an "
				+ "imperative language called java. I wonder where they got the name java from, it must "
				+ "be some kind of pun off of coffee. Anyways java is a super cool language java. Java also "
				+ "can be a very not cool language. This was seen today when I was trying to code in java. "
				+ "Then I realized I wanted a second constructor. I made this constructor and then continued "
				+ "to try to call the first constructor in java form the second. This did not work because java "
				+ "requires that one use the word 'this' in replacment for the constructor's actual name. It also "
				+ "has to be the first line of code in the constructor which seems like a super dumb thing in java.";
		
		String query1 = "jasva";
		
		Searcher search1 = new Searcher(query1, doc1); 
		assertTrue(search1.getSearchResults().get(0).equals(" Anyways java is a super cool language java."));
		assertTrue(search1.getRelatedWords().get(0).equals("java"));
		
	}

}
