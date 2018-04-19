package Searcher;

import java.util.ArrayList;

import Parser.Parser;
import Parser.Documents.DocumentContainer;

public class NetSearcher {
	
	private String query;
	private ArrayList<DocumentContainer> documents;

	public NetSearcher(String query, Parser documents) {
		this.query = query;
		this.documents = documents.getAllDocuments();
	}

	public ArrayList<String> run() {
		
		for (int x = 0; x < documents.size(); x++) {
			DocumentContainer document = documents.get(x);
			NetSearcherNode sentence = new NetSearcherNode();
		}
		
		
		return null;
	}

}
