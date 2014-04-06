package spellthat.activity;


import spellthat.entity.Theme;

import com.example.spellthat.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SpellingActivity extends ActionBarActivity {

	private String inputString;
	private Theme currentTheme; 
	
	public Theme getCurrentTheme() {
		return currentTheme;
	}
	
	public String getInputString() {
		return inputString;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_spelling);
		
		Intent intent = getIntent();
		inputString = intent.getStringExtra(MainActivity.EXTRA_INPUT_STRING);
		currentTheme = intent.getParcelableExtra(MainActivity.EXTRA_THEME);
		
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
		return true;
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
				TextView line = new TextView(activity);
				line.setLayoutParams(
	            	new LayoutParams(
	            		ViewGroup.LayoutParams.WRAP_CONTENT,
	            		ViewGroup.LayoutParams.WRAP_CONTENT
	            	)
	            );
				line.setText(Character.toString(inputString.charAt(i)));
				lettersList.addView(line);
			}
			
			return mainView;
		}
	}

}
