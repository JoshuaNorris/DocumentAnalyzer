package Searcher;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TitleSearcherTest {

	@Test
	public void test() {
		ObservableList<String> titles = FXCollections.observableArrayList();
		titles.add("Little Red Riding Hood");
		titles.add("Blue House");
		titles.add("Yellow Grass");
		titles.add("The Pencil");
		titles.add("Blue Blue Blue");
		titles.add("Hello");
		titles.add("Random Title");
		titles.add("Another Random Title");
		titles.add("Please do not select this title");
		titles.add("Or this one");
		
		
		
		String query1 = "blue";
		TitleSearcher test1 = new TitleSearcher(query1, titles);
		System.out.println(test1.getSearchResults());
	}

}
