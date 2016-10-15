package com.time;

import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.PrivateCredentialPermission;

import com.tabhostdemo.R;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TimerView extends LinearLayout {
	private static final int MSG_WHAT_TIME_IS_UP = 1;
	private static final int MSG_WHAT_TIME_TICK = 2;
	private int allTimerCount = 0;
	private Timer timer = new Timer();
	private TimerTask timerTask = null;
	private Button btnStart = null;
	private Button btnPause = null;
	private Button btnResume = null;
	private Button btnReset = null;

	private EditText etHour = null;
	private EditText etMin = null;
	private EditText etSec = null;

	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("NewApi")
	public TimerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public TimerView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishInflate() {

		super.onFinishInflate();

		btnStart = (Button) findViewById(R.id.btnStart);
		btnStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startTimer();
				
				btnStart.setVisibility(View.GONE);
				btnPause.setVisibility(View.VISIBLE);
				btnReset.setVisibility(View.VISIBLE);
			}
		});
		btnPause = (Button) findViewById(R.id.btnPause);
		btnPause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopTimer();
				btnPause.setVisibility(View.GONE);
				btnResume.setVisibility(View.VISIBLE);
				
			}
		});
		btnReset = (Button) findViewById(R.id.btnReset);
		btnReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopTimer();
				etHour.setText("0");
				etMin.setText("0");
				etSec.setText("0");
				
				btnReset.setVisibility(View.GONE);
				btnResume.setVisibility(View.GONE);
				btnPause.setVisibility(View.GONE);
				btnStart.setVisibility(View.VISIBLE);
			}
		});
		btnResume = (Button) findViewById(R.id.btnResume);
		btnResume.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startTimer();
				btnResume.setVisibility(View.GONE);
				btnPause.setVisibility(View.VISIBLE);
				
			}
		});

		etHour = (EditText) findViewById(R.id.etHour);
		etMin = (EditText) findViewById(R.id.etMin);
		etSec = (EditText) findViewById(R.id.etSec);
		etHour.setText("00");
		etHour.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!TextUtils.isEmpty(s)) {
					int value = Integer.parseInt(s.toString());
					if (value > 59) {
						etHour.setText("59");
					} else if (value < 0) {
						etHour.setText("0");
					}
				}

				checkToEnableBtnStart();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		etMin.setText("00");
		etMin.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!TextUtils.isEmpty(s)) {

					int value = Integer.parseInt(s.toString());
					if (value > 59) {
						etMin.setText("59");
					} else if (value < 0) {
						etMin.setText("0");
					}
				}
				checkToEnableBtnStart();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		etSec.setText("00");
		etSec.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!TextUtils.isEmpty(s)) {
					int value = Integer.parseInt(s.toString());
					if (value > 59) {
						etSec.setText("59");
					} else if (value < 0) {
						etSec.setText("0");
					}
				}
				checkToEnableBtnStart();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		btnStart.setVisibility(View.VISIBLE);
		btnStart.setEnabled(false);
		btnPause.setVisibility(View.GONE);
		btnReset.setVisibility(View.GONE);
		btnResume.setVisibility(View.GONE);
	}

	private void checkToEnableBtnStart() {

		btnStart.setEnabled((!TextUtils.isEmpty(etHour.getText()) && Integer
				.parseInt(etHour.getText().toString()) > 0)
				|| (!TextUtils.isEmpty(etMin.getText()) && Integer
						.parseInt(etMin.getText().toString()) > 0)
				|| (!TextUtils.isEmpty(etSec.getText()) && Integer
						.parseInt(etSec.getText().toString()) > 0));
	}

	public void startTimer() {
		if (timerTask == null) {
			allTimerCount = Integer.parseInt(etHour.getText().toString()) * 60
					* 60 + Integer.parseInt(etMin.getText().toString()) * 60
					+ Integer.parseInt(etSec.getText().toString());
			timerTask = new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					allTimerCount--;
					
					handler.sendEmptyMessage(MSG_WHAT_TIME_TICK);
					
					if (allTimerCount <= 0) {
						handler.sendEmptyMessage(MSG_WHAT_TIME_IS_UP);
						stopTimer();
					}
				}
			};
			timer.schedule(timerTask, 1000, 1000);
			
		}
	}
	
	private void stopTimer(){
		if(timerTask != null){
			timerTask.cancel();
			timerTask = null;
		}
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_WHAT_TIME_IS_UP:
				new AlertDialog.Builder(getContext())
				.setTitle("Time is up")
				.setMessage("Time is up")
				.setNegativeButton("Cancel", null).show();
				btnReset.setVisibility(View.GONE);
				btnResume.setVisibility(View.GONE);
				btnPause.setVisibility(View.GONE);
				btnStart.setVisibility(View.VISIBLE);
				break;
			case MSG_WHAT_TIME_TICK:
				int hour = allTimerCount/60/60;
				int min = (allTimerCount/60) % 60;
				int sec = allTimerCount % 60;
				
				etHour.setText(hour + "");
				etMin.setText(min + "");
				etSec.setText(sec + "");
				
				break;
			default:
				break;
			}
		}
		
	};
	
}
