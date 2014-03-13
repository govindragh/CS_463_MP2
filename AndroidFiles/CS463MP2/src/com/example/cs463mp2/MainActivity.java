package com.example.cs463mp2;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Handler mHandler = new Handler();
	private long mStartRX = 0;
	private long mStartTX = 0;
    private int UID;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //int UID = 0;
        final Button startButton = (Button) findViewById(R.id.startButton);
        final Button stopButton = (Button) findViewById(R.id.stopButton);
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
        	if(packageInfo.packageName.equals("com.webmd.android")) {
        		this.UID = packageInfo.uid;
        	}
        	Log.i("Check UID", "UID is: " + UID);
        	
        }
        startButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO start monitoring traffic
				Toast.makeText(getApplicationContext(), Integer.toString(UID), Toast.LENGTH_LONG).show();
				Intent i = new Intent(MainActivity.this, TrafficService.class);
				i.putExtra(TrafficService.UID, Integer.toString(UID));
				i.putExtra(TrafficService.ACTION, "start");
				startService(i);
				
			}
		});
        stopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO stop monitoring traffic and save file
				Intent i = new Intent(getApplicationContext(), TrafficService.class);
				i.putExtra(TrafficService.UID, Integer.toString(UID));
				i.putExtra(TrafficService.ACTION, "stop");
				
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
