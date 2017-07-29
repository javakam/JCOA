package com.misdk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

/**
 * 网络链接工具类<p>
 * Created by javakam on 2016/7/8.
 */
public class NetUtil {
    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断网络是否可用
     *
     * @param context 上下文
     * @return
     */
    public static boolean isAvailable(Context context) {
        return getNetType(context) != NetType.UNNET;
    }

    /**
     * 判断WiFi是否可用
     *
     * @param context 上下文
     * @return
     */
    public static boolean isWiFiEnabled(Context context) {
        WifiManager manager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        if (manager == null)
            return false;
        else
            return manager.isWifiEnabled();
    }

    /**
     * 判断是否是CMWAP网络
     *
     * @param context 上下文
     * @return
     */
    public static boolean isCMWAPMobile(Context context) {
        return getNetType(context) == NetType.CMWAP;
    }

    /**
     * 判断是否是CMNET网络
     *
     * @param context 上下文
     * @return
     */
    public static boolean isCMNETMobile(Context context) {
        return getNetType(context) == NetType.CMNET;
    }

    /**
     * 判断是否是WiFi网络
     *
     * @param context 上下文
     * @return
     */
    public static boolean isWiFi(Context context) {
        return getNetType(context) == NetType.WIFI;
    }

    /**
     * 判断是否是3G/2G网络
     *
     * @param context 上下文
     * @return
     */
    public static boolean isMobile(Context context) {
        NetType type = getNetType(context);
        return (type == NetType.CMNET || type == NetType.CMWAP);
    }

    /**
     * 获取网络信息
     *
     * @param context 上下文
     * @return
     */
    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return null;
        }
        return manager.getActiveNetworkInfo();
    }

    /**
     * 获取网络类型
     *
     * @param context 上下文
     * @return 类型
     */
    public static NetType getNetType(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        if (networkInfo != null) {
            if (networkInfo.isAvailable()) {
                int type = networkInfo.getType();
                if (type == ConnectivityManager.TYPE_WIFI) {
                    return NetType.WIFI;
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    String info = networkInfo.getExtraInfo();
                    if (!TextUtils.isEmpty(info)) {
                        if (info.toLowerCase().equals("cmnet")) {
                            return NetType.CMNET;
                        } else if (info.toLowerCase().equals("cmwap")) {
                            return NetType.CMWAP;
                        } else {
                            return NetType.UNKNOW;
                        }
                    } else {
                        return NetType.UNKNOW;
                    }
                } else {
                    return NetType.UNKNOW;
                }
            }
        }
        return NetType.UNNET;
    }

    /**
     * 网络类型
     */
    private enum NetType {
        /**
         * 无网络
         */
        UNNET,
        /**
         * 无线网络
         */
        WIFI,
        /**
         * cmwap网络
         */
        CMWAP,
        /**
         * cmnet网络
         */
        CMNET,
        /**
         * 未知
         */
        UNKNOW
    }
}
