package com.beatsportable.tsukikage.android.menu;

import com.beatsportable.tsukikage.R;
import com.beatsportable.tsukikage.android.system.Res;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * ANDROID VER
 * This is the splash screen activity.
 * This will fade in/out a splash image, then launch the Main activity
 * Based on example from:
 * 	http://www.anddev.org/novice-tutorials-f8/splash-fade-activity-animations-overridependingtransition-t9464.html
 */
public class Splash extends Activity {
	
	// Private Variables
	private static final long SPLASH_DELAY_MS = 3000l;
	private Handler launch_Handler;
	private Runnable launch_Runnable;
	
	/**
	 * Main
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set layout
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.menu_splash);
		
		// Fade in image
		ImageView splash_screen_ImageView = (ImageView)this.findViewById(R.id.menu_splash_background_ImageView);
		Animation fade_in_Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		splash_screen_ImageView.startAnimation(fade_in_Animation);
		
		// Initialize static resources, app should hang here til all done
		Res.init(this);
		
		// Launch runnable
		launch_Runnable = new Runnable() {
			@Override
			public void run() {
				// Launch Main
				Intent launch_MainMenu = new Intent(Splash.this, Main.class);
				Splash.this.startActivity(launch_MainMenu);
				// Finish this activity
				Splash.this.finish();
				// Override activity transition animation
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		};
		
		// Run launch_Runnable after a delay
		launch_Handler = new Handler();
		launch_Handler.postDelayed(launch_Runnable, SPLASH_DELAY_MS);
		
		// Tap screen to skip splash
		OnClickListener splash_screen_OnClickListener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				launch_Handler.removeCallbacksAndMessages(null);
				launch_Handler.postDelayed(launch_Runnable, 0); // Run it immediately
			}
		};
		splash_screen_ImageView.setOnClickListener(splash_screen_OnClickListener);
	}
	
	/**
	 * Keypress handler
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// If BACK key pressed
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // Backward compatibility
			// Stop launch
			launch_Handler.removeCallbacksAndMessages(null);
			// Exit app
			Res.onDestroy();
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}

