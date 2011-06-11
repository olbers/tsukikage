package com.beatsportable.tsukikage.android.system;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.Localytics.android.LocalyticsSession;
import com.beatsportable.tsukikage.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * ANDROID VER
 * This is for toasts, tracking and logs
 * This is initialized by Res
 * For Localytics documentation, see http://wiki.localytics.com/doku.php
 */
public abstract class Tracker {
	
	// Private constant
	private static final String TRACK = "Localytics";
	
	// Private fields
	private static boolean debug_log;
	private static String APP_VER;
	private static Context c;
	private static LocalyticsSession localytics;
	
	/**
	 * Initialize Localytics and tracking stuff
	 */
	protected static void init(Activity a) {
		debug_log = Settings.getBoolean(R.string.setting_debug_log);
		APP_VER = String.format("%s %s ", Res.getString(R.string.app_name), Res.getString(R.string.app_ver));
		c = a.getApplicationContext();
		
		// Localytics setup
		localytics = new LocalyticsSession(c, Res.getString(R.string.key_localytics));
		localytics.open();
		localytics.upload();
	}
	
	/**
	 * Toast info string, short
	 * Do not use this for error messages
	 */
	public static void toast(String TAG, String msg) {
		info(TAG, msg);
		Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Toast info string, long
	 * Do not use this for error messages 
	 */
	public static void toast_long(String TAG, String msg) {
		info(TAG, msg);
		Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Toast warning string, long
	 * Warnings shouldn't be a short toast
	 * Do not use this for error messages
	 */
	public static void toast_warn(String TAG, String msg) {
		warn(TAG, msg);
		Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Print info message to logcat
	 */
	public static void info(String TAG, String msg) {
		Log.i(APP_VER + TAG, msg);
	}
	
	/**
	 * Print warning message to logcat
	 */
	public static void warn(String TAG, String msg) {
		Log.w(APP_VER + TAG, msg);
	}
	
	/**
	 * Print warning message and stack trace to logcat
	 */
	public static void warn(String TAG, String msg, Throwable tr) {
		Log.w(APP_VER + TAG, msg, tr);
	}
	
	/**
	 * Print error message to logcat
	 */
	public static void error(String TAG, String msg) {
		Log.e(APP_VER + TAG, msg);
	}
	
	/**
	 * Print error message and stack trace to logcat
	 */
	public static void error(String TAG, String msg, Throwable tr) {
		Log.e(APP_VER + TAG, msg, tr);
	}
	
	/**
	 * Print debug message to logcat
	 * These do not show up on the phone usually
	 * If the debug_log setting is enabled, these messages
	 * will show up as warnings instead
	 */
	public static void debug(String TAG, String msg) {
		if (debug_log) {
			Log.w(APP_VER + TAG, msg);
		} else {
			Log.d(APP_VER + TAG, msg);
		}
	}
	
	/**
	 * Track an event with Localytics
	 */
	public static void track(String TAG, String EVENT) {
		if (localytics != null) {
			localytics.tagEvent(String.format("%s%s - %s", APP_VER, TAG, EVENT));
		}
		String msg = String.format("%s: %s", TAG, EVENT);
		debug(TRACK, msg);
	}
	
	/**
	 * Track an event with a single value with Localytics
	 */
	public static void trackAttribute(String TAG, String EVENT, String attribute, String value) {
		String event = String.format("%s%s - %s", APP_VER, TAG, EVENT);
		if (localytics != null) {
			HashMap<String, String> attributes = new HashMap<String, String>();
			attributes.put(attribute, value);
			localytics.tagEvent(event, attributes);
		}
		String msg = String.format("%s: %s\n%s = %s", TAG, EVENT, attribute, value);
		debug(TRACK, msg);
	}
	
	/**
	 * Track an event with multiple values with Localytics
	 */
	public static void trackAttribute(String TAG, String EVENT, HashMap<String,String> attributes) {
		if (localytics != null) {
			localytics.tagEvent(String.format("%s%s - %s", APP_VER, TAG, EVENT), attributes);
		}
		String msg = String.format("%s: %s", TAG, EVENT);
		Iterator<Entry<String, String>> it = attributes.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> e = it.next();
			msg += String.format("\n%s = %s", e.getKey(), e.getValue());
		}
		debug(TRACK, msg);
	}
	
	/**
	 * Call this on exit of app
	 */
	protected static void onDestroy() {
		if (localytics != null) {
			localytics.upload();
			localytics.close();
		}
		c = null; // For GC
	}
	
}
