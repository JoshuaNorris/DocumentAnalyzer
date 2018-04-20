package Searcher;

import java.util.ArrayList;

import Parser.Documents.*;
import Parser.*;

public class Searcher {
	
	private String query;
	private Parser documents;
	
	public Searcher(String query, Parser documents) {
		this.query = query;
		this.documents = documents;
	}
	
	public ArrayList<String> run() {
		NetSearcher somSearch = new NetSearcher(query, documents);
		return somSearch.run();
		
		}
	

}
