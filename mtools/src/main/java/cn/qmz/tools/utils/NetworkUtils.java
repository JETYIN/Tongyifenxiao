package cn.qmz.tools.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class NetworkUtils {
    //检测wifi是否存在
    public boolean GetWifiState(Context context) {
        try {
            WifiManager wifi;
            wifi = (WifiManager) (context.getSystemService(Context.WIFI_SERVICE));
            return wifi.isWifiEnabled();//返回true时表示存在，
        } catch (Exception e) {
            return false;
        }
    }

    //判断当前网络状态
    public static boolean GetInternetstate(Context context) {
        ConnectivityManager mag = (ConnectivityManager) (context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo info = mag.getActiveNetworkInfo();
        if (info == null || info.equals("")) {
            return false;
        } else return true;
    }


    //判断当前网络状态
    public boolean is_3Gstate(Context context) {
        //如果info==null时表示当前没用网络可用
        //如果wifi不存在，当前网络又可用，说明现在在使用3g或是gprs上网
        if (GetWifiState(context) == false && GetInternetstate(context) == true) {
            return true;
        }
        return false;
    }

}
