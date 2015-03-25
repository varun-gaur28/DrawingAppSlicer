package ca.usask.drawingtoolslicer;

import java.util.ArrayList;
import java.util.Observable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.MotionEvent;

public class Experiment extends Observable implements TouchEventHandler {

	private static final String TAG = Experiment.class.getSimpleName();

	protected Trial[] trials;
	protected int curTrial;
	protected boolean completed;
	protected boolean started;
	protected long randomSeed;
	protected GameView gameView;

	protected FastTapMenu fastTapMenu1;
    protected FastTapMenu fastTapMenu2;
    protected ArrayList<FastTapMenu> fTM;
	protected SliceLayer sliceLayer;

	private Paint mPaint;
	
	public Experiment(long randomSeed, int curTrial) {
		this.randomSeed = randomSeed;
		this.curTrial = curTrial;
		this.completed = false;
		this.started = false;

		this.mPaint = new Paint();
		mPaint.setTextAlign(Align.CENTER);
		mPaint.setColor(0xff000000);
		mPaint.setAntiAlias(true);
	}
	
	public void setView(GameView gameView) {
		this.gameView = gameView;
	}
	
	public void update(long delta) {
		if (!started) {
			StudyLogger.getInstance().createLogEvent("event.startexperiment")
				.attr("experimentclass", this.getClass().getSimpleName())
				.attr("width", this.gameView.getW())
				.attr("height", this.gameView.getH())
				.attr("trials", this.trials.length)
				.attr("curtrial", curTrial)
				.attr("seed", this.randomSeed)
				.commit();
			trials[curTrial].start(this.gameView);
			started = true;
		}
		if (!completed) {
			trials[curTrial].update(delta);
			if (trials[curTrial].isCompleted()) {
				trials[curTrial].end();
				curTrial++;
				if (curTrial < trials.length) {
					trials[curTrial].start(this.gameView);
				} else {
					completed = true;
				}
				// Notify observers that the experiment is completed, or the trial has changed
				this.setChanged();
				this.notifyObservers();
			}
		}
	}
	
	public void draw(Canvas canvas) {
		if (!completed) {
			trials[curTrial].draw(canvas);
		} else {
			canvas.drawColor(Color.WHITE); // TODO: Draw something else (maybe a big DONE!) text
			mPaint.setTextSize(50);
			canvas.drawText("Experiment Completed!", this.gameView.getW()/2f, this.gameView.getH()/2f, mPaint);
		}
	}
	
	public boolean isCompleted() {
		return completed;
	}
	
	public void setCurTrial(int trial) {
		this.curTrial = trial;
	}
	
	public int getCurTrial() {
		return this.curTrial;
	}

	public ArrayList<FastTapMenu> getFastTapMenu() {
		return this.fTM;
	}
	
	public SliceLayer getSliceLayer() {
		return this.sliceLayer;
	}    
		
	@Override
	public void handleTouchEvent(MotionEvent event) {
		if (!completed) {
			// delegate touch event to the current trial
			trials[curTrial].handleTouchEvent(event);
		}
	}
	
}
