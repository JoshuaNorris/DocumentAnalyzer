package Parser.Documents.ParsingDocuments.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Parser.Documents.ParsingDocuments.WordGenerator;

public class WordGeneratorTest {

	@Test
	public void test() {
		String str1 = "Hello how are you doing today?";
		WordGenerator test1 = new WordGenerator(str1);
		assertTrue(test1.run().size() == 2);
		assertTrue(test1.run().get(0).equals("hello"));
		assertFalse(test1.run().get(0).equals("Hello"));
		assertTrue(test1.run().get(1).equals("today"));
		assertFalse(test1.run().get(1).equals("today?"));
	}

}
