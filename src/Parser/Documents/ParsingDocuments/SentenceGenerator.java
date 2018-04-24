package Parser.Documents.ParsingDocuments;

import java.util.ArrayList;

public class SentenceGenerator {

	private String doc;
	
	
	public SentenceGenerator (String doc) {
		this.doc = doc;
	}
	
	public ArrayList<String> run() {
		String[] array = doc.split("");
		ArrayList<String> result = new ArrayList<String>();
		int x = 0;
		String str = "";
		while (x < array.length) {
			str += array[x];
			if (isSentenceEnd(x)) {
				result.add(str);
				str = "";
			}
			x++;
		}
		result.add(str);
		return result;
	}
	
	private boolean isSentenceEnd(int x) {
		if (isPunctuation(x)) {
			if (nextWordIsCapitalized(x) && spaceAfterwards(x) && notTitleAbbreviation(x)) {
				return true;
			}
		}
		return false;
	}

	private boolean notTitleAbbreviation(int x) {
		int y = x;
		while (doc.charAt(y) > 0 && doc.charAt(y) != ' ') {
			y--;
		}
		String title = doc.substring(y + 1, x + 1);
		if (title.equals("Mr.")) {
			return false;
		} else if (title.equals("Mrs.")) {
			return false;
		} else if (title.equals("Dr.")) {
			return false;
		} else if (title.equals("Esq.")) {
			return false;
		} else if (title.equals("Hon.")) {
			return false;
		} else if (title.equals("Jr.")) {
			return false;
		} else if (title.equals("Ms.")) {
			return false;
		} else if (title.equals("Messrs.")) {
			return false;
		} else if (title.equals("Mmes.")) {
			return false;
		} else if (title.equals("Msgr.")) {
			return false;
		} else if (title.equals("Prof.")) {
			return false;
		} else if (title.equals("Rev.")) {
			return false;
		} else if (title.equals("Rt.")) {
			return false;
		} else if (title.equals("Sr.")) {
			return false;
		} else if (title.equals("St.")) {
			return false;
		}
		return true;
	}

	private boolean spaceAfterwards(int x) {
		return doc.charAt(x + 1) == ' ';
	}

	private boolean nextWordIsCapitalized(int x) {
		x++;
		while ((x != doc.length()) && (doc.charAt(x) == ' ')) {
			x++;
		}
		if ((x != doc.length()) && (Character.isUpperCase(doc.charAt(x)))) {
			return true;
		}
		return false;
	}

	private boolean isPunctuation(int x) {
		if (doc.charAt(x) == '.') {
			return true;
		} else if (doc.charAt(x) == '!') {
			return true;
		} else if (doc.charAt(x) == '?') {
			return true;
		}
		return false;
	}
	
	
}
