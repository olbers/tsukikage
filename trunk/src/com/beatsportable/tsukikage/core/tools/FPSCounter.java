package com.beatsportable.tsukikage.core.tools;

import android.os.SystemClock;

/**
 * This is for calculating FPS values
 */
public abstract class FPSCounter {

	// Private variables
	private static int updateFrequency = 60;
	private static long startTime, totalTime, pauseTime;
	private static float frameCount, frameCountTotal;
	private static float currentFPS, averageFPS;
	private static String displayedFPS = "";
	private static String formatFPS = "FPS %.2f/%.2f";
	
	/**
	 * Number of frames per calculation update (default 60)
	 */
	public static void setUpdateFrequency(int frequency) {
		updateFrequency = frequency;
	}
	
	/**
	 * Set formatting for displayed FPS string
	 */
	public static void setFormatting(String format) {
		formatFPS = format;
	}
	
	/**
	 * Reset all values
	 */
	public static void restart() {
		startTime = SystemClock.elapsedRealtime();
		totalTime = 0;
		pauseTime = 0;
		frameCount = 0;
		frameCountTotal = 0;
		displayedFPS = "";
	}
	
	/**
	 * Call this on every frame
	 */
	public static void nextFrame() {
		frameCount++;
		if (frameCount >= updateFrequency) { // It should never go above updateFrequency, but just in case...
			// Elapsed time since last update
			long currentTime = SystemClock.elapsedRealtime();
			long timeDiff = currentTime - startTime;
			// Calculate current FPS
			currentFPS = frameCount / ((float)timeDiff / 1000f);
			// Update totals
			frameCountTotal += frameCount;
			totalTime += timeDiff;
			averageFPS = frameCountTotal / ((float)totalTime / 1000f);
			// Calculate average FPS
			averageFPS = 
			// Clear current data
			frameCount = 0;
			startTime = currentTime;
			// Format displayed FPS string
			displayedFPS = String.format(formatFPS, currentFPS, averageFPS);
		}
	}
	
	/**
	 * Call this before pausing the game
	 */
	public static void pause() {
		pauseTime = SystemClock.elapsedRealtime();
	}
	
	/**
	 * Call this after resuming the game
	 */
	public static void resume() {
		startTime += SystemClock.elapsedRealtime() - pauseTime;
		pauseTime = 0;
	}
	
	/**
	 * Get current FPS value
	 */
	public static float getCurrentFPS() {
		return currentFPS;
	}
	
	/**
	 * Get average FPS value
	 */
	public static float getAverageFPS() {
		return averageFPS;
	}
	
	/**
	 * Get formatted displayed FPS string
	 */
	public static String getDisplayedFPS() {
		return displayedFPS;
	}
}
