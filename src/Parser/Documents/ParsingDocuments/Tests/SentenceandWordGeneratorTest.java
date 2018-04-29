package Parser.Documents.ParsingDocuments.Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Parser.Documents.DocumentContainer;
import Parser.Documents.ParsingDocuments.SentenceandWordGenerator;

public class SentenceandWordGeneratorTest {

	@Test
	public void test() {
		String str1 = "Hello, I am Josh. What is your name? Wow that is really cool I like that name.";
		DocumentContainer doc1 = new DocumentContainer(str1);
		ArrayList<ArrayList<String>> test1 = doc1.getSentencesWithWords();
		
		assertTrue(test1.get(0).size() == 2);
		assertTrue(test1.get(1).size() == 0);
		assertTrue(test1.get(2).size() == 2);
		assertFalse(test1.get(2).get(0).equals(" "));
		
		
	}

}
