package ca.usask.drawingtoolslicer;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class Trial {

	protected int id;
	protected boolean completed;
	
	private static final String TAG = Trial.class.getSimpleName();

	public Trial() {
		this.id = -1;
	}
	
	public Trial(int id) {
		this.id = id;
	}
	
	public abstract void draw(Canvas canvas);
	
	protected abstract void start(GameView gameView);
	
	protected abstract void end();

	protected abstract void update(long delta);

	public abstract void handleTouchEvent(MotionEvent event);

	public boolean isCompleted() {
		return completed;
	}
	
}
