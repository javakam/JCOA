package com.misdk.util;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;

/**
 * SDCardUtil类用于检测sd卡的状态和大小；
 *
 * @author nieshuting
 * @version 0.1
 */
public class SDCardUtil {

    /**
     * 检测SDCard是否可用；
     *
     * @return true 可用，false：不可用
     */
    public static boolean detectAvailable() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // sdcard 已插入并可读写；
            if (Environment.getExternalStorageDirectory().canWrite()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测是否有足够的容量；
     *
     * @param minSize 最小大小；MB
     * @return 空间不足为false，否则为true;
     */
    public static boolean detectStorage(int minSize, String path) {
        if (minSize > 0 && !TextUtils.isEmpty(path)) {
            // 取得SDCard当前的状态
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                // 取得sdcard文件路径
                StatFs statfs = new StatFs(path);
                // 获取SDCard上每个block的SIZE
                long blocSize = statfs.getBlockSize();
                // 获取可供程序使用的Block的数量
                long availaBlock = statfs.getAvailableBlocks();
                // 计算 SDCard 剩余大小MB
                long freeSize = availaBlock * blocSize / 1024 / 1024;
                if (freeSize > minSize) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取指定目录下的空间大小
     *
     * @param path 路径
     * @return 返回大小MB
     */
    public static long getStorage(String path) {
        if (!TextUtils.isEmpty(path)) {
            // 取得SDCard当前的状态
            String sDcString = Environment.getExternalStorageState();
            if (sDcString.equals(Environment.MEDIA_MOUNTED)) {
                // 取得sdcard文件路径
                StatFs statfs = new StatFs(path);
                // 获取SDCard上每个block的SIZE
                long nBlocSize = statfs.getBlockSize();
                // 获取可供程序使用的Block的数量
                long nAvailaBlock = statfs.getAvailableBlocks();
                // 计算 SDCard 剩余大小MB
                long nSDFreeSize = nAvailaBlock * nBlocSize / 1024 / 1024;
                return nSDFreeSize;
            }
        }
        return 0;
    }

    /**
     * 获取声音路径
     *
     * @param voicePath Contants.AUDIO_PATH
     * @return
     */
    public static String getVoicePath(String voicePath) {
        File dir = new File(voicePath);
        if (!dir.exists())
            dir.mkdirs();// 创建文件夹
        // String fileName = System.currentTimeMillis() + ".amr";
        String fileName = TimeUtil.longToDate6(System.currentTimeMillis()) + ".amr";
        File file = new File(voicePath, fileName);
//		Log.i("123","get the audio path is ----->" + file.getAbsolutePath());
        return file.getAbsolutePath();
    }
}
