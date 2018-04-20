package Parser.Documents;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Parser.Parser;

public class DocumentContainerTest {

	@Test
	public void test() {
		String str1 = "hello my name is Josh how are you doing today? The weather is great outside.";
		String str2 = "Wow that is super cool. How are you enjoying this day. DO you like the class.";
		String str3 = "I like the color blue. Orange is a good color too I guess. But red is not so good.";
		ArrayList<String> strs1 = new ArrayList<String>();
		strs1.add(str1);
		strs1.add(str2);
		strs1.add(str3);
		Parser docs1 = new Parser(strs1);
		ArrayList<ArrayList<String>> test1 = docs1.getAllSentenceandWordsCombined();
		assertTrue(test1.get(0).size() == 3);
		assertTrue(test1.get(2).size() == 3);
		assertTrue(test1.get(5).size() == 2);
	}

}
