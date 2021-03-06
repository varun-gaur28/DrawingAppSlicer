package ca.usask.drawingtoolslicer;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Observer {

	private static final String TAG = GameView.class.getSimpleName();

	private Experiment experiment;
	private MainThread thread;
	private int width;
	private int height;
	private GameApp app;
	
	public HashMap<Integer, TouchEventHandler> pointerOwners;

	public GameView(Context context, GameApp app) {
		super(context);

		this.app = app;
		
		// add the callback (this) to the surface holder, so we can intercept events
		getHolder().addCallback(this);

		// start the experiment
		this.experiment = this.app.getExperiment();
		this.experiment.setView(this);
		this.experiment.addObserver(this);
		
		// create the game loop thread
		thread = new MainThread(getHolder(), this);
		// make the GameView focusable so it can handle events
		setFocusable(true);
		
		pointerOwners = new HashMap<Integer, TouchEventHandler>();
	}
	
	public int getW() {
		return this.width;
	}
	
	public int getH() {
		return this.height;
	}
	
	public void shutdownGameThread() {
		this.thread.setRunning(false);
	}
	
	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.width = w;
        this.height = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		Log.d(TAG, "Surface destroying...");
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again to shut down the thread
			}
		}
	}
	
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	// Event handling scheme:
    	// - All touch events get sent to all objects
    	// - An object can claim a pointer, by registering itself in the pointerOwners hash map in this object
    	// - Objects check if pointers for events are owned by other objects, and if so, they should ignore them
    	//   * When they do so, they should also clean up any state they have saved for that pointer
    	//
    	// Note: This scheme can result in individual touch events being handled by multiple objects, but that is OK.
    	//       The key thing is that claiming ownership over events allows us to prevent this to happen when it is
    	//       actually undesirable (e.g., menu selections shouldn't also be registered as swipes to try and kill
    	//       enemies), while permitting it the rest of the time.

    	experiment.handleTouchEvent(event);
    	
    	return true;
    }

	@Override
	public void update(Observable observable, Object data) {
		if (experiment.isCompleted()) {
			if (app.getDbxAccountManager().hasLinkedAccount()) {
				try {
					StudyLogger.getInstance().sendLogFileToDbx(app.getDbxAccountManager().getLinkedAccount());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			shutdownGameThread();
		}
	}
    
    public void render(Canvas canvas) {
    	canvas.drawColor(Color.WHITE);
    	experiment.draw(canvas);
    }
    
    public void update(long delta) {
    	experiment.update(delta);
    }


}