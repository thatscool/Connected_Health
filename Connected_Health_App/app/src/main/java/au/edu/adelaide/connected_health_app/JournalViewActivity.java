package au.edu.adelaide.connected_health_app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JournalViewActivity extends ActionBarActivity {

    private final int patientID = 1;
    private final String journalEntriesUrl = "http://192.168.1.5:9999/ConnectedHealth/patient/" + patientID + "/journal";
    static int viewId = 1;      // get unique ID for views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_view);

        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string JSON response from the Grails app.
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, journalEntriesUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // check if response is a valid JSON array
                        JSONArray journalEntriesJson = null;
                        try {
                            journalEntriesJson = new JSONArray(response);
                        } catch (JSONException je) {
                            System.out.println("Response was not a valid JSON array.");
                        }

                        // Extract values from JSON array to display in the medical notes section of the profile view
                        StringBuilder sb = null;
                        try {
                            JSONObject entryObject;
                            sb = new StringBuilder();
                            sb.append("Journal Entries\n");
                            for (int i = 0; i < journalEntriesJson.length(); i++) {
                                entryObject = journalEntriesJson.getJSONObject(i);
                                sb.append("Created: " + (entryObject.get("created")).toString() + "\n");
                                sb.append("Updated: " + (entryObject.get("updated")).toString() + "\n");
                                sb.append("Content: " + entryObject.getString("content") + "\n");
                            }
                        } catch (JSONException je) {
                            System.out.println("Couldn't extract values from JSON array");
                        }

                        // Display values
                        TextView journal_entries = (TextView)findViewById(R.id.journal_entries);
                        journal_entries.append("\n" + sb.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Volley HTTP request for journal entries failed.");
            }
        });
        // Add the request to the RequestQueue for asynchronous handling.
        queue.add(stringRequest2);


        final String[] staticJournalEntries = {"The first journal entry.\nWith two lines.", "The second journal entry.", "The third journal entry.", "The fourth journal entry."};
        final RelativeLayout staticJournalEntriesLayout=(RelativeLayout) findViewById(R.id.staticJournalEntryLayout);
        ArrayList<TextView> staticEntryTextViews = new ArrayList<TextView>();
        ArrayList<Button> staticEntryEditButtons = new ArrayList<Button>();
        ArrayList<Button> staticEntryDeleteButtons = new ArrayList<Button>();

        // for each journal entry, display the entry content in a TextView, with edit and delete Buttons below it
        // assign a unique ID to each TextView/Button, so they can be arranged in a RelativeLayout
        Button recentEditButton = null;
        for (int i = 0; i < staticJournalEntries.length; i++) {
            TextView currentTextView = new TextView(this);
            staticEntryTextViews.add(currentTextView);
            RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {    // put entry below the buttons for the previous entry (excluding 1st question)
                textViewParams.addRule(RelativeLayout.BELOW, recentEditButton.getId());
            }
            currentTextView.setId(++viewId);
            currentTextView.setLayoutParams(textViewParams);
            currentTextView.setPadding(10, 10, 10, 0);
            currentTextView.setText(staticJournalEntries[i]);
            currentTextView.setTextSize((float) 20);
            staticJournalEntriesLayout.addView(currentTextView);

            Button currentEditButton = new Button(this);
            staticEntryEditButtons.add(currentEditButton);
            RelativeLayout.LayoutParams editButtonParams = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);
            editButtonParams.addRule(RelativeLayout.BELOW, currentTextView.getId());
            currentEditButton.setId(++viewId);
            currentEditButton.setLayoutParams(editButtonParams);
            currentEditButton.setText("Edit");
            currentEditButton.setTextSize((float) 20);
            staticJournalEntriesLayout.addView(currentEditButton);
            recentEditButton = currentEditButton;

            Button currentDeleteButton = new Button(this);
            staticEntryDeleteButtons.add(currentDeleteButton);
            RelativeLayout.LayoutParams deleteButtonParams = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);
            deleteButtonParams.addRule(RelativeLayout.BELOW, currentTextView.getId());
            deleteButtonParams.addRule(RelativeLayout.RIGHT_OF, recentEditButton.getId());
            currentDeleteButton.setId(++viewId);
            currentDeleteButton.setLayoutParams(deleteButtonParams);
            currentDeleteButton.setText("Delete");
            currentDeleteButton.setTextSize((float) 20);
            staticJournalEntriesLayout.addView(currentDeleteButton);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_journal_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
