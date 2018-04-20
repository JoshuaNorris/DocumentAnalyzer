package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JFileChooser;

import Database.Database;
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

public class GUIController {
	@FXML
	Button search, back, viewSummary, viewFull;
	
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
	
	static Database db;
	static {
		try {
			db = new Database();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
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
		           String currentItemSelected = articlesList.getSelectionModel().getSelectedItem();
		           loadArticleViewer(currentItemSelected);
		        }
		    }
		});

	}

	public void pressLoadFileButton() {
		//Oracle documentation
		try {
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
		putFileinDatabase(filename.substring(0, filename.length() - 4), wholeFile);
		s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		populateArticlesList();
	}
	
	private void putFileinDatabase(String name, String fullText) {
		try {
			db.insertDocument(name, fullText);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			//TODO
			e.printStackTrace();
		}
	}
	
	public void loadArticleViewer(String filename) {
		articlesTab.setText(filename);
		articlesTab.setDisable(false);
		tabHolder.getSelectionModel().select(articlesTab);
		title.setText(filename);
		try {
			view.setText(db.getSummaryOf(filename));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}