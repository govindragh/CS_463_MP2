package com.example.cs463mp2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private MonitorService s;
	/*
	 * private Handler mHandler = new Handler(); private long mStartRX = 0;
	 * private long mStartTX = 0;
	 */
	private int UID;
	public SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public String currentTimeStamp = null;
	
	public int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// int UID = 0;
		//final Button startButton = (Button) findViewById(R.id.startButton);
		final Button stopButton = (Button) findViewById(R.id.stopButton);
		final PackageManager pm = getPackageManager();
		List<ApplicationInfo> packages = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);
		for (ApplicationInfo packageInfo : packages) {
			if (packageInfo.packageName.equals("com.webmd.android")) {
				this.UID = packageInfo.uid;
			}
			Log.i("Check UID", "UID is: " + UID);

		}
		/*startButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO start monitoring traffic
				Toast.makeText(getApplicationContext(), Integer.toString(UID),
						Toast.LENGTH_LONG).show();
				
				
			}
		});*/
		stopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				s.setStop();
				/*currentTimeStamp = dateFormat.format(new Date());
				File root = android.os.Environment
						.getExternalStorageDirectory();
				File dir = new File(root.getAbsolutePath() + "/webMDTraffic");
				dir.mkdirs();
				File file = new File(dir, "test" + String.valueOf(i) + ".txt");
				if (file.exists()) {
					file.delete();
				}
				try {
					FileOutputStream f = new FileOutputStream(file);
					PrintWriter pw = new PrintWriter(f);
					pw.println(currentTimeStamp + "; "
							+ String.valueOf(s.getStartRxBytes()) + "; "
							+ String.valueOf(s.getStartTxBytes()) + "; "
							+ String.valueOf(-1) + "; "
							+ String.valueOf(-1));
					pw.flush();
					pw.close();
					f.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					Log.e("Error", "File Not found", e);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("Error", "IO ERROR", e);
				}
				i++;*/
			}
		});
	}
	public void startMonitoring(View view) {
		Intent i = new Intent(getBaseContext(), MonitorService.class);
		getBaseContext().startService(i);
	}
	protected void onResume() {
		super.onResume();
		Intent intent = new Intent(this, MonitorService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}

	protected void onPause() {
		super.onPause();
		unbindService(mConnection);
	}

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder binder) {
			MonitorService.MyBinder b = (MonitorService.MyBinder) binder;
			s = b.getService();
			Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT)
					.show();
			
		}

		public void onServiceDisconnected(ComponentName className) {
			s = null;
		}
	};

}
