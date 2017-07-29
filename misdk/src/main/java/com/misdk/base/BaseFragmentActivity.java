package com.misdk.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.misdk.util.NetUtil;
import com.misdk.util.ToastUtil;
import com.misdk.views.SystemBarTintManager;
import com.misdk.views.parallaxbacklayout.ParallaxActivityBase;


/**
 * Created by kam on 2016/6/25.
 */
public abstract class BaseFragmentActivity extends ParallaxActivityBase {

    public FragmentManager mFragmentManager;
    public LayoutInflater mInflater;

    public void appSetting() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appSetting();
        mFragmentManager = getSupportFragmentManager();
        mInflater = getLayoutInflater();
        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    //设置系统状态栏
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setTranslucentStatus(int statusColor) {
        //判断版本是4.4以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            //打开系统状态栏控制
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(statusColor);//设置背景

//            View layout_project_list = findViewById(R.id.layout_project_list);//
            //设置系统栏需要的内偏移
//            layout_project_list.setPadding(0, getStatusHeight(this), 0, 0);
        }
    }


//==============================重新定义Acticity生命周期===============================//

    /**
     * 初始化变量，包括Intent带的数据和Acticity内的变量
     */
    protected abstract void initVariables();

    /**
     * 加载layout布局文件，初始化控件，为控件挂上事件方法
     *
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);


    /**
     * 载入数据
     */
    protected abstract void loadData();


//=============================Handler消息管理================================//

    /**
     * 覆写此方法，处理Handler发来的消息
     *
     * @param msg
     */
    protected void onMsgObtain(Message msg) {
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            onMsgObtain(msg);
        }
    };

    /**
     * 获取Handler对象
     *
     * @return handler
     */
    public Handler getHandler() {
        return handler;
    }

//=============================吐司================================//

    public  void showShortToast(String text) {
        ToastUtil.shortToast(this,text);
    }


    public void showLongToast(String text) {
        ToastUtil.longToast(this,text);
    }

//===============================Intent跳转==============================//

    /**
     * Intent跳转
     *
     * @param context
     * @param cla
     */
    public void goIntent(Context context, Class<?> cla) {
        Intent intent = new Intent(context, cla);
        context.startActivity(intent);
    }

    //===============================联网==============================//

    /**
     * 检测本地网络是否可用
     *
     * @param context
     * @return null 有网   "无网络"  无网
     */
    public String isNetAvaliable(Context context) {
        if (!NetUtil.isAvailable(context)) {
            return "无网络";
        }
        return null;
    }
}
