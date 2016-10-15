package com.time;

import javax.security.auth.PrivateCredentialPermission;

import com.tabhostdemo.R;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	String tab1 = "时钟";
	String tab2 = "闹钟";
	String tab3 = "计时器";
	String tab4 = "计时器";
	private StopWatchView stopWatchView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		TabHost tabHost = getTabHost();

		TabSpec tabSpec1 = tabHost.newTabSpec(tab1).setIndicator("时钟")
				.setContent(R.id.first);
		tabHost.addTab(tabSpec1);
		
		TabSpec tabSpec2 = tabHost.newTabSpec(tab2).setIndicator("闹钟")
				.setContent(R.id.second);
		tabHost.addTab(tabSpec2);
		
		TabSpec tabSpec3 = tabHost.newTabSpec(tab3).setIndicator("计时器")
				.setContent(R.id.tabTimer);
		tabHost.addTab(tabSpec3);
		TabSpec tabSpec4 = tabHost.newTabSpec(tab4).setIndicator("秒表")
				.setContent(R.id.tabStopWatch);
		tabHost.addTab(tabSpec4);
		stopWatchView = (StopWatchView) findViewById(R.id.tabStopWatch);
	}
	
	@Override
	protected void onDestroy() {
		
		stopWatchView.onDestory();
		
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
