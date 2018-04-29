package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JFileChooser;

import Database.Database;
import Searcher.TitleSearcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

//TODO implement delete file button using deleteFile(title) from database
public class GUIController {
	@FXML
	Button search, back, viewSummary, viewFull,delete;

	@FXML
	TextField searchBar;

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


	public void initialize() {
		articlesTab.setDisable(true);
		view.setEditable(false);
		setUpListView();
	}



	private void setUpListView() {
		populateArticlesList();
		articlesList.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent click) {

		        if (click.getClickCount() == 2) {
							//if (!articlesList.isEmpty()){
		           String currentItemSelected = articlesList.getSelectionModel().getSelectedItem();
		           loadArticleViewer(currentItemSelected);
						 //}
		        }
		    }
		});
	}

	public void pressLoadFileButton() throws SQLException {
		//Oracle documentation
		try {
		String fileExtension = ".txt";
		JFileChooser chooser= new JFileChooser();
		int choice = chooser.showOpenDialog(null);
		File chosenFile = chooser.getSelectedFile();
		if (chosenFile == null) {return;}
		if (!acceptFile(chosenFile)) {
			pressLoadFileButton();
		}
		String filename = chosenFile.getName();
		Scanner s = new Scanner(chosenFile);
		String wholeFile = s.useDelimiter("\\A").next();
		String m = filename.substring(0, filename.length() - fileExtension.length());
		if (db.documentExists(m)){
			m = dubCheck(m + '1',1);
		}
		putFileinDatabase(m, wholeFile);
		s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		populateArticlesList();
	}
	private String dubCheck(String file,Integer n) throws SQLException{
		try{
			if (db.documentExists(file)){
				Integer m = n - 1;
				file = file.substring(0,file.length() - m.toString().length() - 1);
				Integer k = n + 1;
				dubCheck(file + k.toString(), k);
			}
			return file;
		} catch (SQLException e) {
			error = new BadNews ("We could not put the file into your database.");
			e.printStackTrace();
		}
		return "";
	}
	private void putFileinDatabase(String name, String fullText) {
		try {
			db.insertDocument(name, fullText);
		} catch (SQLException e) {
			error = new BadNews("We could not put the file into your database.");
			e.printStackTrace();
		}
	}

	public boolean acceptFile(File f) {
	   String fileName = new String(f.getName());
	   String stringList[] = fileName.split("\\.");
	   if(stringList[stringList.length - 1].equals("txt")) {
	    	return true;
	    } return false;
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

	//TODO ask if this is worth it
	public void setViewToSummary(String name) {
		try {
			view.setText(db.getSummaryOf(name));
		} catch (SQLException e) {
			//TODO
			e.printStackTrace();
		}
	}

	public void setViewToFull() {
		String title = articlesTab.getText();
		try {
			view.setText(db.getFullTextOf(title));
		} catch (SQLException e) {
			//TODO
			e.printStackTrace();
		}
	}
	public void searchKey(){
		TitleSearcher searchTitles = new TitleSearcher(searchBar.getText(),articlesList.getItems());
		ObservableList<String> results = searchTitles.getSearchResults();
		articlesList.setItems(results);
	}
	public void delButt(){
		Integer item = articlesList.getSelectionModel().getSelectedIndex();
		if (item != -1){
			articlesList.getItems().remove(item);
		}
	}
}