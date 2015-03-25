package ca.usask.drawingtoolslicer;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.view.MotionEvent;

public class MovingEnemyTrial extends Trial implements TouchEventHandler {
	
	private static final String TAG = MovingEnemyTrial.class.getSimpleName();

	private static float ENEMY_WIDTH = 0.2f; // in screen width proportion
	private static int BETWEEN_TRIAL_TIME = 1000; // in ms
	
	private float enemySpeed; // in %age of screen height per second
	private SliceLayer sliceLayer;
	private Enemy enemy;
	private GameView gameView;
	private Experiment experiment;
	private FastTapMenu fastTapMenu;
	private long trialStartTimestamp;
	
	private String enemyType;
	private int enemyColor;
	private Bitmap enemyIcon;
	private int delayTimeLeft;

	public MovingEnemyTrial(Experiment experiment, int id, String enemyType, float enemySpeed, int enemyColor, Bitmap enemyIcon) {
		super(id);
		this.experiment = experiment;
		this.enemyType = enemyType;
		this.enemySpeed = enemySpeed;
		this.enemyColor = enemyColor;
		this.enemyIcon = enemyIcon;
		
		this.delayTimeLeft = BETWEEN_TRIAL_TIME;
	}
	
	@Override
	protected void start(GameView gameView) {
		this.gameView = gameView;
		this.fastTapMenu = experiment.getFastTapMenu();
		this.sliceLayer = experiment.getSliceLayer();
		
		Random random = new Random();
		float enemyRadius = (gameView.getW()*ENEMY_WIDTH)/2f;
		float enemySpeedPx = gameView.getH()*this.enemySpeed;
		float padding = 20 + enemyRadius;
		float cx = padding + random.nextInt((int)(gameView.getW() - 2*padding));
		enemy = new Enemy(enemyType, cx, -enemyRadius, enemyRadius, enemySpeedPx, enemyColor, enemyIcon);

		StudyLogger.getInstance().pushLogMode(StudyLogger.LogModeType.TRIAL);
		StudyLogger.getInstance().createLogEvent("event.starttrial")
			.attr("trialid", id)
			.attr("trialtype", this.getClass().getSimpleName())
			.attr("enemyspeed", enemySpeed)
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

		if (fastTapMenu.getCurrentItemName() == enemy.type && sliceLayer.isSlicing(enemy) && enemy.alive) {
			enemy.setAlive(false);
			StudyLogger.getInstance().createLogEvent("event.enemydestroyed")
				.attr("trialid", id)
				.attr("after", currentTime - trialStartTimestamp)
				.commit();
		}

		enemy.update(delta);
		sliceLayer.update(delta);
		fastTapMenu.update(delta);
		
		if (enemy.y > gameView.getH() + enemy.radius) {
			delayTimeLeft -= delta;
			if (delayTimeLeft < 0) {
				completed = true; // end the trial
				if (enemy.alive) {
					StudyLogger.getInstance().createLogEvent("event.enemysurvived")
						.attr("trialid", id)
						.attr("after", currentTime - trialStartTimestamp)
						.commit();
				}
			}
		}
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
