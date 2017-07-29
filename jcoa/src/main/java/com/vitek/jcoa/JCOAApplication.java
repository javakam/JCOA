package com.vitek.jcoa;

import android.content.Context;

import com.misdk.MiApplication;
import com.misdk.net.VolleyUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by javakam on 2017/5/16 0016.
 */
public class JCOAApplication extends MiApplication {
    public static final int WHAT_SHOW_PROGRESS = 9999;
    public static final int WHAT_CLOSE_PROGRESS = 9998;
    public static final int WHAT_SHOW_HORIZONTAL_PROGRESS = 9997;
    public static final int WHAT_UPDATE_PROGRESS = 9996;

    //-----------------===============更新==============------------------//

    //更新列表
    public static final int UPDATE_LOG = 0x980;//工作记录
    public static final int UPDATE_DRAFT = 0x981;
    public static final int UPDATE_RETURNLOG = 0x982;
    public static final int UPDATE_DOCUMENT_APPROVAL = 0x992;//待办公文
    public static final int UPDATE_OFFICE_FILING = 0x993;//公文归档

    public static final int UPDATE_NOHANDLE = 0x901;//更新未处理的公文
    public static final int UPDATE_NOFILE = 0x902;//更新未归档的公文

    private Context mContext;
    private static JCOAApplication instance = new JCOAApplication();
    //开启单线程
    private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    public static JCOAApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        //LeakCanary  --- 暂时。。。
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//         This process is dedicated to LeakCanary for heap analysis.
//         You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        // Normal app init code...

        VolleyUtil.initialize(mContext);

        //JPush
//        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);            // 初始化 JPush

        //开启严格模式（StrictMode）
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().
//                detectDiskWrites().detectNetwork().penaltyLog().build());
    }

    /**
     * 单线程使用
     *
     * @return
     */
    public static ExecutorService getSingleThread() {
        return singleThreadExecutor;
    }
}
/*
 *   ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 *     ┃　　　┃
 *     ┃　　　┃
 *     ┃　　　┗━━━┓
 *     ┃　　　　　　　┣┓
 *     ┃　　　　　　　┏┛
 *     ┗┓┓┏━┳┓┏┛
 *       ┃┫┫　┃┫┫
 *       ┗┻┛　┗┻┛
 *        神兽保佑
 *        代码无BUG!
 */