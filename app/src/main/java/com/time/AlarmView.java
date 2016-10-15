package com.time;

import java.util.Calendar;

import com.tabhostdemo.R;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

public class AlarmView extends LinearLayout {
	private Button btnAddAlarmButton;
	private ListView lvAlarmList;
	private ArrayAdapter<AlarmData> adapter;
	private AlarmManager alarmManager;
	private static final String KEY_ALARM_LIST = "alarmlist";

	@SuppressLint("NewApi")
	public AlarmView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public AlarmView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}

	public AlarmView(Context context) {
		super(context);

		init();
	}

	private void init() {
		alarmManager = (AlarmManager) getContext().getSystemService(
				Context.ALARM_SERVICE);
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		btnAddAlarmButton = (Button) findViewById(R.id.btnAddAlarm);
		lvAlarmList = (ListView) findViewById(R.id.lv);
		btnAddAlarmButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addAlarm();
			}
		});

		adapter = new ArrayAdapter<AlarmView.AlarmData>(getContext(),
				android.R.layout.simple_expandable_list_item_1);

		lvAlarmList.setAdapter(adapter);

		readSaveAlarmList();
		// adapter.add(new AlarmData(System.currentTimeMillis()));
		lvAlarmList
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, final int position, long arg3) {
						new AlertDialog.Builder(getContext())
								.setTitle("操作选项")
								.setItems(new CharSequence[] { "删除" },
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												switch (which) {
												case 0:
													deleteAlarm(position);
													break;

												default:
													break;
												}

											}
										}).setNegativeButton("取消", null).show();
						return true;
					}
				});
	}

	private void deleteAlarm(int position) {
		AlarmData alarmData = adapter.getItem(position);

		adapter.remove(alarmData);
		saveAlarmList();
		alarmManager.cancel(PendingIntent.getBroadcast(getContext(),
				alarmData.getId(),
				new Intent(getContext(), AlarmReceiver.class), 0));
	}

	private void addAlarm() {

		Calendar cal = Calendar.getInstance();

		new TimePickerDialog(getContext(),
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						Calendar calendar = Calendar.getInstance();
						calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
						calendar.set(Calendar.MINUTE, minute);
						calendar.set(calendar.SECOND, 0);
						calendar.set(calendar.MILLISECOND, 0);

						Calendar currentTime = Calendar.getInstance();
						if (calendar.getTimeInMillis() <= currentTime
								.getTimeInMillis()) {
							calendar.setTimeInMillis(calendar.getTimeInMillis()
									+ 24 * 60 * 60 * 1000);
						}
						AlarmData aData = new AlarmData(calendar
								.getTimeInMillis());
						adapter.add(aData);
						alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
								aData.getTime(), 5 * 60 * 1000, PendingIntent
										.getBroadcast(getContext(), (int) aData
												.getId(), new Intent(
												getContext(),
												AlarmReceiver.class), 0));
						saveAlarmList();
					}
				}, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
				true) {
			@Override
			protected void onStop() {
				// super.onStop();
			}
		}.show();

	}

	private void saveAlarmList() {
		Editor editor = getContext().getSharedPreferences(
				AlarmView.class.getName(), Context.MODE_PRIVATE).edit();
		StringBuffer sBuffer = new StringBuffer();

		for (int i = 0; i < adapter.getCount(); i++) {
			sBuffer.append(adapter.getItem(i).getTime()).append(",");

		}
		if (sBuffer.length() > 1) {
			String content = sBuffer.toString().substring(0,
					sBuffer.length() - 1);

			editor.putString(KEY_ALARM_LIST, content);

			System.out.println(content);
		} else {
			editor.putString(KEY_ALARM_LIST, null);
		}

		editor.commit();
	}

	private void readSaveAlarmList() {
		SharedPreferences sPreferences = getContext().getSharedPreferences(
				AlarmView.class.getName(), Context.MODE_PRIVATE);

		String contentString = sPreferences.getString(KEY_ALARM_LIST, null);

		if (contentString != null) {
			String[] timeStrings = contentString.split(",");
			for (String string : timeStrings) {
				adapter.add(new AlarmData(Long.parseLong(string)));
			}
		}

	}

	private static class AlarmData {

		private long time = 0;
		private String timeLabel = "";
		private Calendar date;

		public AlarmData(long time) {
			this.time = time;
			date = Calendar.getInstance();
			date.setTimeInMillis(time);
			timeLabel = String.format("%d月%d日 %d:%d",
					date.get(Calendar.MONTH) + 1,
					date.get(Calendar.DAY_OF_MONTH),
					date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
		}

		public long getTime() {
			return time;
		}

		public String getTimeLabel() {
			return timeLabel;
		}

		public int getId() {
			return (int) (getTime() / 1000 / 60);
		}

		@Override
		public String toString() {
			return timeLabel;
		}
	}
}
