package com.example.phpor;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import java.util.*;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by junjie on 14-1-26.
 * 参考资料：
 * sysinfo: http://blog.csdn.net/gumanren/article/details/6209237
 * hashmap: http://www.cnblogs.com/meieiem/archive/2011/11/02/2233041.html
 * 学到的：
 *  1.  a对于需要的权限如果不预先定义，直接获取信息会失败的，如何动态获取权限呢？
 */
public class SysInfoActivity extends Activity {
	private MyHandler myHandler;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sysinfo);

		final TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		myHandler = new MyHandler();

		Log.d("phpor", "I am here");
		(new Thread(new Runnable() {
			@Override
			public void run() {
				Log.d("phpor", "start new thread");
				String cell_location = "";
				/*
			   * 电话方位：
			   * 这个需要异步获取
			   */

				CellLocation cl = tm.getCellLocation();//CellLocation
				if (cl !=  null) {
					cell_location = cl.toString();
				}
				Message msg = new Message();
				msg.what = 1;
				msg.obj = cell_location;
				myHandler.sendMessage(msg);
				Log.d("phpor", "end new thread");
			}
		})).start();
		String info = "";
		HashMap<String, String> tel_info = new HashMap<String, String>();

		/*
	     * 电话状态：
	     * 1.tm.CALL_STATE_IDLE=0     无活动
	     * 2.tm.CALL_STATE_RINGING=1  响铃
	     * 3.tm.CALL_STATE_OFFHOOK=2  摘机
	     */
		String callstate = "";
		switch (tm.getCallState()) {
			case TelephonyManager.CALL_STATE_IDLE:
				callstate = "CALL_STATE_IDLE: 空闲";
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				callstate = "CALL_STATE_RINGING: 响铃"; //这个测试结果也不太对
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				callstate = "CALL_STATE_OFFHOOK: 摘机"; //这个解释的可能不太对
				break;
		}

		tel_info.put("电话状态", callstate);

		tel_info.put("IMEI", tm.getDeviceId());
		tel_info.put("软件版本号" , tm.getDeviceSoftwareVersion());
		tel_info.put("手机号", tm.getLine1Number());

	  /*
	   * 当前使用的网络类型：
	   * 例如： NETWORK_TYPE_UNKNOWN  网络类型未知  0
	     NETWORK_TYPE_GPRS     GPRS网络  1
	     NETWORK_TYPE_EDGE     EDGE网络  2
	     NETWORK_TYPE_UMTS     UMTS网络  3
	     NETWORK_TYPE_HSDPA    HSDPA网络  8
	     NETWORK_TYPE_HSUPA    HSUPA网络  9
	     NETWORK_TYPE_HSPA     HSPA网络  10
	     NETWORK_TYPE_CDMA     CDMA网络,IS95A 或 IS95B.  4
	     NETWORK_TYPE_EVDO_0   EVDO网络, revision 0.  5
	     NETWORK_TYPE_EVDO_A   EVDO网络, revision A.  6
	     NETWORK_TYPE_1xRTT    1xRTT网络  7
	   */
		HashMap<Integer, String> network = new HashMap<Integer, String>();
		network.put(TelephonyManager.NETWORK_TYPE_UNKNOWN, "NETWORK_TYPE_UNKNOWN");
		network.put(TelephonyManager.NETWORK_TYPE_GPRS, "NETWORK_TYPE_GPRS");
		network.put(TelephonyManager.NETWORK_TYPE_EDGE, "NETWORK_TYPE_EDGE");
		network.put(TelephonyManager.NETWORK_TYPE_UMTS, "NETWORK_TYPE_UMTS");
		network.put(TelephonyManager.NETWORK_TYPE_HSDPA, "NETWORK_TYPE_HSDPA");
		network.put(TelephonyManager.NETWORK_TYPE_HSUPA, "NETWORK_TYPE_HSUPA");
		network.put(TelephonyManager.NETWORK_TYPE_HSPA, "NETWORK_TYPE_HSPA");
		network.put(TelephonyManager.NETWORK_TYPE_CDMA, "NETWORK_TYPE_CDMA");
		network.put(TelephonyManager.NETWORK_TYPE_EVDO_0, "NETWORK_TYPE_EVDO_0");
		network.put(TelephonyManager.NETWORK_TYPE_EVDO_A, "NETWORK_TYPE_EVDO_A");
		network.put(TelephonyManager.NETWORK_TYPE_1xRTT, "NETWORK_TYPE_1xRTT");

		tel_info.put("网络类型" , network.get(tm.getNetworkType()));
		/**
		 * 手机类型：
		 * 例如： PHONE_TYPE_NONE  无信号
		 * PHONE_TYPE_GSM   GSM信号
		 * PHONE_TYPE_CDMA  CDMA信号
		 */
		String phone_type = "";
		switch (tm.getPhoneType()) {
			case TelephonyManager.PHONE_TYPE_NONE:
				phone_type = "PHONE_TYPE_NONE: 无信号";
				break;
			case TelephonyManager.PHONE_TYPE_GSM:
				phone_type = "PHONE_TYPE_GSM: GSM";
				break;
			case TelephonyManager.PHONE_TYPE_CDMA:
				phone_type = "PHONE_TYPE_CDMA: CDMA";
				break;
		}
		tel_info.put("电话类型", phone_type);

		/*
	   * 唯一的用户ID：
	   * 例如：IMSI(国际移动用户识别码) for a GSM phone.
	   * 需要权限：READ_PHONE_STATE
	   */
		tel_info.put("唯一用户ID", tm.getSubscriberId());
		/*
		   * 是否漫游:
		   * (在GSM用途下)   todo: 什么意思？
		   */
		tel_info.put("漫游", (tm.isNetworkRoaming()?"是":"否"));

		Iterator iterator = tel_info.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry item = (Map.Entry)iterator.next();
			info += item.getKey() +":" + item.getValue() + "\n";
		}
		((TextView) findViewById(R.id.textView)).setText(info);
	}



	private class MyHandler extends Handler{

		@Override
		public void dispatchMessage(Message msg) {
			Log.d("phpor", "in dispatch");
			switch (msg.what) {
				case 1:
					Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_LONG).show();
					break;
			}
		}

	}
}