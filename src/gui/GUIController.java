package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JFileChooser;

import Database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GUIController {
	@FXML
	Button search, back, viewSummary, viewFull;
	
	@FXML
	TextField searchBar;
	
	@FXML
	ListView articlesList;
	
	@FXML
	TextArea view;
	
	@FXML
	Label title, keywords;
	
	@FXML
	Tab articlesTab;
	
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
		Scanner s = new Scanner(chosenFile);		
		String wholeFile = s.useDelimiter("\\A").next();
		putFileinDatabase(chosenFile.getName(), wholeFile);
		s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
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
	

}
