package Sectioner;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Parser.Documents.DocumentContainer;

public class DocumentSectionerTest {
	DocumentSectioner thisSectioner;
	
	@Before
	public void init() {
		String text = "Hello, my friends. This is a sentence. Four score and seven years ago, this is another sentence. Consider: another sentence here. "
				+ "I am for testing purposes. Hear me roar. I hope my tests don't fail. That would be sad. That would make me sad.";
		thisSectioner = new DocumentSectioner(text);
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
