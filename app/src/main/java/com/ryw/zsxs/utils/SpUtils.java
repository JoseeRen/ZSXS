package com.ryw.zsxs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by gui on 2017/6/8.
 */

public class SpUtils {
    private static final String NAMESPACE = "namespace";

    private static SharedPreferences sp;

    public static void putString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAMESPACE, Context.MODE_PRIVATE);

        }
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAMESPACE, Context.MODE_PRIVATE);
        }
        String s = sp.getString(key, "");
        return s;
    }

    public static void putInt(Context context, String key, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAMESPACE, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAMESPACE, Context.MODE_PRIVATE);
        }
        int b = sp.getInt(key, 0);
        return b;
    }

    public static void putBoolean(Context context, String key, boolean value) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAMESPACE, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAMESPACE, Context.MODE_PRIVATE);
        }
        boolean b = sp.getBoolean(key, false);
        return b;
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
            Toast.makeText(context, "请先打开WIfi或者请重新打开网络！", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}
