package Sectioner;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;


public class DocumentSectionerTest {
	DocumentSectioner thisSectioner;
	
	@Before
	public void init() throws URISyntaxException {
		String text = "Hello, my friends. This is a sentence. Four score and seven years ago, this is another sentence. Consider: another sentence here. "
				+ "I am for testing purposes. Hear me roar. I hope my tests don't fail. That would be sad. That would make me sad.";
		thisSectioner = new DocumentSectioner(text);
		
		File file = new File(DocumentSectionerTest.class.getResource("Sectioner Test").toURI());

	}

	@Test
	public void getSentenceIndexByPercentTest() {
		int lowerBound = 0;
		int upperBound = thisSectioner.getSentenceIndexByPercent(lowerBound, 10); 
		assertEquals(1, upperBound);
		
		lowerBound = 2;
		upperBound = thisSectioner.getSentenceIndexByPercent(lowerBound, 20); 
		assertEquals(4, upperBound);
	}

}
