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
		
		return finalSections;
	}
	
	

}
