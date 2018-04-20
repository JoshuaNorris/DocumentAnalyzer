package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private Statement stat;
	private Connection con;

	public Database() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:Summarizer");
		stat = con.createStatement();
		stat.executeUpdate("CREATE TABLE IF NOT EXISTS document_full(title String, body String, summary String);");
		stat.executeUpdate("CREATE TABLE IF NOT EXISTS document_summary(title String, sentence String, numSentence INTEGER, score INTEGER);");
	}
	
	public void insertDocument(String title, String text) throws SQLException {
		String summary = text; //TODO
		System.out.println("INSERT INTO document_full (title, body, summary) VALUES ('" + title + "', '" + text + "', '" + summary + "');");
		stat.executeUpdate("INSERT INTO document_full (title, body, summary) VALUES ('" + title + "', '" + text + "', '" + summary + "');" );
	}
	
	public void insertSummarySentence(String title, int index, String sentence, double score) throws SQLException {
		stat.executeUpdate("INSERT INTO document_summary (title, index, sentence, score) VALUES ('" +
				title + "', " + index + ", '" + sentence + "', " + score + ";)");
	}
}
