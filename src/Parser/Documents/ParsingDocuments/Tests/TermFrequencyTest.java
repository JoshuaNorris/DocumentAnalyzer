package Parser.Documents.ParsingDocuments.Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import Parser.Documents.ParsingDocuments.TermFrequencyGenerator;
import Parser.Documents.ParsingDocuments.WordGenerator;

public class TermFrequencyTest {

	@Test
	public void test() {
		String str1 = "In this application pizza. flies application Pizza does not fly yellow blue green pizza.";
		WordGenerator words1 = new WordGenerator(str1);
		ArrayList<String> word1 = words1.run();
		TermFrequencyGenerator test1 = new TermFrequencyGenerator(word1);
		HashMap<String, Double> result1 = test1.run();
		assertTrue(result1.get("pizza") == .3);
		assertTrue(result1.get("application") == .2);
	}

}
