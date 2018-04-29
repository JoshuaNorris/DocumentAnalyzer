package Parser.Documents.ParsingDocuments.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Parser.Documents.DocumentContainer;

public class SentenceGeneratorTest {

	@Test
	public void test() {
		String str1 = "Hello how are you doing?";
		DocumentContainer test1 = new DocumentContainer(str1);
		assertTrue(test1.getSentences().get(0).equals(str1));
		
		String str2 = "My name is Josh. How are you?";
		DocumentContainer test2 = new DocumentContainer(str2);
		assertTrue(test2.getSentences().size() == 2);
		
		String str3 = "Hello Mr. Josh my name is Josh as well.";
		DocumentContainer test3 = new DocumentContainer(str3);
		assertTrue(test3.getSentences().size() == 1);
		
		String str4 = "Hello Mr. Josh I live on a Str. in Texas.";
		DocumentContainer test4 = new DocumentContainer(str4);
		assertTrue(test4.getSentences().size() == 1);
		
		String str5 = "Tosh.0 is a Tv show.";
		DocumentContainer test5 = new DocumentContainer(str5);
		assertTrue(test5.getSentences().size() == 1);
	}

}