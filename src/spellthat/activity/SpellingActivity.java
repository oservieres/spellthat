package spellthat.activity;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import spellthat.entity.Theme;
import spellthat.entity.WordIndex;

import com.spellthat.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SpellingActivity extends ActionBarActivity {

	private String inputString;
	private Theme currentTheme; 
	private WordIndex wordIndex;
	
	public Theme getCurrentTheme() {
		return currentTheme;
	}
	
	public String getInputString() {
		return inputString;
	}
	
	protected void buildWordIndex() {
		wordIndex = new WordIndex();
		XPathFactory factory = XPathFactory.newInstance();
	    XPath xPath = factory.newXPath();
	    try {
	    	int fileId = getResources().getIdentifier("words_" + Integer.toString(currentTheme.getId()), "raw", getPackageName());
			NodeList words = (NodeList) xPath.evaluate(
				"/words/word",
				new InputSource(getResources().openRawResource(fileId)), 
				XPathConstants.NODESET
			);
			for (int i = 0; i < words.getLength(); ++i) {
				Element word = (Element) words.item(i);
				wordIndex.add(word.getAttribute("letter").charAt(0), word.getTextContent());
			}
	    } catch (XPathExpressionException e1) {
		} catch (NotFoundException e1) {
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_spelling);
		
		Intent intent = getIntent();
		inputString = intent.getStringExtra(MainActivity.EXTRA_INPUT_STRING);
		currentTheme = intent.getParcelableExtra(MainActivity.EXTRA_THEME);
		
		buildWordIndex();
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					                   .add(R.id.container, new PlaceholderFragment())
					                   .commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spelling, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View mainView = inflater.inflate(R.layout.fragment_spelling, container, false);
			
			SpellingActivity activity = (SpellingActivity)getActivity(); 
			
			String inputString = activity.getInputString();
			
			TextView title = (TextView)mainView.findViewById(R.id.activity_title);
			title.setText(
				"\"" +
				inputString
				+ "\" épelé avec le thème \""
				+ activity.getCurrentTheme().getLabel()
				+ "\""
			);
			
			LinearLayout lettersList = (LinearLayout)mainView.findViewById(R.id.letters_list);

			for (int i = 0 ; i < inputString.length(); ++i) {
				char letter = inputString.charAt(i);
				LinearLayout line = new LinearLayout(activity);
				
				TextView letterView = new TextView(activity);
				letterView.setText(Character.toString(Character.toUpperCase(letter)));
				letterView.setTextSize(35);
				letterView.setTypeface(Typeface.MONOSPACE);
				line.addView(letterView);
				
				TextView linkView = new TextView(activity);
				linkView.setText(" comme ");
				linkView.setTextSize(10);
				line.addView(linkView);
				
				TextView wordView = new TextView(activity);
				wordView.setText(activity.wordIndex.getWordByLetter(letter));
				wordView.setTextSize(20);
				line.addView(wordView);
				
				lettersList.addView(line);
			}
			
			Button button = (Button)mainView.findViewById(R.id.back_to_main);
			button.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
			    	Intent intent = new Intent(getActivity(), MainActivity.class);
			    	startActivity(intent);
			    	getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
			    }
			});
			
			return mainView;
		}
	}

}
