package com.example.cs463mp2;

import java.text.SimpleDateFormat;

import android.app.Service;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class TrafficService extends Service {

	public long StartRxBytes = 0;
	public long tempRxBytes = 0;
	public long StartTxBytes = 0;
	public long tempTxBytes = 0;
	public long StartRxPackets = 0;
	public long tempRxPackets = 0;
	public long StartTxPackets = 0;
	public long tempTxPackets = 0;
	public int i = 0;
	public SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public String currentTimeStamp = null;
	Handler handler;
	public String uid = "10070";

	public void onStart(Intent intent, int startId) {
		// super.onStart(intent, startId);
		Toast.makeText(this, "Service running", Toast.LENGTH_SHORT).show();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				// Toast.makeText(TrafficService.this, "5 secs has passed",
				// Toast.LENGTH_SHORT).show();
				StartRxBytes = TrafficStats
						.getUidRxBytes(Integer.parseInt(uid));
				if (StartRxBytes != tempRxBytes) {
					Log.i("Monitor Traffic", "Starting to monitor traffic now");
					tempRxBytes = StartRxBytes;
					Log.i("RxBytes Change",
							"StartRxBytes is: " + String.valueOf(StartRxBytes));
				}
				StartTxBytes = TrafficStats
						.getUidTxBytes(Integer.parseInt(uid));
				if (StartTxBytes != tempTxBytes) {
					tempTxBytes = StartTxBytes;
					Log.i("TxBytes Change",
							"StartTxBytes is : " + String.valueOf(StartTxBytes));
				}
				/*StartRxPackets = TrafficStats.getUidRxPackets(Integer
						.parseInt(uid));
				StartTxPackets = TrafficStats.getUidTxPackets(Integer
						.parseInt(uid));*/
			}
		};
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {
						Thread.sleep(5000);
						handler.sendEmptyMessage(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * // private int result = Activity.RESULT_CANCELED; public static final
	 * String UID = "uid"; public static final String ACTION = "action"; public
	 * long StartRxBytes = 0; public long tempRxBytes = 0; public long
	 * StartTxBytes = 0; public long tempTxBytes = 0; public long StartRxPackets
	 * = 0; public long tempRxPackets = 0; public long StartTxPackets = 0;
	 * public long tempTxPackets = 0; public int i = 0; public SimpleDateFormat
	 * dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss"); public String
	 * currentTimeStamp = null;
	 * 
	 * public TrafficService() { super("TrafficService"); }
	 * 
	 * @SuppressLint("NewApi")
	 * 
	 * @Override protected void onHandleIntent(Intent intent) { String uid =
	 * intent.getStringExtra(UID); String action =
	 * intent.getStringExtra(ACTION); Log.i("Check if service started",
	 * "Service has Started"); Log.i("Service Action", "Action is: " + action);
	 * if (action.equals("start")) { //while (1 == 1) { StartRxBytes =
	 * TrafficStats .getUidRxBytes(Integer.parseInt(uid)); if (StartRxBytes !=
	 * tempRxBytes) { Log.i("Monitor Traffic",
	 * "Starting to monitor traffic now"); tempRxBytes = StartRxBytes;
	 * Log.i("RxBytes Change", "StartRxBytes is: " +
	 * String.valueOf(StartRxBytes)); } StartTxBytes = TrafficStats
	 * .getUidTxBytes(Integer.parseInt(uid)); if (StartTxBytes != tempTxBytes) {
	 * tempTxBytes = StartTxBytes; Log.i("TxBytes Change", "StartTxBytes is : "
	 * + String.valueOf(StartTxBytes)); } StartRxPackets =
	 * TrafficStats.getUidRxPackets(Integer .parseInt(uid)); StartTxPackets =
	 * TrafficStats.getUidTxPackets(Integer .parseInt(uid));
	 * 
	 * // } Log.i("Test", "Skipped start action process"); //} } if
	 * (action.equals("stop")) {
	 * 
	 * currentTimeStamp = dateFormat.format(new Date()); //
	 * generateFile("test.txt");
	 * 
	 * File root = android.os.Environment.getExternalStorageDirectory(); File
	 * dir = new File(root.getAbsolutePath() + "/webMDTraffic"); dir.mkdirs();
	 * File file = new File(dir, "test" + String.valueOf(i) + ".txt"); if
	 * (file.exists()) { file.delete(); } try { FileOutputStream f = new
	 * FileOutputStream(file); PrintWriter pw = new PrintWriter(f);
	 * pw.println(currentTimeStamp + "; " + String.valueOf(StartRxBytes) + "; "
	 * + String.valueOf(StartTxBytes) + "; " + String.valueOf(StartRxPackets) +
	 * "; " + String.valueOf(StartTxPackets)); pw.flush(); pw.close();
	 * f.close(); } catch (FileNotFoundException e) { e.printStackTrace();
	 * Log.e("Error", "File Not found", e); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); Log.e("Error",
	 * "IO ERROR", e); } //i++;
	 * 
	 * } }
	 * 
	 * @Override public IBinder onBind(Intent intent) { return null; }
	 */

}
