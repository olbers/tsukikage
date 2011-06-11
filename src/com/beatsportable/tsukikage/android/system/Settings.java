package com.beatsportable.tsukikage.android.system;

import java.util.HashMap;

import com.beatsportable.tsukikage.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.view.Display;

/**
 * ANDROID VER
 * This is for keeping track of app settings, drawn from SharedPreferences
 * This is initialized by Res
 * Call reload() on every relevant Activity onCreate()
 */
public abstract class Settings {

	// Public fields
	/**
	 * Pixel width and height based on default display
	 */
	public static int screen_w, screen_h;
	
	/**
	 * Do NOT use this for fonts - fonts auto-scale
	 */
	public static float scale_factor;
	
	// Private fields
	private static final float MAX_HEIGHT_PIXELS = 800f; // WVGA800
	private static Resources res;
	private static SharedPreferences prefs;
	private static SharedPreferences.Editor editor;
	private static HashMap<Integer, String> settings;
	
	// Manually keep this updated with settings_const.xml
	private static int[][] keys = {
		{R.string.setting_reset,			R.string.setting_reset_default},
		{R.string.setting_debug_log,		R.string.setting_debug_log_default},
		{R.string.setting_debug_track,		R.string.setting_debug_track_default}
	};
	
	/**
	 * Initialize, rebuilds all settings
	 */
	protected static void init(Activity a) {
		// Setup
		res = a.getApplicationContext().getResources();
		prefs = PreferenceManager.getDefaultSharedPreferences(a.getApplicationContext());
		editor = prefs.edit();
		
		// Screen Dimensions
		Display display = a.getWindowManager().getDefaultDisplay();
		screen_w = display.getWidth();
		screen_h = display.getHeight();
		
		// Note: Do NOT use this for fonts - fonts auto-scale
		scale_factor =
				(screen_h > screen_w) ?
				(screen_h / MAX_HEIGHT_PIXELS) :
				(screen_w / MAX_HEIGHT_PIXELS);
		
		// Load settings
		reload();
	}
	
	/**
	 * Reload all the settings.
	 */
	public static void reload() {
		settings = new HashMap<Integer, String>();
		for (int i = 0; i < keys.length; i++) {
			int setting = keys[i][0];
			int defaultValue = keys[i][1];
			String value = prefs.getString(res.getString(setting), res.getString(defaultValue));
			settings.put(setting, value);
		}
	}
	
	/**
	 * Clear all settings and reload
	 */
	public static void resetSettings() {
		editor.clear();
		editor.commit();
		//editor.putString(res.getString(R.string.setting_reset), "0"); // default_reset is "0" (off)
		reload();
	}
	
	/**
	 * Edit a setting
	 */
	public static void setSetting(int id_Setting, String value) {
		editor.putString(res.getString(id_Setting), value);
		editor.commit();
		settings.put(id_Setting, value);
	}
	
	/**
	 * Return setting value as a float, -1f on exception
	 */
	public static float getFloat(int id_Setting) {
		float ret = -1f;
		try {
			ret = Float.parseFloat(settings.get(id_Setting));
		} catch (Exception e) {
			// TODO
		}
		return ret;
	}
	
	/**
	 * Return setting value as a int, -1 on exception
	 */
	public static int getInt(int id_Setting) {
		int ret = -1;
		try {
			ret = Integer.parseInt(settings.get(id_Setting));
		} catch (Exception e) {
			// TODO
		}
		return ret;
	}
	
	/**
	 * Return setting value as a boolean (check against "1"), false on exception
	 */
	public static boolean getBoolean(int id_Setting) {
		boolean ret = false;
		try {
			ret = settings.get(id_Setting).equals("1");
		} catch (Exception e) {
			// TODO
		}
		return ret; 
	}
	
	/**
	 * Return setting value as a String, "" on exception
	 */
	public static String getString(int id_Setting) {
		String ret = "";
		try {
			ret = settings.get(id_Setting);
		} catch (Exception e) {
			// TODO
		}
		return ret;
	}
	
	/**
	 * Call this on exit of app
	 */
	protected static void onDestroy() {
		res = null; // For GC
		prefs = null;
		editor = null;
		settings = null;
	}
	
}
