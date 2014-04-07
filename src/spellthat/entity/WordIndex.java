package spellthat.entity;

import java.util.HashMap;

public class WordIndex {
	
	private HashMap<Character, String> map = new HashMap<Character, String>();

	public void add(Character letter, String word) {
		map.put(letter, word);
	}
	
	public String getWordByLetter(Character letter) {
		String word = map.get(Character.toLowerCase(letter));
		if (word == null) {
			word = "n'importe quoi";
		}
		return word;
	}
}
