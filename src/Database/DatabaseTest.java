package Database;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gui.GUIController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DatabaseTest {
	
	@Before
	public void init() throws SQLException {
		GUIController.db.insertSummarySentence("Summarytest", "I am important", 1, 2.0);
		GUIController.db.insertSummarySentence("Summarytest", "I am not important", 2, 0.0);
		GUIController.db.insertSummarySentence("Summarytest", "I am more important", 3, 3.0);
		GUIController.db.insertSummarySentence("Summarytest", "I am most important", 4, 4.0);
		GUIController.db.insertSummarySentence("Summarytest", "I am a little important", 5, 1.0); 
		
		GUIController.db.insertDocument("document", "This is the document text.");
		
		GUIController.db.insertKeyword("Keywordtest", "key1");
		GUIController.db.insertKeyword("Keywordtest", "key2");
		GUIController.db.insertKeyword("Keywordtest", "key3");
		
		GUIController.db.insertKeyword("Keywordtest2", "key1");
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
	public void documentExists() throws SQLException {
		assertTrue(GUIController.db.documentExists("document"));
		assertFalse(GUIController.db.documentExists("not here"));
	}
	
	@Test
	public void getKeywordsTest() throws SQLException {
		ObservableList<String> keywords = FXCollections.observableArrayList();
		keywords = GUIController.db.getKeywords("Keywordtest");
		assertTrue(keywords.contains("key1"));
		assertTrue(keywords.contains("key2"));
		assertTrue(keywords.contains("key3"));
		assertFalse(keywords.contains("key4"));
	}
	
	@Test 
	public void getTitlesWithKeyword() throws SQLException {
		ObservableList<String> articles = FXCollections.observableArrayList();
		articles = GUIController.db.getTitlesWithKeyword("key1");
		assertTrue(articles.contains("Keywordtest"));
		assertTrue(articles.contains("Keywordtest2"));
	}
	
	@After
	public void cleanSlate() throws SQLException {
		GUIController.db.deleteFile("Summarytest");
		GUIController.db.deleteFile("document");
		GUIController.db.deleteFile("Keywordtest");
	}

}
