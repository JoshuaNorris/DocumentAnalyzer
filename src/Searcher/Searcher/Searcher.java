package Searcher.Searcher;

import java.util.ArrayList;

import Parser.Documents.*;
import Parser.*;

public class Searcher {
	
	private String query;
	private DocumentContainer documents;
	
	public Searcher(String query, String document) {
		this.query = query;
		this.documents = new DocumentContainer(document);
	}
	
	public ArrayList<String> run() {
		NetSearcher netSearch = new NetSearcher(query, documents);
		return netSearch.run();
		}
	

}
