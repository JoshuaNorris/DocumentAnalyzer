package Database;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import gui.GUIController;


public class DatabaseTest {
	
	//redo these once documents are a thing
	/*
	@Before
	public void init() throws SQLException {
		GUIController.db.insertSummarySentence("Summarytest", "I am important", 1, 1);
		GUIController.db.insertSummarySentence("Summarytest", "I am not important", 2, 0);
		GUIController.db.insertSummarySentence("Summarytest", "I am more important", 3, 2);
		GUIController.db.insertSummarySentence("Summarytest", "I am most important", 4, 3);
		GUIController.db.insertSummarySentence("Summarytest", "I am a little important", 5, .1); 
		
		GUIController.db.insertDocument("document", "This is the document text.");
	}
	
	@Test
	public void summaryGetter() throws SQLException {
		String test = GUIController.db.getTopSentencesOf("Summarytest", 3); //order should be 1, 3, 4
		
		assertEquals(test, "I am important I am more important I am most important "); 
	}

	@Test
	public void getFulltext() throws SQLException {
		String test = GUIController.db.getFullTextOf("document");
		
		assertEquals("This is the document text.", test);
	}
	
	@Test
	public void getSummaryOf() throws SQLException {
		String test = GUIController.db.getFullTextOf("document");

	}*/
}
