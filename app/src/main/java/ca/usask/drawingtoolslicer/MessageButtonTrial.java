package ca.usask.drawingtoolslicer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.view.MotionEvent;

public class MessageButtonTrial extends Trial {

	public static final String TAG = MessageButtonTrial.class.getSimpleName();
	public static final int BG_COLOR = 0xffffffff;
	public static final int LOCK_OUT_TIME = 10; // in seconds
	
	private String message;
	private String buttontext;
	private GameView gameView;
	private Paint mPaint;
	
	private RectF buttonRect;
	private int lockOutTime;
	
	public MessageButtonTrial(String message, String buttontext) {
		super();
		this.message = message;
		this.buttontext = buttontext;
		this.lockOutTime = LOCK_OUT_TIME*1000;
		
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
		mPaint.setTextSize(40);
		float x = canvasWidth/2f;
		float y = 2f*canvasHeight/5f;
		for(String line : message.split("\n")){
		      canvas.drawText(line, x, y, mPaint);
		      y += 1.1*(-mPaint.ascent() + mPaint.descent());
		}

		float lr_padding = 100;
		float button_height = 150;
		float button_cy = 3f*canvasHeight/5f;
		this.buttonRect = new RectF(lr_padding, button_cy - button_height/2f, canvasWidth - lr_padding, button_cy + button_height/2f);		
		mPaint.setColor(0xff0066cc);
		canvas.drawRoundRect(this.buttonRect, 20f, 20f, mPaint);		
		mPaint.setTextSize(50);
		mPaint.setColor(0xffffffff);
		canvas.drawText(buttontext, canvasWidth/2f, 3f*canvasHeight/5f + mPaint.descent(), mPaint);
	}

	@Override
	protected void start(GameView gameView) {
		this.gameView = gameView;
	}

	@Override
	protected void end() {
		// pass
	}

	@Override
	protected void update(long delta) {
		this.lockOutTime -= delta;
	}

	@Override
	public void handleTouchEvent(MotionEvent event) {
		int index = event.getActionIndex();
		float x = event.getX(index);
		float y = event.getY(index);
		int id = event.getPointerId(index);
		long currentTime = event.getEventTime();
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			if (this.lockOutTime < 0 && this.buttonRect.contains(x, y)) {
				this.completed = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
	        break;
		}
	}

	@Override
	public boolean isCompleted() {
		return completed;
	}

}
