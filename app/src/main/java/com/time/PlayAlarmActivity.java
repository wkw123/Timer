package com.time;

import com.tabhostdemo.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

public class PlayAlarmActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.alarm_player_aty);
		mPlayer = MediaPlayer.create(this, R.raw.music);
		mPlayer.start();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		mPlayer.stop();
		mPlayer.release();
	}
	
	
	private MediaPlayer mPlayer;
}
