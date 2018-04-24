package Searcher.NetSearcher;

import java.util.ArrayList;

import Parser.Documents.DocumentContainer;
import javafx.util.Pair;

public class NetSearcher {

	private String query;
	private DocumentContainer document;

	public NetSearcher(String query, DocumentContainer document) {
		this.query = query;
		this.document = document;
	}

	public ArrayList<String> run() {
		ArrayList<Pair<Integer, Double>> scores = getScores(document, query);
		return getArrayList(scores);
	}

	private ArrayList<String> getArrayList(ArrayList<Pair<Integer, Double>> scores) {
		ArrayList<String> result = new ArrayList<String>();
		for (int x = 0; x < scores.size(); x++) {
			result.add(document.getSentences().get(scores.get(x).getKey()));
		}
		return result;
	}

	private ArrayList<Pair<Integer, Double>> getScores(DocumentContainer document, String query) {
		ArrayList<ArrayList<String>> sentences = document.getSentencesWithWords();
		ArrayList<Pair<Integer, Double>> result = new ArrayList<Pair<Integer, Double>>();
		for (int x = 0; x < sentences.size(); x++) {
			Pair<Integer, Double> sentenceWithScore = new Pair<Integer, Double>(x, getTheSentenceScore(sentences, x, query));
			result.add(sentenceWithScore);

		}
		
		ArrayList<Pair<Integer, Double>> orderedResult = new ArrayList<Pair<Integer, Double>>();
		orderedResult = orderScores(result, orderedResult);

		return orderedResult;

	}

	private ArrayList<Pair<Integer, Double>> orderScores(ArrayList<Pair<Integer, Double>> result,
			ArrayList<Pair<Integer, Double>> orderedResult) {

		if (result.size() == 0) {
			return orderedResult;
		} else {
			int index = getMax(result);
			orderedResult.add(result.get(index));
			result.remove(index);
			return orderScores(result, orderedResult);
		}
	}

	private int getMax(ArrayList<Pair<Integer, Double>> result) {
		double max = 0;
		int index = 0;
		for (int x = 0; x < result.size(); x++) {
			if (result.get(x).getValue() > max) {
				index = x;
				max = result.get(x).getValue();
			}
		}
		return index;
	}

	private double getTheSentenceScore(ArrayList<ArrayList<String>> sentences, int x, String query) {
		ArrayList<ArrayList<String>> tempSentences = sentences;
		NetSearcherNode searchNode = new NetSearcherNode(tempSentences.get(x), query);
		searchNode = setSearchNodeEnviroment(tempSentences, x, query, searchNode);
		return searchNode.getMatchValue();
	}

	private NetSearcherNode setSearchNodeEnviroment(ArrayList<ArrayList<String>> sentences, int x, String query,
			NetSearcherNode searchNode) {
		
		if (x == 0) {
			if (x < sentences.size() - 2) {
				searchNode.setNext1(new NetSearcherNode(sentences.get(x + 1), query));
				searchNode.setNext2(new NetSearcherNode(sentences.get(x + 2), query));
			} else if (x == sentences.size() - 2) {
				searchNode.setNext1(new NetSearcherNode(sentences.get(x + 1), query));
			}
		} else if (x == 1) {
			if (x < sentences.size() - 2) {
				searchNode.setPrev1(new NetSearcherNode(sentences.get(x - 1), query));
				searchNode.setNext1(new NetSearcherNode(sentences.get(x + 1), query));
				searchNode.setNext2(new NetSearcherNode(sentences.get(x + 2), query));
			} else if (x == sentences.size() - 2) {
				searchNode.setPrev1(new NetSearcherNode(sentences.get(x - 1), query));
				searchNode.setNext1(new NetSearcherNode(sentences.get(x + 1), query));
			}
		} else {
			if (x < sentences.size() - 2) {
				searchNode.setPrev2(new NetSearcherNode(sentences.get(x - 2), query));
				searchNode.setPrev1(new NetSearcherNode(sentences.get(x - 1), query));
				searchNode.setNext1(new NetSearcherNode(sentences.get(x + 1), query));
				searchNode.setNext2(new NetSearcherNode(sentences.get(x + 2), query));
			} else if (x == sentences.size() - 2) {
				searchNode.setPrev2(new NetSearcherNode(sentences.get(x - 2), query));
				searchNode.setPrev1(new NetSearcherNode(sentences.get(x - 1), query));
				searchNode.setNext1(new NetSearcherNode(sentences.get(x + 1), query));
			}
		}
		return searchNode;
	}
}