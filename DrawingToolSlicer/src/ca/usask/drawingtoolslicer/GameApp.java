package ca.usask.drawingtoolslicer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.SystemClock;

import com.dropbox.sync.android.DbxAccountManager;

public class GameApp extends Application implements Observer {

	private static final String TAG = GameApp.class.getSimpleName();

	public static final String PREFS_NAME = "ExperimentState";
	public static final String GAME_IN_PROGRESS_KEY = "GameInProgressKey";
	public static final String RANDOM_SEED_KEY = "RandomSeedKey";
	public static final String START_DATE_KEY = "StartDateKey";
	public static final String START_TIMESTAMP_KEY = "StartTimestampKey";
	public static final String TRIAL_NUMBER_KEY = "TrialNumberKey";

	private SharedPreferences prefs;
	private Experiment experiment;

	private DbxAccountManager mDbxAcctMgr;
	
	@Override
	public void onCreate() {
		super.onCreate();
		prefs = getSharedPreferences(PREFS_NAME, 0);
		mDbxAcctMgr = DbxAccountManager.getInstance(this.getApplicationContext(),"sw666mmqneso32b", "m1o3nf5yxoizwxh");
	}
	
	private void prepareExperiment() {
		//experiment = new StaticEnemyExperiment( getRandomSeed(), getTrialNumber(), this);
		experiment = new ToolSlicerExperiment( getRandomSeed(), getTrialNumber(), this);
		experiment.addObserver(this);
	}
	
	public void startNewGame() {
		String dt = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss", Locale.US).format(new Date());
		long ts = SystemClock.uptimeMillis();
		
		// Put all the shared preferences back to their defaults
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(GAME_IN_PROGRESS_KEY, true);
		editor.putLong(RANDOM_SEED_KEY, new Random().nextLong());
		editor.putString(START_DATE_KEY, dt);
		editor.putLong(START_TIMESTAMP_KEY, ts);
		editor.putInt(TRIAL_NUMBER_KEY, 0);
		editor.commit();
		
		StudyLogger.getInstance().startLog(dt, ts);
		prepareExperiment();
	}
	
	public void resumeGame() {
		String dt = getStartDate();
		long ts = getStartTimestamp();
		
		StudyLogger.getInstance().resumeLog(dt, ts);
		prepareExperiment();
	}
	
	public String getStartDate() {
		return prefs.getString(START_DATE_KEY, "");
	}

	public long getStartTimestamp() {
		return prefs.getLong(START_TIMESTAMP_KEY, 0);
	}

	public long getRandomSeed() {
		return prefs.getLong(RANDOM_SEED_KEY, 0);
	}
	
	public int getTrialNumber() {
		return prefs.getInt(TRIAL_NUMBER_KEY, 0);
	}

	public boolean getGameInProgress() {
		return prefs.getBoolean(GAME_IN_PROGRESS_KEY, false);
	}
	
	public Experiment getExperiment() {
		return this.experiment;
	}
	
	public DbxAccountManager getDbxAccountManager() {
		return this.mDbxAcctMgr;
	}
	
	@Override
	public void update(Observable observable, Object data) {
		// TODO: Add some handling for the experiment being completed
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(TRIAL_NUMBER_KEY, experiment.getCurTrial());
		editor.putBoolean(GAME_IN_PROGRESS_KEY, !experiment.isCompleted());
		editor.commit();
	}
}
