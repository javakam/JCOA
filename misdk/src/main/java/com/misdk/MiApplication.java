package com.misdk;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/25 0025.
 */
public class MiApplication extends android.app.Application {
    /**
     * Activity容器
     */
    private List<Activity> activityList = new LinkedList<Activity>();

    /**
     * 开启严格模式（StrictMode）  调试检查很有帮助  2.3以后支持  onCreate中调用
     */
    private void openStrictMode() {
        int appFlags = getApplicationInfo().flags;
        if ((appFlags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            try {
                //Android 2.3及以上调用严苛模式
                Class sMode = Class.forName("android.os.StrictMode");
                Method enableDefaults = sMode.getMethod("enableDefaults");
                enableDefaults.invoke(null);
            } catch (Exception e) {
                // StrictMode not supported on this device, punt
                Log.v("StrictMode", "... not supported. Skipping...");
            }
        }
    }


//================================Activity管理==============================//

    /**
     * 添加Activity到容器中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 遍历所有Activity并finish
     */
    public void exit() {
        for (Activity activity : activityList) {
            if (activity != null)
                activity.finish();
        }
        System.exit(0);
//        android.os.Process.killProcess(android.os.Process.myPid());
    }
//================================输入法管理==============================//

    /**
     * 显示软键盘
     *
     * @param activity
     */
    public void showInputMethod(Activity activity) {
        InputMethodManager imm = (InputMethodManager) getSystemService(activity.INPUT_METHOD_SERVICE);
        if (!imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public void hideInputMethod(Activity activity) {
        InputMethodManager imm = ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE));
        if (imm != null && imm.isActive()) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 返回true，则表示输入法打开
     */
    public boolean isInputMethodOpenned(Activity activity) {
        InputMethodManager imm = (InputMethodManager) getSystemService(activity.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        return isOpen;
    }

    //===============================观察者模式===============================//

    // 实现整个APP观察者模式
    private Map<Integer, ObserverListener> observerListeners = new HashMap<Integer, ObserverListener>();

    // 实现整个app观察者模式,同一个action可以注册多个监听

    /**
     * 注册监听，不需要的时候要取消监听，可在onDestory()中取消
     *
     * @param action
     * @param listener
     */
    public void registerObserver(int action, ObserverListener listener) {
        if (listener != null) {
            observerListeners.put(action, listener);
        }
    }

    /**
     * 解除注册--注册必须解除,防止内存泄露
     *
     * @param action
     */
    public void unRegisterObserver(int action) {
        if (observerListeners.containsKey(action)) {
            observerListeners.remove(action);
        }
    }

    /**
     * 通知已经注册此action的监听去执行 ,action 必传，其他可传(null)
     *
     * @param action 需要传递的action要与注册的一样，
     * @param bundle 封装对象，
     * @param object 也可以传递自己封装的对象，
     */
    public void notifyDataChange(int action, Bundle bundle, Object object) {
        if (observerListeners.containsKey(action) && observerListeners.get(action) != null) {
            observerListeners.get(action).notifyChange(bundle, object);
        }
    }

    //==============================================================//
}
