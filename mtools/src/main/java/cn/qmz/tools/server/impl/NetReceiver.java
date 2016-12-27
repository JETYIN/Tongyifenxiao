package cn.qmz.tools.server.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.List;

import cn.qmz.tools.server.INetEventHandle;

public class NetReceiver extends BroadcastReceiver {
	/**
	 * 枚举网络状态
	 * NET_NO：没有网络
	 * NET_2G:2g网络
	 * NET_3G：3g网络
	 * NET_4G：4g网络
	 * NET_WIFI：wifi
	 * NET_UNKNOWN：未知网络
	 */
	public static enum NetState{ NET_NO, NET_2G, NET_3G, NET_4G, NET_WIFI, NET_UNKNOWN };

	public static List<INetEventHandle> ehList = new ArrayList<INetEventHandle>();

	private NetState stateCode = NetState.NET_NO;

	@Override
	public void onReceive(Context context, Intent intent) {
		//向所有实现接口的activity传递消息
		for (INetEventHandle e : ehList) {
			//减少重复传递信息
//			NetState state = isConnected(context);
//			if(state != stateCode) {
				e.netState(isConnected(context));
//			}
		}
	}

	/**
	 * 判断当前是否网络连接
	 *
	 * @param context
	 * @return 状态码
	 */
	public NetState isConnected(Context context) {
		stateCode = NetState.NET_NO;
		ConnectivityManager cm = (ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if(ni != null && ni.isConnectedOrConnecting()) {
			switch (ni.getType()) {
				case ConnectivityManager.TYPE_WIFI:
					stateCode = NetState.NET_WIFI;
					break;
				case ConnectivityManager.TYPE_MOBILE:
					switch (ni.getSubtype()) {
						case TelephonyManager.NETWORK_TYPE_GPRS: //联通2g
						case TelephonyManager.NETWORK_TYPE_CDMA: //电信2g
						case TelephonyManager.NETWORK_TYPE_EDGE: //移动2g
						case TelephonyManager.NETWORK_TYPE_1xRTT:
						case TelephonyManager. NETWORK_TYPE_IDEN:
							stateCode = NetState.NET_2G;
							break;
						case TelephonyManager.NETWORK_TYPE_EVDO_A: //电信3g
						case TelephonyManager.NETWORK_TYPE_UMTS:
						case TelephonyManager.NETWORK_TYPE_EVDO_0:
						case TelephonyManager.NETWORK_TYPE_HSDPA:
						case TelephonyManager.NETWORK_TYPE_HSUPA:
						case TelephonyManager.NETWORK_TYPE_HSPA:
						case TelephonyManager.NETWORK_TYPE_EVDO_B:
						case TelephonyManager.NETWORK_TYPE_EHRPD:
						case TelephonyManager.NETWORK_TYPE_HSPAP:
							stateCode = NetState.NET_3G;
							break;
						case TelephonyManager.NETWORK_TYPE_LTE:
							stateCode = NetState.NET_4G;
							break;
						default:
							stateCode = NetState.NET_UNKNOWN;
					}

				default:
					stateCode = NetState.NET_UNKNOWN;
			}
		}
		return stateCode;
	}

}
