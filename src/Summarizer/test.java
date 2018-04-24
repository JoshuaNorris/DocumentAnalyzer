package Summarizer;

import java.io.IOException;

public abstract class test {

	public static void main(String[] args) throws IOException {
		ScoreSummarizer y = new ScoreSummarizer();
		y.stopMaker();
		y.ScoreSentences("The dog ran across the street. This is my brother John."
				+ " We can have hamburgers for lunch. Have you been to Chicago? The sun is out today. "
				+ " Joan brought some cookies. Cross when the light is green. This is my new car. I ate three apples yesterday. "
				+ " I hope that this test works okay. I haven't done much testing of it yet. I don't know how long of a string it can handle. "
				+ " It could be very slow. I need at least ten sentneces to test based on the way I have set things up. "
				+ " I will shoot for twenty or more to be sure things work okay. I used a lot of ArrayLists. I also used a lot of loops and counters. "
				+ " I bet I made a mistake somewhere. I promise I don't talk to myself all the time, it's just easy to come up with these sentences. "
				+ " If it works, I think the way I've implemented it handles duplicate scores well. Although I think I remembered something I forgot to do."
				+ " The first few sentences were pasted from the internet, but the formatting made it take more time to copy them than to write my own."
				+ " I've got one of those orange salt lamps in my room.");

		y.copyScores();
		y.findTopSentences();
		y.topReturner();
	}

}
