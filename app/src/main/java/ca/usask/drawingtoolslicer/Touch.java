package ca.usask.drawingtoolslicer;

import android.graphics.PointF;

public class Touch {
	public PointF point;
	public long timestamp;
	
	public Touch(PointF point, long timestamp) {
		this.point = point;
		this.timestamp = timestamp;
	}
}