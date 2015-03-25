package ca.usask.drawingtoolslicer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class GameActivity extends Activity {

	private static final String TAG = GameActivity.class.getSimpleName();

	private GameApp app;
	private GameView gameView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		app = (GameApp)getApplication();    
        gameView = new GameView(this, app);
        setContentView(gameView);
        Log.d(TAG, "View added");        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
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
    protected void onDestroy() {
    	Log.d(TAG, "Destroying...");
    	super.onDestroy();
    }
    
    @Override
    protected void onStop() {
    	Log.d(TAG, "Stopping...");
    	super.onStop();
		this.finish(); // So 'Back' and 'Home' act the same
    }
    
    @Override
    protected void onPause() {
    	Log.d(TAG, "Pausing...");
    	super.onPause();
    	gameView.shutdownGameThread();
    }
    
    @Override
    protected void onResume() {
    	Log.d(TAG, "Resuming...");
    	super.onResume();
    }
}
