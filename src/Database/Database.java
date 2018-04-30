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

	public void insertDocument(String title, String text, String summary) throws SQLException {
		String safeTitle = makeSafe(title);
		String safeText = makeSafe(text);
		stat.executeUpdate("INSERT INTO document_full (title, body, summary) VALUES ('" + safeTitle + "', '" + safeText
				+ "', '" + summary + "');");
	}

	public void insertSummarySentence(String title, String sentence,  int index, Double score) throws SQLException {
		String safeTitle = makeSafe(title);
		String safeSentence = makeSafe(sentence);
		stat.executeUpdate("INSERT INTO document_summary (title, sentence, numSentence, score) VALUES ('" + safeTitle + "', '"
				+ safeSentence + "', " + index + ", " + score + ");");
	}



	public ObservableList<String> getAllArticles() throws SQLException {
		System.out.println("Getting all articles");

		ObservableList<String> articles = FXCollections.observableArrayList();
		StringBuilder temp = new StringBuilder();
		ResultSet info = stat.executeQuery("SELECT title FROM document_full;");
		while (info.next()) {
			temp.append(info.getString("title").replace("''", "'"));
			articles.add(temp.toString());
			temp.setLength(0); // clears Stringbuilder for new row
		}
		return articles;
	}

	public String getSummaryOf(String title) throws SQLException {
		String safeTitle = makeSafe(title);
		String sum = "";
		ResultSet info = stat.executeQuery("SELECT summary FROM document_full WHERE title = '" + safeTitle + "';");
		while (info.next()) {
			sum += info.getString("summary").replace("''", "'");
		}
		return sum;
	}

	public String getFullTextOf(String title) throws SQLException {
		String safeTitle = makeSafe(title);
		String full = "";
		ResultSet info = stat.executeQuery("SELECT body FROM document_full WHERE title = '" + safeTitle + "';");
		while (info.next()) {
			full += info.getString("body").replace("''", "'");
		}
		return full;
	}

	public String getTopSentencesOf(String title, int numSentences) throws SQLException {
		String finalSummary = "";
		String safeTitle = makeSafe(title);
		ResultSet info = stat.executeQuery("SELECT sentence FROM("
				+ "SELECT sentence, numSentence FROM document_summary WHERE title = '"
				+ safeTitle + "' " + " ORDER BY score DESC LIMIT " + numSentences +")"
						+ "ORDER BY numSentence ASC");
		while (info.next()) {
			finalSummary += info.getString("sentence").replace("''", "'") + " ";
		}
		return finalSummary;
	}

	public void insertKeyword(String title, String word) throws SQLException {
		System.out.println("ADDED " + word);
		String safeTitle = makeSafe(title);
		String safeKey = makeSafe(word);
		stat.executeUpdate("INSERT INTO document_keywords (title, keyword) VALUES ('" + safeTitle + "', '"
				+ safeKey + "');");
	}

	public ObservableList<String> getKeywords(String title) throws SQLException {
		ObservableList<String> keywords = FXCollections.observableArrayList();
		String safeTitle = makeSafe(title);
		System.out.println("SAFETITLE " + safeTitle);
		ResultSet info = stat.executeQuery("SELECT keyword FROM document_keywords WHERE title='" + safeTitle + "'");
		while (info.next()) {
			keywords.add(info.getString("keyword"));
		}
		System.out.println("GOT KEYWORDS");
		System.out.println(keywords);
		return keywords;
	}

	public ObservableList<String> getTitlesWithKeyword(String keyword) throws SQLException {
		ObservableList<String> titles = FXCollections.observableArrayList();
		String safeKeyword = makeSafe(keyword);
		ResultSet info = stat.executeQuery("SELECT title FROM document_keywords WHERE keyword='" + safeKeyword +"';");
		while (info.next()) {
			titles.add(info.getString("title"));
		}
		return titles;
	}

	public boolean documentExists(String title) throws SQLException {
		boolean bool = false;
		String safeTitle = makeSafe(title);
		ResultSet info = stat.executeQuery("SELECT title FROM document_full WHERE title='" + safeTitle + "';");
		if (info.next()) {
			bool = true;
		}
		return bool;
	}

	public void deleteFile(String title) throws SQLException {
		String safeTitle = makeSafe(title);
		stat.executeUpdate("DELETE FROM document_full WHERE title='" + safeTitle + "';");
		stat.executeUpdate("DELETE FROM document_summary WHERE title='" + safeTitle + "';");
		stat.executeUpdate("DELETE FROM document_keywords WHERE title='" + safeTitle + "';");
	}
	private String makeSafe(String word) {
		String safe = word.replace("'", "''");
		return safe;
	}



	private void dropTables() throws SQLException {
		stat.executeUpdate("DROP TABLE document_full");
		stat.executeUpdate("DROP TABLE document_summary");

	}
}
