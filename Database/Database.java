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
			stat.executeUpdate("CREATE TABLE IF NOT EXISTS document_summary(title String, sentence String, numSentence INTEGER, score INTEGER);");
			//dropTables();
		}
		
		public void insertDocument(String title, String text) throws SQLException {
			String summary = "in sum: " + text; //TODO
			System.out.println("INSERT INTO document_full (title, body, summary) VALUES ('" + title + "', '" + text + "', '" + summary + "');");
			stat.executeUpdate("INSERT INTO document_full (title, body, summary) VALUES ('" + title + "', '" + text + "', '" + summary + "');" );
		}
		
		public void insertSummarySentence(String title, int index, String sentence, double score) throws SQLException {
			stat.executeUpdate("INSERT INTO document_summary (title, index, sentence, score) VALUES ('" +
					title + "', " + index + ", '" + sentence + "', " + score + ";)");
		}
		
		public ObservableList<String> getAllArticles() throws SQLException {
			System.out.println("Getting all articles");

			ObservableList<String> articles = FXCollections.observableArrayList();
				StringBuilder temp = new StringBuilder();
				ResultSet info = stat.executeQuery("SELECT title FROM document_full;");
				while (info.next()) {
					System.out.println("while get art");

					temp.append(info.getString("title"));
					articles.add(temp.toString());
					temp.setLength(0); // clears Stringbuilder for new row
				}
			return articles;
		}
		
		public String getSummaryOf(String title) throws SQLException {
			String sum = "";
			ResultSet info = stat.executeQuery("SELECT summary FROM document_full WHERE title = '" + title + "';");
			while (info.next()) {
				System.out.println("while get sum");
				sum += info.getString("summary");
			}
			return sum;
		}
		
		public String getFullTextOf(String title) throws SQLException {
			String full = "";
			ResultSet info = stat.executeQuery("SELECT body FROM document_full WHERE title = '" + title + "';");
			while (info.next()) {
				System.out.println("while get body");
				full += info.getString("body");
			}
			return full;
		}
		
		private void dropTables() throws SQLException {
			stat.executeUpdate("DROP TABLE document_full");
			stat.executeUpdate("DROP TABLE document_summary");

		}
	}


