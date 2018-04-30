package Summarizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StopWordMaker {

	public ArrayList<String> stopWords = new ArrayList<String>();
	private File wordList = new File("/DocumentAnalyzer/files/stop-words-list.txt");

	public StopWordMaker() {
	}

	public void stopMaker() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(wordList));
		while (reader.readLine() != null) {
			stopWords.add(reader.readLine());
		}

	}
}
