package com.time;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("ƒ÷÷”÷¥––¡À");
		AlarmManager aManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		aManager.cancel(PendingIntent.getBroadcast(context, getResultCode(), new Intent(context, AlarmReceiver.class), 0));
		
		Intent it = new Intent(context, PlayAlarmActivity.class);
		it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(it);
	}

}
