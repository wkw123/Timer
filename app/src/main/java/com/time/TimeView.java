package com.time;

import java.util.Calendar;

import com.tabhostdemo.R;

import android.R.string;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimeView extends LinearLayout {
	private TextView tvTime;

	@SuppressLint("NewApi")
	public TimeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public TimeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TimeView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvTime.setText("hello");

		timeHandler.sendEmptyMessage(0);
	}

	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		// TODO Auto-generated method stub
		super.onVisibilityChanged(changedView, visibility);

		if (visibility == View.VISIBLE) {
			timeHandler.sendEmptyMessage(0);
		} else {
			timeHandler.removeMessages(0);
		}
	}

	private void refreshTime() {
		Calendar calendar = Calendar.getInstance();
		tvTime.setText(String.format("%d:%d:%d",
				calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)));
	}

	private Handler timeHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			refreshTime();
			if (getVisibility() == View.VISIBLE) {
				timeHandler.sendEmptyMessageDelayed(0, 1000);
			} else {

			}
		};

	};
}
