package Sectioner;

import java.util.ArrayList;

import Parser.Documents.DocumentContainer;

public class DocumentSectioner {
	private DocumentContainer document;
	private ArrayList<String> sectionedText;
	
	public DocumentSectioner(String text) {
		this.document = new DocumentContainer(text);
		sectionedText = section(document);
	}
	
	public String getLargerText() {return document.getText();}
	public ArrayList<String> getSectionedText() {return sectionedText;}

	private ArrayList<String> section(DocumentContainer text) {
		ArrayList<String> finalSections = new ArrayList<String>();
		int lowerSentence = 0;
		int upperSentence = getSentenceIndexByPercent(text, 1, lowerSentence); //arbitrary 1% chosen to start the sectioning process
		finalSections = sectionOut(text.getText(), lowerSentence, upperSentence);
		return finalSections;
	}
	
	private int getSentenceIndexByPercent(DocumentContainer text, int lowerBound, int percent) {
		int index = 0;
		
		return index;
	}
	
	private ArrayList<String> sectionOut(String text, int lower, int upper) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	

}
