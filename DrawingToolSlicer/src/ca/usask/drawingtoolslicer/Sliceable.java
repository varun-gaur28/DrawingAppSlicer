package ca.usask.drawingtoolslicer;

import android.graphics.PointF;

public interface Sliceable {

	public abstract boolean isHit(PointF p1, PointF p2);

}
