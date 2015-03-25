package ca.usask.drawingtoolslicer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.PointF;

public class Enemy implements Sliceable {
	
	public static final String TAG = Enemy.class.getSimpleName();

	private static final int FADE_OUT_DURATION = 650;
	private static final int FLASH_DURATION = 100;
	
	public String type;
	public float x;
	public float y;
	public float radius;
	public boolean alive;
	private int color;
	private Bitmap icon;
	private double speed;
	
	private int fadeOut;
	private int flashTime;
	
	Paint paint;
	Paint labelPaint;
	
	public Enemy(String type, float x, float y, float radius, double speed, int color, Bitmap icon) {
		
		this.type = type;
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.speed = speed;
		this.color = color;
		this.icon = icon;
		this.alive = true;
		this.fadeOut = FADE_OUT_DURATION;
		this.flashTime = FLASH_DURATION;
		
		paint = new Paint();

		labelPaint = new Paint();
		labelPaint.setTextAlign(Align.CENTER);
		labelPaint.setColor(0xff000000);
		labelPaint.setTextSize(18);
		labelPaint.setAntiAlias(true);	
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void update(long delta) {
		this.y += this.speed*(delta/1000.0);
		if (!alive) {
			this.fadeOut -= delta;
			this.flashTime -= delta;
		}
	}
	
	public void draw(Canvas canvas) {
		if (alive) {
			paint.setColor(color);
	        paint.setStyle(Paint.Style.FILL);
			canvas.drawCircle(this.x, this.y, this.radius, this.paint);
			if (icon != null) {
				paint.setColor(0xff000000);
				canvas.drawBitmap(icon, this.x - icon.getWidth()/2f, this.y - icon.getHeight()/2f, paint);
				canvas.drawText(this.type, this.x, this.y + 56, labelPaint);
			}
		} else {
			if (fadeOut > 0) {
				paint.setStrokeCap(Cap.ROUND);
				paint.setStrokeWidth(4f);
				paint.setStyle(Paint.Style.FILL);
				paint.setColor(color);
				paint.setAlpha(255*this.fadeOut/FADE_OUT_DURATION);
				labelPaint.setAlpha(255*this.fadeOut/FADE_OUT_DURATION);
				canvas.drawCircle(this.x, this.y, this.radius, this.paint);
				if (icon != null) {
					canvas.drawBitmap(icon, this.x - icon.getWidth()/2f, this.y - icon.getHeight()/2f, paint);
					canvas.drawText(this.type, this.x, this.y + 56, labelPaint);
				}
			}
			if (flashTime > 0) {
				paint.setStyle(Paint.Style.FILL_AND_STROKE);
				paint.setColor(Color.MAGENTA);
				canvas.drawCircle(this.x, this.y, this.radius, this.paint);
			}
		}
	}
	
	@Override
	public boolean isHit(PointF p1, PointF p2) {
		final boolean p1in = GeometryUtils.dist(x, y, p1.x, p1.y) < radius;
		final boolean p2in = GeometryUtils.dist(x, y, p2.x, p2.y) < radius;
		return (p1in && !p2in) || (p2in && !p1in);
	}
	
}
