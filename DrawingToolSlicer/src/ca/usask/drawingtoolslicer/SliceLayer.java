package ca.usask.drawingtoolslicer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.SystemClock;
import android.util.SparseArray;
import android.view.MotionEvent;

public class SliceLayer implements TouchEventHandler {
	
	private static final String TAG = SliceLayer.class.getSimpleName();

	private SparseArray<Touch> mOrigins;
	private SparseArray<Touch> mEnds;
	private List<SliceTrail> mTrails;
	private Paint slicePaint;
	private GameView gameView;

	public SliceLayer() {
		mOrigins = new SparseArray<Touch>();
        mEnds = new SparseArray<Touch>();
        mTrails = Collections.synchronizedList(new LinkedList<SliceTrail>());
        slicePaint = new Paint();
        slicePaint.setColor(Color.MAGENTA);
        slicePaint.setStrokeWidth(3f);
	}
	
	public boolean isSlicing(Sliceable thing) {
		synchronized(mTrails) {
			for (SliceTrail t : mTrails) {
				if (t.isCutting() && thing.isHit(t.start, t.end)) {
					return true;
				}
			}
		}
		return false;
	}

	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}
	
	public void draw(Canvas canvas) {
		synchronized(mTrails) {
			for (SliceTrail t : mTrails) {
				canvas.drawLine(t.start.x, t.start.y, t.end.x, t.end.y, slicePaint);			
			}
		}
	}
	
	public void update(long delta) {
		// Remove expired trails
        synchronized (mTrails) {
			ArrayList<SliceTrail> toDelete = new ArrayList<SliceTrail>();
			for (SliceTrail t : mTrails) {
				t.delay -= delta;
				if (t.delay < 0) {
					toDelete.add(t);
				}
			}
			for (SliceTrail t : toDelete) {
				mTrails.remove(t);
			}
        }
	}
	
	private void sliceMoveUpdate(int pointerid, long timestamp, float x, float y) {
		if (!isValidPointer(pointerid)) {
			return;
		}
    	if (mEnds.get(pointerid) != null) {
    		mOrigins.put(pointerid, mEnds.get(pointerid));
    	}
        mEnds.put(pointerid, new Touch(new PointF(x, y), timestamp));
        synchronized (mTrails) {
        	mTrails.add(new SliceTrail(mOrigins.get(pointerid).point, mEnds.get(pointerid).point));
        }
	}
	
	private boolean isValidPointer(int pointerid) {
		// if the pointer is owned by another object, the event should be ignored
		if (gameView.pointerOwners.containsKey(pointerid) && gameView.pointerOwners.get(pointerid) != this) {
			if (mOrigins.get(pointerid) != null) {
				StudyLogger.getInstance().createLogEvent("event.swipeabort")
				.attr("pointerid", pointerid)
				.commit();
			}
			// clean up any state related to the id that we just found out we don't own
			mOrigins.remove(pointerid);
			mEnds.remove(pointerid);
			return false;
		}
		return true;
	}
	
	@Override
	public void handleTouchEvent(MotionEvent event) {
		int index = event.getActionIndex();
		float x = event.getX(index);
		float y = event.getY(index);
		int id = event.getPointerId(index);
		long currentTime = event.getEventTime();

		if (!isValidPointer(id)) {
			return;
		}
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			mOrigins.put(id, new Touch(new PointF(x, y), currentTime));
			StudyLogger.getInstance().createLogEvent("event.swipestart")
				.attr("pointerid", id)
				.attr("x", x)
				.attr("y", y)
				.commit();			
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			// If we own the pointer (that is, if no-one else owns it), register a swipe end
			if (mOrigins.get(id) != null && gameView.pointerOwners.get(id) == null) {
				StudyLogger.getInstance().createLogEvent("event.swipeend")
					.attr("pointerid", id)
					.attr("x", x)
					.attr("y", y)
					.commit();
			}
			mOrigins.remove(id);
			mEnds.remove(id);
			break;
		case MotionEvent.ACTION_MOVE:
			// Move event can involve multiple pointer IDs
			final int historySize = event.getHistorySize();
			final int pointerCount = event.getPointerCount();
			long historicTime;
			// Process batched motion events
			for (int h = 0; h < historySize; h++) {
				historicTime = event.getHistoricalEventTime(h);
				for (int p = 0; p < pointerCount; p++) {
					id = event.getPointerId(p);
					x = event.getHistoricalX(p,h);
					y = event.getHistoricalY(p,h);
					sliceMoveUpdate(id, historicTime, x, y);
				}
			}
			// Process the latest motion event
			for (int p = 0; p < pointerCount; p++) {
				id = event.getPointerId(p);
				x = event.getX(p);
				y = event.getY(p);
				sliceMoveUpdate(id, currentTime, x, y);
			}
	        break;
		}
	}
	
	private class SliceTrail {
		private static final int SWIPE_TRAIL_LENGTH = 200; // in milliseconds
		private static final int SWIPE_KILL_LENGTH = 60; // in milliseconds
		public PointF start;
		public PointF end;
		public int delay;

		public SliceTrail(PointF start, PointF end) {
			this.start  = start;
			this.end = end;
			delay = SWIPE_TRAIL_LENGTH;
		}
		
		public boolean isCutting() {
			return delay >= SWIPE_TRAIL_LENGTH - SWIPE_KILL_LENGTH;
		}
	}

}
