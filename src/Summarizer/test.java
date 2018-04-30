package Summarizer;

import java.io.IOException;
import java.sql.SQLException;

import Database.Database;

public abstract class test {

	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		StopWordMaker t = new StopWordMaker();
		Database testDB = new Database();
		ScoreSummarizer y = new ScoreSummarizer(t, testDB);
		y.scoreSentences("Test sentence. This is a test.", "test4");
		y.topReturner("test");
	}

}
