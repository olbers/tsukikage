package com.beatsportable.tsukikage.android.menu;

import com.beatsportable.tsukikage.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

/**
 * ANDROID VER
 * This is the main menu activity
 */
public class Main extends Activity {
	
	// Private Variables
	private static final String MENU_FONT = "RoundStyleBasic.ttf";
	private Typeface tf;
	
	/**
	 * Main
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set layout
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFormat(PixelFormat.RGBA_8888); // prevent banding in menu background image
		setContentView(R.layout.menu_main);
		
		// Update layout
		updateLayout();
		
	}
	
	/**
	 * Update the menu layout
	 */
	private void updateLayout() {
		
		// Load font
		tf = Typeface.createFromAsset(getAssets(), MENU_FONT);
		
		// Start
		formatMenuItem(
				R.id.menu_main_start_TextView,
				R.string.menu_main_start,
				null
				);
		
		// Music
		formatMenuItem(
				R.id.menu_main_music_TextView,
				R.string.menu_main_music,
				null
				);
		
		//Options
		formatMenuItem(
				R.id.menu_main_options_TextView,
				R.string.menu_main_options,
				null
				);
		
		// Exit
		OnClickListener menu_main_exit_OnClickListener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Exit the app
				finish();
			}
		};
		formatMenuItem(
				R.id.menu_main_exit_TextView,
				R.string.menu_main_exit,
				menu_main_exit_OnClickListener
				);
	}
	
	/**
	 * Format menu items
	 * Contains some hard-coded values (laziness)
	 */
	private void formatMenuItem(int id_TextView, int id_String, OnClickListener action_OnClickListener) {
		try {
			// Find the text view, may possibly fail?
			final TextView tv = (TextView)findViewById(id_TextView);
			
			// Set the font format
			tv.setTypeface(tf);
			tv.setTextColor(Color.BLACK);
			tv.setShadowLayer(5f, 0, 0, Color.WHITE);
			
			// Set highlight listener
			tv.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent e) {
					if (e.getAction() == MotionEvent.ACTION_DOWN) {
						tv.setTextColor(Color.WHITE);
						tv.setShadowLayer(9f, 0, 0, Color.BLACK);
					} else if (e.getAction() == MotionEvent.ACTION_UP) {
						tv.setTextColor(Color.BLACK);
						tv.setShadowLayer(5f, 0, 0, Color.WHITE);
					}
					return false;
				}
			});
			
			// Set click listener
			if (action_OnClickListener != null) {
				tv.setOnClickListener(action_OnClickListener);
			}
			
			// Set text string
			tv.setText(id_String);
			
		} catch (Exception e) {
			// TODO
		}
	}
	
}
