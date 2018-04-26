package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {
	private Statement stat;
	private Connection con;

	public Database() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:Summarizer");
		stat = con.createStatement();
		stat.executeUpdate("CREATE TABLE IF NOT EXISTS document_full(title String, body String, summary String);");
		stat.executeUpdate(
				"CREATE TABLE IF NOT EXISTS document_summary(title String, sentence String, numSentence INTEGER, score INTEGER);");
		stat.executeUpdate("CREATE TABLE IF NOT EXISTS document_keywords(title String, keyword String);");
		// dropTables();
	}

	public void insertDocument(String title, String text) throws SQLException {
		String safeTitle = title.replace("'", "''");
		String safeText = text.replace("'", "''");
		String summary = "in sum: " + safeText; // TODO
		System.out.println("INSERT INTO document_full (title, body, summary) VALUES ('" + safeTitle + "', '" + safeText
				+ "', '" + summary + "');");
		stat.executeUpdate("INSERT INTO document_full (title, body, summary) VALUES ('" + safeTitle + "', '" + safeText
				+ "', '" + summary + "');");
	}

	public void insertSummarySentence(String title, String sentence,  int index, int score) throws SQLException {
		String safeTitle = title.replace("'", "''");
		String safeSentence = sentence.replace("'", "''");
		System.out.println("INSERT INTO document_summary (title, sentence, numSentence, score) VALUES ('" + safeTitle + "', '"
				+ safeSentence + "', " + index + ", " + score + ";)");
		stat.executeUpdate("INSERT INTO document_summary (title, sentence, numSentence, score) VALUES ('" + safeTitle + "', '"
				+ safeSentence + "', " + index + ", " + score + ");");
	}
	
	public void insertKeyword(String title, String word) throws SQLException {
		String safeTitle = title.replace("'", "''");
		String safeKey = word.replace("'", "''");
		stat.executeUpdate("INSERT INTO document_keywords (title, keyword) VALUES ('" + safeTitle + "', '"
				+ safeKey + ");");
	}

	public ObservableList<String> getAllArticles() throws SQLException {
		System.out.println("Getting all articles");

		ObservableList<String> articles = FXCollections.observableArrayList();
		StringBuilder temp = new StringBuilder();
		ResultSet info = stat.executeQuery("SELECT title FROM document_full;");
		while (info.next()) {
			System.out.println("while get art");

			temp.append(info.getString("title").replace("''", "'"));
			articles.add(temp.toString());
			temp.setLength(0); // clears Stringbuilder for new row
		}
		return articles;
	}

	public String getSummaryOf(String title) throws SQLException {
		String safeTitle = title.replace("'", "''");
		String sum = "";
		ResultSet info = stat.executeQuery("SELECT summary FROM document_full WHERE title = '" + safeTitle + "';");
		while (info.next()) {
			System.out.println("while get sum");
			sum += info.getString("summary").replace("''", "'");
		}
		return sum;
	}

	public String getFullTextOf(String title) throws SQLException {
		String safeTitle = title.replace("'", "''");
		String full = "";
		ResultSet info = stat.executeQuery("SELECT body FROM document_full WHERE title = '" + safeTitle + "';");
		while (info.next()) {
			System.out.println("while get body");
			full += info.getString("body").replace("''", "'");
		}
		return full;
	}

	public String getTopSentencesOf(String title, int numSentences) throws SQLException {
		String finalSummary = "";
		String safeTitle = title.replace("'", "''");
		ResultSet info = stat.executeQuery("SELECT sentence FROM("
				+ "SELECT sentence, numSentence FROM document_summary WHERE title = '"
				+ safeTitle + "' " + " ORDER BY score DESC LIMIT " + numSentences +")"
						+ "ORDER BY numSentence ASC");
		while (info.next()) {
			finalSummary += info.getString("sentence").replace("''", "'") + " ";
		}
		return finalSummary;
	}
	
	public ObservableList<String> getKeywords(String title) throws SQLException {
		ObservableList<String> keywords = FXCollections.observableArrayList();
		String safeTitle = title.replace("'", "''");
		ResultSet info = stat.executeQuery("SELECT keyword FROM '" + safeTitle + "'");
		while (info.next()) {
			keywords.add(info.getString("keyword"));
		}
		return keywords;
	}

	private void dropTables() throws SQLException {
		stat.executeUpdate("DROP TABLE document_full");
		stat.executeUpdate("DROP TABLE document_summary");

	}
}
