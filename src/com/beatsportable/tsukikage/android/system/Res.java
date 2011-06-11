package com.beatsportable.tsukikage.android.system;

import java.util.Locale;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;

/**
 * ANDROID VER
 * This is for accessing internal resources
 * This also initializes all the other static resources
 */
public abstract class Res {

	// Private constants
	private static final String TAG = "RES";
	
	// Private fields
	private static Resources res = null;

	/**
	 * Initialize all resources
	 */
	public static void init(Activity a) {
		res = a.getApplicationContext().getResources();
		Settings.init(a);
		Tracker.init(a);
		
		//if (Settings.getBoolean(R.string.setting_debug_log)) {
			toast_debug_info();
		//}
	}
	
	/**
	 * For debugging only
	 */
	private static void toast_debug_info() {
		Locale defaultLocale = Locale.getDefault();
		Tracker.toast(TAG, String.format(
				"Device: %s / Model: %s / SDK: %d / ID: %s\n" +
				"Width: %s / Height: %s / Scale: %s\n" +
				"Country: %s / Language: %s",
				Build.DEVICE, Build.MODEL, Build.VERSION.SDK_INT, Build.ID,
				Settings.screen_w, Settings.screen_h, Settings.scale_factor,
				defaultLocale.getCountry(), defaultLocale.getLanguage()
				));
	}
	
	/**
	 * Return a string defined in string resources
	 */
	public static String getString(int id_String) {
		String ret;
		try {
			ret = res.getString(id_String);
		} catch (Exception e) { // NotFound
			Tracker.error(TAG, "getString failed to find id_String " + id_String);
			ret = "";
		}
		return ret;
	}
	
	/**
	 * Return a string array defined in string resources
	 */
	public static String[] getStringArray(int id_String) {
		String[] ret;
		try {
			ret = res.getStringArray(id_String);
		} catch (Exception e) { // NotFound
			Tracker.error(TAG, "getStringArray failed to find id_String " + id_String);
			ret = new String[0];
		}
		return ret;
	}
	
	/**
	 * Call this on exit of app
	 */
	public static void onDestroy() {
		// Reverse order of init
		Tracker.onDestroy();
		Settings.onDestroy();
		res = null;
		System.gc();
	}
	
}
