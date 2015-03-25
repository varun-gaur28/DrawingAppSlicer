package ca.usask.drawingtoolslicer;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.view.MotionEvent;

public class StaticEnemyTrial extends Trial implements TouchEventHandler {
	
	private static final String TAG = StaticEnemyTrial.class.getSimpleName();

	private static float ENEMY_WIDTH = 0.2f; // in screen width proportion
	private static float ENEMY_Y_POS = 300f;
	private static int BETWEEN_TRIAL_DELAY = 1000; // in ms
	
	private SliceLayer sliceLayer;
	private Enemy enemy;
	private GameView gameView;
	private Experiment experiment;
	private FastTapMenu fastTapMenu;
	private long trialStartTimestamp;
	private int betweenTrialDelayCountdown;
	
	private String enemyType;
	private int enemyColor;
	private Bitmap enemyIcon;

	public StaticEnemyTrial(Experiment experiment, int id, String enemyType, int enemyColor, Bitmap enemyIcon) {
		super(id);
		this.experiment = experiment;
		this.enemyType = enemyType;
		this.enemyColor = enemyColor;
		this.enemyIcon = enemyIcon;
	}
	
	@Override
	protected void start(GameView gameView) {
		this.gameView = gameView;
		this.fastTapMenu = experiment.getFastTapMenu();
		this.sliceLayer = experiment.getSliceLayer();
		
		Random random = new Random();
		float enemyRadius = (gameView.getW()*ENEMY_WIDTH)/2f;
		float padding = 20 + enemyRadius;
		float cx = gameView.getW() / 2f;
		float cy = ENEMY_Y_POS;
		enemy = new Enemy(enemyType, cx, cy, enemyRadius, 0, enemyColor, enemyIcon);

		StudyLogger.getInstance().pushLogMode(StudyLogger.LogModeType.TRIAL);
		StudyLogger.getInstance().createLogEvent("event.starttrial")
			.attr("trialid", id)
			.attr("trialtype", this.getClass().getSimpleName())
			.attr("enemytype", enemyType)
			.commit();
		
		this.trialStartTimestamp = SystemClock.uptimeMillis();
	}

	@Override
	protected void end() {
		StudyLogger.getInstance().createLogEvent("event.endtrial")
			.attr("trialid", id)
			.commit();
		StudyLogger.getInstance().popLogMode();
	}
	
	@Override
	public void update(long delta) {
		final long currentTime = SystemClock.uptimeMillis();

		if (!enemy.alive) {
			betweenTrialDelayCountdown -= delta;
			if (betweenTrialDelayCountdown < 0) {
				completed = true;
				return;
			}
		}
		
		if (fastTapMenu.getCurrentItemName() == enemy.type && sliceLayer.isSlicing(enemy) && enemy.alive) {
			enemy.setAlive(false);
			StudyLogger.getInstance().createLogEvent("event.enemydestroyed")
				.attr("trialid", id)
				.attr("after", currentTime - trialStartTimestamp)
				.commit();
			betweenTrialDelayCountdown = BETWEEN_TRIAL_DELAY;
		}

		enemy.update(delta);
		sliceLayer.update(delta);
		fastTapMenu.update(delta);
		
	}
	
	@Override
	public void draw(Canvas canvas) {
		enemy.draw(canvas);
		sliceLayer.draw(canvas);
		fastTapMenu.draw(canvas);
	}

	@Override
	public void handleTouchEvent(MotionEvent event) {
		fastTapMenu.handleTouchEvent(event);
		sliceLayer.handleTouchEvent(event);
	}
}
