package ca.usask.drawingtoolslicer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import com.dropbox.sync.android.DbxAccount;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;

public class StudyLogger {
	
	private final static String TAG = StudyLogger.class.getSimpleName();

	private static StudyLogger instance = null;

	public static enum LogModeType {
		GLOBAL, TRIAL
	}
	
	public static StudyLogger getInstance() {
		if (instance == null) {
			instance = new StudyLogger();
		}
		return instance;
	}

	private File mLogDir;
	private File mLogFile;

	private String mStartDate;
	private long mStartTimestamp;
	private long mResumeTimestamp;
	private String mLogFileName;
	
	private GlobalLogMode globalLogMode;
	private LogMode currentLogMode;

	protected StudyLogger() {
		// Create the logging directory if necessary
		mLogDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DrawingToolSlicer/logs");
		mLogDir.mkdirs();
		
		globalLogMode = new GlobalLogMode(this);
		currentLogMode = globalLogMode;
	}

	public void startLog(String dt, long ts) {
		mLogFileName = dt + ".log";
		mStartTimestamp = ts;
		mResumeTimestamp = 0;
		
		resetLogMode();

		mLogFile = new File(mLogDir, mLogFileName);
		createLogEvent("event.startlogging").commit();
	}

	public void resumeLog(String dt, long ts) {
		mLogFileName = dt + ".log";
		mStartTimestamp = ts;
		mResumeTimestamp = SystemClock.uptimeMillis();
		String resumeDate = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss", Locale.US).format(new Date());
		
		resetLogMode();

		mLogFile = new File(mLogDir, mLogFileName);
		createLogEvent("event.resumelogging")
			.attr("resumedatetime", resumeDate)
			.commit();
	}

	public LogEvent createLogEvent(String type) {
		long ts = SystemClock.uptimeMillis();
		return new LogEvent(this.currentLogMode).attr("type", type)
				.attr("startdate", mStartDate)
				.attr("startts", mStartTimestamp)
				.attr("resumets", mResumeTimestamp)
				.attr("timestamp", ts);
	}
	
	public void pushLogMode(LogModeType logModeType) {
		if (this.currentLogMode != this.globalLogMode) {
			throw new RuntimeException("Attempt to push log frame when current frame is not global!");
		}
		if (logModeType == LogModeType.TRIAL) {
			this.currentLogMode = new TrialLogMode(this);
		}
	}

	public void popLogMode() {
		if (this.currentLogMode == this.globalLogMode) {
			throw new RuntimeException("Attempt to pop global log frame!");
		}
		this.currentLogMode.commit();
		this.currentLogMode = this.globalLogMode;
	}
	
	public void resetLogMode() {
		this.currentLogMode = this.globalLogMode;
	}
	
	public synchronized void writeLogEvent(LogEvent evt) {
		Log.d(TAG, evt.toString());
		try {
			FileWriter fw = new FileWriter(mLogFile, true);
			fw.append(evt.toString() + "###\n");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendLogFileToDbx(DbxAccount account) throws DbxException, IOException {
		// Based on http://stackoverflow.com/questions/20100698/android-dropbox-sync-api-upload-file-and-display-progress-bar
		DbxPath path = new DbxPath("/DrawingToolSlicer/logs/" + mLogFile.getName());
		DbxFileSystem fs = DbxFileSystem.forAccount(account);
		DbxFile outfile;
		try {
			outfile = fs.open(path);
		} catch (DbxException.NotFound e) {
			outfile = fs.create(path);
		}
		FileOutputStream out = outfile.getWriteStream();
		FileInputStream in = new FileInputStream(mLogFile);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}				
		out.close();
		outfile.close();
		in.close();
	}

	public abstract class LogMode {
		protected StudyLogger logger;
		
		public LogMode(StudyLogger logger) {
			this.logger = logger;
		}
		
		public abstract LogEvent createLogEvent(String type);
		
		public abstract void commitLogEvent(LogEvent e);
		
		public abstract void commit();
	}
	
	public class GlobalLogMode extends LogMode {
		
		public GlobalLogMode(StudyLogger logger) {
			super(logger);
		}

		@Override
		public LogEvent createLogEvent(String type) {
			return logger.createLogEvent(type);
		}
		
		@Override
		public void commitLogEvent(LogEvent e) {
			logger.writeLogEvent(e);
		}
		
		@Override
		public void commit() {
			// do nothing, events are written as we go
		}

	}
	
	public class TrialLogMode extends LogMode {
		
		private List<LogEvent> events;
		
		public TrialLogMode(StudyLogger logger) {
			super(logger);
			this.events = Collections.synchronizedList(new LinkedList<LogEvent>());
		}
		
		@Override
		public LogEvent createLogEvent(String type) {
			return logger.createLogEvent(type);
		}

		@Override
		public void commitLogEvent(LogEvent e) {
			events.add(e);
		}
		
		@Override
		public void commit() {
			synchronized (events) {
				for (LogEvent e : events) {
					logger.writeLogEvent(e);
				}
				events.clear();
			}
		}
		
	}
}
