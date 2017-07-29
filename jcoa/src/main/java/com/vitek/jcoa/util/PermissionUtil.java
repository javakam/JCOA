package com.vitek.jcoa.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * 针对6.0之后的动态权限申请问题---替换为：
 * requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, YOUR_REQUEST_CODE);
 * <p>
 * Created by javakam on 2017/5/24 0024.
 */
@Deprecated()
public class PermissionUtil {

    /**
     * 文件权限
     */
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 0x555;
    /**
     * 语音录制权限
     */
    private static final int RECORD_AUDIO_REQUEST_CODE = 0x666;

    public static void requestWriteExternalStoragePermission(Activity activity) {
        requestPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
    }

    public static void requestRecordAudioPermission(Activity activity) {
        requestPermission(activity, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO_REQUEST_CODE);
    }

    private static void requestPermission(Activity activity, String permission, int code) {
        //动态设置权限（他妈的6.0）
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(activity, new String[]{permission},//可以一次申请多个！！！
                    code);
        }
    }
}
