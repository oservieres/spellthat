package spellthat.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class WordIndex {
	
	private HashMap<Character, ArrayList<String>> map = new HashMap<Character, ArrayList<String>>();

	public void add(Character letter, String word) {
		ArrayList<String> list = null;
		list = map.get(Character.toLowerCase(letter));
		if (null == list) {
			list = new ArrayList<String>();
			map.put(Character.toLowerCase(letter), list);
		}
		list.add(word);
	}
	
	public String getWordByLetter(Character letter) {
		ArrayList<String> wordsList = map.get(Character.toLowerCase(letter));
		if (null == wordsList) {
			return "n'importe quoi";
		}
		Collections.shuffle(wordsList);
		return wordsList.get(0);
	}
}
