package Parser;

import java.util.ArrayList;

import Parser.Documents.DocumentContainer;

public class Parser {

	private ArrayList<DocumentContainer> allDocuments = new ArrayList<DocumentContainer>();
	
	public Parser(ArrayList<String> allDocuments) {
		this.allDocuments = convertToDocumentContainer(allDocuments);
	}

	private ArrayList<DocumentContainer> convertToDocumentContainer(ArrayList<String> allDocs) {
		ArrayList<DocumentContainer> allDocuments = new ArrayList<DocumentContainer>();
		for (int x = 0; x < allDocs.size(); x++) {
			DocumentContainer doc = new DocumentContainer(allDocs.get(x));
			allDocuments.add(doc);
		}
		return allDocuments;
	}
}
