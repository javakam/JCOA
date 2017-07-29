package com.misdk.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * 一些工具<p>
 * Created by javakam on 2016-8-5 11:09:12 .
 */
public class Utils {

    /**
     * 判断网络连接状态
     *
     * @param context
     * @return true 网络正处于连接状态，false 网络状态不可用
     */
    public static boolean checkNetInfo(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }


    /**
     * 将InputStream转换成String
     *
     * @param in
     * @return
     * @throws Exception
     */
    public static String InputStreamTOString(InputStream in) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[2 * 1024];
        int count = -1;
        while ((count = in.read(data)) != -1) {
            outStream.write(data, 0, count);
        }
        data = null;
        return new String(outStream.toByteArray(), "UTF-8");
    }

    /**
     * 将浮点数精确到小数点后2位
     *
     * @param t
     * @return
     */
    public static float getAccurateTwoPlaces(float t) {
        float ff;
        BigDecimal bigDecimal = new BigDecimal(t);
        ff = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN).floatValue();
        return ff;
    }

    private static long lastClickTime;

    /**
     * 防止连续点击控件
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 2000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 将日期字符串转成毫秒数
     *
     * @param date 日期字符串，格式为 2014-03-28 17:00:00
     * @return
     */
    public static long dateToLong(String date) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return c.getTimeInMillis();
    }

    /**
     * 获取设备mac地址(使用Linux底层命令)
     *
     * @return
     */
    public static String getMac() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }

    /**
     * 获取设备mac地址(使用googleAPI)
     *
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) (context
                .getSystemService(Context.WIFI_SERVICE));
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 隐藏webview的缩放按钮 适用于3.0和以后
     *
     * @param view
     * @param args
     */
    public static void setZoomControlGoneX(WebSettings view, Object[] args) {
        Class<?> classType = view.getClass();
        try {
            Class<?>[] argsClass = new Class[args.length];

            for (int i = 0, j = args.length; i < j; i++) {
                argsClass[i] = args[i].getClass();
            }
            Method[] ms = classType.getMethods();
            for (int i = 0; i < ms.length; i++) {
                if (ms[i].getName().equals("setDisplayZoomControls")) {
                    try {
                        ms[i].invoke(view, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                // Log.e("test", ">>"+ms[i].getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 隐藏webview的缩放按钮 适用于3.0以前
     *
     * @param view
     */
    public static void setZoomControlGone(View view) {
        Class<?> classType;
        Field field;
        try {
            classType = WebView.class;
            field = classType.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(
                    view);
            mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
            try {
                field.set(view, mZoomButtonsController);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9(现在有17开头)
		 */
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 此方法作用，只限于本工程，下载地址回传解析，封装成http下载地址 <br>
     * result 格式 --{"errorCode":"1","url":
     * "/DownLoad/5b0a6c8b-e937-4ca5-bfd6-.zip"}</br>
     *
     * @return String[] , 0 : url, 1:filename
     */
    public static String[] getDownLoadUrl(String result) {
        String url = null;
        String fileName = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has("url")) {
                url = jsonObject.getString("url");
                fileName = url.substring(url.lastIndexOf("/") + 1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new String[]{url, fileName};
    }

    /***
     * @param result
     * @return
     */
    public static String[] getDownLoadUrlFromString(String result) {
        // String url = null;
        String fileName = null;
        // url = MobileEduService.host + result;
        fileName = result.substring(result.lastIndexOf("/") + 1);
        return new String[]{result, fileName};
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful. If a
     * deletion fails, the method stops attempting to delete and returns
     * "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    /**
     * 获取MD5加密的字符串
     *
     * @param timeStamp
     * @return
     */
    public static String md5Encrypt(String userName, String timeStamp) {
        String str = userName + "50216E2B-D885-419E-9D7C-A317F30140A6"
                + timeStamp;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 获取MD5加密的字符串
     *
     * @param str
     * @return
     */
    public static String md5Encrypt(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /*
     * @author sichard
     *
     * @category 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     *
     * @return
     */
    public static final boolean ping1() {

        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec(" ping -c 2 -w 100 " + ip);
            // ping网址3次
            // 读取ping的内容，可以不加
            // InputStream input = p.getInputStream();
            // BufferedReader in = new BufferedReader(new
            // InputStreamReader(input));
            // StringBuffer stringBuffer = new StringBuffer();
            // String content = "";
            // while ((content = in.readLine()) != null) {
            // stringBuffer.append(content);
            // }
            // Log.d("------ping-----", "result content : " +
            // stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static final boolean ping2() {
        boolean is = false;

        try {
            int status = executeCommand("www.baidu.com", 5000);
            if (status == 0) {
//				result = "success";
                is = true;
            } else {
//				result = "failed";
                is = false;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return is;
    }

    public static int executeCommand(final String command, final long timeout)
            throws IOException, InterruptedException, TimeoutException {
        Process process = Runtime.getRuntime().exec(
                " ping -c 3 -w 100 " + command);
        PingWorker worker = new PingWorker(process);
        worker.start();
        try {
            worker.join(timeout);
            if (worker.exit != null) {
                return worker.exit;
            } else {
                throw new TimeoutException();
            }
        } catch (InterruptedException ex) {
            worker.interrupt();
            Thread.currentThread().interrupt();
            throw ex;
        } finally {
            process.destroy();
        }
    }

    public static class PingWorker extends Thread {
        private final Process process;
        private Integer exit;

        public PingWorker(Process process) {
            this.process = process;
        }

        @Override
        public void run() {
            try {
                exit = process.waitFor();
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 判断微信是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

}
