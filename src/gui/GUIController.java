package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Database.Database;
import Parser.Documents.DocumentContainer;
import Searcher.Searcher;
import Searcher.TitleSearcher;
import Sectioner.DocumentSectioner;
import Summarizer.KeywordLocator;
import Summarizer.ScoreSummarizer;
import Summarizer.StopWordMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

//TODO implement delete file button using deleteFile(title) from database
public class GUIController {
	@FXML
	Button search, search2, back, viewSummary, viewFull, delete;

	@FXML
	TextField searchBar, secondSearchBar, keylist;

	@FXML
	ListView<String> articlesList;

	@FXML
	ListView<String> recommendedArticles;

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

				if (click.getClickCount() == 2 & !articlesList.getItems().isEmpty()) {
					String currentItemSelected = articlesList.getSelectionModel().getSelectedItem();
					loadArticleViewer(currentItemSelected);
					try {
						ObservableList<String> words = db.getKeywords(currentItemSelected);
						keylist.setText(words.toString().substring(1, words.toString().length()));
					} catch (SQLException e) {
						error = new BadNews("We were unable to load that document.");
						e.printStackTrace();
					}

				}
			}
		});
	}

	public void pressLoadFileButton() throws SQLException {
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
			KeywordLocator keywordlocator = new KeywordLocator(10, wholeFile, m);
			keywordlocator.insertRelatedWordsInDatabase();
			populateArticlesList();
		} catch (SQLException e) {
			error = new BadNews("We could not put the file into your database.");
			e.printStackTrace();
		}
	}

	public String Load(File save) {
		StringBuilder contentBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(save))) {

			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				contentBuilder.append(sCurrentLine).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentBuilder.toString();
	}

	private void putFileinDatabase(String name, String fullText) {
		try {
			if (!db.documentExists(name)) {
				fullText = fullText.replaceAll("—", " ");
				
				String sum = separate(new DocumentContainer(fullText), name);
				
				fullText = fullText.replaceAll("/n", "");
				db.insertDocument(name, fullText, sum);
			} else {
				error = new BadNews("That document is already in the database.");
			}
		} catch (SQLException e) {
			error = new BadNews("We could not put the file into your database.");
			e.printStackTrace();
		}
	}

	private String separate(DocumentContainer doc, String name) {
		String summary = "";
		StopWordMaker stopper = new StopWordMaker();
		ScoreSummarizer scoreSum = new ScoreSummarizer(stopper, db);
		int calculatedPercent = calculatePercent(doc.getSentences().size());
		DocumentSectioner container = new DocumentSectioner(doc.getText(), calculatedPercent);
		ArrayList<String> separated = container.getSectionedText();
		for (String sentences : separated) {
			try {
				scoreSum.scoreSentences(sentences, name);
				summary += scoreSum.topReturner(name);
			} catch (SQLException e) {
				error = new BadNews("We could not summarize this document.");
				e.printStackTrace();
			}
		}

		return summary;
	}

	private int calculatePercent(int numSentences) { //Look, I know there's a better way
		System.out.println("numSentences: " + numSentences);
		int percent = 100;
		if (numSentences > 500) {
			percent = 50;
		} else if (numSentences > 1500) {
			percent = 20;
		} else if (numSentences > 5000) {
			percent = 10;
		} else if (numSentences > 1000) {
			percent = 5;
		}
		return percent;
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

	public void setViewToSummary(String name) {
		try {
			view.setText(db.getSummaryOf(name));
			ObservableList<String> words = db.getKeywords(name);
			keylist.setText(words.toString().substring(1, words.toString().length()));
			setRelatedDocuments(name, words);
		} catch (SQLException e) {
			error = new BadNews("We could not load the summary.");
			e.printStackTrace();
		}
	}

	private void setRelatedDocuments(String name, ObservableList<String> words) throws SQLException {
		try {
			recommendedArticles.setItems(db.getRelatedDocuments(name, words));
		} catch (NullPointerException e) {
			ObservableList<String> noDocuments = FXCollections.observableArrayList();
			noDocuments.add("There are no documents that contain the same keywords as this document.");
			recommendedArticles.setItems(noDocuments);
		}
	}

	public void setViewToFull() {
		String title = articlesTab.getText();
		try {
			view.setText(db.getFullTextOf(title));
		} catch (SQLException e) {
			error = new BadNews("We could not load the full text.");
			e.printStackTrace();
		}
	}

	public void searchKey() {
		TitleSearcher searchTitles = new TitleSearcher(searchBar.getText(), articlesList.getItems());
		ObservableList<String> results = searchTitles.getSearchResults();
		articlesList.setItems(results);
	}

	public void fullTextSearch() throws SQLException {
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);

		VBox labels = new VBox();
		Label query = new Label(secondSearchBar.getText() + " in: ");
		Label popupTitle = new Label(this.title.getText());
		labels.getChildren().setAll(query, popupTitle);

		HBox results = new HBox();
		TextArea searchResultsTextArea = new TextArea();
		searchResultsTextArea.setPrefSize(800, 500);
		searchResultsTextArea.setWrapText(true);
		ListView<String> relatedWordsListView = new ListView<String>();
		relatedWordsListView.setLayoutX(75);
		results.getChildren().setAll(relatedWordsListView, searchResultsTextArea);

		VBox layout = new VBox();
		layout.getChildren().setAll(labels, results);

		Scene popupscene = new Scene(layout, 1000, 600);
		popup.setScene(popupscene);
		popup.show();

		Searcher search = new Searcher(secondSearchBar.getText(), db.getFullTextOf(title.getText()), 10);
		ObservableList<String> searchResults = search.getSearchResults();
		ObservableList<String> relatedWordsList = search.getRelatedWords(4);
		relatedWordsList.add(0, "Related Words: ");
		relatedWordsListView.setItems(relatedWordsList);
		String searchResultsText = listToText(searchResults);
		searchResultsTextArea.setText(searchResultsText);
	}

	private String listToText(ObservableList<String> searchResults) {
		String result = "Search Results: \n";
		for (int x = 0; x < searchResults.size(); x++) {
			result += (x + 1) + ": " + searchResults.get(x) + "\n";
		}
		return result;
	}

	public void delete(ActionEvent event) {
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
