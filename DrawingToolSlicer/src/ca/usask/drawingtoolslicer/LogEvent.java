package ca.usask.drawingtoolslicer;

import org.json.JSONException;
import org.json.JSONObject;

public class LogEvent extends JSONObject {

	StudyLogger.LogMode logMode;
	
	public LogEvent(StudyLogger.LogMode logMode) {
		this.logMode = logMode;
	}

	public <T> LogEvent attr(String k, T v) {
		try {
			this.put(k, v);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this; // so multiple calls can be chained
	}
	
	public void commit() {
		logMode.commitLogEvent(this);
	}
	
}
