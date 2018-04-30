package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

import Database.Database;
import Searcher.Searcher;
import Searcher.TitleSearcher;
import Summarizer.KeywordLocator;
import Summarizer.ScoreSummarizer;
import Summarizer.StopWordMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

//TODO implement delete file button using deleteFile(title) from database
public class GUIController {
	@FXML
	Button search, search2, back, viewSummary, viewFull, delete;

	@FXML
	TextField searchBar, secondSearchBar, keylist;

	@FXML
	ListView<String> articlesList;

	@FXML
	TextArea view;

	@FXML
	Label title, keywords;

	@FXML
	Tab articlesTab;

	@FXML
	TabPane tabHolder;

	static BadNews error;

	public static Database db;
	static {
		try {
			db = new Database();
		} catch (ClassNotFoundException | SQLException e) {
			error = new BadNews("We could not load your database.");
			e.printStackTrace();
		}
	}

	@FXML
	public void initialize() {
		articlesTab.setDisable(true);
		view.setEditable(false);
		setUpListView();
		view.setWrapText(true);
	}

	private void setUpListView() {
		populateArticlesList();
		articlesList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent click) {

				if (click.getClickCount() == 2) {
					if (!articlesList.getItems().isEmpty()) {
						String currentItemSelected = articlesList.getSelectionModel().getSelectedItem();
						loadArticleViewer(currentItemSelected);
						try {
							ObservableList<String> words = db.getKeywords(currentItemSelected);
							keylist.setText(words.toString().substring(1, words.toString().length()));
						} catch (SQLException e) {
							e.printStackTrace();
						}

					}
				}
			}
		});
	}

	public void pressLoadFileButton() throws SQLException {
		// Oracle documentation
		try {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Open File");
			File chosenFile = chooser.showOpenDialog(new Stage());
			String fileExtension = ".txt";
			if (chosenFile == null) {
				return;
			}
			if (!acceptFile(chosenFile)) {
				pressLoadFileButton();
			}
			String filename = chosenFile.getName();
			String wholeFile = Load(chosenFile);
			String m = filename.substring(0, filename.length() - fileExtension.length());

			putFileinDatabase(m, wholeFile);
			System.out.println("IN KEYWORDLOCATOR");
			KeywordLocator keywordlocator = new KeywordLocator(10, wholeFile, m);
			keywordlocator.insertRelatedWordsInDatabase();
			populateArticlesList();
		} catch (SQLException e) {
			error = new BadNews("We could not put the file into your database.");
			e.printStackTrace();
		}
	}

	public String Load(File save) {
		String stringyFile = "";
		try {
			Scanner scanner = new Scanner(save);
			while (scanner.hasNextLine()) {
				stringyFile += (scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return stringyFile;
	}

	private void putFileinDatabase(String name, String fullText) {
		try {
			if (!db.documentExists(name)) {
				StopWordMaker stopper = new StopWordMaker();
				ScoreSummarizer scoreSum = new ScoreSummarizer(stopper, db);
				scoreSum.scoreSentences(fullText, name);
				String sum = scoreSum.topReturner(name);
				db.insertDocument(name, fullText, sum);
			} else {
				error = new BadNews("That document is already in the database.");
			}
		} catch (SQLException e) {
			error = new BadNews("We could not put the file into your database.");
			e.printStackTrace();
		}
	}

	public boolean acceptFile(File f) {
		String fileName = new String(f.getName());
		String stringList[] = fileName.split("\\.");
		if (stringList[stringList.length - 1].equals("txt")) {
			return true;
		}
		return false;
	}

	public void populateArticlesList() {
		try {
			articlesList.setItems(db.getAllArticles());
		} catch (SQLException e) {
			error = new BadNews("We could not load your articles.");
			e.printStackTrace();
		}
	}

	public void loadArticleViewer(String filename) {
		articlesTab.setText(filename);
		articlesTab.setDisable(false);
		tabHolder.getSelectionModel().select(articlesTab);
		title.setText(filename);
		setViewToSummary(filename);
	}

	public void pressSeeSummary() {
		String title = articlesTab.getText();
		setViewToSummary(title);
	}

	// TODO ask if this is worth it
	public void setViewToSummary(String name) {
		try {
			view.setText(db.getSummaryOf(name));
			ObservableList<String> words = db.getKeywords(name);
			keylist.setText(words.toString().substring(1, words.toString().length()));
		} catch (SQLException e) {
			error = new BadNews("We could not load the summary.");
			// TODO
			e.printStackTrace();
		}
	}

	public void setViewToFull() {
		String title = articlesTab.getText();
		try {
			view.setText(db.getFullTextOf(title));
		} catch (SQLException e) {
			error = new BadNews("We could not load the full text.");
			// TODO
			e.printStackTrace();
		}
	}

	public void searchKey() {
		TitleSearcher searchTitles = new TitleSearcher(searchBar.getText(), articlesList.getItems());
		ObservableList<String> results = searchTitles.getSearchResults();
		articlesList.setItems(results);
	}

	public void search2() throws SQLException {
		try {
			Searcher search = new Searcher(secondSearchBar.getText(), db.getFullTextOf(title.getText()), 10);
			ObservableList<String> results = search.getSearchResults();
			System.out.println("RELATED WORDS" + search.getRelatedWords(10));
			keylist.setText(search.getRelatedWords(10).toString());
			String output = "";
			for (String item : results) {
				output += item + '\n';
			}
			view.setText(output);
		} catch (SQLException e) {
			error = new BadNews("Search bad.");
			e.printStackTrace();
		}
	}

	public void delbutt(ActionEvent event) {
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final int idx = articlesList.getSelectionModel().getSelectedIndex();
				if (idx != -1) {
					try {
						db.deleteFile(articlesList.getItems().get(idx));
						articlesTab.setText("Article");
						articlesTab.setDisable(true);
					} catch (SQLException e) {
						error = new BadNews("We could not delete the full text.");
						e.printStackTrace();
					}
					articlesList.getItems().remove(idx);
				}
			}
		});

	}
}
