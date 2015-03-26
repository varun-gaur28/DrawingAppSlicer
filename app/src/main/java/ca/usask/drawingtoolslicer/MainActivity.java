package ca.usask.drawingtoolslicer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_LINK_TO_DBX = 0;

    private GameApp app;
    private Button startGameButton;
    private Button resumeGameButton;
    private ListView listView;
    private AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGameButton = (Button)findViewById(R.id.startgame);
        resumeGameButton = (Button)findViewById(R.id.resumegame);
        listView = (ListView)findViewById(R.id.listview);
        app = (GameApp)getApplication();

        alert = new AlertDialog.Builder(this);
        alert.setTitle("Discard log data?");
        alert.setMessage("Starting a new session will discard all data for the experiment in progress.");
        alert.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startNewGame();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        update();

        // Check if linked to Dropbox, and if not, link it up
        if (app.getDbxAccountManager().hasLinkedAccount() == false) {
            app.getDbxAccountManager().startLink((Activity)this, REQUEST_LINK_TO_DBX);
        }
    }

    public void handleStartButtonClicked(View v) {
        if (app.getGameInProgress()) {
            alert.show();
        } else {
            startNewGame();
        }
    }

    public void handleResumeButtonClicked(View v) {
        app.resumeGame();
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void startNewGame() {
        app.startNewGame();
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void update() {
        if (app.getGameInProgress()) {
            resumeGameButton.setEnabled(true);
            listView.setVisibility(View.VISIBLE);
            // Update the game state list view
            StateItem[] items = {new StateItem("Start Date", app.getStartDate()),
                    new StateItem("Trial Number", app.getTrialNumber() + "")};
            final StateItemAdapter adapter = new StateItemAdapter(this, items);
            listView.setAdapter(adapter);
        } else {
            resumeGameButton.setEnabled(false);
            listView.setVisibility(View.INVISIBLE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LINK_TO_DBX) {
            if (resultCode == Activity.RESULT_OK) {
                // ... Start using Dropbox files.
            } else {
                // ... Link failed or was cancelled by the user.
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class StateItemAdapter extends ArrayAdapter<StateItem> {
        private final Context context;
        private final StateItem[] values;

        public StateItemAdapter(Context context, StateItem[] values) {
            super(context, R.layout.rowlayout, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
            TextView labelView = (TextView)rowView.findViewById(R.id.label);
            TextView valueView = (TextView)rowView.findViewById(R.id.value);
            labelView.setText(values[position].label);
            valueView.setText(values[position].value);
            return rowView;
        }

    }

    private class StateItem {
        public String label;
        public String value;
        public StateItem(String label, String value) {
            this.label = label;
            this.value = value;
        }
    }

}
