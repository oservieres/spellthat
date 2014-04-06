package spellthat.activity;

import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import spellthat.entity.Theme;

import com.example.spellthat.R;

public class MainActivity extends ActionBarActivity {
	
	public final static String EXTRA_THEME = "com.example.spellthat.THEME";
	public final static String EXTRA_INPUT_STRING = "com.example.spellthat.INPUT_STRING";
	
	ArrayList<Theme> themesList = new ArrayList<Theme>();
	
	public ArrayList<Theme> getThemesList() {
		return themesList;
	}
	
	protected void parseThemes() {
		
		XPathFactory factory = XPathFactory.newInstance();
	    XPath xPath = factory.newXPath();
	    try {
			NodeList themes = (NodeList) xPath.evaluate("/dictionary/theme", new InputSource(getResources().openRawResource(R.raw.themes)), XPathConstants.NODESET);
			for (int i = 0; i < themes.getLength(); ++i) {
			    Element theme = (Element) themes.item(i);
			    themesList.add(new Theme(Integer.valueOf(theme.getAttribute("id")), theme.getAttribute("label")));
			}
		} catch (XPathExpressionException e1) {
			Log.e("HEYYY", e1.getMessage());
			e1.printStackTrace();
		} catch (NotFoundException e1) {
			Log.e("HOOOOY", e1.getMessage());
			e1.printStackTrace();
		}
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        parseThemes();
        
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            
            View mainView = inflater.inflate(R.layout.fragment_main, container, false);
            LinearLayout themesLayout = (LinearLayout)mainView.findViewById(R.id.themes);

            Iterator<Theme> it = ((MainActivity)getActivity()).getThemesList().iterator();
            while (it.hasNext()) {
            	final Theme theme = it.next();
	            Button button = new Button(getActivity());
	            button.setText(theme.getLabel());
	            button.setOnClickListener(
            		new View.OnClickListener() {
            		    public void onClick(View v) {
            		    	EditText textField = (EditText)(getActivity().findViewById(R.id.word_to_spell));
            		    	String inputValue = textField.getText().toString();
            		    	if (inputValue.length() == 0) {
            		    		Toast.makeText(getActivity(), "Veuillez renseigner votre nom !", 2000).show();
            		    		return;
            		    	}
            		    	Intent intent = new Intent(getActivity(), SpellingActivity.class);
            		    	intent.putExtra(EXTRA_THEME, theme);
            		    	intent.putExtra(EXTRA_INPUT_STRING, inputValue);
            		    	startActivity(intent);
            		    	getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            		    }
            		}
	            );
	            button.setLayoutParams(
	            	new LayoutParams(
	            		ViewGroup.LayoutParams.WRAP_CONTENT,
	            		ViewGroup.LayoutParams.WRAP_CONTENT
	            	)
	            );
	            themesLayout.addView(button);
            }
	            
            return mainView;
        }
    }

}
