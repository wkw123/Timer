package com.time;

import java.util.Timer;
import java.util.TimerTask;

import com.tabhostdemo.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class StopWatchView extends LinearLayout {

	public StopWatchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	private static final int MSG_WHAT_SHOW_TIME = 1;
	private TextView tvHour;
	private TextView tvMin;
	private TextView tvSec;
	private TextView tvMSec;

	private Button btnStart;
	private Button btnResume;
	private Button btnReset;
	private Button btnPause;
	private Button btnLap;

	private ListView lvTimeList;

	private ArrayAdapter<String> adapter;

	private Timer timer = new Timer();
	private TimerTask timerTask = null;
	private int tenMSecs = 0;
	private TimerTask showtTimerTask = null;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_WHAT_SHOW_TIME:
				tvHour.setText("" + tenMSecs / 100 / 60 / 60);
				tvMin.setText("" + (tenMSecs / 100 / 60) % 60);
				tvSec.setText("" + tenMSecs / 100 % 60);
				tvMSec.setText("" + tenMSecs % 100);
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onFinishInflate() {

		super.onFinishInflate();

		tvHour = (TextView) findViewById(R.id.timeHour);
		tvHour.setText("0");
		tvMin = (TextView) findViewById(R.id.timeMin);
		tvMin.setText("0");
		tvSec = (TextView) findViewById(R.id.timeSec);
		tvSec.setText("0");
		tvMSec = (TextView) findViewById(R.id.timeMSec);
		tvMSec.setText("0");

		btnStart = (Button) findViewById(R.id.btnSWStart);
		btnStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startTimer();

				btnStart.setVisibility(View.GONE);
				btnPause.setVisibility(View.VISIBLE);
				btnLap.setVisibility(View.VISIBLE);

			}
		});
		btnStart.setVisibility(View.VISIBLE);
		btnResume = (Button) findViewById(R.id.btnSWResume);
		btnResume.setVisibility(View.GONE);
		btnResume.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startTimer();
				btnResume.setVisibility(View.GONE);
				btnPause.setVisibility(View.VISIBLE);
				btnReset.setVisibility(View.GONE);
				btnLap.setVisibility(View.VISIBLE);
			}
		});
		btnReset = (Button) findViewById(R.id.btnSWReset);
		btnReset.setVisibility(View.GONE);
		btnReset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopTimer();
				tenMSecs = 0;
				adapter.clear();
				btnReset.setVisibility(View.GONE);
				btnPause.setVisibility(View.GONE);
				btnLap.setVisibility(View.GONE);
				btnResume.setVisibility(View.GONE);
				btnStart.setVisibility(View.VISIBLE);
			}
		});
		btnPause = (Button) findViewById(R.id.btnSWPause);
		btnPause.setVisibility(View.GONE);
		btnPause.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopTimer();
				btnPause.setVisibility(View.GONE);
				btnResume.setVisibility(View.VISIBLE);
				btnLap.setVisibility(View.GONE);
				btnReset.setVisibility(View.VISIBLE);
			}
		});
		btnLap = (Button) findViewById(R.id.btnSWLap);
		btnLap.setVisibility(View.GONE);
		btnLap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adapter.insert(String.format("%d:%d:%d.%d",
						tenMSecs / 100 / 60 / 60, tenMSecs / 100 / 60 % 60,
						tenMSecs / 100 % 60, tenMSecs % 100), 0);
				
			}
		});
		lvTimeList = (ListView) findViewById(R.id.lvWatchTimeList);

		adapter = new ArrayAdapter<String>(getContext(),
				android.R.layout.simple_list_item_1);
		lvTimeList.setAdapter(adapter);

		showtTimerTask = new TimerTask() {

			@Override
			public void run() {
				handler.sendEmptyMessage(MSG_WHAT_SHOW_TIME);

			}
		};
		timer.schedule(showtTimerTask, 200, 200);
	}

	private void startTimer() {
		if (timerTask == null) {
			timerTask = new TimerTask() {

				@Override
				public void run() {
					tenMSecs++;

				}
			};
			timer.schedule(timerTask, 10, 10);
		}
	}

	public void stopTimer() {
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
	}

	public void onDestory() {
		timer.cancel();

	}
}
