package ca.usask.drawingtoolslicer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.MotionEvent;

public class CountdownTrial extends Trial {

	public static final String TAG = CountdownTrial.class.getSimpleName();
	public static final int BG_COLOR = 0xffffffff;
	
	private String message;
	private long remainingTimeMillis;
	private GameView gameView;
	private Paint mPaint;
	
	public CountdownTrial(int seconds, String message) {
		super();
		this.message = message;
		this.remainingTimeMillis = seconds*1000;
		
		mPaint = new Paint();
		mPaint.setTextAlign(Align.CENTER);
		mPaint.setColor(0xff000000);
		mPaint.setAntiAlias(true);
	}

	@Override
	public void draw(Canvas canvas) {
		float canvasWidth = gameView.getW();
		float canvasHeight = gameView.getH();
		mPaint.setColor(BG_COLOR);
		canvas.drawRect(0, 0, canvasWidth, canvasHeight, mPaint);
		
		mPaint.setColor(0xff000000);
		mPaint.setTextSize(canvasWidth/3f);
		canvas.drawText(this.remainingTimeMillis/1000 + "", canvasWidth/2f, 3f*canvasHeight/5f, mPaint);
		mPaint.setTextSize(50);
		canvas.drawText(message, canvasWidth/2f, 2f*canvasHeight/5f, mPaint);
	}

	@Override
	protected void start(GameView gameView) {
		this.gameView = gameView;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void update(long delta) {
		this.remainingTimeMillis -= delta;
	}

	@Override
	public void handleTouchEvent(MotionEvent event) {
		// Ignore touch events
	}

	@Override
	public boolean isCompleted() {
		return this.remainingTimeMillis <= 0;
	}

}
