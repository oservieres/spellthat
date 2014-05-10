package spellthat.entity;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class WordIndex {
	
	private HashMap<Character, ArrayList<String>> map = new HashMap<Character, ArrayList<String>>();

	public void add(Character letter, String word) {
		ArrayList<String> list = null;
		Character slug = slugify(letter);
		list = map.get(slug);
		if (null == list) {
			list = new ArrayList<String>();
			map.put(slug, list);
		}
		list.add(word);
	}
	
	public String getWordByLetter(Character letter) {
		ArrayList<String> wordsList = map.get(slugify(letter));
		if (null == wordsList) {
			return "n'importe quoi";
		}
		Collections.shuffle(wordsList);
		return wordsList.get(0);
	}
	
	public Character slugify(Character letter) {
		String slug = Normalizer.normalize(Character.toString(letter), Normalizer.Form.NFD);
	    slug = slug.replaceAll("[^\\p{ASCII}]", "");
		return Character.toLowerCase(slug.charAt(0));
	}
}
